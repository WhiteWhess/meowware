package wtf.whess.meowware.client.main.commands.impl.misc;

import wtf.whess.meowware.MeowWare;
import wtf.whess.meowware.client.main.commands.Command;
import wtf.whess.meowware.client.api.utilities.client.ChatUtil;

public final class Config extends Command {
    public Config() {
        super("Config", "Configuration load/save/create/remove", "[ load | save | create | remove | list ] [ config name ]", "cfg", "config");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length <= 0) {
            ChatUtil.printChat("&cInvalid Syntax. Use .cfg" + getSyntax() + ".");
        }

        if (args.length > 0) {
            try {
                MeowWare.getConfigManager().update();
                if (args[0].equalsIgnoreCase("load")) {
                    MeowWare.getConfigManager().load(args[1]);
                    ChatUtil.printChat("&aConfig " + args[1] + " loaded.");
                }
                if (args[0].equalsIgnoreCase("save")) {
                    MeowWare.getConfigManager().save(args[1]);
                    ChatUtil.printChat("&aConfig " + args[1] + " saved.");
                }
                if (args[0].equalsIgnoreCase("create")) {
                    MeowWare.getConfigManager().create(args[1]);
                    ChatUtil.printChat("&aConfig " + args[1] + " created!");
                }
                if (args[0].equalsIgnoreCase("remove")) {
                    MeowWare.getConfigManager().delete(args[1]);
                    ChatUtil.printChat("&aConfig " + args[1] + " removed.");
                }
                if (args[0].equalsIgnoreCase("list")) {
                    ChatUtil.printChat("&bConfig list: ");
                    for (wtf.whess.meowware.client.api.configsystem.Config config : MeowWare.getConfigManager().getConfigs()) {
                        ChatUtil.printChat("&8- &6" + config.getName());
                    }
                }
            } catch (Exception e) {
                ChatUtil.printChat("&cError.");
                e.printStackTrace();
            }

        }
    }
}
