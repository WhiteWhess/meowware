package wtf.whess.meowware.client.api.utilities.render.utils;

import wtf.whess.meowware.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import wtf.whess.meowware.client.api.utilities.Utility;
import wtf.whess.meowware.client.api.utilities.render.presets.Shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.*;

public final class ShaderUtil extends Utility {

    private static Minecraft mc = Minecraft.getMinecraft();

    private static Framebuffer framebuffer;
    private static Shader blur = Shader.load("default", "blur");

    public static void beginBlur() {
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        glDisable(GL_ALPHA_TEST);
        GlStateManager.enableTexture2D();

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        framebuffer = setupFramebuffer();
        framebuffer.framebufferClear();
        framebuffer.bindFramebuffer(true);
        glColorMask(true, true, true, true);
        glClearColor(0, 0, 0, 0);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GlStateManager.enableBlend();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void endBlur(float blurStrength, float x, float y, float x2, float y2) {
        framebuffer.unbindFramebuffer();
        mc.getFramebuffer().bindFramebuffer(true);

        ScaledResolution resolution = new ScaledResolution(mc);

        GlStateManager.enableBlend();
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableTexture2D();

        glColor4f(1f, 1f, 1f, 1f);
        //framebuffer.bindFramebufferTexture();

        blur.use();
        glActiveTexture(GL_TEXTURE0);
        GlStateManager.enableTexture2D();
        glDisable(GL_ALPHA_TEST);
        framebuffer.bindFramebufferTexture();
        glUniform1i(blur.getUniform("sampler"), 0);
        glUniform1f(blur.getUniform("blur"), blurStrength);
        glUniform4f(blur.getUniform("effectBorders"), x * (float) resolution.getScaleFactor(), y * (float) resolution.getScaleFactor(), x2 * (float) resolution.getScaleFactor(), y2 * (float) resolution.getScaleFactor());

        glBegin(GL_POLYGON);
        glTexCoord2f(0f, 1f);
        glVertex2f(0f, 0f);
        glTexCoord2f(0f, 0f);
        glVertex2f(0f, resolution.getScaledHeight());
        glTexCoord2f(1f, 0f);
        glVertex2f(resolution.getScaledWidth(), resolution.getScaledHeight());
        glTexCoord2f(1f, 1f);
        glVertex2f(resolution.getScaledWidth(), 0f);
        glEnd();

        blur.stop();

        framebuffer.unbindFramebufferTexture();

        glEnable(GL_ALPHA_TEST);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    public static void endBlur(float blurStrength) {
        endBlur(blurStrength, 0, 0, mc.displayWidth, mc.displayHeight);
    }

    private static Framebuffer setupFramebuffer() {
        if(framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight) {
            if(framebuffer != null)
                framebuffer.deleteFramebuffer();
            return new Framebuffer(mc.displayWidth, mc.displayHeight, false);
        }
        return framebuffer;
    }

}
