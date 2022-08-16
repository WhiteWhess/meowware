package wtf.whess.meowware.client.ui.clickgui.tab.module.component;

import wtf.whess.meowware.Minecraft;

public abstract class Component {

    protected final Minecraft mc = Minecraft.getMinecraft();

    public int x, y, width, height;
    public static boolean locked;

    public Component(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 140;
        this.height = 15;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

    }

    public void keyTyped(char typedChar, int keyCode) {

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    public void initGui() {

    }
}
