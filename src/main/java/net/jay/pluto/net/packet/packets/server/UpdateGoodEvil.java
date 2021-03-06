package net.jay.pluto.net.packet.packets.server;

import lombok.AllArgsConstructor;
import net.jay.pluto.net.PacketBuffer;
import net.jay.pluto.net.Packets;
import net.jay.pluto.net.VariableSizePacketBuffer;
import net.jay.pluto.net.packet.SPacket;

@AllArgsConstructor
public class UpdateGoodEvil implements SPacket {
    private static final Packets enumRepresentation = Packets.UpdateGoodEvil;

    public byte totalGood;
    public byte totalEvil;
    public byte totalCrimson;

    @Override
    public PacketBuffer writePacketData() {
        VariableSizePacketBuffer buffer = new VariableSizePacketBuffer();
        buffer.writeByte(totalGood);
        buffer.writeByte(totalEvil);
        buffer.writeByte(totalCrimson);
        return buffer.toNormal();
    }

    @Override
    public PacketBuffer writePacketData(PacketBuffer buffer) {
        buffer.writeByte(totalGood);
        buffer.writeByte(totalEvil);
        buffer.writeByte(totalCrimson);
        return buffer;
    }

    @Override
    public Packets getEnum() {
        return enumRepresentation;
    }
}
