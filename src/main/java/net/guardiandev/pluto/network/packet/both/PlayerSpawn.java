package net.guardiandev.pluto.network.packet.both;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.network.packet.server.ServerPacket;

public class PlayerSpawn implements ClientPacket, ServerPacket {
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
        handler.handlePlayerSpawn(this);
    }

    @Override
    public void writePacket(ByteBuf buf) {
        buf.writeByte(playerId);
        buf.writeShortLE(spawnX);
        buf.writeShortLE(spawnY);
        buf.writeIntLE(respawnTimeRemaining);
        buf.writeShortLE(numberOfDeathsPVE);
        buf.writeShortLE(numberOfDeathsPVP);
        buf.writeByte(playerSpawnContext);
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
