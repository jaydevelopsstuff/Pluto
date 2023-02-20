package net.guardiandev.pluto.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.PacketType;

public class PlayerSpawn implements ClientPacket {
    public byte playerId;
    public short spawnX;
    public short spawnY;
    public int respawnTimeRemaining;
    public short numberOfDeathsPVE;
    public short numberOfDeathsPVP;
    public byte playerSpawnContext;

    @Override
    public void readPacket(ByteBuf buf) {
        playerId = buf.readByte();
        spawnX = buf.readShortLE();
        spawnY = buf.readShortLE();
        respawnTimeRemaining = buf.readIntLE();
        numberOfDeathsPVE = buf.readShortLE();
        numberOfDeathsPVP = buf.readShortLE();
        playerSpawnContext = buf.readByte();
    }

    @Override
    public void processPacket(LoginHandler handler) {
        handler.handlePlayerSpawn(this);
    }

    @Override
    public void processPacket(PlayHandler handler) {

    }

    @Override
    public UsableStates getUsableState() {
        return UsableStates.Both;
    }

    @Override
    public PacketType getType() {
        return PacketType.PlayerSpawn;
    }
}
