package wtf.whess.meowware.client.main.functions.impl.visual;

import net.minecraft.util.EnumHandSide;
import org.lwjgl.opengl.GL11;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.TransformSideFirstPersonEvent;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;

public final class ViewModel extends Function {
    private final NumberSetting rightX = new NumberSetting("Right X", 0, -2, 2);
    private final NumberSetting rightY = new NumberSetting("Right Y", 0, -2, 2);
    private final NumberSetting rightZ = new NumberSetting("Right Z", 0, -2, 2);
    private final NumberSetting leftX = new NumberSetting("Left X", 0, -2, 2);
    private final NumberSetting leftY = new NumberSetting("Left Y", 0, -2, 2);
    private final NumberSetting leftZ = new NumberSetting("Left Z", 0, -2, 2);

    public ViewModel() {
        super("ViewModel", Category.visual);
        addSetting(rightX, rightY, rightZ, leftX, leftY, leftZ);
    }

    @EventTarget
    public void onRenderArms(TransformSideFirstPersonEvent event) {
        if (event.getEnumHandSide() == EnumHandSide.RIGHT) {
            GL11.glTranslated(
                    rightX.getValue(),
                    rightY.getValue(),
                    rightZ.getValue()
            );
        }
        if (event.getEnumHandSide() == EnumHandSide.LEFT) {
            GL11.glTranslated(
                    -leftX.getValue(),
                    leftY.getValue(),
                    -leftZ.getValue()
            );
        }
    }
}
