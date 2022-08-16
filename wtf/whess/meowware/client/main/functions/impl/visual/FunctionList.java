package wtf.whess.meowware.client.main.functions.impl.visual;

import wtf.whess.meowware.MeowWare;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.RenderGameOverlayEvent;
import wtf.whess.meowware.client.api.fontmanager.FontManager;
import wtf.whess.meowware.client.api.utilities.render.utils.RenderUtil;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public final class FunctionList extends Function {
    public FunctionList() {
        super("FunctionList", Category.misc);
        setToggled(true);
    }

    @EventTarget
    public void onRender(RenderGameOverlayEvent ignoredEvent) {
        assert FontManager.montserratLight18 != null; int y = 15;
        ArrayList<Function> functions = MeowWare.getInstance().getFunctionManager().getFunctions();
        functions.sort(Comparator.comparingInt(component -> FontManager.montserratLight18.getStringWidth(component.toString().toLowerCase())).reversed());
        for (Function function: functions) {
            if (function.isToggled()) {
                RenderUtil.drawRect(2, 10 + y, 7 + FontManager.montserratLight18.getStringWidth(function.getName().toLowerCase()), 10 + FontManager.montserratLight18.getFontHeight() + y, new Color(21, 21, 21, 170).getRGB());
                FontManager.montserratLight18.drawString(function.getName().toLowerCase(), 4, 10 + y, -1);
                y += FontManager.montserratLight18.getFontHeight();
            }
        }
    }
}
