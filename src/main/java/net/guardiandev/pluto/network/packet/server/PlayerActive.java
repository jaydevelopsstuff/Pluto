package net.guardiandev.pluto.network.packet.server;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.network.packet.PacketType;

@AllArgsConstructor
public class PlayerActive implements ServerPacket {
    public byte playerId;
    public boolean active;

    @Override
    public void writePacket(ByteBuf buf) {
        buf.writeByte(playerId);
        buf.writeBoolean(active);
    }

    @Override
    public PacketType getType() {
        return PacketType.PlayerActive;
    }
}
