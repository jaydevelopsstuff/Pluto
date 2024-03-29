package net.guardiandev.pluto.network.packet.both;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.network.packet.server.ServerPacket;

@AllArgsConstructor
public class ManaEffect implements ClientPacket, ServerPacket {
    public byte playerId;
    public short manaAmount;

    public ManaEffect() {}

    @Override
    public void readPacket(ByteBuf buf) {
        playerId = buf.readByte();
        manaAmount = buf.readShortLE();
    }

    @Override
    public void processPacket(LoginHandler handler) {

    }

    @Override
    public void processPacket(PlayHandler handler) {

    }

    @Override
    public UsableStates getUsableState() {
        return UsableStates.Both;
    }

    @Override
    public void writePacket(ByteBuf buf) {
        buf.writeByte(playerId);
        buf.writeShortLE(manaAmount);
    }

    @Override
    public PacketType getType() {
        return PacketType.ManaEffect;
    }
}
