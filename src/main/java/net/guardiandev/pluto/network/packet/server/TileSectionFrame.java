package net.guardiandev.pluto.network.packet.server;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.network.packet.PacketType;

@AllArgsConstructor
public class TileSectionFrame implements ServerPacket {
    public short startSectionX;
    public short startSectionY;
    public short endSectionX;
    public short endSectionY;

    @Override
    public void writePacket(ByteBuf buf) {
        buf.writeShortLE(startSectionX);
        buf.writeShortLE(startSectionY);
        buf.writeShortLE(endSectionX);
        buf.writeShortLE(endSectionY);
    }

    @Override
    public PacketType getType() {
        return PacketType.TileSectionFrame;
    }
}
