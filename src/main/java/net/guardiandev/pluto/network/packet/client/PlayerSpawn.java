package net.guardiandev.pluto.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.packet.PacketType;

public class PlayerSpawn implements ClientPacket {
    public byte playerId;
    public short spawnX;
    public short spawnY;
    public int respawnTimeRemaining;
    public byte playerSpawnContext;

    @Override
    public void readPacket(ByteBuf buf) {
        playerId = buf.readByte();
        spawnX = buf.readShortLE();
        spawnY = buf.readShortLE();
        respawnTimeRemaining = buf.readIntLE();
        playerSpawnContext = buf.readByte();
    }

    @Override
    public void processPacket(LoginHandler handler) {
        handler.handlePlayerSpawn(this);
    }

    @Override
    public PacketType getType() {
        return PacketType.PlayerSpawn;
    }
}
