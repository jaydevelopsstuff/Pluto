package net.guardiandev.pluto.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.server.ServerPacket;

public class PlayerBuff implements ClientPacket {

    @Override
    public void readPacket(ByteBuf buf) {
        buf.readByte();
        for(int i = 0; i < 22; i++) {
            buf.readShortLE();
        }
    }

    @Override
    public void processPacket(LoginHandler handler) {

    }


    @Override
    public PacketType getType() {
        return PacketType.PlayerBuff;
    }
}
