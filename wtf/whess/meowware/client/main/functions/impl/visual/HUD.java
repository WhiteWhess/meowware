package wtf.whess.meowware.client.main.functions.impl.visual;

import wtf.whess.meowware.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import wtf.whess.meowware.MeowWare;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PacketReceiveEvent;
import wtf.whess.meowware.client.api.eventsystem.impl.RenderGameOverlayEvent;
import wtf.whess.meowware.client.api.fontmanager.FontManager;
import wtf.whess.meowware.client.api.utilities.misc.TimeUtil;
import wtf.whess.meowware.client.api.utilities.render.utils.RenderUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

import java.awt.*;
import java.util.ArrayList;

public final class HUD extends Function {
    public HUD() {
        super("HUD", Category.visual);
        setToggled(true);
    }

    private static final TimeUtil tpsTimer = new TimeUtil();
    public static double lastTps = 20.0;
    private static final ArrayList<Long> times = new ArrayList<>();

    @EventTarget
    public void onRender2D(RenderGameOverlayEvent ignoredEvent) {
        assert FontManager.montserrat20 != null;
        ScaledResolution sr = new ScaledResolution(mc);

        // Watermark
        String text = MeowWare.NAME.toUpperCase() + " " + MeowWare.VERSION;
        double width = FontManager.montserrat20.getStringWidth(text);
        double height = FontManager.montserrat20.getFontHeight();
        RenderUtil.drawRoundedRect(3, 3, width + 11, height + 7, 7.5F, new Color(21, 21, 21, 175).getRGB());
        FontManager.montserrat20.drawStringWithShadow(text, 7, 5, -1);

        // HUD
        FontManager.montserratLight18.drawStringWithShadow("TPS: " + String.format("%.2f", lastTps), 6, sr.getScaledHeight() - 25, -1);
        FontManager.montserratLight18.drawStringWithShadow("FPS: " + Minecraft.getDebugFPS(), 6, sr.getScaledHeight() - 15, -1);
    }

    @EventTarget
    public void onPacketRecieved(PacketReceiveEvent modPacket) {
        if (!(modPacket.getPacket() instanceof SPacketTimeUpdate))
            return;
        times.add(Math.max(1000L, tpsTimer.getTime()));
        long timesAdded = 0L;
        if (times.size() > 5)
            times.remove(0);
        for (long l : times)
            timesAdded += l;
        long roundedTps = timesAdded / (long) times.size();
        lastTps = 20.0 / (double) roundedTps * 1000.0;
        tpsTimer.reset();
    }
}
