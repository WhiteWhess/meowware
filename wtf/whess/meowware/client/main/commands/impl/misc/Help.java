package wtf.whess.meowware.client.main.commands.impl.misc;

import wtf.whess.meowware.MeowWare;
import wtf.whess.meowware.client.main.commands.Command;
import wtf.whess.meowware.client.api.utilities.client.ChatUtil;

public final class Help extends Command {
    public Help() {
        super("Help", "Help", "", "help");
    }

    @Override
    public void onCommand(String[] args, String command) {
        ChatUtil.printChat("&bAll commands: ");
        for (Command c: MeowWare.getInstance().getCommandManager().commands)
            if (!(c instanceof Help))
                ChatUtil.printChat("&8- &6" + c.getAliases().get(0).toLowerCase());
    }
}
