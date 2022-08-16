package wtf.whess.meowware.client.main.functions.impl.move;

import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerUpdateEvent;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class AirJump extends Function {
    public AirJump() {
        super("AirJump", Category.move);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent ignoredEvent) {
        if (mc.player.motionY < 0)
            mc.player.onGround = true;
        /*pushMatrix();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        net.minecraft.client.renderer.GlStateManager.disableDepth();
        net.minecraft.client.renderer.GlStateManager.disableTexture2D();
        RenderUtil.enableSmoothLine(2.5f);
        glShadeModel(GL_SMOOTH);
        glDisable(GL_CULL_FACE);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glTranslatef(0, (float)((double)mc.player.height + pizda), 0);
        glRotatef(-mc.player.rotationYaw, 0, 1f, 0);
        GL11.glBegin(GL_TRIANGLE_FAN);
        glColor4f(255, 255, 255, 255);
        glVertex3d(0, 0.3, 0);
        glVertex3d(0, 0.3, 0);
        GL11.glEnd();
        GL11.glBegin(GL_LINE_LOOP);
        glVertex3d(Math.cos((double) xuy * Math.PI / 180.0) * 0.45, 0.15, Math.sin((double) xuy * Math.PI / 180.0) * 0.45);
        glVertex3d(Math.cos((double) xuy * Math.PI / 180.0) * 0.45, 0.13, Math.sin((double) xuy * Math.PI / 180.0) * 0.45);
        glVertex3d(Math.cos((double) xuy * Math.PI / 180.0) * 0.45, 0.11, Math.sin((double) xuy * Math.PI / 180.0) * 0.45);
        glVertex3d(Math.cos((double) xuy * Math.PI / 180.0) * 0.47, 0.15, Math.sin((double) xuy * Math.PI / 180.0) * 0.47);
        glVertex3d(Math.cos((double) xuy * Math.PI / 180.0) * 0.47, 0.13, Math.sin((double) xuy * Math.PI / 180.0) * 0.47);
        glVertex3d(Math.cos((double) xuy * Math.PI / 180.0) * 0.47, 0.11, Math.sin((double) xuy * Math.PI / 180.0) * 0.47);

        GL11.glEnd();
        enableAlpha();
        RenderUtil.disableSmoothLine();
        glShadeModel(7424);
        glEnable(2884);
        glDisable(3042);
        enableTexture2D();
        enableDepth();
        resetColor();

        popMatrix();*/
    }
}
