package wtf.whess.meowware.client.api.utilities.render.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import wtf.whess.meowware.client.api.utilities.Utility;
import wtf.whess.meowware.client.api.utilities.misc.MathUtil;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Random;

public final class ColorUtil extends Utility {
    public static int getColor(Color color) {
        return ColorUtil.getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static Color getColorWithOpacity(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static int rainbow(int delay,float saturation, float brightness, long index) {
        double rainbowState = Math.ceil(System.currentTimeMillis() + index + (long)delay) / 15.0;
        return Color.getHSBColor((float)((rainbowState %= 360.0) / 360.0), saturation, brightness).getRGB();
    }

    public static Color fade(Color color) {
        return ColorUtil.fade(color, 2, 100);
    }

    public static int color(int n, int n2, int n3, int n4) {
        n4 = 255;
        return new Color(n, n2, n3, n4).getRGB();
    }

    public static Color getAlpha(int n4) {
        return new Color(255,255,255,n4);
    }

    public static int getRandomColor() {
        char[] letters = "012345678".toCharArray();
        StringBuilder color = new StringBuilder("0x");
        for (int i = 0; i < 6; ++i) {
            color.append(letters[new Random().nextInt(letters.length)]);
        } return Integer.decode(color.toString());
    }

    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = 0.003921569f * (float)c.getRed();
        float g = 0.003921569f * (float)c.getGreen();
        float b = 0.003921569f * (float)c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }

    public static Color getGradientOffset(Color color1, Color color2, double offset) {
        if (offset > 1.0) {
            double left = offset % 1.0;
            int off = (int)offset;
            offset = off % 2 == 0 ? left : 1.0 - left;
        }
        double inverse_percent = 1.0 - offset;
        int redPart = (int)((double)color1.getRed() * inverse_percent + (double)color2.getRed() * offset);
        int greenPart = (int)((double)color1.getGreen() * inverse_percent + (double)color2.getGreen() * offset);
        int bluePart = (int)((double)color1.getBlue() * inverse_percent + (double)color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart);
    }

    public static int getColor1(int brightness) {
        return ColorUtil.getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(int red, int green, int blue) {
        return ColorUtil.getColor(red, green, blue, 255);
    }

    public static int getColor(int red, int green, int blue, int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        return color |= blue;
    }

    public static int getColor(int brightness) {
        return ColorUtil.getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(int brightness, int alpha) {
        return ColorUtil.getColor(brightness, brightness, brightness, alpha);
    }

    public static Color getColor4(int brightness, int alpha) {
        return new Color(brightness, brightness, brightness, alpha);
    }

    public static Color fade(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0f + (float)index / (float)count * 2.0f) % 2.0f - 1.0f);
        brightness = 0.5f + 0.5f * brightness;
        hsb[2] = brightness % 2.0f;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color getHealthColor(EntityLivingBase entityLivingBase) {
        float health = entityLivingBase.getHealth();
        float[] fractions = new float[]{0.0f, 0.15f, 0.55f, 0.7f, 0.9f};
        Color[] colors = new Color[]{new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
        float progress = health / entityLivingBase.getMaxHealth();
        return health >= 0.0f ? ColorUtil.blendColors(fractions, colors, progress).brighter() : colors[0];
    }

    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        if (fractions == null) {
            throw new IllegalArgumentException("Fractions can't be null");
        }
        if (colors == null) {
            throw new IllegalArgumentException("Colours can't be null");
        }
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
        }
        int[] indicies = ColorUtil.getFractionIndicies(fractions, progress);
        float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
        Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};
        float max = range[1] - range[0];
        float value = progress - range[0];
        float weight = value / max;
        return ColorUtil.blend(colorRange[0], colorRange[1], 1.0f - weight);
    }

    public static int[] getFractionIndicies(float[] fractions, float progress) {
        int startPoint;
        int[] range = new int[2];
        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
        }
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float)ratio;
        float ir = 1.0f - r;
        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        if (red < 0.0f) {
            red = 0.0f;
        } else if (red > 255.0f) {
            red = 255.0f;
        }
        if (green < 0.0f) {
            green = 0.0f;
        } else if (green > 255.0f) {
            green = 255.0f;
        }
        if (blue < 0.0f) {
            blue = 0.0f;
        } else if (blue > 255.0f) {
            blue = 255.0f;
        }
        Color color = null;
        try {
            color = new Color(red, green, blue);
        }
        catch (IllegalArgumentException exp) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
        }
        return color;
    }

    public static int astolfo(int delay, float offset) {
        float hue;
        float speed = 3000.0f;
        for (hue = Math.abs((float)(System.currentTimeMillis() % (long)delay) + -offset / 21.0f * 2.0f); hue > speed; hue -= speed) {
        }
        if ((double)(hue /= speed) > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.HSBtoRGB(hue += 0.5f, 0.5f, 1.0f);
    }

    public static Color astolfo1(int delay, float offset) {
        float hue;
        float speed = 3000.0f;
        for (hue = Math.abs((float)(System.currentTimeMillis() % (long)delay) + -offset / 21.0f * 2.0f); hue > speed; hue -= speed) {
        }
        if ((double)(hue /= speed) > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.getHSBColor(hue += 0.5f, 0.5f, 1.0f);
    }

    public static int astolfo(float speed, int delay, float offset) {
        float hue;
        for (hue = Math.abs((float)(System.currentTimeMillis() % (long)delay) + -offset / 21.0f * 2.0f); hue > speed; hue -= speed) {
        }
        if ((double)(hue /= speed) > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.HSBtoRGB(hue += 0.5f, 0.5f, 1.0f);
    }

    public static Color astolfo1(float speed, int delay, float offset) {
        float hue;
        for (hue = Math.abs((float)(System.currentTimeMillis() % (long)delay) + -offset / 21.0f * 2.0f); hue > speed; hue -= speed) {
        }
        if ((double)(hue /= speed) > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.getHSBColor(hue += 0.5f, 0.5f, 1.0f);
    }

    public static int Yellowastolfo(int delay, float offset) {
        float hue;
        float speed = 2900.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + ((float)(-delay) - offset) * 9.0f; hue > speed; hue -= speed) {
        }
        if ((double)(hue /= speed) > 0.6) {
            hue = 0.6f - (hue - 0.6f);
        }
        return Color.HSBtoRGB(hue += 0.6f, 0.5f, 1.0f);
    }

    public static Color Yellowastolfo1(long delay, float offset) {
        float hue;
        float speed = 2900.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + ((float)delay - offset) * 9.0f; hue > speed; hue -= speed) {
        }
        if ((double)(hue /= speed) > 0.6) {
            hue = 0.6f - (hue - 0.6f);
        }
        return new Color(hue += 0.6f, 0.5f, 1.0f);
    }

    public static Color TwoColoreffect(Color cl1, Color cl2, double speed) {
        double thing = speed / 4.0 % 1.0;
        float val = MathUtil.clamp((float)Math.sin(Math.PI * 6 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
        return new Color(MathUtil.lerp((float)cl1.getRed() / 255.0f, (float)cl2.getRed() / 255.0f, val), MathUtil.lerp((float)cl1.getGreen() / 255.0f, (float)cl2.getGreen() / 255.0f, val), MathUtil.lerp((float)cl1.getBlue() / 255.0f, (float)cl2.getBlue() / 255.0f, val));
    }

    public static Color TwoColoreffect2(Color cl1, Color cl2, double speedRed, double speedGreen, double speedBlue) {
        double red = 0,green = 0,blue = 0;

        if (cl1.getRed() <= 255) {
            red = red + speedRed;
        } else {
            red = red - speedRed;
        }

        if (cl1.getGreen() <= 255) {
            green = green + speedGreen;
        } else {
            green = green - speedGreen;
        }

        if (cl1.getBlue() <= 255) {
            blue = blue + speedBlue;
        } else {
            blue = blue - speedBlue;
        }

        return new Color((int) red, (int) green, (int) blue);

    }

    public static int TwoColoreffect1(int cl1r,int cl1g,int cl1b, int cl2r,int cl2g,int cl2b,int alpha, double speed)
    {
        double thing = speed / 4.0 % 1.0;
        float val = MathUtil.clamp((float)Math.sin(Math.PI * 6 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
        return ColorUtil.getColor((int) MathUtil.lerp(cl1r, cl2r, val), (int) MathUtil.lerp(cl1g, cl2g, val), (int) MathUtil.lerp(cl1b, cl2b, val),alpha);
    }


    public static int astolfoColors(int yOffset, int yTotal) {
        float hue;
        float speed = 2900.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (float)((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
        }
        if ((double)(hue /= speed) > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.HSBtoRGB(hue += 0.5f, 0.5f, 1.0f);
    }

    public static int getTeamColor(Entity entityIn) {
        int i = -1;
        i = entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("\u043f\u0457\u0405f[\u043f\u0457\u0405cR\u043f\u0457\u0405f]\u043f\u0457\u0405c" + entityIn.getName()) ? ColorUtil.getColor(new Color(255, 60, 60)) : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("\u043f\u0457\u0405f[\u043f\u0457\u04059B\u043f\u0457\u0405f]\u043f\u0457\u04059" + entityIn.getName()) ? ColorUtil.getColor(new Color(60, 60, 255)) : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("\u043f\u0457\u0405f[\u043f\u0457\u0405eY\u043f\u0457\u0405f]\u043f\u0457\u0405e" + entityIn.getName()) ? ColorUtil.getColor(new Color(255, 255, 60)) : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase("\u043f\u0457\u0405f[\u043f\u0457\u0405aG\u043f\u0457\u0405f]\u043f\u0457\u0405a" + entityIn.getName()) ? ColorUtil.getColor(new Color(60, 255, 60)) : ColorUtil.getColor(new Color(255, 255, 255)))));
        return i;
    }

    public static Color astolfoColors1(int yOffset, int yTotal) {
        float hue;
        float speed = 2900.0f;
        for (hue = (float)(System.currentTimeMillis() % (long)((int)speed)) + (float)((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
        }
        if ((double)(hue /= speed) > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return new Color(hue += 0.5f, 0.5f, 1.0f);
    }

    public static Color rainbowCol(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + (long)delay) / 16L);
        return Color.getHSBColor((float)((rainbow %= 360.0) / 360.0), saturation, brightness);
    }

    public static Color rainbowCol(int delay, float saturation, float brightness, long y) {
        double rainbow = Math.ceil(((System.currentTimeMillis() + y) + (long)delay) / 16L);
        return Color.getHSBColor((float)((rainbow %= 360.0) / 360.0), saturation, brightness);
    }

    public static int rainbowNew(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + (long)delay) / 16L);
        return Color.getHSBColor((float)((rainbow %= 360.0) / 360.0), saturation, brightness).getRGB();
    }

    public static int rainbowNewWave(float delay, float saturation, float brightness, long index) {
        float hue = ((System.currentTimeMillis() + index) % (int)delay * 1000) / (delay * 1000);
        int color = Color.HSBtoRGB(hue,saturation,brightness);
        return color;
    }
}

