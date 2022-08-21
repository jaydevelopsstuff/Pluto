package net.guardiandev.pluto.network.packet.server;

import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.util.ByteBufUtil;

public class Disconnect implements ServerPacket {
    @Override
    public void writePacket(ByteBufUtil buf) {
        // TODO: Network Text
    }

    @Override
    public PacketType getType() {
        return PacketType.Disconnect;
    }
}
