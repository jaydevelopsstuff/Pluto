package net.guardiandev.pluto.network.packet.server;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.packet.Packet;
import net.guardiandev.pluto.util.ByteBufUtil;

public interface ServerPacket extends Packet {
    void writePacket(ByteBuf buf);
}
