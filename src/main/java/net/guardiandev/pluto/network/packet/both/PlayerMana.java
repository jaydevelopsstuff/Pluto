package net.guardiandev.pluto.network.packet.both;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.network.packet.server.ServerPacket;

@AllArgsConstructor
public class PlayerMana implements ClientPacket, ServerPacket {
    public byte playerId;
    public short mana;
    public short manaMax;

    public PlayerMana() {}

    @Override
    public void readPacket(ByteBuf buf) {
        playerId = buf.readByte();
        mana = buf.readShortLE();
        manaMax = buf.readShortLE();
    }

    @Override
    public void processPacket(LoginHandler handler) {
        handler.handlePlayerMana(this);
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
        buf.writeShortLE(mana);
        buf.writeShortLE(manaMax);
    }

    @Override
    public PacketType getType() {
        return PacketType.PlayerMana;
    }
}
