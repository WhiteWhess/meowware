package wtf.whess.meowware.client.ui.clickgui.tab.module;

import org.lwjgl.input.Keyboard;
import wtf.whess.meowware.client.ui.clickgui.tab.module.component.impl.ComponentCheckBox;
import wtf.whess.meowware.client.ui.clickgui.tab.module.component.impl.ComponentComboBox;
import wtf.whess.meowware.client.ui.clickgui.tab.module.component.impl.ComponentSlider;
import wtf.whess.meowware.client.ui.clickgui.tab.module.component.Component;
import wtf.whess.meowware.client.api.fontmanager.FontManager;
import wtf.whess.meowware.client.main.functions.Function;
import wtf.whess.meowware.client.main.settings.Setting;
import wtf.whess.meowware.client.main.settings.impl.BooleanSetting;
import wtf.whess.meowware.client.main.settings.impl.ModeSetting;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;
import wtf.whess.meowware.client.api.utilities.render.utils.HoverUtils;
import wtf.whess.meowware.client.api.utilities.render.utils.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class ModuleTab {
    private final List<Component> components = new ArrayList<>();
    private final HashMap<ModuleTab, Double> translate = new HashMap<ModuleTab, Double>();

    public int x, y, width, height;
    private boolean binding;

    private double translateX, translateY;
    private double bindingX, bindingY;

    private final Function module;

    public ModuleTab(int x, int y, Function module) {
        this.x = x;
        this.y = y;
        this.width = 140;
        this.height = 30;

        this.module = module;

        int y3 = y + height;
        for (Setting setting : module.getSettings()) {
            if (setting.isVisible()) {
                if (setting instanceof NumberSetting) {
                    components.add(new ComponentSlider(x, y3, (NumberSetting) setting));
                    y3 += 15;
                } else if (setting instanceof BooleanSetting) {
                    components.add(new ComponentCheckBox(x, y3, (BooleanSetting) setting));
                    y3 += 15;
                } else if (setting instanceof ModeSetting) {
                    components.add(new ComponentComboBox(x, y3, (ModeSetting) setting));
                    y3 += 15;
                }
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        RenderUtil.drawRect(x, y, x + width, y + height, new Color(21, 21, 21).hashCode());
        RenderUtil.drawRect(x + 3.5, y + 15.0, x + 3.5 + (width - 7.0), y + 15.0 + 1.0, new Color(0x252525).hashCode());

        String bind = Keyboard.getKeyName(module.getBind()).toLowerCase();

        FontManager.museo_500.drawStringWithShadow(module.getName(), (int) (x + 4.5), (int) (y + 2.0), -1);

        FontManager.museo_500.drawStringWithShadow((binding ? "Enable [" + (bind.substring(0, 1).toUpperCase() + bind.substring(1)) + "]" : "Enable"), (int) (x + 4.5), (int) (y + 17.5), this.module.isToggled() ? -1 : new Color(0xA4A4A4).getRGB());


        for(int i = 0; i < 11; i++) {
            RenderUtil.drawFilledCircle(x + width - 16.5 + i, y + 23.5, 2.5, this.module.isToggled() ? new Color(0x303030).hashCode() : new Color(0x0D0D0D).hashCode());
            RenderUtil.drawCircle(x + width - 16.5 + i, y + 23.5, 2.5, this.module.isToggled() ? new Color(0x303030).hashCode() : new Color(0x0D0D0D).hashCode());
        }

       /*if(this.translate.containsKey(this)) {
            this.translate.entrySet().forEach(t -> t.setValue((double) Interpolator.EASE_OUT.interpolate((double) t.getValue(), 0.0, 0.15)));
            this.translateX = this.translate.get(this);
       }*/ //anim

        RenderUtil.drawFilledCircle(x + width - (this.module.isToggled() ? 8 : 16.5) - this.translateX, y + 23.5, 3.2, this.module.isToggled() ? new Color(0xF1F1F1).hashCode() : new Color(0x616161).hashCode());
        RenderUtil.drawCircle(x + width - (this.module.isToggled() ? 8 : 16.5) - this.translateX, y + 23.5, 3.2, this.module.isToggled() ? new Color(0xF1F1F1).hashCode() : new Color(0x616161).hashCode());

        int y3 = y + height;

        for (Component component : components) {
            component.x = x;
            component.y = y3;

            y3 += 15;
        }

        components.forEach(c -> c.drawScreen(mouseX, mouseY, partialTicks));
    }

    public void keyTyped(char typedChar, int keyCode) {

        components.forEach(c -> c.keyTyped(typedChar, keyCode));

        if(this.binding) {
            this.module.setBind(keyCode);
            this.binding = false;

            if(keyCode == Keyboard.KEY_ESCAPE) {
                this.module.setBind(0);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

        if(!(this.binding)) {
            components.forEach(c -> c.mouseClicked(mouseX, mouseY, mouseButton));
        }

        if(HoverUtils.hovered(x, y + 15, x + width, y + 15 + 15, mouseX, mouseY)) {
            if(mouseButton == 0 && !(this.binding)) {
                this.translate.put(this, 10.0);
                this.module.toggle();
            } else if(mouseButton == 1) {
                binding = !binding;
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        components.forEach(c -> c.mouseReleased(mouseX, mouseY, state));
    }

    public int getComponentHeight() {

        int height = 0;

        for (Setting ignored : module.getSettings()) {
            height += 15;
        }

        return height;
    }

    public void initGui() {
        components.forEach(c -> c.initGui());
    }
}
