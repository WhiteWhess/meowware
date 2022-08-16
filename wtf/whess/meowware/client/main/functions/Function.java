package wtf.whess.meowware.client.main.functions;

import lombok.Getter;
import lombok.Setter;
import wtf.whess.meowware.Minecraft;
import wtf.whess.meowware.client.api.eventsystem.EventManager;
import wtf.whess.meowware.client.main.settings.Setting;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Function {
    protected static final Minecraft mc = Minecraft.getMinecraft();

    @Getter
    private boolean toggled;

    @Getter
    private final String name;

    @Getter
    private final Category category;

    @Getter @Setter
    private int bind;

    @Getter @Setter
    private boolean visible;

    @Getter
    private final ArrayList<Setting> settings = new ArrayList<>();

    public Function(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public void toggle() {
        setToggled(!isToggled());
    }

    protected void onEnable() {}

    protected void onDisable() {}

    protected void reload() {
        toggle();
        toggle();
    }

    public void setToggled(boolean toggled) {
        if (this.toggled == toggled) return;

        this.toggled = toggled;

        if (toggled) {
            onEnable();
            EventManager.register(this);
        } else {
            onDisable();
            EventManager.unregister(this);
        }
    }

    public void addSetting(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public void addSetting(Setting setting) {
        this.getSettings().add(setting);
    }

}
