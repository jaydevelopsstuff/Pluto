package net.guardiandev.pluto.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.packet.PacketType;

public class RequestWorldData implements ClientPacket {
    @Override
    public void readPacket(ByteBuf buf) {}

    @Override
    public void processPacket(LoginHandler handler) {
        handler.handleRequestWorldData(this);
    }

    @Override
    public PacketType getType() {
        return PacketType.RequestWorldData;
    }
}
