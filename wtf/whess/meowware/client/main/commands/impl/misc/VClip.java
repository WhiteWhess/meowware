package wtf.whess.meowware.client.main.commands.impl.misc;

import wtf.whess.meowware.client.main.commands.Command;
import wtf.whess.meowware.client.api.utilities.client.ChatUtil;

public final class VClip extends Command {
    public VClip() {
        super("VClip", "Vertical Clip", "[ y ]", "VClip");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length >= 1) {
            try {
                mc.player.setPositionAndUpdate(mc.player.posX, mc.player.posY + Double.parseDouble(args[0]), mc.player.posZ);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } if (args.length <= 0) {
            ChatUtil.printChat("&cCommand Use -> " + this.getName() + " -> " + this.getSyntax() + ". ");
        }
    }
}
