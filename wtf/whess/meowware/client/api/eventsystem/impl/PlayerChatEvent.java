package wtf.whess.meowware.client.api.eventsystem.impl;

import wtf.whess.meowware.client.api.eventsystem.Event;

public final class PlayerChatEvent extends Event {
    private String text;

    public PlayerChatEvent(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
