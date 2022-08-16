package wtf.whess.meowware.client.api.utilities.render.utils;

import wtf.whess.meowware.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import wtf.whess.meowware.client.api.utilities.Utility;
import wtf.whess.meowware.client.api.utilities.misc.MathUtil;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public final class RenderUtil extends Utility {
    public static final ResourceLocation OPTIONS_BACKGROUND = new ResourceLocation("textures/gui/options_background.png");
    public static final ResourceLocation STAT_ICONS = new ResourceLocation("textures/gui/container/stats_icons.png");
    public static final ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");
    private static float zLevel;

    /**
     * Draws a thin horizontal line between two points.
     */

    public static void drawOutlineRect(float x, float y, float width, float height, Color color, Color colorTwo) {
        drawRect(x, y, x + width, y + height, color.getRGB());
        int colorRgb = colorTwo.getRGB();
        drawRect(x - 1, y, x, y + height, colorRgb);
        drawRect(x + width, y, x + width + 1, y + height, colorRgb);
        drawRect(x - 1, y - 1, x + width + 1, y, colorRgb);
        drawRect(x - 1, y + height, x + width + 1, y + height + 1, colorRgb);
    }

    public static void drawHorizontalLine(int startX, int endX, int y, int color)
    {
        if (endX < startX)
        {
            int i = startX;
            startX = endX;
            endX = i;
        }

        drawRect(startX, y, endX + 1, y + 1, color);
    }

    public static void trace(Entity e, float partialTicks, Color color) {
        if (mc.getRenderManager() != null) {
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glLineWidth(2F);

            GL11.glPushMatrix();
            GL11.glDepthMask(false);
            GL11.glColor3d(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);

            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glBegin(GL11.GL_LINES);

            Vec3d v = new Vec3d(0.0D, 0.0D, 1.0D).rotatePitch(-((float) Math.toRadians(mc.player.rotationPitch))).rotateYaw(-((float) Math.toRadians(mc.player.rotationYaw)));

            GL11.glVertex3d(v.xCoord, mc.player.getEyeHeight() + v.yCoord, v.zCoord);

            double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * partialTicks;
            double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * partialTicks;
            double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * partialTicks;

            GL11.glVertex3d(x - RenderManager.viewerPosX, y - RenderManager.viewerPosY + 0.25, z - RenderManager.viewerPosZ);

            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPopMatrix();
        }
    }

    public static void enableSmoothLine(float width) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(width);
    }

    public static void disableSmoothLine() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    private static final Frustum frustum = new Frustum();

    public static float getDeltaTime() {
        return 1f / Minecraft.getDebugFPS();
    }

    public static void scissorBox(float x, float y, float x2, float y2) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);

        glScissor((int) (x * scaledResolution.getScaleFactor()), (int) ((scaledResolution.getScaledHeight() - y2) * scaledResolution.getScaleFactor()),
                (int) ((x2 - x) * scaledResolution.getScaleFactor()), (int) ((y2 -y) * scaledResolution.getScaleFactor()));
    }

    public static void scissorBox(float x, float y, float x2, float y2, float additionalScaleFactor) {
        scissorBox(x * additionalScaleFactor, y * additionalScaleFactor, x2 * additionalScaleFactor, y2 * additionalScaleFactor);
    }

    public static boolean isInView(Entity entity) {
        return isInView(entity.getEntityBoundingBox());
    }

    public static boolean isInView(AxisAlignedBB axisAlignedBB) {
        frustum.setPosition(mc.getRenderManager().viewerPosX, mc.getRenderManager().viewerPosY, mc.getRenderManager().viewerPosZ);
        return frustum.isBoundingBoxInFrustum(axisAlignedBB);
    }

    public static void drawGlowRect(float x, float y, float x2, float y2, float size, float blur, int color) {
        ShaderUtil.beginBlur();

        x += -size;
        y += size * (y > y2 ? 1 : -1);
        x2 += size;
        y2 += size * (y > y2 ? -1 : 1);

        drawRect(x, y, x2, y2 , color);
        ShaderUtil.endBlur(blur, x - blur - 1, y - blur - 1, x2 + blur + 1, y2 + blur + 1);
    }

    public static void drawRoundedRect(float x, float y, float x2, float y2, float radius, int color) {
        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();

        glBegin(GL_POLYGON);

        for (int i = 0; i <= 90; i += 3)
            glVertex2f((float) (x + radius + (Math.sin(i * Math.PI / 180f) * -radius)), (float) (y + radius + (Math.cos((i * Math.PI / 180f)) * -radius)));

        for (int i = 90; i <= 190; i += 3)
            glVertex2f((float) (x + radius + (Math.sin(i * Math.PI / 180f) * -radius)), (float) (y2 - radius + (Math.cos((i * Math.PI / 180f)) * -radius)));

        for (int i = 0; i <= 90; i += 3)
            glVertex2f((float) (x2 - radius + (Math.sin(i * Math.PI / 180f) * radius)), (float) (y2 - radius + (Math.cos((i * Math.PI / 180f)) * radius)));

        for (int i = 90; i <= 180; i += 3)
            glVertex2f((float) (x2 - radius + (Math.sin(i * Math.PI / 180f) * radius)), (float) (y + radius + (Math.cos((i * Math.PI / 180f)) * radius)));

        glEnd();

        GlStateManager.enableTexture2D();
    }



    public static void drawRoundedRectWithOutline(float x, float y, float x2, float y2, float radius, float outlineThickness, int color, int outlineColor) {
        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);

        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBegin(GL_POLYGON);

        for (int i = 0; i <= 90; i += 3)
            glVertex2f((float) (x + radius + (Math.sin(i * Math.PI / 180f) * -radius)), (float) (y + radius + (Math.cos((i * Math.PI / 180)) * -radius)));

        for (int i = 90; i <= 190; i += 3)
            glVertex2f((float) (x + radius + (Math.sin(i * Math.PI / 180f) * -radius)), (float) (y2 - radius + (Math.cos((i * Math.PI / 180)) * -radius)));

        for (int i = 0; i <= 90; i += 3)
            glVertex2f((float) (x2 - radius + (Math.sin(i * Math.PI / 180f) * radius)), (float) (y2 - radius + (Math.cos((i * Math.PI / 180)) * radius)));

        for (int i = 90; i <= 180; i += 3)
            glVertex2f((float) (x2 - radius + (Math.sin(i * Math.PI / 180f) * radius)), (float) (y + radius + (Math.cos((i * Math.PI / 180)) * radius)));

        glEnd();

        glColor4f((outlineColor >> 16 & 0xFF) / 255f, (outlineColor >> 8 & 0xFF) / 255f, (outlineColor & 0xFF) / 255f, (outlineColor >> 24 & 0xFF) / 255f);

        glLineWidth(outlineThickness);
        GlStateManager.enableBlend();
        glEnable(GL_LINE_SMOOTH);
        GlStateManager.disableTexture2D();
        glBegin(GL_LINE_LOOP);

        for (int i = 0; i <= 90; i += 3)
            glVertex2f((float) (x + radius + (Math.sin(i * Math.PI / 180f) * -radius)), (float) (y + radius + (Math.cos((i * Math.PI / 180)) * -radius)));

        for (int i = 90; i <= 190; i += 3)
            glVertex2f((float) (x + radius + (Math.sin(i * Math.PI / 180f) * -radius)), (float) (y2 - radius + (Math.cos((i * Math.PI / 180)) * -radius)));

        for (int i = 0; i <= 90; i += 3)
            glVertex2f((float) (x2 - radius + (Math.sin(i * Math.PI / 180f) * radius)), (float) (y2 - radius + (Math.cos((i * Math.PI / 180)) * radius)));

        for (int i = 90; i <= 180; i += 3)
            glVertex2f((float) (x2 - radius + (Math.sin(i * Math.PI / 180f) * radius)), (float) (y + radius + (Math.cos((i * Math.PI / 180)) * radius)));

        glEnd();
    }

    public static void drawRectWithOutline(float x, float y, float x2, float y2, float outlineThickness, int color, int outlineColor) {
        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);

        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);

        glBegin(GL_POLYGON);
        glVertex2f(x, y);
        glVertex2f(x, y2);
        glVertex2f(x2, y2);
        glVertex2f(x2, y);
        glEnd();

        glColor4f((outlineColor >> 16 & 0xFF) / 255f, (outlineColor >> 8 & 0xFF) / 255f, (outlineColor & 0xFF) / 255f, (outlineColor >> 24 & 0xFF) / 255f);

        glLineWidth(outlineThickness);
        GlStateManager.enableBlend();
        glEnable(GL_LINE_SMOOTH);
        GlStateManager.disableTexture2D();

        glBegin(GL_LINE_LOOP);
        glVertex2f(x, y);
        glVertex2f(x2, y);
        glVertex2f(x2, y2);
        glVertex2f(x, y2);
        glEnd();
    }

    public static void drawRectWithOutline1(float x, float y, float x2, float y2, float outlineThickness, int outlineColor, int color) {
        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);

        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);

        glBegin(GL_POLYGON);
        glVertex2f(x, y);
        glVertex2f(x, y2);
        glVertex2f(x2, y2);
        glVertex2f(x2, y);
        glEnd();

        glColor4f((outlineColor >> 16 & 0xFF) / 255f, (outlineColor >> 8 & 0xFF) / 255f, (outlineColor & 0xFF) / 255f, (outlineColor >> 24 & 0xFF) / 255f);

        glLineWidth(outlineThickness);
        GlStateManager.enableBlend();
        glEnable(GL_LINE_SMOOTH);
        GlStateManager.disableTexture2D();

        glBegin(GL_LINE_LOOP);
        glVertex2f(x, y);
        glVertex2f(x2, y);
        glVertex2f(x2, y2);
        glVertex2f(x, y2);
        glEnd();
    }

    public static void drawArrow(float x, float y, float size, float thickness, float angle, int color) {
        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);

        GlStateManager.enableBlend();
        glEnable(GL_LINE_SMOOTH);
        GlStateManager.disableTexture2D();
        glLineWidth(thickness);

        glPushMatrix();
        glTranslatef(x, y, 0);
        glRotatef(angle, 0, 0, 1);
        glBegin(GL_LINE_STRIP);
        size /= 2f;
        glVertex2f(-size * 2, size);
        glVertex2f(0, -size);
        glVertex2f(size * 2, size);
        glEnd();
        glPopMatrix();
    }

    public static void drawCircle(float x, float y, float radius, int color) {
        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();

        glBegin(GL_POLYGON);
        for(int i = -180; i < 180; i++) {
            glVertex2f((float) (x + Math.sin(i * Math.PI / 180.0) * radius), (float) ((y + Math.cos(i * Math.PI / 180.0) * radius)));
        }
        glEnd();
    }

    public static void drawOutlineCircle(float x, float y, float radius, float thickness, int color) {
        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);

        glEnable(GL_LINE_SMOOTH);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();

        glLineWidth(thickness);

        glBegin(GL_LINE_LOOP);
        for(int i = -180; i < 180; i++) {
            glVertex2f((float) (x + Math.sin(i * Math.PI / 180.0) * radius), (float) ((y + Math.cos(i * Math.PI / 180.0) * radius)));
        }
        glEnd();
    }

    public static void drawFullSizedImage(ResourceLocation texture, float x, float y, float x2, float y2, float textureWidth, float textureHeight, int color) {
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();

        mc.getTextureManager().bindTexture(texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);
        drawScaledCustomSizeModalRect(x, y, 0, 0, textureWidth, textureHeight, x2 - x, y2 - y, textureWidth, textureHeight);

        GlStateManager.disableBlend();
        GlStateManager.disableTexture2D();
    }

    public static void drawFullSizedImage(ResourceLocation texture, float x, float y, float x2, float y2, float textureWidth, float textureHeight) {
        drawFullSizedImage(texture, x, y, x2, y2, textureWidth, textureHeight, -1);
    }

    public static void drawPlayerHead(AbstractClientPlayer player, float x, float y, float x2, float y2, int color) {
        ResourceLocation headTexture = player.getLocationSkin();
        mc.getTextureManager().bindTexture(headTexture);
        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);

        drawScaledCustomSizeModalRect(x, y, 8, 8, 8, 8, x2 - x, y2 -y, 64, 64);
    }

    public static void drawCirclePlayerHead(AbstractClientPlayer player, float x, float y, float x2, float y2, int color) {
        ResourceLocation headTexture = player.getLocationSkin();

        StencilUtil.write();
        drawCircle(x + (x2 - x) / 2f, y + (y2 - y) / 2f, (y2 - y) / 2f, -1);
        StencilUtil.read();

        mc.getTextureManager().bindTexture(headTexture);
        GlStateManager.enableTexture2D();
        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);
        drawScaledCustomSizeModalRect(x, y, 8, 8, 8, 8, x2 - x, y2 -y, 64, 64);
        StencilUtil.cut();
    }

    public static void color(Color color) {
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
    }

    public static void drawImage(ResourceLocation location, double posX, double posY, double width, double height, Color color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        Minecraft.getMinecraft().getTextureManager().bindTexture(location);
        final float f = 1.0f / (float)width;
        final float f2 = 1.0f / (float)height;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder worldrenderer = tessellator.getBuffer();
        GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(posX, posY + height, 0.0).tex((double)(0.0f * f), (double)((0.0f + (float)height) * f2)).endVertex();
        worldrenderer.pos(posX + width, posY + height, 0.0).tex((double)((0.0f + (float)width) * f), (double)((0.0f + (float)height) * f2)).endVertex();
        worldrenderer.pos(posX + width, posY, 0.0).tex((double)((0.0f + (float)width) * f), (double)(0.0f * f2)).endVertex();
        worldrenderer.pos(posX, posY, 0.0).tex((double)(0.0f * f), (double)(0.0f * f2)).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
    }

    public static void drawRoundedRect(double x, double y, double width, double height, double radius, int color) {
        double x1 = width;
        double y1 = height;
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GlStateManager.enableBlend();
        x *= 2.0D;
        y *= 2.0D;
        x1 *= 2.0D;
        y1 *= 2.0D;
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        int i;
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D,
                    y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D,
                    y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius,
                    y1 - radius + Math.cos(i * Math.PI / 180.0D) * radius);
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d(x1 - radius + Math.sin(i * Math.PI / 180.0D) * radius,
                    y + radius + Math.cos(i * Math.PI / 180.0D) * radius);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GlStateManager.disableBlend();
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glPopAttrib();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawRoundedRect2(float x, float y, float x2, float y2, float radius, int color) {
        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();

        glBegin(GL_POLYGON);

        for (int i = 0; i <= 90; i += 3)
            glVertex2f((float) (x + radius + (Math.sin(Math.toRadians(i)) * -radius)), (float) (y + radius + (Math.cos((Math.toRadians(i))) * -radius)));

        for (int i = 90; i <= 180; i += 3)
            glVertex2f((float) (x + radius + (Math.sin(Math.toRadians(i)) * -radius)), (float) (y2 - radius + (Math.cos((Math.toRadians(i))) * -radius)));

        for (int i = 0; i <= 90; i += 3)
            glVertex2f((float) (x2 - radius + (Math.sin(Math.toRadians(i)) * radius)), (float) (y2 - radius + (Math.cos((Math.toRadians(i))) * radius)));

        for (int i = 90; i <= 180; i += 3)
            glVertex2f((float) (x2 - radius + (Math.sin(Math.toRadians(i)) * radius)), (float) (y + radius + (Math.cos((Math.toRadians(i))) * radius)));

        glEnd();

        GlStateManager.enableTexture2D();
    }

    /**
     * Draw a 1 pixel wide vertical line. Args : x, y1, y2, color
     */
    public static void drawVerticalLine(int x, int startY, int endY, int color)
    {
        if (endY < startY)
        {
            int i = startY;
            startY = endY;
            endY = i;
        }

        drawRect(x, startY + 1, x + 1, endY, color);
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color.
     */
    public static void drawRect(double left, double top, double right, double bottom, int color)
    {
        if (left < right)
        {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double)left, (double)bottom, 0.0D).endVertex();
        worldrenderer.pos((double)right, (double)bottom, 0.0D).endVertex();
        worldrenderer.pos((double)right, (double)top, 0.0D).endVertex();
        worldrenderer.pos((double)left, (double)top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawOutLine(double x, double y, double width, double height, int color)
    {
        drawRect(x - 1, y, x, height, color);
        drawRect(x, y - 1, width, y, color);
        drawRect(width, y, width + 1, height, color);
        drawRect(x, height, width, height + 1, color);
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors (ARGB format). Args : x1, y1, x2, y2,
     * topColor, bottomColor
     */
    public static void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor)
    {
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, (double)zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)top, (double)zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (col1 >> 24 & 0xFF) / 255.0F;
        float f1 = (col1 >> 16 & 0xFF) / 255.0F;
        float f2 = (col1 >> 8 & 0xFF) / 255.0F;
        float f3 = (col1 & 0xFF) / 255.0F;

        float f4 = (col2 >> 24 & 0xFF) / 255.0F;
        float f5 = (col2 >> 16 & 0xFF) / 255.0F;
        float f6 = (col2 >> 8 & 0xFF) / 255.0F;
        float f7 = (col2 & 0xFF) / 255.0F;

        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);

        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);

        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static void drawGradient(double x2, double y2, double x22, double y22, int col1, int col2) {
        float f2 = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f22 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        float f4 = (float)(col2 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(col2 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(col2 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f1, f22, f3, f2);
        GL11.glVertex2d(x22, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(x2, y22);
        GL11.glVertex2d(x22, y22);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    /**
     * Renders the specified text to the screen, center-aligned. Args : renderer, string, x, y, color
     */
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawStringWithShadow(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
    }

    /**
     * Renders the specified text to the screen. Args : renderer, string, x, y, color
     */
    public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawStringWithShadow(text, (float)x, (float)y, color);
    }

    /**
     * Draws a textured rectangle at the current z-value.
     */
    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, zLevel).tex((float)(textureX) * 0.00390625F, (float)(textureY + height) * 0.00390625F).endVertex();
        worldrenderer.pos(x + width, y + height, zLevel).tex((float)(textureX + width) * 0.00390625F, (float)(textureY + height) * 0.00390625F).endVertex();
        worldrenderer.pos(x + width, y, zLevel).tex((float)(textureX + width) * 0.00390625F, (float)(textureY) * 0.00390625F).endVertex();
        worldrenderer.pos(x, y, zLevel).tex((float)(textureX) * 0.00390625F, (float)(textureY) * 0.00390625F).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a textured rectangle using the texture currently bound to the TextureManager
     */
    public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(xCoord + 0.0F), (double)(yCoord + (float)maxV), (double)this.zLevel).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + (float)maxU), (double)(yCoord + (float)maxV), (double)this.zLevel).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + (float)maxU), (double)(yCoord + 0.0F), (double)this.zLevel).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + 0.0F), (double)(yCoord + 0.0F), (double)this.zLevel).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }

    public static void setupColor(final int color) {
        final float f3 = (color >> 24 & 0xFF) / 255.0f;
        final float f4 = (color >> 16 & 0xFF) / 255.0f;
        final float f5 = (color >> 8 & 0xFF) / 255.0f;
        final float f6 = (color & 0xFF) / 255.0f;
        glColor4f(f4, f5, f6, f3);
    }

    public static void startSmooth() {
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
    }

    public static void endSmooth() {
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glEnable(2832);
    }

    public static void drawCircle(final double xx, final double yy, final double radius, final int color) {
        final int sections = 20;
        final double dAngle = MathUtil.PI2 / sections;
        GL11.glPushMatrix();
        GL11.glPushAttrib(8192);
        startSmooth();
        GL11.glEnable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        glBegin(2);
        for (int i = 0; i < sections; ++i) {
            final double x = (float)(radius * Math.sin(i * dAngle));
            final double y = (float)(radius * Math.cos(i * dAngle));
            setupColor(color);
            GL11.glVertex2d(xx + x, yy + y);
        }
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnd();
        endSmooth();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawFilledCircle(final double xx, final double yy, final double radius, final int color) {
        final int sections = 20;
        final double dAngle = 6.283185307179586 / sections;
        GL11.glPushMatrix();
        GL11.glPushAttrib(8192);
        GL11.glEnable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glLineWidth(1.0f);
        glBegin(6);
        for (int i = 0; i < sections; ++i) {
            final double x = (float)(radius * Math.sin(i * dAngle));
            final double y = (float)(radius * Math.cos(i * dAngle));
            setupColor(color);
            GL11.glVertex2d(xx + x, yy + y);
        }
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnd();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawFilledCircleWithOutline(final double xx, final double yy, final double radius,double lineWidth, final int color, final int outlineColor) {
        final int sections = 20;
        final double dAngle = 6.283185307179586 / sections;
        GL11.glPushMatrix();
        GL11.glPushAttrib(8192);
        GL11.glEnable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glLineWidth(1.0f);
        glBegin(GL_LINE_LOOP);
        glColor4f((outlineColor >> 16 & 0xFF) / 255f, (outlineColor >> 8 & 0xFF) / 255f, (outlineColor & 0xFF) / 255f, (outlineColor >> 24 & 0xFF) / 255f);

        for(int i = -180; i < 180; i++) {
            glVertex2f((float) (xx + Math.sin(i * Math.PI / 180.0) * radius), (float) ((yy + Math.cos(i * Math.PI / 180.0) * radius)));
        }
        glEnd();

        glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f, (color >> 24 & 0xFF) / 255f);

        glEnable(GL_LINE_SMOOTH);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();

        glBegin(6);
        for (int i = 0; i < sections; ++i) {
            final double x = (float)(radius * Math.sin(i * dAngle));
            final double y = (float)(radius * Math.cos(i * dAngle));
            setupColor(color);
            GL11.glVertex2d(xx + x, yy + y);
        }
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnd();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

    public static void drawFilledCircleWithBlur(final double xx, final double yy, final double radius, final int color, final int blurRadius) {
        final int sections = 20;
        final double dAngle = 6.283185307179586 / sections;
        ShaderUtil.beginBlur();
        GL11.glPushMatrix();
        GL11.glPushAttrib(8192);
        GL11.glEnable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glLineWidth(1.0f);
        glBegin(6);
        for (int i = 0; i < sections; ++i) {
            final double x = (float)(radius * Math.sin(i * dAngle));
            final double y = (float)(radius * Math.cos(i * dAngle));
            setupColor(color);
            GL11.glVertex2d(xx + x, yy + y);
        }
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnd();
        GL11.glPopAttrib();
        GL11.glPopMatrix();
        ShaderUtil.endBlur(blurRadius);
    }

    public static void prepareScissorBox(final float x, final float y, final float x2, final float y2) {
        final ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        final int factor = scale.getScaleFactor();
        GL11.glScissor((int)(x * factor), (int)((scale.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
    }

    /**
     * Draws a texture rectangle using the texture currently bound to the TextureManager
     */
    public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + heightIn), (double)this.zLevel).tex((double)textureSprite.getMinU(), (double)textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)this.zLevel).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + 0), (double)this.zLevel).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMinV()).endVertex();
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + 0), (double)this.zLevel).tex((double)textureSprite.getMinU(), (double)textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height, textureWidth, textureHeight
     */
    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight)
    {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't used anywhere in vanilla code.
     */
    public static void drawScaledCustomSizeModalRect(float x, float y, float u, float v, float uWidth, float vHeight, float width, float height, float tileWidth, float tileHeight)
    {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)vHeight) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)uWidth) * f), (double)(v * f1)).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }



}
