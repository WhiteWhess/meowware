package wtf.whess.meowware.client.api.configsystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import wtf.whess.meowware.MeowWare;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.Setting;
import wtf.whess.meowware.client.main.settings.impl.BooleanSetting;
import wtf.whess.meowware.client.main.settings.impl.ModeSetting;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;

import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public final class Config {

    @Getter
    private String name;
    private final File file;

    public Config(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public void load() {
        Gson gson = new Gson();

        if(!file.exists())
            save();

        try {
            Reader reader = new FileReader(file);

            Map<String, Object> data = gson.fromJson(reader, Map.class);

            name = String.valueOf(data.get("name"));

            Map<String, Object> modules = (Map<String, Object>) data.get("functions");

            for (Function function : MeowWare.getInstance().getFunctionManager().getFunctions()) {
                if (!modules.containsKey(function.getName()))
                    continue;

                Map<String, Object> moduleData = (Map<String, Object>) modules.get(function.getName());

                function.setToggled((boolean) moduleData.get("toggled"));
                function.setBind((int) ((double) moduleData.get("keybind")));

                Map<String, Object> settings = (Map<String, Object>) moduleData.get("settings");

                for (Setting setting : function.getSettings()) {
                    if (!settings.containsKey(setting.getName()))
                        continue;

                    if (setting instanceof BooleanSetting)
                        ((BooleanSetting) setting).setToggled((boolean) settings.get(setting.getName()));
                    if (setting instanceof ModeSetting)
                        ((ModeSetting) setting).setMode(String.valueOf(settings.get(setting.getName())));
                    if (setting instanceof NumberSetting)
                        ((NumberSetting) setting).setValue((double) settings.get(setting.getName()));
                }

            }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    public void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (!file.exists())
            try {
                if (!file.createNewFile())
                    return;
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        Map<String, Object> data = new HashMap<>();

        data.put("name", name);

        Map<String, Object> functions = new HashMap<>();

        for (Function module : MeowWare.getInstance().getFunctionManager().getFunctions()) {
            Map<String, Object> moduleData = new HashMap<>();
            moduleData.put("toggled", module.isToggled());
            moduleData.put("keybind", module.getBind());

            Map<String, Object> settingsData = new HashMap<>();

            for (Setting setting : module.getSettings()) {
                if (setting instanceof BooleanSetting)
                    settingsData.put(setting.getName(), ((BooleanSetting) setting).isToggled());
                if (setting instanceof ModeSetting)
                    settingsData.put(setting.getName(), ((ModeSetting) setting).getMode());
                if (setting instanceof NumberSetting)
                    settingsData.put(setting.getName(), ((NumberSetting) setting).getValue());
            }

            moduleData.put("settings", settingsData);
            functions.put(module.getName(), moduleData);
        }

        data.put("functions", functions);

        try {
            PrintStream printStream = new PrintStream(file);
            printStream.println(gson.toJson(data, Map.class));
            printStream.flush();
            printStream.close();
        } catch(Exception exception) {
            exception.printStackTrace();
        }

    }

    public void delete() {
        file.delete();
    }

}
