package net.guardiandev.pluto.network.packet.server;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.packet.PacketType;

public class CompleteConnectionAndSpawn implements ServerPacket {
    @Override
    public void writePacket(ByteBuf buf) {}


    @Override
    public PacketType getType() {
        return PacketType.CompleteConnectionAndSpawn;
    }
}
