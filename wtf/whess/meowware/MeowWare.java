package wtf.whess.meowware;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.Color;
import viamcp.ViaMCP;
import wtf.whess.meowware.client.api.configsystem.ConfigManager;
import wtf.whess.meowware.client.api.eventsystem.EventManager;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.KeyPressEvent;
import wtf.whess.meowware.client.api.utilities.client.DiscordRPC;
import wtf.whess.meowware.client.api.utilities.render.presets.Shader;
import wtf.whess.meowware.client.main.commands.CommandManager;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.functions.FunctionManager;
import wtf.whess.meowware.client.ui.alt.AltManager;
import wtf.whess.meowware.client.ui.clickgui.ClickGuiScreen;
import wtf.whess.meowware.client.ui.proxy.ProxyManager;
import wtf.whess.meowware.client.ui.proxy.ProxyType;

import java.io.File;

import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform2f;

public final class MeowWare {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final Shader shader = Shader.load("default", "bg");
    private static final File directory = new File("/MeowWare");
    public static final String NAME = "MeowWare";
    public static final String STATUS = "Beta";
    public static final String VERSION = "0.3";

    @Getter @Setter
    private static String proxyIP = "127.0.0.1";

    @Getter @Setter
    private ProxyType proxyType;

    @Getter
    private static MeowWare instance;

    @Getter
    private final FunctionManager functionManager;

    @Getter
    private final AltManager altManager;

    @Getter
    private final CommandManager commandManager;

    @Getter
    private static ClickGuiScreen clickGuiScreen;

    @Getter
    private static ConfigManager configManager;

    @Getter
    private static DiscordRPC discordRPC;

    public MeowWare() {
        instance = this;

        if (!directory.exists())
            if (!directory.mkdirs())
                throw new RuntimeException();

        ProxyManager.downloadFile();
        ProxyManager.loadProxiesFromFile();

        this.proxyType = ProxyType.NONE;

        Display.setTitle(NAME + " " + STATUS + " " + VERSION);

        functionManager = new FunctionManager();
        commandManager = new CommandManager();
        clickGuiScreen = new ClickGuiScreen();
        configManager = new ConfigManager();
        discordRPC = new DiscordRPC();
        altManager = new AltManager();

        discordRPC.init();
        discordRPC.update("Developed by whess", "Bot Edition " + VERSION);

        try {
            ViaMCP.getInstance().start();
            ViaMCP.getInstance().initAsyncSlider(20, 50, 100, 20);
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventManager.register(this);

        Runtime.getRuntime().addShutdownHook(new Thread(this::end));
    }

    private void end() {
        discordRPC.shutdown();
    }

    public static void drawBackground() {
        assert shader != null;
        shader.use();
        ScaledResolution sr = new ScaledResolution(mc);
        glUniform2f(shader.getUniform("resolution"), sr.getScaledWidth() * sr.getScaleFactor(), sr.getScaledHeight() * sr.getScaleFactor());
        glUniform1f(shader.getUniform("time"), (float) (((double) Sys.getTime()) / 2000D));
        Gui.drawRect(0, 0, sr.getScaledWidth() * sr.getScaleFactor(), sr.getScaledHeight() * sr.getScaleFactor(), new Color(21,21,21).hashCode());
        shader.stop();
    }

    @EventTarget
    public void onKeyPressEvent(KeyPressEvent e) {
        for (Function module : functionManager.getFunctions()) {
            if (module.getBind() == e.getKey()) {
                module.toggle();
            }
        }
    }
}
