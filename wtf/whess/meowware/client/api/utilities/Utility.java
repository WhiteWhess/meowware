package wtf.whess.meowware.client.api.utilities;

import wtf.whess.meowware.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.Random;

public abstract class Utility {
    protected static final Minecraft mc = Minecraft.getMinecraft();
    protected static final ScaledResolution sr = new ScaledResolution(mc);
    protected static final Random random = new Random();
}
