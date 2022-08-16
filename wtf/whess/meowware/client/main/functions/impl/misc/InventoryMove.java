package wtf.whess.meowware.client.main.functions.impl.misc;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import wtf.whess.meowware.client.api.eventsystem.EventTarget;
import wtf.whess.meowware.client.api.eventsystem.impl.ClientTickEvent;
import wtf.whess.meowware.client.main.functions.Category;
import wtf.whess.meowware.client.main.functions.Function;

public final class InventoryMove extends Function {
    private final KeyBinding[] keyBindings = new KeyBinding[]{mc.gameSettings.keyBindSprint, mc.gameSettings.keyBindForward, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindJump};
    public InventoryMove() {
        super("InventoryMove", Category.move);
        setToggled(true);
    }
    
    @EventTarget
    public void onUpdate(ClientTickEvent ignoredEvent) {
        if     (mc.world == null ||
                mc.currentScreen instanceof GuiChat ||
                mc.currentScreen instanceof GuiEditSign
               )
            return;
        for (KeyBinding keyBinding : keyBindings)
            keyBinding.setPressed(Keyboard.isKeyDown(keyBinding.getKeyCode()));
    }
}
