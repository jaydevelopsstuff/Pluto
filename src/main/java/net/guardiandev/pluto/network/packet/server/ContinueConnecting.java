package net.guardiandev.pluto.network.packet.server;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.network.packet.PacketType;

// AKA SetUserSlot
@AllArgsConstructor
public class ContinueConnecting implements ServerPacket {
    public short playerId;

    @Override
    public void writePacket(ByteBuf buf) {
        buf.writeByte(playerId);
        buf.writeBoolean(false);
    }

    @Override
    public PacketType getType() {
        return PacketType.ContinueConnecting;
    }
}
