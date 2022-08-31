package net.guardiandev.pluto.network.packet.both;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.network.packet.server.ServerPacket;

@AllArgsConstructor
public class UpdateItemDrop2 implements ClientPacket, ServerPacket {
    public short itemUid;
    public float posX;
    public float posY;
    public float velocityX;
    public float velocityY;
    public short stackSize;
    public byte prefix;
    public byte noDelay;
    public short itemNetId;

    public UpdateItemDrop2() {
    }

    @Override
    public void readPacket(ByteBuf buf) {
        itemUid = buf.readShortLE();
        posX = buf.readFloatLE();
        posY = buf.readFloatLE();
        velocityX = buf.readFloatLE();
        velocityY = buf.readFloatLE();
        stackSize = buf.readShortLE();
        prefix = buf.readByte();
        noDelay = buf.readByte();
        itemNetId = buf.readShortLE();
    }

    @Override
    public void processPacket(LoginHandler handler) {
    }

    @Override
    public void processPacket(PlayHandler handler) {

    }

    @Override
    public UsableStates getUsableState() {
        return UsableStates.Play;
    }

    @Override
    public void writePacket(ByteBuf buf) {
        buf.writeShortLE(itemUid);
        buf.writeFloatLE(posX);
        buf.writeFloatLE(posY);
        buf.writeFloatLE(velocityX);
        buf.writeFloatLE(velocityY);
        buf.writeShortLE(stackSize);
        buf.writeByte(prefix);
        buf.writeByte(noDelay);
        buf.writeShortLE(itemNetId);
    }

    @Override
    public PacketType getType() {
        return PacketType.UpdateItemDrop;
    }
}
