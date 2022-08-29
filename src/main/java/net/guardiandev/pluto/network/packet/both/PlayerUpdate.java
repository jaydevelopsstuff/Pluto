package net.guardiandev.pluto.network.packet.both;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.network.packet.server.ServerPacket;
import net.guardiandev.pluto.util.BitsByte;

@AllArgsConstructor
public class PlayerUpdate implements ClientPacket, ServerPacket {
    public byte playerId;
    public byte controlFlags;
    public byte pulleyFlags;
    public byte miscFlags;
    public byte sleepingFlags;
    public byte selectedItem;
    public float positionX;
    public float positionY;
    public float velocityX;
    public float velocityY;
    public float originalPosX;
    public float originalPosY;
    public float homePosX;
    public float homePosY;

    public PlayerUpdate() {}

    @Override
    public void readPacket(ByteBuf buf) {
        playerId = buf.readByte();
        controlFlags = buf.readByte();
        pulleyFlags = buf.readByte();
        miscFlags = buf.readByte();
        sleepingFlags = buf.readByte();
        selectedItem = buf.readByte();
        positionX = buf.readFloatLE();
        positionY = buf.readFloatLE();

        BitsByte pFlagsBb = new BitsByte(pulleyFlags);
        BitsByte mFlagsBb = new BitsByte(miscFlags);
        boolean updateVelocity = pFlagsBb.getBit(2);
        boolean usedPotionOfReturn = mFlagsBb.getBit(6);

        if(updateVelocity) {
            velocityX = buf.readFloatLE();
            velocityY = buf.readFloatLE();
        }
        if(usedPotionOfReturn) {
            originalPosX = buf.readFloatLE();
            originalPosY = buf.readFloatLE();
            homePosX = buf.readFloatLE();
            homePosY = buf.readFloatLE();
        }
    }

    @Override
    public void processPacket(LoginHandler handler) {}

    @Override
    public void processPacket(PlayHandler handler) {
        handler.handlePlayerUpdate(this);
    }

    @Override
    public UsableStates getUsableState() {
        return UsableStates.Play;
    }

    @Override
    public void writePacket(ByteBuf buf) {
        BitsByte pFlagsBb = new BitsByte(pulleyFlags);
        BitsByte mFlagsBb = new BitsByte(miscFlags);
        boolean updateVelocity = pFlagsBb.getBit(2);
        boolean usedPotionOfReturn = mFlagsBb.getBit(6);

        buf.writeByte(playerId);
        buf.writeByte(controlFlags);
        buf.writeByte(pulleyFlags);
        buf.writeByte(miscFlags);
        buf.writeByte(sleepingFlags);
        buf.writeByte(selectedItem);
        buf.writeFloatLE(positionX);
        buf.writeFloatLE(positionY);
        if(updateVelocity) {
            buf.writeFloatLE(velocityX);
            buf.writeFloatLE(velocityY);
        }
        if(usedPotionOfReturn) {
            buf.writeFloatLE(originalPosX);
            buf.writeFloatLE(originalPosY);
            buf.writeFloatLE(homePosX);
            buf.writeFloatLE(homePosY);
        }
    }

    @Override
    public PacketType getType() {
        return PacketType.PlayerUpdate;
    }
}
