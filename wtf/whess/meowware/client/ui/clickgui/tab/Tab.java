package wtf.whess.meowware.client.ui.clickgui.tab;

import wtf.whess.meowware.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import wtf.whess.meowware.MeowWare;
import wtf.whess.meowware.client.ui.clickgui.ClickGuiScreen;
import wtf.whess.meowware.client.ui.clickgui.tab.module.ModuleTab;
import wtf.whess.meowware.client.api.fontmanager.FontManager;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.api.utilities.misc.MathUtil;
import wtf.whess.meowware.client.api.utilities.render.utils.HoverUtils;
import wtf.whess.meowware.client.api.utilities.render.utils.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Tab {
    Minecraft mc = Minecraft.getMinecraft();
    private HashMap<Tab, Integer> alpha = new HashMap<Tab, Integer>();

    private List<ModuleTab> tabs = new ArrayList<>();

    public int x, y, width, height;
    public Category category;

    private ClickGuiScreen parent;

    private double targetAlpha = 0;
    private float scrollOffset, renderScrollOffset;

    public Tab(int x, int y, Category category, ClickGuiScreen parent) {
        this.x = x;
        this.y = y;
        this.width = 80;
        this.height = 15;

        this.parent = parent;

        this.category = category;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        targetAlpha = MathUtil.animation((float) targetAlpha, isExtended() ? 200 : 0, 1000F / Minecraft.getDebugFPS());
        RenderUtil.drawRoundedRect(x + 60 - FontManager.museo_500.getStringWidth(category.getName().toLowerCase()), y, x + width + 1, y + height,5, new Color(180,180,180, (int) targetAlpha).getRGB());

        FontManager.museo_500.drawStringWithShadow(category.getName().toLowerCase(), x + 65 - FontManager.museo_500.getStringWidth(category.getName().toLowerCase()), y + height / 2F - FontManager.museo_500.getFontHeight() / 2F, -1);
        RenderUtil.drawImage(new ResourceLocation("meowware/category/" + category.getName().toLowerCase() + ".png"), x + 67, y + 2, 12, 12, new Color(255, 255, 255));

       /* if(this.alpha.containsKey(this)) {
            for (Map.Entry<Tab, Integer> tabIntegerEntry : this.alpha.entrySet()) {
                tabIntegerEntry.setValue(Interpolator.EASE_OUT.interpolate((int) tabIntegerEntry.getValue(), 200, 0.15));
            }
            this.targetAlpha = this.alpha.get(this);
        }*/

        if(this.isExtended()) {

            int x1 = x + 95;
            int y2 = parent.y + 35;

            for (ModuleTab tab : tabs) {
                if(((y2 - parent.y + 35) + 30 > (this.category.equals(Category.visual) ? 690 : this.parent.height) + 35) && ((x1 - x + 95) + 150 < this.parent.width)) {
                    x1 += 150;
                    y2 = parent.y + 35;
                }

                tab.x = x1;
                tab.y = y2 + (int) this.renderScrollOffset;

                y2 += 35 + tab.getComponentHeight();
            }

            renderScrollOffset = scrollOffset;

            if(this.renderScrollOffset > 0)
                renderScrollOffset = 0;

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);

            RenderUtil.prepareScissorBox(x + 95, parent.y + 35, x + 95 + this.parent.width, parent.y + 35 + parent.height - 45);

            tabs.forEach(t -> t.drawScreen(mouseX, mouseY, partialTicks));

            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopMatrix();
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
        if(this.isExtended()) {
            tabs.forEach(t -> t.keyTyped(typedChar, keyCode));
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

        if(this.isExtended()) {
            tabs.forEach(t -> t.mouseClicked(mouseX, mouseY, mouseButton));
        }

        if(HoverUtils.hovered(x, y, x + width, y + height, mouseX, mouseY)) {
            if(mouseButton == 0) {
                this.alpha.put(this, 0);

                MeowWare.getClickGuiScreen().setCurrentTab(this);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if(this.isExtended()) {
            tabs.forEach(t -> t.mouseReleased(mouseX, mouseY, state));
        }
    }

    public void initGui() {
        tabs.clear();
        int x1 = x + 95;
        int y2 = parent.y + 35;
        for (Function module : MeowWare.getInstance().getFunctionManager().getFunctions()) {
            if(module.getCategory() == this.category) {

                if(((y2 - parent.y + 35) + 30 > this.parent.height + 35) && ((x1 - x + 95) + 150 < this.parent.width)) {
                    x1 += 150;
                    y2 = parent.y + 35;
                }

                tabs.add(new ModuleTab(x1, y2, module));
                y2 += 35;
            }
        }

        if(this.isExtended()) {
            tabs.forEach(t -> t.initGui());
        }
    }

    private boolean isExtended() {
        return MeowWare.getClickGuiScreen().getCurrentTab() == this;
     }
    
    public void handleMouseInput() {

        int i = Mouse.getEventDWheel();
        i = Integer.compare(i, 0);

        this.scrollOffset += i * 7f;
    }
}
