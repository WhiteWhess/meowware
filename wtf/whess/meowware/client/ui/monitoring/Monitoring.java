package wtf.whess.meowware.client.ui.monitoring;

import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.client.network.ServerPinger;
import net.minecraft.client.resources.I18n;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Monitoring extends GuiScreen implements IGuiMultiplayer
{
    private final ServerPinger oldServerPinger;
    private final GuiScreen parentScreen;
    private ServerSelectionList serverListSelector;
    private ServerList savedServerList;
    public static ArrayList<String> servers = new ArrayList<>();
    private final GuiScreen guiScreen = this;

    public Monitoring(final GuiScreen parentScreen) {
        this.oldServerPinger = new ServerPinger();
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.savedServerList = new ServerList(this.mc);
        this.buttonList.add(new GuiButton(99, -10000, -10000, "сасеш"));
        this.buttonList.add(new GuiButton(0, 15, height - 400, "Generate Servers"));
        this.buttonList.add(new GuiButton(2, 15, height - 375, "Remove half of Servers"));
        this.buttonList.add(new GuiButton(3, 15, height - 350, "Remove"));
        this.buttonList.add(new GuiButton(5, 15, height - 325, "Remove All"));
        this.buttonList.add(new GuiButton(4, 15, height - 300, "Refresh Server List"));
        this.buttonList.add(new GuiButton(1, 15, height - 275, "Back"));
        (this.serverListSelector = new ServerSelectionList(this, this.mc, width, height, 32, height - 64, 36)).updateOnlineShkoloServers(this.savedServerList);
        this.selectServer(this.serverListSelector.getSelected());
    }

    @Override
    public void handleMouseInput() throws IOException {
        this.serverListSelector.handleMouseInput();
        super.handleMouseInput();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        this.oldServerPinger.pingPendingNetworks();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.oldServerPinger.clearPendingNetworks();
    }

    public void parseMonitoringMinecraft(final String version) {
        try {
            final ArrayList<String> servers = new ArrayList<String>();
            final List<String> temp = new CopyOnWriteArrayList<>();
            final Document document = Jsoup.connect("https://monitoringminecraft.ru/novie-servera-" + version).get();
            final Elements elements = document.getElementsByAttributeValue("class", "server");
            for (final Element element : elements) {
                temp.addAll(findStringsByRegex(element.text(), Pattern.compile(pattern)));
            } temp.removeIf(str -> !str.matches(pattern));
            servers.addAll(temp);
        }
        catch (Exception eg) {
            eg.printStackTrace();
        }
    }

    public void parseMinecraftRating(final String version) {
        try {
            List<String> temp = new CopyOnWriteArrayList<>();
            Document document = Jsoup.connect("https://minecraftrating.ru/new-servers/" + version).get();
            Elements elements = document.getElementsByAttributeValue("class", "tooltip");
            for (Element element : elements) {
                try {
                    String text = element.ownText();
                    if (!text.matches(".*[A-z].*") && findStringByRegex(text, Pattern.compile(pattern)) != null) {
                        try {
                            temp.add(findStringByRegex(text, Pattern.compile(pattern)));
                        } catch (Exception ignored) {}
                        continue;
                    }
                    try {
                        if (text.contains(":")) {
                            temp.add(InetAddress.getByName(text.split(":")[0]).getHostAddress() + ":" + InetAddress.getByName(text.split(":")[0]).getHostAddress());
                            continue;
                        } temp.add(InetAddress.getByName(text).getHostAddress() + ":25565");
                    } catch (Exception ignored) {}
                } catch (Exception ignored) {}
            } temp = new CopyOnWriteArrayList<>(new HashSet<>(temp));
            temp.removeIf(str -> !str.matches(pattern));
            servers.addAll(temp);
        } catch (Exception ignored) {}
    }

    private List<String> findStringsByRegex(final String text, final Pattern regex) {
        final List<String> strings = new ArrayList<String>();
        final Matcher match = regex.matcher(text);
        while (match.find()) {
            strings.add(text.substring(match.start(), match.end()));
        } return strings;
    }

    private final String pattern = "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?):\\d{1,5}\\b";

    @Override
    protected void actionPerformed(final GuiButton guibutton) {
        if (!guibutton.enabled)
            return;

        final GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.getSelected() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.getSelected());
        if (guibutton.id == 0) {
            new Thread(() -> {
                for (Version version : Version.values()) {
                    parseMonitoringMinecraft(version.getName());
                    parseMinecraftRating(version.getName());

                    for (final String serv : servers) {
                        System.out.println(serv);
                        ServerData serverData = new ServerData(serv, serv, false);
                        if (!getServerList().getShkoloservers().contains(serverData)) {
                            getServerList().addShkoloServerData(serverData);
                        }
                    }

                    getServerList().saveShkoloServerList();
                    mc.displayGuiScreen(guiScreen);
                }
            }).start();
        }

        else if (guibutton.id == 1) {
            this.mc.displayGuiScreen(new GuiMainMenu());
        }

        else if (guibutton.id == 2) {
            for (int i = 0; i < this.getServerList().getShkoloservers().size(); ++i) {
                this.getServerList().removeShkoloServerData(i);
                this.getServerList().saveShkoloServerList();
            } this.mc.displayGuiScreen(this);
        }

        else if (guibutton.id == 3) {
            if (this.serverListSelector.getSelected() >= 1)
                this.getServerList().removeShkoloServerData(this.serverListSelector.getSelected());
            this.getServerList().saveShkoloServerList();
            this.mc.displayGuiScreen(this);
        }

        else if (guibutton.id == 4) {
            this.mc.displayGuiScreen(this);
        }

        else if (guibutton.id == 5) {
            this.getServerList().clearShkoloServerData();
            this.getServerList().saveShkoloServerList();
            this.mc.displayGuiScreen(this);
        }

        //else if (guibutton.id == 5) {
        //    this.mc.displayGuiScreen(new GuiProtocolSelector(this));
        //}
    }

    @Override
    public void confirmClicked(final boolean result, final int id) {
        final GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.getSelected() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.getSelected());
    }

    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        final int i = this.serverListSelector.getSelected();
        final GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (i < 0) ? null : this.serverListSelector.getListEntry(i);
        if (keyCode != 63) {
            if (i >= 0) {
                if (keyCode == 200) {
                    if (isShiftKeyDown()) {
                        if (i > 0 && guilistextended$iguilistentry instanceof ServerListEntryNormal) {
                            this.savedServerList.swapShkoloServers(i, i - 1);
                            this.selectServer(this.serverListSelector.getSelected() - 1);
                            this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                            this.serverListSelector.updateOnlineShkoloServers(this.savedServerList);
                        }
                    }
                    else if (i > 0) {
                        this.selectServer(this.serverListSelector.getSelected() - 1);
                        this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                        if (this.serverListSelector.getListEntry(this.serverListSelector.getSelected()) instanceof ServerListEntryLanScan) {
                            if (this.serverListSelector.getSelected() > 0) {
                                this.selectServer(this.serverListSelector.getSize() - 1);
                                this.serverListSelector.scrollBy(-this.serverListSelector.getSlotHeight());
                            }
                            else {
                                this.selectServer(-1);
                            }
                        }
                    }
                    else {
                        this.selectServer(-1);
                    }
                }
                else if (keyCode == 208) {
                    if (isShiftKeyDown()) {
                        if (i < this.savedServerList.countShkoloServers() - 1) {
                            this.savedServerList.swapShkoloServers(i, i + 1);
                            this.selectServer(i + 1);
                            this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                            this.serverListSelector.updateOnlineShkoloServers(this.savedServerList);
                        }
                    }
                    else if (i < this.serverListSelector.getSize()) {
                        this.selectServer(this.serverListSelector.getSelected() + 1);
                        this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                        if (this.serverListSelector.getListEntry(this.serverListSelector.getSelected()) instanceof ServerListEntryLanScan) {
                            if (this.serverListSelector.getSelected() < this.serverListSelector.getSize() - 1) {
                                this.selectServer(this.serverListSelector.getSize() + 1);
                                this.serverListSelector.scrollBy(this.serverListSelector.getSlotHeight());
                            }
                            else {
                                this.selectServer(-1);
                            }
                        }
                    }
                    else {
                        this.selectServer(-1);
                    }
                }
                else if (keyCode != 28 && keyCode != 156) {
                    super.keyTyped(typedChar, keyCode);
                }
                else {
                    this.actionPerformed(this.buttonList.get(2));
                }
            }
            else {
                super.keyTyped(typedChar, keyCode);
            }
        }
    }

    public static String findStringByRegex(String text, Pattern regex) {
        Matcher match = regex.matcher(text);
        if (match.find())
            return text.substring(match.start(), match.end());
        return null;
    }


    @Override
    public void drawScreen(final int i, final int j, final float f) {
        this.drawDefaultBackground();
        this.serverListSelector.drawScreen(i, j, f);
        this.drawCenteredString(this.fontRendererObj, I18n.format("Servers"), width / 2, 20, 16777215);
        super.drawScreen(i, j, f);
    }

    @Override
    public void connectToSelected() {
        final GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (this.serverListSelector.getSelected() < 0) ? null : this.serverListSelector.getListEntry(this.serverListSelector.getSelected());
        if (guilistextended$iguilistentry instanceof ServerListEntryNormal) {
            this.connectToServer(((ServerListEntryNormal)guilistextended$iguilistentry).getServerData());
        }
        else if (guilistextended$iguilistentry instanceof ServerListEntryLanDetected) {
            final LanServerInfo lanserverinfo = ((ServerListEntryLanDetected)guilistextended$iguilistentry).getServerData();
            this.connectToServer(new ServerData(lanserverinfo.getServerMotd(), lanserverinfo.getServerIpPort(), true));
        }
    }

    private void connectToServer(final ServerData server) {
        this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, server));
    }

    @Override
    public void selectServer(final int index) {
        this.serverListSelector.setSelectedSlotIndex(index);
        final GuiListExtended.IGuiListEntry guilistextended$iguilistentry = (index < 0) ? null : this.serverListSelector.getListEntry(index);
        if (guilistextended$iguilistentry != null && !(guilistextended$iguilistentry instanceof ServerListEntryLanScan)) {
            final boolean b = guilistextended$iguilistentry instanceof ServerListEntryNormal;
        }
    }

    @Override
    public ServerPinger getOldServerPinger() {
        return this.oldServerPinger;
    }

    @Override
    public void setHoveringText(final String p_146793_1_) {
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.serverListSelector.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        this.serverListSelector.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public ServerList getServerList() {
        return this.savedServerList;
    }

    @Override
    public boolean canMoveUp(final ServerListEntryNormal p_175392_1_, final int p_175392_2_) {
        return p_175392_2_ > 0;
    }

    @Override
    public boolean canMoveDown(final ServerListEntryNormal p_175394_1_, final int p_175394_2_) {
        return p_175394_2_ < this.savedServerList.countShkoloServers() - 1;
    }

    @Override
    public void moveServerUp(final ServerListEntryNormal p_175391_1_, final int p_175391_2_, final boolean p_175391_3_) {
        final int i = p_175391_3_ ? 0 : (p_175391_2_ - 1);
        this.savedServerList.swapShkoloServers(p_175391_2_, i);
        if (this.serverListSelector.getSelected() == p_175391_2_) {
            this.selectServer(i);
        }
        this.serverListSelector.updateOnlineShkoloServers(this.savedServerList);
    }

    @Override
    public void moveServerDown(final ServerListEntryNormal p_175393_1_, final int p_175393_2_, final boolean p_175393_3_) {
        final int i = p_175393_3_ ? (this.savedServerList.countShkoloServers() - 1) : (p_175393_2_ + 1);
        this.savedServerList.swapShkoloServers(p_175393_2_, i);
        if (this.serverListSelector.getSelected() == p_175393_2_) {
            this.selectServer(i);
        }
        this.serverListSelector.updateOnlineShkoloServers(this.savedServerList);
    }
}
