package net.guardiandev.pluto.network.packet.both;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.network.packet.server.ServerPacket;

@AllArgsConstructor
public class ManaEffect implements ClientPacket, ServerPacket {
    public byte playerId;
    public short manaAmount;
    public short manaMax;

    public ManaEffect() {}

    @Override
    public void readPacket(ByteBuf buf) {
        playerId = buf.readByte();
        manaAmount = buf.readShortLE();
        manaMax = buf.readShortLE();
    }

    @Override
    public void processPacket(LoginHandler handler) {

    }

    @Override
    public void writePacket(ByteBuf buf) {
        buf.writeByte(playerId);
        buf.writeShortLE(manaAmount);
        buf.writeShortLE(manaMax);
    }

    @Override
    public PacketType getType() {
        return PacketType.ManaEffect;
    }
}
