package wtf.whess.meowware.client.ui.clickgui;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import wtf.whess.meowware.client.ui.clickgui.tab.Tab;
import wtf.whess.meowware.client.api.fontmanager.FontManager;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.impl.visual.ClickGUI;
import wtf.whess.meowware.client.api.utilities.misc.MathUtil;
import wtf.whess.meowware.client.api.utilities.render.utils.HoverUtils;
import wtf.whess.meowware.client.api.utilities.render.utils.RenderUtil;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ClickGuiScreen extends GuiScreen {

    private final List<Tab> tabs = new ArrayList<>();

    public int x, y, width, height, dragX, dragY;
    private boolean dragging;
    public int cfgX, cfgY, cfgWidth, cfgHeight, cfgDragX, cfgDragY;
    private boolean cfgDragging;
    private @Getter @Setter Tab currentTab;
    private boolean showConfigTab;
    //public boolean lightTheme;

    public ClickGuiScreen() {
        this.x = 5;
        this.y = 5;
        this.width = 400;
        this.height = 300;
        int y1 = y + 38;
        showConfigTab = false;
        this.cfgX = 5;
        this.cfgY = 5;
        this.cfgWidth = 140;
        this.cfgHeight = 200;

        //this.lightTheme = false;

        for (Category value : Category.values()) {
            tabs.add(new Tab(x + 5, y1, value, this));
            y1 += 15;
        }

        for (Tab tab : tabs) {
            if (tab.category.getName().equalsIgnoreCase("COMBAT")) {
                this.currentTab = tab;
            }
        }

    }

    /*private Color getColor() {
        if (lightTheme) {
            return new Color(255,235,235);
        } return new Color(0xE4100A0A, true);
    }*/

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);

        RenderUtil.drawGradient(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0,0,0,220).getRGB(), new Color(150, 150, 150, 219).getRGB());

        if(dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
            x = MathUtil.clamp(x,-20,sr.getScaledWidth() - width);
            y = MathUtil.clamp(y,0,sr.getScaledHeight() - height);
        }

        /*if(cfgDragging) {
            cfgX = mouseX - cfgDragX;
            cfgY = mouseY - cfgDragY;
            cfgX = MathUtil.clamp(cfgX,0,sr.getScaledWidth() - cfgWidth);
            cfgY = MathUtil.clamp(cfgY,0,sr.getScaledHeight() - cfgHeight);
        }*/

        RenderUtil.drawRoundedRect(x + 20, y, x + width, y + height, 5, new Color(0xD4252525, true).hashCode());
        RenderUtil.drawRoundedRect(x + 90.0, y, x + 90.0 + (width - 90.0), y + height,10, new Color(0xE3101010, true).getRGB());

        //RenderUtil.drawImage(new ResourceLocation("helse/img/icons/settings.png"), x + width - 20, y + 5, 14, 14, new Color(255,255,255));

        //RenderUtil.drawFilledCircleWithBlur(x + width - 12, y + 12, 6, -1,1);
        //RenderUtil.drawFilledCircleWithBlur(x + width - 28, y + 12, 6, new Color(0,0,0).getRGB(), 1);

        RenderUtil.drawRect(x + 90.0, y + 25.0, x + 90.0 + (width - 90.0), y + 25.0 + 1.0, new Color(0xBA181818, true).hashCode());

        //mc.fontRenderer.drawStringWithShadow("Save", (int) (x + 120.5), (int) (y + 11.5), new Color(0x84959C).hashCode());

        if (!ClickGUI.girl.is("Off"))
            RenderUtil.drawImage(new ResourceLocation("meowware/girls/"  + ClickGUI.girl.getMode().toLowerCase() + ".png"), sr.getScaledWidth() - 200, sr.getScaledHeight() - 300,200, 300, new Color(255,255,255));

        FontManager.museo_900.drawString("MW", (int) (x + 115.0 / 2 - FontManager.museo_900.getStringWidth("MW") / 2), (int) (y + 9.5), new Color(0xABABAB).hashCode());
        FontManager.museo_900.drawString("MW", (int) (x + 115.0 / 2 - FontManager.museo_900.getStringWidth("MW") / 2 - 1.5), y + 9, new Color(0xFFFFFF).hashCode());

        int y1 = y + 38;

        for (Tab tab : tabs) {
            tab.x = x + 5;
            tab.y = y1;

            //y1 += 15; //15
            y1 += 15;
        }

        tabs.forEach(t -> t.drawScreen(mouseX, mouseY, partialTicks));

        /*if (showConfigTab) {
            RenderUtil.drawRoundedRect(cfgX, cfgY, cfgX + cfgWidth, cfgY + cfgHeight,5, new Color(16,11,11).getRGB());

            RenderUtil.drawRoundedRect(cfgX + 5, cfgY + cfgHeight - 30, cfgX + cfgWidth - 5, cfgY + cfgHeight - 20,2, new Color(16,16,16, 150).getRGB());

            RenderUtil.drawRoundedRect(cfgX + 5, cfgY + cfgHeight - 17, cfgX + 45,cfgY + cfgHeight - 5,4, new Color(21,21,21).getRGB());
            FontManager.museo_300.drawStringWithShadow("Create", cfgX + 11, cfgY + cfgHeight - 17, -1);

            RenderUtil.drawRoundedRect(cfgX + 5 + 45, cfgY + cfgHeight - 17, cfgX + 45 + 45,cfgY + cfgHeight - 5,4, new Color(21,21,21).getRGB());
            FontManager.museo_300.drawStringWithShadow("Save", cfgX + 14 + 45, cfgY + cfgHeight - 17, -1);

            RenderUtil.drawRoundedRect(cfgX + 5 + 90, cfgY + cfgHeight - 17, cfgX + 45 + 90,cfgY + cfgHeight - 5,4, new Color(21,21,21).getRGB());
            FontManager.museo_300.drawStringWithShadow("Delete", cfgX + 14 + 87, cfgY + cfgHeight - 17, -1);
            //FontManager.museo_300.drawStringWithShadow(!cfgSearch.isEmpty() ? cfgSearch : "Name", cfgX + 8, cfgY + cfgHeight - 31, new Color(155,155,155,150).getRGB());

        }*/

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void drawHead(ResourceLocation skin, int width, int height) {
        GL11.glColor4f(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(skin);
        Gui.drawScaledCustomSizeModalRect(width, height, 8, 8, 8, 8, 19, 19, 64, 64);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        tabs.forEach(t -> t.keyTyped(typedChar, keyCode));
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        tabs.forEach(t -> t.mouseClicked(mouseX, mouseY, mouseButton));

        if(HoverUtils.hovered(x + 10, y, x + 91, y + 35, mouseX, mouseY)) {
            if(mouseButton == 0) {
                dragging = true;
                dragX = mouseX - x;
                dragY = mouseY - y;
            }
        }

        /*
        if(HoverUtils.hovered(cfgX, cfgY, cfgX + cfgWidth, cfgY + 15, mouseX, mouseY)) {
            if(mouseButton == 0) {
                cfgDragging = true;
                cfgDragX = mouseX - cfgX;
                cfgDragY = mouseY - cfgY;
            }
        }

        if(HoverUtils.hovered(x + width - 20, y + 5, x + width - 20 + 14, y + 5 + 14, mouseX, mouseY)) {
            if(mouseButton == 0) {
                cfgX = x + width + 15;
                cfgY = y;
                showConfigTab = !showConfigTab;
            }
        }
        */

        /*

        if(HoverUtils.hovered(x + width - 12 - 6, y + 12 - 6, x + width - 12 + 6, y + 12 + 6, mouseX, mouseY)) {
            if(mouseButton == 0) {
                lightTheme = true;
            }
        }

        if(HoverUtils.hovered(x + width - 28 - 6, y + 12 - 6, x + width - 28 + 6, y + 12 + 6, mouseX, mouseY)) {
            if(mouseButton == 0) {
                lightTheme = false;
            }
        }

        */

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        tabs.forEach(t -> t.mouseReleased(mouseX, mouseY, state));
        dragging = false;
        cfgDragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void handleMouseInput() throws IOException {
        tabs.forEach(t -> t.handleMouseInput());
        super.handleMouseInput();
    }

    @Override
    public void initGui() {
        tabs.forEach(t -> t.initGui());
        super.initGui();
    }
}
