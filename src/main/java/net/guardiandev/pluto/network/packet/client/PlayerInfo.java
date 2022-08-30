package net.guardiandev.pluto.network.packet.client;

import io.netty.buffer.ByteBuf;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.util.ByteBufUtil;
import net.guardiandev.pluto.util.TColor;

public class PlayerInfo implements ClientPacket {
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
    public PacketType getType() {
        return PacketType.PlayerInfo;
    }
}
