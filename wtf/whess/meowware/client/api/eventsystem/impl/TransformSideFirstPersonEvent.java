package wtf.whess.meowware.client.api.eventsystem.impl;

import net.minecraft.util.EnumHandSide;
import wtf.whess.meowware.client.api.eventsystem.Event;

public final class TransformSideFirstPersonEvent extends Event {
    private final EnumHandSide enumHandSide;

    public TransformSideFirstPersonEvent(EnumHandSide enumHandSide) {
        this.enumHandSide = enumHandSide;
    }

    public EnumHandSide getEnumHandSide() {
        return this.enumHandSide;
    }
}
