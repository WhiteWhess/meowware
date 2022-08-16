package wtf.whess.meowware.client.api.eventsystem.impl;

import net.minecraft.network.Packet;
import wtf.whess.meowware.client.api.eventsystem.Event;

public final class PacketReceiveEvent extends Event {
    private Packet packet;

    public PacketReceiveEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
