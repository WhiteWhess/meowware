package wtf.whess.meowware.client.main.commands.impl.nbt;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import wtf.whess.meowware.client.api.utilities.client.ChatUtil;
import wtf.whess.meowware.client.main.commands.Command;

import java.util.Objects;

public final class ItemInfo extends Command {
    public ItemInfo() {
        super("ItemInfo", "", "", "dura", "item", "ii", "iteminfo", "infoitem");
    }

    @Override
    public void onCommand(String[] args, String command) {
        ItemStack stack = mc.player.inventory.getCurrentItem().copy();
        NBTTagCompound nBTTagCompound = stack.getTagCompound();
        ChatUtil.printChat(ChatFormatting.LIGHT_PURPLE + "Item ID - " + Item.getIdFromItem(stack.getItem()) + ":" + stack.getMetadata());
        ChatUtil.printChat(ChatFormatting.LIGHT_PURPLE + "Item name - " + stack.getDisplayName());
        ChatUtil.printChat(ChatFormatting.LIGHT_PURPLE + "Item unlocalized name - " + stack.getItem().getUnlocalizedName());
        ChatUtil.printChat(ChatFormatting.LIGHT_PURPLE + "Item path - " + stack.getItem().setFull3D());
        ChatUtil.printChat(ChatFormatting.LIGHT_PURPLE + "NBT - " + nBTTagCompound);
        GuiScreen.setClipboardString(Objects.requireNonNull(nBTTagCompound).toString().replace("§", "&"));
        ChatUtil.printChat(ChatFormatting.LIGHT_PURPLE + "NBT tag has copied");
    }
}
