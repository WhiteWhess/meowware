package wtf.whess.meowware.client.ui.clickgui.tab.module.component.impl;

import wtf.whess.meowware.client.ui.clickgui.tab.module.component.Component;
import wtf.whess.meowware.client.api.fontmanager.FontManager;
import wtf.whess.meowware.client.main.settings.impl.ModeSetting;
import wtf.whess.meowware.client.api.utilities.render.utils.HoverUtils;
import wtf.whess.meowware.client.api.utilities.render.utils.RenderUtil;

import java.awt.*;

public final class ComponentComboBox extends Component {

    private final ModeSetting setting;

    private boolean extended;

    public ComponentComboBox(int x, int y, ModeSetting setting) {
        super(x, y);

        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        RenderUtil.drawRect(x, y, x + width, y + height, new Color(0x151515).hashCode());

        locked = HoverUtils.hovered(x + (width - 55), y + 2, x + (width - 55) + 53, y + 2 + 9, mouseX, mouseY);

        FontManager.museo_500.drawStringWithShadow(this.setting.getName(), (int) (x + 4.5), y + height / 2f - FontManager.museo_500.getFontHeight() / 2f, this.extended ? -1 : new Color(0xA4A4A4).hashCode());
        FontManager.museo_500.drawStringWithShadow(this.setting.getMode(), (int) (x + (width - 5.0) - FontManager.museo_500.getStringWidth(this.setting.getMode())), y + height / 2f - FontManager.museo_500.getFontHeight() / 2f, this.extended ? -1 : new Color(0xA4A4A4).hashCode());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(HoverUtils.hovered(x, y + 2, x + width, y + 2 + 9, mouseX, mouseY)) {
            if(mouseButton == 0) {
                this.setting.cycle();
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

}
