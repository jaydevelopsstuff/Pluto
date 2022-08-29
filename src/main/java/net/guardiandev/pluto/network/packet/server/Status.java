package net.guardiandev.pluto.network.packet.server;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.data.NetworkText;
import net.guardiandev.pluto.network.packet.PacketType;

@AllArgsConstructor
public class Status implements ServerPacket {
    public int max;
    public NetworkText text;
    public byte flags;

    @Override
    public void writePacket(ByteBuf buf) {
        buf.writeIntLE(max);
        text.serialize(buf);
        buf.writeByte(flags);
    }

    @Override
    public PacketType getType() {
        return PacketType.Status;
    }
}
