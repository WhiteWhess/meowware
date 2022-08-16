package wtf.whess.meowware.client.main.functions.impl.visual;

import lombok.Getter;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;

public final class SwingAnimation extends Function {
    @Getter
    private static final NumberSetting speed = new NumberSetting("Speed", 0.5, 0.1, 5);

    public SwingAnimation() {
        super("SwingAnimation", Category.visual);
        addSetting(speed);
    }
}
