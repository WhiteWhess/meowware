package wtf.whess.meowware.client.api.eventsystem.impl;

import net.minecraft.entity.Entity;
import wtf.whess.meowware.client.api.eventsystem.Event;

public final class AttackEntityEvent extends Event {
    private final Entity entity;

    public AttackEntityEvent(Entity targetEntity) {
        this.entity = targetEntity;
    }

    public Entity getEntity() {
        return entity;
    }
}
