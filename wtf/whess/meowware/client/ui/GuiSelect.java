package wtf.whess.meowware.client.ui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;
import viamcp.gui.GuiProtocolSelector;
import wtf.whess.meowware.client.ui.alt.GuiAltManager;
import wtf.whess.meowware.client.ui.hashbrute.Base64;
import wtf.whess.meowware.client.ui.hashbrute.HashBruteforce;
import wtf.whess.meowware.client.ui.monitoring.Monitoring;
import wtf.whess.meowware.client.ui.mysqlbrute.MySQLBruteforce;
import wtf.whess.meowware.client.ui.portscanner.PortScanner;
import wtf.whess.meowware.client.ui.proxy.ProxyGui;
import wtf.whess.meowware.client.ui.uuidspoofer.UUIDSpoofer;

public final class GuiSelect extends GuiScreen {
    private final GuiScreen parentScreen;
    private final PortScanner portScanner = new PortScanner(this);

    public GuiSelect(final GuiScreen guiscreen) {
        this.parentScreen = guiscreen;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
        if (!button.enabled)
            return;
        switch (button.id) {
            case 0: {
                mc.displayGuiScreen(parentScreen);
                break;
            }

            case 77: {
                this.mc.displayGuiScreen(new GuiAltManager(this));
                break;
            }

            case 34: {
                this.mc.displayGuiScreen(new Base64(this));
                break;
            }

            case 35: {
                this.mc.displayGuiScreen(new MySQLBruteforce(this));
                break;
            }

            case 87: {
                this.mc.displayGuiScreen(new ProxyGui(this));
                break;
            }

            case 69: {
                this.mc.displayGuiScreen(new GuiProtocolSelector(this));
                break;
            }

            case 64: {
                this.mc.displayGuiScreen(new Monitoring(this));
                break;
            }

            case 62: {
                this.mc.displayGuiScreen(portScanner);
                break;
            }

            case 39: {
                this.mc.displayGuiScreen(new HashBruteforce(this));
                break;
            }

            case 63: {
                this.mc.displayGuiScreen(new UUIDSpoofer(this));
                break;
            }
        }
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(77, this.width / 2 - 100, height / 4 + 85 - 24 * 5, I18n.format("Alt Manager")));
        this.buttonList.add(new GuiButton(87, this.width / 2 - 100, height / 4 + 85 - 24 * 4, I18n.format("Proxies")));
        this.buttonList.add(new GuiButton(64, this.width / 2 - 100, height / 4 + 85 - 24 * 3, I18n.format("Server Monitoring")));
        this.buttonList.add(new GuiButton(69, this.width / 2 - 100, height / 4 + 85 - 24 * 2, I18n.format("ViaVersion")));
        this.buttonList.add(new GuiButton(62, this.width / 2 - 100, height / 4 + 85 - 24, I18n.format("Port Scanner")));
        this.buttonList.add(new GuiButton(63, this.width / 2 - 100, height / 4 + 85, I18n.format("UUID Spoofer")));
        this.buttonList.add(new GuiButton(39, this.width / 2 - 100, height / 4 + 85 + 24, I18n.format("Hash Bruteforce")));
        this.buttonList.add(new GuiButton(34, this.width / 2 - 100, height / 4 + 85 + 24 * 2, I18n.format("Base64 Decoder")));
        this.buttonList.add(new GuiButton(35, this.width / 2 - 100, height / 4 + 85 + 24 * 3, I18n.format("MySQL Bruteforce")));
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 85 + 24 * 4, "Cancel"));
    }

    @Override
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        super.drawScreen(i, j, f);
    }
}
