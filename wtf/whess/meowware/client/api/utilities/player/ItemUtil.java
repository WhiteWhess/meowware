package wtf.whess.meowware.client.api.utilities.player;

import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import wtf.whess.meowware.client.api.utilities.Utility;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ItemUtil extends Utility {
    public static int getItemSlot(Item item) {
        List<Slot> slots = mc.player.inventoryContainer.inventorySlots.stream()
                .filter(slot -> slot.getStack().getItem() == item && slot.slotNumber >= 9 && slot.slotNumber <= 44)
                .collect(Collectors.toList());

        if(slots.size() > 0)
            return slots.get(0).slotNumber;

        return -999;
    }

    public static List<Slot> findEmptySlots() {
        return mc.player.inventoryContainer.inventorySlots.stream()
                .filter(slot -> !slot.getHasStack() && slot.slotNumber >= 9 && slot.slotNumber <= 44)
                .collect(Collectors.toList());
    }

    public static int findFartherEmptySlot() {
        List<Slot> slots = findEmptySlots();
        if(slots.size() <= 0)
            return -999;

        slots.sort(Comparator.comparingInt(slot -> slot.slotNumber));

        return slots.get(0).slotNumber;
    }

    public static int findNearestEmptySlot() {
        List<Slot> slots = findEmptySlots();
        if(slots.size() <= 0)
            return -999;

        slots.sort(Comparator.comparingInt(slot -> ((Slot) slot).slotNumber).reversed());

        return slots.get(0).slotNumber;
    }
}
