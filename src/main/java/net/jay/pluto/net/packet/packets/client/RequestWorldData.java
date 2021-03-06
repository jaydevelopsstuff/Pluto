package net.jay.pluto.net.packet.packets.client;

import net.jay.pluto.net.PacketBuffer;
import net.jay.pluto.net.Packets;
import net.jay.pluto.net.handlers.IServerLoginNetHandler;
import net.jay.pluto.net.packet.CPacket;

public class RequestWorldData implements CPacket<IServerLoginNetHandler> {
    private static final Packets enumRepresentation = Packets.RequestWorldData;

    @Override
    public void readPacketData(PacketBuffer buffer) {

    }

    @Override
    public void processPacket(IServerLoginNetHandler handler) {
        handler.processRequestWorldData(this);
    }

    @Override
    public Packets getEnum() {
        return enumRepresentation;
    }
}
