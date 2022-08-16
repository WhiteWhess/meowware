package wtf.whess.meowware.client.main.functions.impl.visual;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.BufferUtils;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.RenderGameOverlayEvent;
import wtf.whess.meowware.client.api.eventsystem.impl.RenderWorldEvent;
import wtf.whess.meowware.client.api.fontmanager.FontManager;
import wtf.whess.meowware.client.api.utilities.render.utils.RenderUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.BooleanSetting;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluProject;

public final class ESP extends Function {
    private final NumberSetting thickness = new NumberSetting("Thickness", 1.5, 1, 2, 0.1);
    private final BooleanSetting outline = new BooleanSetting("Outline", true);
    private final BooleanSetting name = new BooleanSetting("Name", true);
    private final NumberSetting outlineThickness = new NumberSetting("Outline thickness", 2.5, 2, 3, 0.1);

    public ESP() {
        super("ESP", Category.visual);
        addSetting(name, thickness, outline, outlineThickness);
    }

    @EventTarget
    public void onOverlayRender(RenderGameOverlayEvent event) {
        List<Entity> targets = mc.world.loadedEntityList.stream()
                .filter(
                        entity ->
                                RenderUtil.isInView(entity) && (entity instanceof EntityOtherPlayerMP)
                )
                .collect(Collectors.toList());

        GlStateManager.disableTexture2D();
        glEnable(GL_LINE_SMOOTH);
        GlStateManager.enableBlend();

        for (Entity entity : targets) {
            glPushMatrix();

            double
                    x = entity.prevPosX + (entity.posX - entity.prevPosX) * event.getPartialTicks(),
                    y = entity.prevPosY + (entity.posY - entity.prevPosY) * event.getPartialTicks(),
                    z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * event.getPartialTicks();
            float
                    sizeX = (float) (entity.getRenderBoundingBox().maxX - entity.getRenderBoundingBox().minX) / 2f,
                    sizeY = (float) (entity.getRenderBoundingBox().maxY - entity.getRenderBoundingBox().minY) / 2f,
                    sizeZ = (float) (entity.getRenderBoundingBox().maxZ - entity.getRenderBoundingBox().minZ) / 2f;

            AxisAlignedBB aabb = new AxisAlignedBB(x - sizeX, y, z - sizeZ, x + sizeX, y + sizeY * 2f, z + sizeZ);
            ArrayList<Vec3d> positions = new ArrayList<>(Arrays.asList(
                    new Vec3d(aabb.minX, aabb.minY, aabb.minZ), new Vec3d(aabb.maxX, aabb.minY, aabb.minZ),
                    new Vec3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vec3d(aabb.minX, aabb.minY, aabb.maxZ),
                    new Vec3d(aabb.minX, aabb.maxY, aabb.minZ), new Vec3d(aabb.maxX, aabb.maxY, aabb.minZ),
                    new Vec3d(aabb.maxX, aabb.maxY, aabb.maxZ), new Vec3d(aabb.minX, aabb.maxY, aabb.maxZ)
            ));

            ScaledResolution scaledResolution = new ScaledResolution(mc);

            positions.sort(Comparator.comparingDouble(position -> getPositionOnScreen(position, scaledResolution).x));
            float left = getPositionOnScreen(positions.get(0), scaledResolution).x;
            float right = getPositionOnScreen(positions.get(positions.size() - 1), scaledResolution).x;
            positions.sort(Comparator.comparingDouble(position -> getPositionOnScreen(position, scaledResolution).y));
            float bottom = getPositionOnScreen(positions.get(0), scaledResolution).y;
            float top = getPositionOnScreen(positions.get(positions.size() - 1), scaledResolution).y;

            float distance = (float) mc.getRenderManager().getDistanceToCamera(entity.posX, entity.posY, entity.posZ);
            float zLayer = distance / -100f;

            if (name.isToggled())
                FontManager.montserratLight18.drawStringWithShadow(entity.getName(), (right + left) / 2 - (FontManager.montserratLight18.getStringWidth(entity.getName()) / 2F), bottom - FontManager.montserratLight18.getFontHeight(), -1);

            Color color = new Color(255, 255, 255);
            Color outlineColor = new Color(30, 30, 30);

            if (outline.isToggled()) {
                glColor4f(outlineColor.getRed() / 255f, outlineColor.getGreen() / 255f, outlineColor.getBlue() / 255f, 1f);

                glLineWidth((float) (thickness.getValue() + outlineThickness.getValue()));

                glBegin(GL_LINE_LOOP);
                glVertex3d(left, top, zLayer);
                glVertex3d(right, top, zLayer);
                glVertex3d(right, bottom, zLayer);
                glVertex3d(left, bottom, zLayer);
                glEnd();
            }

            glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1f);

            glLineWidth((float) thickness.getValue());

            glBegin(GL_LINE_LOOP);
            glVertex3d(left, top, zLayer);
            glVertex3d(right, top, zLayer);
            glVertex3d(right, bottom, zLayer);
            glVertex3d(left, bottom, zLayer);
            glEnd();

            glPopMatrix();
        }

        glDisable(GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
    }

    FloatBuffer projection = BufferUtils.createFloatBuffer(16);
    FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
    IntBuffer viewport = BufferUtils.createIntBuffer(16);

    @EventTarget
    public void onWorldRender(RenderWorldEvent ignoredEvent) {
        glGetFloat(GL_PROJECTION_MATRIX, projection);
        glGetFloat(GL_MODELVIEW_MATRIX, modelView);
        glGetInteger(GL_VIEWPORT, viewport);
    }

    private Vec2f getPositionOnScreen(Vec3d position, ScaledResolution scaledResolution) {
        FloatBuffer result = BufferUtils.createFloatBuffer(3);

        gluProject((float) (position.xCoord - RenderManager.viewerPosX), (float) (position.yCoord - RenderManager.viewerPosY), (float) (position.zCoord - RenderManager.viewerPosZ), modelView, projection, viewport, result);

        return new Vec2f(result.get(0) / scaledResolution.getScaleFactor(), scaledResolution.getScaledHeight() - result.get(1) / scaledResolution.getScaleFactor());
    }
}
