package wtf.whess.meowware.client.api.eventsystem.impl;

import lombok.Getter;
import lombok.Setter;
import wtf.whess.meowware.client.api.eventsystem.Event;

public final class MotionEvent extends Event {
    public MotionEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Getter @Setter
    private double x, y, z;
}
