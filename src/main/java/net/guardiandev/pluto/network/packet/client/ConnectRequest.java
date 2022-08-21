package net.guardiandev.pluto.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.util.ByteBufUtil;

public class ConnectRequest implements ClientPacket {
    public String version;

    @Override
    public void readPacket(ByteBuf buf) {
        version = ByteBufUtil.readTString(buf);
    }

    @Override
    public void processPacket(LoginHandler handler) {
        handler.handleConnectRequest(this);
    }

    @Override
    public PacketType getType() {
        return PacketType.ConnectRequest;
    }
}
