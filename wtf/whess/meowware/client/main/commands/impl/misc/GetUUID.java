package wtf.whess.meowware.client.main.commands.impl.misc;

import wtf.whess.meowware.client.main.commands.Command;
import wtf.whess.meowware.client.api.utilities.client.ChatUtil;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public final class GetUUID extends Command {
    public GetUUID() {
        super("GetUUID", "Get uuid", "", "getuuid", "uuid", "getuid", "uid");
    }

    @Override
    public void onCommand(String[] args, String command) {
        try {
            String uid = mc.player.getUniqueID().toString();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(uid), null);
            ChatUtil.printChat("&b" + uid + " &7(Copied)");
        } catch (Exception e) {
            ChatUtil.printChat("&cError.");
            e.printStackTrace();
        }
    }
}
