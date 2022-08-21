package net.guardiandev.pluto.network.packet.server;

import net.guardiandev.pluto.network.packet.Packet;
import net.guardiandev.pluto.util.ByteBufUtil;

public interface ServerPacket extends Packet {
    void writePacket(ByteBufUtil buf);
}
