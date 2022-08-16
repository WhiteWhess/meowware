package wtf.whess.meowware.client.main.functions.impl.visual;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.RenderWorldEvent;
import wtf.whess.meowware.client.api.utilities.misc.MathUtil;
import wtf.whess.meowware.client.api.utilities.render.utils.ColorUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.BooleanSetting;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public final class ChinaHat extends Function {
    private final NumberSetting vertexes = new NumberSetting("Vertex's", 24,6,48);
    private final BooleanSetting noRenderFirstPerson = new BooleanSetting("NoRenderFirstPerson", false);
    private final NumberSetting alpha = new NumberSetting("Alpha", 0.1,0.01,1);

    public ChinaHat() {
        super("ChinaHat", Category.visual);
        addSetting(vertexes, noRenderFirstPerson, alpha);
        setToggled(true);
    }

    @EventTarget
    public void onRenderWorld(RenderWorldEvent e) {
        for (int i = 0; i < 420; i++) {
            drawHat(mc.player, 0.009 + (double) i * 0.0014, e.getPartialTicks(), (int) (vertexes.getValue()), 2.0f, 2.2f - (float) i * 7.85E-4f - (mc.player.isSneaking() ? 0.3f : 0.025f), ColorUtil.astolfoColors(i, 11111), alpha.getValue());
        }
    }

    private void drawHat(Entity entity, double radius, float partialTicks, int points, float width, float yAdd, int color, double alpha) {
        if (noRenderFirstPerson.isToggled() && mc.gameSettings.thirdPersonView == 0) return;

        double x = interpolate(entity.prevPosX, entity.posX, partialTicks) - RenderManager.viewerPosX;
        double y = interpolate(entity.prevPosY + yAdd, entity.posY + yAdd, partialTicks) - RenderManager.viewerPosY;
        double z = interpolate(entity.prevPosZ, entity.posZ, partialTicks) - RenderManager.viewerPosZ;

        glPushMatrix();
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glDepthMask(false);
        glLineWidth(width);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_ALPHA_TEST);

        glBegin(GL_LINE_STRIP);

        GlStateManager.color(new Color(color).getRed() / 255.0f, new Color(color).getGreen() / 255.0f, new Color(color).getBlue() / 255.0f, (float) alpha);

        for (int i = 0; i <= points; i++) {
            glVertex3d(x + radius * Math.cos(i * MathUtil.PI2 / points), y, z + radius * Math.sin(i * MathUtil.PI2 / points));
        }

        glEnd();

        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_TEXTURE_2D);

        glEnable(GL_ALPHA_TEST);
        glPopMatrix();
    }

    private double interpolate(double old, double current, double scale) {
        return old + (current - old) * scale;
    }
}
