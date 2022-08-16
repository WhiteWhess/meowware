package wtf.whess.meowware.client.main.commands;

import wtf.whess.meowware.client.api.eventsystem.EventManager;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.PlayerChatEvent;
import wtf.whess.meowware.client.api.utilities.client.ChatUtil;
import wtf.whess.meowware.client.main.commands.impl.crash.Crash;
import wtf.whess.meowware.client.main.commands.impl.crash.Figure;
import wtf.whess.meowware.client.main.commands.impl.exploit.Creatives;
import wtf.whess.meowware.client.main.commands.impl.exploit.WorldDownloader;
import wtf.whess.meowware.client.main.commands.impl.misc.*;
import wtf.whess.meowware.client.main.commands.impl.nbt.ItemInfo;
import wtf.whess.meowware.client.main.commands.impl.nbt.LvL127;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CommandManager {

    public List<Command> commands = new ArrayList<Command>();
    public static char prefix = '.';

    public CommandManager() {
        EventManager.register(this);
        registerCommands();
    }

    public void registerCommands() {
        commands.add(new Calc());
        commands.add(new VClip());
        commands.add(new Config());
        commands.add(new Figure());
        commands.add(new GetIP());
        commands.add(new GetUUID());
        commands.add(new WorldDownloader());
        commands.add(new Creatives());
        commands.add(new Crash());
        commands.add(new Help());
        commands.add(new LvL127());
        commands.add(new ItemInfo());
    }

    @EventTarget
    public void onChat(final PlayerChatEvent event) {
        String message = event.getText();

        if (!message.startsWith(String.valueOf(prefix)))
            return;

        event.setCancelled(true);
        message = message.substring(1);
        if (message.split(" ").length > 0) {
            boolean foundCommand = false;
            String commandName = message.split(" ")[0];
            for (Command c : commands) {
                String cmdName = c.name.toLowerCase();
                if (c.aliases.contains(commandName) || cmdName.equalsIgnoreCase(commandName)) {
                    c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                    foundCommand = true;
                    break;
                }
            } if (!foundCommand) {
                ChatUtil.printChat("Unknown command...");
            }
        }
    }

    public static void setPrefix(char pref) {
        prefix = pref;
    }
}