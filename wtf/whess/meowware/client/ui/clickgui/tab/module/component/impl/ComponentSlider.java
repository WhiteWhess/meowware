package wtf.whess.meowware.client.ui.clickgui.tab.module.component.impl;

import wtf.whess.meowware.client.ui.clickgui.tab.module.component.Component;
import wtf.whess.meowware.client.api.fontmanager.FontManager;
import wtf.whess.meowware.client.main.settings.impl.NumberSetting;
import wtf.whess.meowware.client.api.utilities.render.utils.HoverUtils;
import wtf.whess.meowware.client.api.utilities.render.utils.RenderUtil;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public final class ComponentSlider extends Component {

    private final HashMap<ComponentSlider, Double> translate = new HashMap<ComponentSlider, Double>();

    private final NumberSetting setting;
    private boolean dragging;

    private double percentValue;
    private double translateX, translateY;

    public ComponentSlider(int x, int y, NumberSetting setting) {
        super(x, y);

        this.setting = setting;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.percentValue = ((55.0)) * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());

        RenderUtil.drawRect(x, y, x + width, y + height, new Color(0x151515).hashCode());
        RenderUtil.drawRect(x + (width - 76.5), y + 6.5, x + (width - 76.5) + 55.0, y + 6.5 + 1.0, new Color(0x252525).hashCode());

        RenderUtil.drawRect(x + (width - 15.5) - 0.5, y + 3.5 - 0.5, x + (width - 15.5) + 13.0 + 0.5, y + 3.5 + 7.0 + 0.5, new Color(0x181818).hashCode());
        RenderUtil.drawRect(x + (width - 15.5), y + 3.5, x + (width - 15.5) + 13.0, y + 3.5 + 7.0, new Color(0x0D0D05).hashCode());

        /*if(this.translate.containsKey(this)) {
            this.translate.entrySet().forEach(t -> t.setValue((double) Interpolator.EASE_OUT.interpolate((double) t.getValue(), 0.0, 0.55)));
            this.translateX = this.translate.get(this);
        }*/ //anim

        RenderUtil.drawRect(x + (width - 76.5), y + 6.5, x + (width - 76.5) + percentValue - translateX, y + 6.5 + 1.0, new Color(0xF1F1F1).hashCode());

        RenderUtil.drawFilledCircle(x + (width - 76.5) + percentValue - translateX, y + 7.0, 3.2, new Color(0xF1F1F1).hashCode());
        RenderUtil.drawCircle(x + (width - 76.5) + percentValue - translateX, y + 7.0, 3.2, new Color(0xF1F1F1).hashCode());

        FontManager.museo_500.drawStringWithShadow(this.setting.getName(), (int) (x + 4.5), y + height / 2f - FontManager.museo_500.getFontHeight() / 2f, new Color(0xA4A4A4).hashCode());
        FontManager.museo_500.drawStringWithShadow(Integer.toString((int) Math.round(setting.getValue())), (x + width - 14.5f), y + height / 2f - FontManager.museo_500.getFontHeight() / 2f + 1.0f, new Color(0xA4A4A4).hashCode());

        if(this.dragging) {
            double diff = Math.min((55.0), Math.max(0, mouseX - (x + (width - 76.5))));

            double min = setting.getMin();
            double max = setting.getMax();

            if (diff == 0) {
                setting.setValue(setting.getMin());
            }else {
                double value = roundToPlace(((diff / (55.0)) * (max - min) + min), 2);
                setting.setValue(value);
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.dragging = (HoverUtils.hovered(x + (width - 77), y, x + (width - 77) + 55, y + height, mouseX, mouseY) && !locked) && mouseButton == 0;
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void initGui() {
        this.translate.put(this, this.percentValue);
        super.initGui();
    }

    private double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
