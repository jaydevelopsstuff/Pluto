package net.guardiandev.pluto.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.util.ByteBufUtil;

public class ClientUUID implements ClientPacket {
    public String uuidString;

    @Override
    public void readPacket(ByteBuf buf) {
        uuidString = ByteBufUtil.readTString(buf);
    }

    @Override
    public void processPacket(LoginHandler handler) {

    }

    @Override
    public PacketType getType() {
        return PacketType.ClientUUID;
    }
}
