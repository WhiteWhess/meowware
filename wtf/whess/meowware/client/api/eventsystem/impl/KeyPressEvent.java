package wtf.whess.meowware.client.api.eventsystem.impl;

import wtf.whess.meowware.client.api.eventsystem.Event;

public final class KeyPressEvent extends Event {
	private int key;

	public KeyPressEvent(int key) {
		this.key = key;
	}

	public int getKey() {
		return this.key;
	}

	public void setKey(int key) {
		this.key = key;
	}
}
