package wtf.whess.meowware.client.main.commands.impl.misc;

import wtf.whess.meowware.client.main.commands.Command;
import wtf.whess.meowware.client.api.utilities.client.ChatUtil;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public final class GetIP extends Command {
    public GetIP() {
        super("GetIP", "Get ip from Domain", "", "getip", "ip");
    }

    @Override
    public void onCommand(String[] args, String command) {
        try {
            String ip = mc.player.connection.getNetworkManager().getRemoteAddress().toString();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(ip), null);
            ChatUtil.printChat("&b" + ip + " &7(Copied)");
        } catch (Exception e) {
            ChatUtil.printChat("&cError.");
            e.printStackTrace();
        }
    }
}
