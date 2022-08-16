package wtf.whess.meowware.client.main.functions.impl.visual;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.RenderGameOverlayEvent;
import wtf.whess.meowware.client.api.utilities.render.utils.RenderUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.functions.impl.combat.Aura;

import java.awt.*;
import java.util.Objects;

public final class TargetHUD extends Function {
    public TargetHUD() {
        super("TargetHUD", Category.visual);
    }

    @EventTarget
    public void onRenderOverlay(RenderGameOverlayEvent ignoredEvent) {
        if (mc.world != null && mc.player != null) {
            if (Aura.getTarget() == null)
                return;

            double posX = 60;
            double posY = -250;

            ScaledResolution sr = new ScaledResolution(mc);

            final float scaledWidth = sr.getScaledWidth();

            final float x = (float) (scaledWidth / 2.0f - posX);
            final float y = (float) (scaledWidth / 2.0f + posY);

            final float health = Math.round(Aura.getTarget().getHealth());
            double hpPercentage = MathHelper.clamp((health / 20) * 97, 0, 97);

            RenderUtil.drawRect((int) (x + 125.5), (int) (y - 9.5), (int) (x + 265), (int) (y + 30.5f), new Color(31, 31, 31, 255).getRGB());
            RenderUtil.drawRect((int) (x + 166.0f), (int) (y + 6.0f), (int) (x + 263.0f), (int) (y + 15.0f), new Color(40, 40, 40, 255).getRGB());
            RenderUtil.drawRect((int) (x + 166.0f), (int) (y + 6.0f), (int) (x + 166.0f + hpPercentage), (int) (y + 15.0f), new Color(0x2AFA00).getRGB());

            RenderUtil.drawRect((int) (x + 166.0f), (int) (y + 6.0f), (int) (x + 166.0f + hpPercentage), (int) (y + 15.0f), new Color(0x2AFA00).getRGB());

            mc.fontRendererObj.drawStringWithShadow(String.valueOf(health), x + 132.0f + 46.0f - mc.fontRendererObj.getStringWidth(String.valueOf(health)) / 2.0f, y + 19.5f, -1);
            mc.fontRendererObj.drawStringWithShadow("\u2764", x + 128.0f + 46.0f + mc.fontRendererObj.getStringWidth(String.valueOf(health)), y + 19.5f, new Color(0x2AFA00).getRGB());
            mc.fontRendererObj.drawStringWithShadow(Aura.getTarget().getName(), x + 167, y - 5.0f, -1);

            try {
                drawHead(Objects.requireNonNull(mc.getConnection()).getPlayerInfo(Aura.getTarget().getUniqueID()).getLocationSkin(), (int) (x + 127), (int) (y - 8));
            } catch (Exception ignored) {}
        }
    }

    private void drawHead(ResourceLocation skin, int width, int height) {
        GL11.glColor4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(skin);
        Gui.drawScaledCustomSizeModalRect(width, height, 8, 8, 8, 8, 37, 37, 64, 64);
    }
}
