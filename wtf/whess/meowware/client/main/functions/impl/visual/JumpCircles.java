package wtf.whess.meowware.client.main.functions.impl.visual;

import lombok.Data;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerJumpEvent;
import wtf.whess.meowware.client.api.eventsystem.impl.RenderWorldEvent;
import wtf.whess.meowware.client.api.utilities.misc.MathUtil;
import wtf.whess.meowware.client.api.utilities.render.utils.ColorUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.ModeSetting;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public final class JumpCircles extends Function {
    private final ModeSetting mode = new ModeSetting("Mode", "Outer", "Outer", "Inner");
    private final ArrayList<Circle> circles = new ArrayList<>();

    public JumpCircles() {
        super("JumpCircles", Category.visual);
        addSetting(mode);
        setToggled(true);
    }

    @EventTarget
    public void onJump(PlayerJumpEvent ignoredEvent) {
        if (!mc.player.onGround)
            return;
        circles.add(new Circle(mc.player.posX, mc.player.posY + 0.005, mc.player.posZ, System.currentTimeMillis()));
    }

    @EventTarget
    public void onWorldRender(RenderWorldEvent ignoredEvent) {
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();

        circles.removeIf(circle -> System.currentTimeMillis() - circle.creationTime > 2500);

        for (Circle circle : circles) {
            glPushMatrix();
            glTranslated(circle.x - RenderManager.viewerPosX, circle.y - RenderManager.viewerPosY, circle.z - RenderManager.viewerPosZ);

            float radius = MathUtil.clamp((System.currentTimeMillis() - circle.creationTime) / 1000f * 0.6f, 0, 1f);

            circle(circle, radius);
            glPopMatrix();
        }

        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();
    }

    private void circle(Circle circle, float radius) {
        int vertexes = 32;

        Color color = ColorUtil.astolfoColors1(0, 0);

        GlStateManager.shadeModel(GL_SMOOTH);
        glDisable(GL_ALPHA_TEST);

        glBegin(GL_QUAD_STRIP);

        float alpha = MathUtil.clamp((2000 - (System.currentTimeMillis() - circle.creationTime)) / 2000f, 0f, 1f);
        for (int i = 0; i <= vertexes; i+=1) {
            float theta = MathUtil.PI2 * i / vertexes;
            glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha);
            glVertex3d(Math.cos(theta) * radius, 0, Math.sin(theta) * radius);
            if(mode.is("Outer")) {
                glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0);
                glVertex3d(Math.cos(theta) * radius * 1.4, 0, Math.sin(theta) * radius * 1.4);
            } else {
                glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0);
                glVertex3d(Math.cos(theta) * radius / 1.4, 0, Math.sin(theta) * radius / 1.4);
            }
        }

        glEnd();
        glColor4f(1f, 1f, 1f, 1f);
        glEnable(GL_ALPHA_TEST);

        GlStateManager.shadeModel(GL_FLAT);
    }

    @Data
    private static final class Circle {
        private final double x, y, z;
        private final long creationTime;
    }
}
