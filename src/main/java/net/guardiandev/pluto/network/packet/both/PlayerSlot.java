package net.guardiandev.pluto.network.packet.both;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.network.packet.server.ServerPacket;

@AllArgsConstructor
public class PlayerSlot implements ClientPacket, ServerPacket {
    public byte playerId;
    public short slotIndex;
    public short stackSize;
    public byte itemPrefix;
    public short itemNetId;

    public PlayerSlot() {}

    @Override
    public void readPacket(ByteBuf buf) {
        playerId = buf.readByte();
        slotIndex = buf.readShortLE();
        stackSize = buf.readShortLE();
        itemPrefix = buf.readByte();
        itemNetId = buf.readShortLE();
    }

    @Override
    public void processPacket(LoginHandler handler) {

    }

    @Override
    public void writePacket(ByteBuf buf) {
        buf.writeByte(playerId);
        buf.writeShortLE(slotIndex);
        buf.writeShortLE(stackSize);
        buf.writeByte(itemPrefix);
        buf.writeShortLE(itemNetId);
    }

    @Override
    public PacketType getType() {
        return PacketType.PlayerSlot;
    }
}
