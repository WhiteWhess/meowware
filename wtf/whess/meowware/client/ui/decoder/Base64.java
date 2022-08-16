package wtf.whess.meowware.client.ui.hashbrute;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import wtf.whess.meowware.client.api.fontmanager.FontManager;

import java.awt.*;
import java.io.IOException;

public final class Base64 extends GuiScreen {
    private final GuiScreen parentScreen;
    private GuiTextField string;
    private String str = "";

    public Base64(final GuiScreen guiscreen) {
        this.parentScreen = guiscreen;
    }

    @Override
    public void updateScreen() {
        this.string.updateCursorCounter();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        if (!guibutton.enabled)
            return;
        switch (guibutton.id) {
            case 0: {
                mc.displayGuiScreen(parentScreen);
                break;
            }
            case 1: {
                str = new String(java.util.Base64.getDecoder().decode(string.getText()));
                break;
            }
        }
    }

    @Override
    protected void keyTyped(final char c, final int i) {
        this.string.textboxKeyTyped(c, i);
        if (c == '\t' && this.string.isFocused()) {
            this.string.setFocused(false);
        }
        if (c == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(final int i, final int j, final int k) throws IOException {
        super.mouseClicked(i, j, k);
        this.string.mouseClicked(i, j, k);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 74 + 42, "Decode"));
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 85 + 64, "Cancel"));
        this.string = new GuiTextField(2, this.fontRendererObj, width / 2 - 100, 116, 200, 20);
    }

    @Override
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        assert FontManager.montserratLight18 != null;
        this.string.drawTextBox();
        FontManager.montserratLight18.drawString(!this.string.getText().isEmpty() ? "" : "Encoded String", width / 2F - 95, 120, Color.gray.getRGB());
        FontManager.montserratLight18.drawStringWithShadow(str.isEmpty() ? "" : "Decoded String : " + str, width / 2F - 95, 150, 1);
        super.drawScreen(i, j, f);
    }
}
