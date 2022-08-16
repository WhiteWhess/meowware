package wtf.whess.meowware.client.ui.hashbrute;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import wtf.whess.meowware.client.api.fontmanager.FontManager;
import wtf.whess.meowware.client.api.utilities.misc.MathUtil;

import java.awt.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public final class HashBruteforce extends GuiScreen {
    private final GuiScreen parentScreen;
    private GuiTextField string;
    private String pass = "";
    private ArrayList<String> sha256 = new ArrayList<>();
    private ArrayList<String> md5 = new ArrayList<>();

    public HashBruteforce(final GuiScreen guiscreen) {
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
                new Thread(() -> {
                    while (pass.isEmpty()) {
                        String str = MathUtil.randomString(new Random().nextInt(9));
                        while (md5.contains(str)) {
                            str = MathUtil.randomString(new Random().nextInt(9));
                        } if (toMD5(str).equals(string.getText())) {
                            pass = str; System.out.println("Congratulations! Password = " + str);
                        } md5.add(str);
                        System.out.println(str + toMD5(str));
                    }
                }).start();
                break;
            }
            case 2: {
                new Thread(() -> {
                    while (pass.isEmpty()) {
                        String str = MathUtil.randomString(new Random().nextInt(9));
                        while (sha256.contains(str)) {
                            str = MathUtil.randomString(new Random().nextInt(9));
                        } if (toSHA256(str).equals(string.getText())) {
                            pass = str; System.out.println("Congratulations! Password = " + str);
                        } sha256.add(str);
                        System.out.println(str + toSHA256(str));
                    }
                }).start();
                break;
            }
        }
    }

    public static String toSHA256(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(String.format("%02X", aByte));
            } return sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            return pass;
        }
    }

    public static String toMD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(String.format("%02X", aByte));
            } return sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            return pass;
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
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 74 + 42, "Brute MD5"));
        this.buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 74 + 64, "Brute SHA256"));
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 85 + 86, "Cancel"));
        this.string = new GuiTextField(3, this.fontRendererObj, width / 2 - 100, 116, 200, 20);
    }

    @Override
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        assert FontManager.montserratLight18 != null;
        this.string.drawTextBox();
        FontManager.montserratLight18.drawString(!this.string.getText().isEmpty() ? "" : "Hashed String", width / 2F - 95, 120, Color.gray.getRGB());
        FontManager.montserratLight18.drawString(pass.isEmpty() ? "" : "Found pass (" + pass + ")", width / 2F - 115, 120, Color.gray.getRGB());
        super.drawScreen(i, j, f);
    }
}
