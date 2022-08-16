package wtf.whess.meowware.client.api.utilities.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.TextComponentString;
import wtf.whess.meowware.client.api.utilities.Utility;

public final class ChatUtil extends Utility {
    public static final String prefix = ChatFormatting.DARK_GRAY + "[" + ChatFormatting.GRAY + "M" + ChatFormatting.WHITE + "eow" + ChatFormatting.GRAY + "W" + ChatFormatting.WHITE + "are" + ChatFormatting.DARK_GRAY + "]" + ChatFormatting.RESET + " ";

    public static void printChat(String text) {
        if (mc.player != null && mc.world != null) {
            text = text.replaceAll("&", "§").replaceAll("<<", "«").replaceAll(">>", "»");
            mc.player.addChatComponentMessage(new TextComponentString(prefix + text), false);
        }
    }

    public static void sendChat_NoFilter(String text) {
        if (mc.player != null && mc.world != null) {
            text = text.replaceAll("&", "§").replaceAll("<<", "«").replaceAll(">>", "»");
            mc.player.connection.sendPacket(new CPacketChatMessage(prefix + text));
        }
    }

    public static void sendChat(String text) {
        if (mc.player != null && mc.world != null) {
            text = text.replaceAll("&", "§").replaceAll("<<", "«").replaceAll(">>", "»");
            mc.player.sendChatMessage(prefix + text);
        }
    }
}
