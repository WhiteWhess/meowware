package wtf.whess.meowware.client.main.settings.impl;

import lombok.Getter;
import lombok.Setter;
import wtf.whess.meowware.client.main.settings.Setting;

import java.util.ArrayList;
import java.util.Arrays;

public final class ModeSetting extends Setting {

    @Getter @Setter
    private int index, defaultIndex;
    public final ArrayList<String> modes;

    public ModeSetting(String name, String defaultMode, String... modes) {
        super(name);
        this.modes = new ArrayList<>(Arrays.asList(modes));
        this.index = this.modes.indexOf(defaultMode);
        this.defaultIndex = this.modes.indexOf(defaultMode);
    }

    public void setMode(int index) {
        this.index = index;
    }

    public void setMode(String str) {
        this.index = str.indexOf(str);
    }

    public String getMode() {
        return modes.get(index);
    }

    public boolean is(String mode) {
        return index == modes.indexOf(mode);
    }

    public void cycle() {
        if(index < modes.size() - 1) {
            index++;
        } else {
            index = 0;
        }
    }

}
