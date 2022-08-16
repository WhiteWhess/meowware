package wtf.whess.meowware.client.api.eventsystem.impl;

import wtf.whess.meowware.client.api.eventsystem.Event;

public final class RenderWorldEvent extends Event {
	private float partialTicks;

	public RenderWorldEvent(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}
}