package net.guardiandev.pluto.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.packet.PacketType;

public class RequestEssentialTiles implements ClientPacket {
    public int playerSpawnX;
    public int playerSpawnY;

    @Override
    public void readPacket(ByteBuf buf) {
        playerSpawnX = buf.readIntLE();
        playerSpawnY = buf.readIntLE();
    }

    @Override
    public void processPacket(LoginHandler handler) {
        handler.handleRequestEssentialTiles(this);
    }

    @Override
    public PacketType getType() {
        return PacketType.RequestEssentialTiles;
    }
}
