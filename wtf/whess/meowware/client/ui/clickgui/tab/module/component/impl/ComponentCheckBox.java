package wtf.whess.meowware.client.ui.clickgui.tab.module.component.impl;

import wtf.whess.meowware.client.ui.clickgui.tab.module.component.Component;
import wtf.whess.meowware.client.api.fontmanager.FontManager;
import wtf.whess.meowware.client.main.settings.impl.BooleanSetting;
import wtf.whess.meowware.client.api.utilities.render.utils.HoverUtils;
import wtf.whess.meowware.client.api.utilities.render.utils.RenderUtil;

import java.awt.*;
import java.util.HashMap;

public final class ComponentCheckBox extends Component {

    private final HashMap<ComponentCheckBox, Double> translate = new HashMap<>();

    private final BooleanSetting setting;

    private double translateX, translateY;

    public ComponentCheckBox(int x, int y, BooleanSetting setting) {
        super(x, y);
        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(x, y, x + width, y + height, new Color(0x151515).hashCode());

        for(int i = 0; i < 11; i++) {
            RenderUtil.drawFilledCircle(x + width - 16.5 + i, y + 7.0, 2.5, this.setting.isToggled() ? new Color(0x303030).hashCode() : new Color(0x0D0D0D).hashCode());
            RenderUtil.drawCircle(x + width - 16.5 + i, y + 7.0, 2.5, this.setting.isToggled() ? new Color(0x303030).hashCode() : new Color(0x0D0D0D).hashCode());
        }

        /*if(this.translate.containsKey(this)) {
            this.translate.entrySet().forEach(t -> t.setValue(Interpolator.EASE_OUT.interpolate((double) t.getValue(), 0.0, 0.15)));
            this.translateX = this.translate.get(this);
        }*/ //anim

        RenderUtil.drawFilledCircle(x + width - (this.setting.isToggled() ? 8 : 16.5) + this.translateX, y + 7.0, 3.2, this.setting.isToggled() ? new Color(0xF1F1F1).hashCode() : new Color(0x616161).hashCode());
        RenderUtil.drawCircle(x + width - (this.setting.isToggled() ? 8 : 16.5) + this.translateX, y + 7.0, 3.2, this.setting.isToggled() ? new Color(0xF1F1F1).hashCode() : new Color(0x616161).hashCode());

        FontManager.museo_500.drawStringWithShadow(this.setting.getName(), (x + 4.5f), y + height / 2f - FontManager.museo_500.getFontHeight() / 2f, this.setting.isToggled() ? -1 : new Color(0xA4A4A4).hashCode());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(HoverUtils.hovered(x, y, x + width, y + height, mouseX, mouseY) && !locked) {
            if(mouseButton == 0) {
                this.translate.put(this, 10.0);
                this.setting.toggle();
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
