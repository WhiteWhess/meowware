package wtf.whess.meowware.client.main.settings.impl;

import lombok.Getter;
import lombok.Setter;
import wtf.whess.meowware.client.main.settings.Setting;

public final class BooleanSetting extends Setting {

    @Getter @Setter
    private boolean toggled;

    public BooleanSetting(String name, boolean toggled) {
        super(name);
        this.toggled = toggled;
    }

    public void toggle() {
        this.toggled = !toggled;
    }

}
