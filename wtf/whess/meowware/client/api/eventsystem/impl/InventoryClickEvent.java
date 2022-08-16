package wtf.whess.meowware.client.api.eventsystem.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import wtf.whess.meowware.client.api.eventsystem.Event;

@Data
@EqualsAndHashCode(callSuper = false)
public class InventoryClickEvent extends Event {
    private final int slot;
    private final int mouseButton;
    private final ClickType clickType;
    private final ItemStack stack;
}
