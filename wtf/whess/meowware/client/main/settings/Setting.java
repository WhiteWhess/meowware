package wtf.whess.meowware.client.main.settings;

import lombok.Getter;

import java.util.function.Supplier;

public abstract class Setting {
    public Setting(String name) {
        this.name = name;
    }

    @Getter
    private final String name;

    private Supplier<Boolean> visible = () -> true;

    public boolean isVisible() {
        return visible.get().booleanValue();
    }

    public Setting setVisible(Supplier<Boolean> state) {
        this.visible = state;
        return this;
    }

}
