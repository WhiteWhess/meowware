package wtf.whess.meowware.client.ui.portscanner;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import wtf.whess.meowware.client.api.fontmanager.FontManager;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public final class PortScanner extends GuiScreen {
    private final GuiScreen parentScreen;
    private GuiTextField ipTextField;
    private GuiTextField min, max;

    private final ArrayList<String> ports = new ArrayList<>(),
                                    checkedPorts = new ArrayList<>();

    public PortScanner(final GuiScreen guiscreen) {
        this.parentScreen = guiscreen;
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
            case 1: {
                ports.clear(); checkedPorts.clear();
                new Thread(() -> {
                    for (int i = Integer.parseInt(min.getText()); i <= Integer.parseInt(max.getText()); ++i) {
                        if (this.portIsValid(ipTextField.getText(), i, 150)) {
                            System.out.println(i + " >> true");
                            ports.add(String.valueOf(i));
                        } else {
                            System.out.println(i + " >> false");
                        } checkedPorts.add(String.valueOf(i));
                    } System.out.println(ports);
                    final File file = new File("ports.txt");
                    try {
                        if (!file.exists())
                            if (!file.createNewFile())
                                throw new RuntimeException();
                        FileWriter fileWriter = new FileWriter(file);
                        fileWriter.write(ports.toString());
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
            }
            case 0: {
                mc.displayGuiScreen(parentScreen);
                break;
            }
        }

    }

    @Override
    protected void keyTyped(final char c, final int i) {
        this.ipTextField.textboxKeyTyped(c, i);
        if (c == '\t' && this.ipTextField.isFocused()) {
            this.ipTextField.setFocused(false);
        }
        this.min.textboxKeyTyped(c, i);
        if (c == '\t' && this.min.isFocused()) {
            this.min.setFocused(false);
        }
        this.max.textboxKeyTyped(c, i);
        if (c == '\t' && this.max.isFocused()) {
            this.max.setFocused(false);
        }
        if (c == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(final int i, final int j, final int k) throws IOException {
        super.mouseClicked(i, j, k);
        this.ipTextField.mouseClicked(i, j, k);
        this.min.mouseClicked(i, j, k);
        this.max.mouseClicked(i, j, k);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 70, "Scanner"));
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 92, "Cancel"));
        this.ipTextField = new GuiTextField(2, this.fontRendererObj, width / 2 - 100, 126, 200, 20);
        this.min = new GuiTextField(3, this.fontRendererObj, width / 2 - 100, 96, 200, 20);
        this.max = new GuiTextField(4, this.fontRendererObj, width / 2 - 100, 66, 200, 20);
    }

    @Override
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        this.ipTextField.drawTextBox();
        this.min.drawTextBox();
        this.max.drawTextBox();
        assert FontManager.montserratLight18 != null;
        FontManager.montserratLight18.drawString(!this.ipTextField.getText().isEmpty() ? "" : "IP Address", width / 2F - 95, 130, Color.gray.getRGB());
        FontManager.montserratLight18.drawString(!this.max.getText().isEmpty() ? "" : "End port", width / 2F - 95, 70, Color.gray.getRGB());
        FontManager.montserratLight18.drawString(!this.min.getText().isEmpty() ? "" : "Start port", width / 2F - 95, 100, Color.gray.getRGB());
        FontManager.montserratLight18.drawCentralizedStringWithShadow(ports.toString(), width / 2F, 160, -1);
        FontManager.montserratLight18.drawCentralizedStringWithShadow("Processing " + (checkedPorts.size() - 1), width / 2F, 175, -1);
        super.drawScreen(i, j, f);
    }

    public boolean portIsValid(final String s, final int n, final int n2) {
        try {
            final Socket socket = new Socket();
            socket.connect(new InetSocketAddress(s, n), n2);
            socket.close();
            return true;
        }
        catch (final Exception ex) {
            return false;
        }
    }

}