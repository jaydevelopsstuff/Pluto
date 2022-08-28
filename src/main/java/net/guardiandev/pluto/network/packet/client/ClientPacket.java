package net.guardiandev.pluto.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.packet.Packet;
import net.guardiandev.pluto.util.ByteBufUtil;

public interface ClientPacket extends Packet {
    void readPacket(ByteBuf buf);

    void processPacket(LoginHandler handler);
    // void processPacket();
}
