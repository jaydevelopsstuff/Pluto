package net.guardiandev.pluto.network.packet.both;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.network.packet.server.ServerPacket;

@AllArgsConstructor
public class PlayerHP implements ClientPacket, ServerPacket {
    public byte playerId;
    public short hp;
    public short maxHp;

    public PlayerHP() {}

    @Override
    public void readPacket(ByteBuf buf) {
        playerId = buf.readByte();
        hp = buf.readShortLE();
        maxHp = buf.readShortLE();
    }

    @Override
    public void processPacket(LoginHandler handler) {

    }

    @Override
    public void writePacket(ByteBuf buf) {

    }

    @Override
    public PacketType getType() {
        return PacketType.PlayerHp;
    }
}
