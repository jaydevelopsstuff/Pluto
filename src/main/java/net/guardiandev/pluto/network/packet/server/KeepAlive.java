package net.guardiandev.pluto.network.packet.server;

import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.util.ByteBufUtil;

public class KeepAlive implements ServerPacket {
    @Override
    public void writePacket(ByteBufUtil buf) {}

    @Override
    public PacketType getType() {
        return PacketType.KeepAlive;
    }
}
