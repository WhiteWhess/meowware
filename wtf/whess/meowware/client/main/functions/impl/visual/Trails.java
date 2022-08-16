package wtf.whess.meowware.client.main.functions.impl.visual;

import lombok.Data;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.RenderWorldEvent;
import wtf.whess.meowware.client.api.utilities.render.utils.ColorUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.impl.ModeSetting;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;

import java.awt.*;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public final class Trails extends Function {
    private final ModeSetting mode = new ModeSetting("Mode", "Akrien", "Akrien", "DeadCode");
    private final NumberSetting length = new NumberSetting("Length", 0.5, 0.1, 2);

    public Trails() {
        super("Trails", Category.visual);
        this.addSetting(mode, length);
        setToggled(true);
    }

    @Data
    private static final class TrailsPart {
        private final double x, y, z;
        private final long creationTime;
        private final Color firstColor;
        private final Color secondColor;
    }

    private final ArrayList<TrailsPart> trailsParts = new ArrayList<>();

    @EventTarget
    public void onWorldRender(RenderWorldEvent event) {
        if (mc.gameSettings.thirdPersonView == 0)
            return;
        Color color = new Color(21,21,21); Color color2 = new Color(21,21,21);
        switch (mode.getMode()) {
            case "Akrien": {
                color = ColorUtil.astolfo1(1200, 9999999,0);
                color2 = ColorUtil.astolfo1(1200, 9999999,0);
                break;
            }
            case "DeadCode": {
                color = ColorUtil.astolfo1(4000, 9999999,0);
                color2 = ColorUtil.astolfo1(4500, 9999999,0);
                break;
            }
        }
        trailsParts.add(new TrailsPart(
                mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * event.getPartialTicks(),
                mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * event.getPartialTicks(),
                mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * event.getPartialTicks(),
                System.currentTimeMillis(),
                color,
                color2
        ));
        trailsParts.removeIf(trailsPart -> System.currentTimeMillis() - trailsPart.getCreationTime() > length.getValue() * 1000L);

        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();

        glDisable(GL_ALPHA_TEST);

        glBegin(GL_QUAD_STRIP);

        boolean isDC = mode.is("DeadCode");

        for(TrailsPart part : trailsParts) {
            double x = part.getX() - RenderManager.viewerPosX;
            double y = part.getY() - RenderManager.viewerPosY + 0.1f;
            double z = part.getZ() - RenderManager.viewerPosZ;

            double alpha = (length.getValue() * 1000.0 - (System.currentTimeMillis() - part.getCreationTime())) / (length.getValue() * 1000.0) / 1.5;

            if (!isDC)
                glColor4f(part.firstColor.getRed() / 255F, part.firstColor.getGreen() / 255F, part.firstColor.getBlue() / 255F, (float) alpha);
            else
                glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, (float) alpha);

            glVertex3d(x, y + mc.player.getEyeHeight(), z);

            if (!isDC)
                glColor4f(part.secondColor.getRed() / 255F, part.secondColor.getGreen() / 255F, part.secondColor.getBlue() / 255F, (float) alpha);
            else
                glColor4f(color2.getRed() / 255F, color2.getGreen() / 255F, color2.getBlue() / 255F, (float) alpha);

            glVertex3d(x, y, z);

        }

        glEnd();

        glBegin(GL_LINE_STRIP);

        for(TrailsPart part : trailsParts) {
            double x = part.getX() - RenderManager.viewerPosX;
            double y = part.getY() - RenderManager.viewerPosY + 0.1f;
            double z = part.getZ() - RenderManager.viewerPosZ;

            double alpha = (length.getValue() * 1000.0 - (System.currentTimeMillis() - part.getCreationTime())) / (length.getValue() * 1000.0) / 1.5;
            if (!isDC)
                glColor4f(part.secondColor.getRed() / 255F, part.secondColor.getGreen() / 255F, part.secondColor.getBlue() / 255F, (float) alpha);
            else
                glColor4f(color2.getRed() / 255F, color2.getGreen() / 255F, color2.getBlue() / 255F, (float) alpha);

            glVertex3d(x, y, z);
        }

        glEnd();

        glBegin(GL_LINE_STRIP);

        for(TrailsPart part : trailsParts) {
            double x = part.getX() - RenderManager.viewerPosX;
            double y = part.getY() - RenderManager.viewerPosY + 0.1f;
            double z = part.getZ() - RenderManager.viewerPosZ;

            double alpha = (length.getValue() * 1000.0 - (System.currentTimeMillis() - part.getCreationTime())) / (length.getValue() * 1000.0) / 1.5;

            if (!isDC)
                glColor4f(part.firstColor.getRed() / 255F, part.firstColor.getGreen() / 255F, part.firstColor.getBlue() / 255F, (float) alpha);
            else
                glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, (float) alpha);

            glVertex3d(x, y + mc.player.getEyeHeight(), z);
        }

        glEnd();

        glColor4f(1f, 1f, 1f, 1f);
        glEnable(GL_ALPHA_TEST);
        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
    }
}
