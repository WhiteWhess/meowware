package wtf.whess.meowware.client.api.eventsystem.impl;

import wtf.whess.meowware.client.api.eventsystem.Event;

public final class MouseEvent extends Event {
    public static final int LEFT_MOUSE_BUTTON = 0;
    public static final int RIGHT_MOUSE_BUTTON = 1;
    public static final int MIDDLE_MOUSE_BUTTON = 2;

    private int key;

    public MouseEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
