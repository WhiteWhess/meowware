package wtf.whess.meowware.client.ui.proxy;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import wtf.whess.meowware.MeowWare;
import wtf.whess.meowware.client.api.fontmanager.FontManager;

import java.awt.*;
import java.io.IOException;

public final class ProxyGui extends GuiScreen {
    private GuiScreen parentScreen;
    private GuiTextField ipPortTextField;
    public static String strIpPort;

    static {
        ProxyGui.strIpPort = "";
    }

    public ProxyGui(final GuiScreen guiscreen) {
        this.parentScreen = guiscreen;
    }

    @Override
    public void updateScreen() {
        this.ipPortTextField.updateCursorCounter();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        if (!guibutton.enabled) {
            return;
        }
        if (guibutton.id == 0) {
            MeowWare.getInstance().setProxyType(ProxyType.SOCKS5);
            MeowWare.setProxyIP(this.ipPortTextField.getText());
            System.out.println("[MeowWare] Setted proxy socks5 " + MeowWare.getProxyIP());
        }
        else if (guibutton.id == 1) {
            MeowWare.getInstance().setProxyType(ProxyType.SOCKS4);
            MeowWare.setProxyIP(this.ipPortTextField.getText());
            System.out.println("[MeowWare] Setted proxy socks4 " + MeowWare.getProxyIP());
        }
        else if (guibutton.id == 2) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        else if (guibutton.id == 3) {
            try {
                ProxyManager.ProxyItem proxyItem = ProxyManager.genProxy();
                MeowWare.getInstance().setProxyType(proxyItem.getType());
                MeowWare.setProxyIP(proxyItem.getAddress().toString().replace("/", ""));
            } catch (Exception ignored) {}

            System.out.println("[MeowWare] Setted proxy " + MeowWare.getInstance().getProxyType().name().toLowerCase() + " " + MeowWare.getProxyIP());
        }
        else if (guibutton.id == 4) {
            MeowWare.getInstance().setProxyType(ProxyType.NONE);
            System.out.println("[MeowWare] Setted ip to default");
        }
        else if (guibutton.id == 5) {
            MeowWare.getInstance().setProxyType(ProxyType.HTTP);
            MeowWare.setProxyIP(this.ipPortTextField.getText());
            System.out.println("[MeowWare] Setted proxy http " + MeowWare.getProxyIP());
        }
    }

    @Override
    protected void keyTyped(final char c, final int i) {
        this.ipPortTextField.textboxKeyTyped(c, i);
        if (c == '\t' && this.ipPortTextField.isFocused()) {
            this.ipPortTextField.setFocused(false);
        }
        if (c == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(final int i, final int j, final int k) throws IOException {
        super.mouseClicked(i, j, k);
        this.ipPortTextField.mouseClicked(i, j, k);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 50, "Reset Proxy"));
        this.buttonList.add(new GuiButton(3, width / 2 - 100, height / 4 + 72, "Random Proxy"));
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 74 + 20, "Connect Socks5"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 74 + 42, "Connect Socks4"));
        this.buttonList.add(new GuiButton(5, width / 2 - 100, height / 4 + 74 + 64, "Connect HTTP"));
        this.buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 85 + 86, "Cancel"));
        this.ipPortTextField = new GuiTextField(2, this.fontRendererObj, width / 2 - 100, 116, 200, 20);
    }

    @Override
    public void drawScreen(final int i, final int j, final float f) {
        String text = "Current Proxy " + (!MeowWare.getInstance().getProxyType().name().equals(ProxyType.NONE.name()) ? MeowWare.getInstance().getProxyType().name() + " " + MeowWare.getProxyIP() : "127.0.0.1");
        this.drawDefaultBackground();
        FontManager.montserratLight18.drawStringWithShadow(text, (width / 2F) - FontManager.montserratLight18.getStringWidth(text) / 2F, height / 4F - 60 + 20, -1);
        this.ipPortTextField.drawTextBox();
        FontManager.montserratLight18.drawString(!this.ipPortTextField.getText().isEmpty() ? "" : "ip:port", width / 2F - 95, 120, Color.gray.getRGB());
        super.drawScreen(i, j, f);
    }
}