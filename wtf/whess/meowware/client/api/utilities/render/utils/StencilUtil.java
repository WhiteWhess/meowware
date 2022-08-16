package wtf.whess.meowware.client.api.utilities.render.utils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import wtf.whess.meowware.client.api.utilities.Utility;

import static org.lwjgl.opengl.ARBFramebufferObject.*;
import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.GL11.*;

public final class StencilUtil extends Utility {
    public static void write() {
        setupFramebuffer();
        glClearStencil(0);
        glClear(GL_STENCIL_BUFFER_BIT);
        glEnable(GL_STENCIL_TEST);
        glStencilFunc(GL_ALWAYS, 1, 0xFF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
        glColorMask(false, false, false, true);
    }

    public static void read() {
        read(false);
    }

    public static void read(boolean inverse) {
        glStencilFunc(inverse ? GL_NOTEQUAL : GL_EQUAL, 1, 0xFF);
        glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);
        glColorMask(true, true, true, true);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
    }

    public static void cut() {
        glDisable(GL_STENCIL_TEST);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
    }

    private static void setupFramebuffer() {
        Framebuffer framebuffer = mc.getFramebuffer();
        if(framebuffer != null && framebuffer.depthBuffer > -1) {
            glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
            int stencilBuffer = glGenRenderbuffersEXT();
            glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, stencilBuffer);
            glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_STENCIL, mc.displayWidth, mc.displayHeight);
            glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_STENCIL_ATTACHMENT, GL_RENDERBUFFER_EXT, stencilBuffer);
            glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER_EXT, stencilBuffer);
            framebuffer.depthBuffer = -1;
        }
    }

}
