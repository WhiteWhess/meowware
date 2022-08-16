package wtf.whess.meowware.client.main.commands.impl.misc;

import wtf.whess.meowware.client.main.commands.Command;
import wtf.whess.meowware.client.api.utilities.client.ChatUtil;

public final class Calc extends Command {
    public Calc() {
        super("Calc", "Calculate", "[ + | - | * | / ] [ x ] [ x ]", "calc");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length >= 3) {
            try {
                switch (args[0]) {
                    case "*": {
                        ChatUtil.printChat("&aResult -> " + (Double.parseDouble(args[1]) * Double.parseDouble(args[2])) + ".");
                        break;
                    }
                    case "/": {
                        ChatUtil.printChat("&aResult -> " + (Double.parseDouble(args[1]) / Double.parseDouble(args[2])) + ".");
                        break;
                    }
                    case "-": {
                        ChatUtil.printChat("&aResult -> " + (Double.parseDouble(args[1]) - Double.parseDouble(args[2])) + ".");
                        break;
                    }
                    default: {
                        ChatUtil.printChat("&aResult -> " + (Double.parseDouble(args[1]) + Double.parseDouble(args[2])) + ".");
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } if (args.length <= 2) {
            ChatUtil.printChat("&cCommand Use -> " + this.getName() + " -> " + this.getSyntax() + ". ");
        }
    }
}
