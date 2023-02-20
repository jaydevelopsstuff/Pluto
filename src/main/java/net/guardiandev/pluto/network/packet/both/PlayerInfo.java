package net.guardiandev.pluto.network.packet.both;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.data.Character;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.network.packet.server.ServerPacket;
import net.guardiandev.pluto.util.ByteBufUtil;
import net.guardiandev.pluto.util.TColor;

@AllArgsConstructor
public class PlayerInfo implements ClientPacket, ServerPacket {
    public byte playerId;
    public byte skinVariant;
    public byte hair;
    public String playerName;
    public byte hairDye;
    public byte hideVisuals;
    public byte hideVisuals2;
    public byte hideMisc;
    public TColor hairColor;
    public TColor skinColor;
    public TColor eyeColor;
    public TColor shirtColor;
    public TColor undershirtColor;
    public TColor pantsColor;
    public TColor shoeColor;
    public byte difficultyFlags;
    public byte torchFlags;
    public byte otherFlags;

    public PlayerInfo() {
    }

    public PlayerInfo(byte playerId, Character character) {
        this.playerId = playerId;
        this.skinVariant = (byte)character.getSkinVariant();
        this.hair = (byte)character.getHair();
        this.playerName = character.getName();
        this.hairDye = (byte)character.getHairDye();
        this.hideVisuals = character.getHideVisuals();
        this.hideVisuals2 = character.getHideVisuals2();
        this.hideMisc = character.getHideMisc();
        this.hairColor = character.getHairColor();
        this.skinColor = character.getSkinColor();
        this.eyeColor = character.getEyeColor();
        this.shirtColor = character.getShirtColor();
        this.undershirtColor = character.getUndershirtColor();
        this.pantsColor = character.getPantsColor();
        this.shoeColor = character.getShoeColor();
        this.difficultyFlags |= character.getDifficulty().bit;
        if(character.isExtraAccessory()) this.difficultyFlags |= (byte)0b00001000;
        this.torchFlags = character.getTorchFlags();
        this.otherFlags = 0; // TODO
    }

    @Override
    public void readPacket(ByteBuf buf) {
        playerId = buf.readByte();
        skinVariant = buf.readByte();
        hair = buf.readByte();
        playerName = ByteBufUtil.readTString(buf);
        hairDye = buf.readByte();
        hideVisuals = buf.readByte();
        hideVisuals2 = buf.readByte();
        hideMisc = buf.readByte();
        hairColor = TColor.deserialize(buf);
        skinColor = TColor.deserialize(buf);
        eyeColor = TColor.deserialize(buf);
        shirtColor = TColor.deserialize(buf);
        undershirtColor = TColor.deserialize(buf);
        pantsColor = TColor.deserialize(buf);
        shoeColor = TColor.deserialize(buf);
        difficultyFlags = buf.readByte();
        torchFlags = buf.readByte();
        otherFlags = buf.readByte();
    }

    @Override
    public void processPacket(LoginHandler handler) {
        handler.handlePlayerInfo(this);
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
        buf.writeByte(skinVariant);
        buf.writeByte(hair);
        ByteBufUtil.writeTString(playerName, buf);
        buf.writeByte(hairDye);
        buf.writeByte(hideVisuals);
        buf.writeByte(hideVisuals2);
        buf.writeByte(hideMisc);
        hairColor.serialize(buf);
        skinColor.serialize(buf);
        eyeColor.serialize(buf);
        shirtColor.serialize(buf);
        undershirtColor.serialize(buf);
        pantsColor.serialize(buf);
        shoeColor.serialize(buf);
        buf.writeByte(difficultyFlags);
        buf.writeByte(torchFlags);
    }

    @Override
    public PacketType getType() {
        return PacketType.PlayerInfo;
    }
}
