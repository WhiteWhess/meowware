package wtf.whess.meowware.client.main.functions.impl.visual;

import org.lwjgl.input.Keyboard;
import wtf.whess.meowware.MeowWare;
import wtf.whess.meowware.client.ui.clickgui.ClickGuiScreen;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.ModeSetting;

public final class ClickGUI extends Function {
    public static final ModeSetting girl = new ModeSetting("Girls", "Girl1", "Off", "Girl1", "Girl2", "Girl3", "Girl4", "Girl5", "Girl6", "Girl7", "Girl8", "Girl9", "Girl10", "Girl11", "Girl12", "Girl13", "Girl14", "Girl15", "Girl16", "Girl17", "Girl18", "Girl19", "Girl20", "Girl21", "Girl22");
    public ClickGUI() {
        super("ClickGUI", Category.visual);
        setBind(Keyboard.KEY_RSHIFT);
        addSetting(girl);
    }

    @Override
    public void onEnable() {
        if (!(mc.currentScreen instanceof ClickGuiScreen))
            mc.displayGuiScreen(MeowWare.getClickGuiScreen());
        this.toggle();
    }

    @Override
    public void onDisable() {}

}
