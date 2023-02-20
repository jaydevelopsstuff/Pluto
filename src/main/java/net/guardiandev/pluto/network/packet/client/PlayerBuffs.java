package net.guardiandev.pluto.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.server.ServerPacket;

public class PlayerBuffs implements ClientPacket, ServerPacket {
    public byte playerId;

    @Override
    public void readPacket(ByteBuf buf) {
        playerId = buf.readByte();
        for(int i = 0; i < 44; i++) {
            System.out.print(buf.readShortLE() + " - ");
        }
    }

    @Override
    public void processPacket(LoginHandler handler) {
        handler.handlePlayerBuffs(this);
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

    }

    @Override
    public PacketType getType() {
        return PacketType.PlayerBuffs;
    }
}
