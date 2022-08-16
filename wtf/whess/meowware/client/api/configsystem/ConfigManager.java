package wtf.whess.meowware.client.api.configsystem;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ConfigManager {

    private final File CONFIGS_PATH = new File("/MeowWare/cfg/".replace("/", File.separator));

    @Getter @Setter
    private Config currentConfig;

    @Getter
    private final List<Config> configs =  new ArrayList<>();

    private boolean isConfigFile(File file) {
        return file.isFile() && file.getName().endsWith(".cfg");
    }

    public void load(String name) {
        for (Config config : configs)
            if (config.getName().equals(name))
                config.load();
    }

    public void save(String name) {
        for (Config config : configs)
            if (config.getName().equals(name))
                config.save();
    }

    public void delete(String name) {
        for (Config config : configs)
            if (config.getName().equals(name))
                config.delete();
    }

    public void update() {
        if (!CONFIGS_PATH.exists())
            if (!CONFIGS_PATH.mkdirs())
                throw new RuntimeException();
        try {
            configs.clear();
            Gson gson = new Gson();

            for (File configFile : Objects.requireNonNull(CONFIGS_PATH.listFiles(this::isConfigFile))) {
                Reader reader = new FileReader(configFile);

                Map <?, ?> data = gson.fromJson(reader, Map.class);

                if (data == null || data.isEmpty())
                    continue;

                configs.add(new Config(String.valueOf(data.get("name")), configFile));
                reader.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void create(String name) {
        Config config = new Config(name, new File(CONFIGS_PATH, name.toLowerCase() + ".cfg"));
        configs.add(config);
        config.save();
    }

}
