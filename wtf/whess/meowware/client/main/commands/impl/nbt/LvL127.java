package wtf.whess.meowware.client.main.commands.impl.nbt;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import wtf.whess.meowware.client.api.utilities.client.ChatUtil;
import wtf.whess.meowware.client.main.commands.Command;

public final class LvL127 extends Command {
    public LvL127() {
        super("LvL127", "", "", "127", "lvl127", "127lvl");
    }

    @Override
    public void onCommand(String[] args, String command) {
        ItemStack stack = mc.player.inventory.getCurrentItem().copy();
        for (int i = 0; i < 50; i++)
            stack.addEnchantment(Enchantment.getEnchantmentByID(i), 127);
        mc.player.connection.sendPacket(new CPacketCreativeInventoryAction(36 + mc.player.inventory.currentItem, stack));
        ChatUtil.printChat(ChatFormatting.GOLD + "Success!");
    }
}
