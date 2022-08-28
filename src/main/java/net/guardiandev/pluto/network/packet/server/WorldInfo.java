package net.guardiandev.pluto.network.packet.server;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.util.ByteBufUtil;
import net.guardiandev.pluto.world.WorldData;

@AllArgsConstructor
public class WorldInfo implements ServerPacket {
    public WorldData worldData;

    @Override
    public void writePacket(ByteBuf buf) {
        buf.writeIntLE((int)worldData.time);
        buf.writeByte(0b10000000);
        buf.writeByte(worldData.moonPhase);
        buf.writeShortLE(worldData.maxTilesX);
        buf.writeShortLE(worldData.maxTilesY);
        buf.writeShortLE(worldData.spawnX);
        buf.writeShortLE(worldData.spawnY);
        buf.writeShortLE(worldData.surfaceLayer);
        buf.writeShortLE(worldData.rockLayer);
        buf.writeIntLE(worldData.worldId);
        ByteBufUtil.writeTString(worldData.worldName, buf);
        buf.writeByte(worldData.difficulty);
        buf.writeLongLE(worldData.worldUuid.getLeastSignificantBits());
        buf.writeLongLE(worldData.worldUuid.getMostSignificantBits());
        buf.writeLong(worldData.worldGenVersion);
        buf.writeByte(worldData.moonType);
        buf.writeByte(worldData.treeBackground1);
        buf.writeByte(worldData.corruptionBackground);
        buf.writeByte(worldData.jungleBackground);
        buf.writeByte(worldData.snowBackground);
        buf.writeByte(worldData.hallowBackground);
        buf.writeByte(worldData.crimsonBackground);
        buf.writeByte(worldData.desertBackground);
        buf.writeByte(worldData.oceanBackground);
        buf.writeByte(worldData.mushroomBackground);
        buf.writeByte(worldData.underworldBackground);
        buf.writeByte(worldData.treeBackground2);
        buf.writeByte(worldData.treeBackground3);
        buf.writeByte(worldData.treeBackground4);
        buf.writeByte(worldData.iceBackStyle);
        buf.writeByte(worldData.jungleBackStyle);
        buf.writeByte(worldData.hellBackStyle);
        buf.writeFloatLE(worldData.windSpeedTarget);
        buf.writeByte(worldData.cloudCount);
        buf.writeIntLE(worldData.treeX1);
        buf.writeIntLE(worldData.treeX2);
        buf.writeIntLE(worldData.treeX3);
        buf.writeByte(worldData.treeStyle1);
        buf.writeByte(worldData.treeStyle2);
        buf.writeByte(worldData.treeStyle3);
        buf.writeByte(worldData.treeStyle4);
        buf.writeByte(worldData.caveBackX1);
        buf.writeByte(worldData.caveBackX2);
        buf.writeByte(worldData.caveBackX3);
        buf.writeByte(worldData.caveBackStyle1);
        buf.writeByte(worldData.caveBackStyle2);
        buf.writeByte(worldData.caveBackStyle3);
        buf.writeByte(worldData.caveBackStyle4);
        buf.writeByte(worldData.forest1TreeTopStyle);
        buf.writeByte(worldData.forest2TreeTopStyle);
        buf.writeByte(worldData.forest3TreeTopStyle);
        buf.writeByte(worldData.forest4TreeTopStyle);
        buf.writeByte(worldData.corruptionTreeTopStyle);
        buf.writeByte(worldData.jungleTreeTopStyle);
        buf.writeByte(worldData.snowTreeTopStyle);
        buf.writeByte(worldData.hallowTreeTopStyle);
        buf.writeByte(worldData.crimsonTreeTopStyle);
        buf.writeByte(worldData.desertTreeTopStyle);
        buf.writeByte(worldData.oceanTreeTopStyle);
        buf.writeByte(worldData.mushroomTreeTopStyle);
        buf.writeByte(worldData.underworldTreeTopStyle);
        buf.writeFloatLE(worldData.maxRain);
        buf.writeByte(worldData.eventInfo1);
        buf.writeByte(worldData.eventInfo2);
        buf.writeByte(worldData.eventInfo3);
        buf.writeByte(worldData.eventInfo4);
        buf.writeByte(worldData.eventInfo5);
        buf.writeByte(worldData.eventInfo6);
        buf.writeByte(worldData.eventInfo7);
        buf.writeShortLE(worldData.copperTinTier);
        buf.writeShortLE(worldData.ironLeadTier);
        buf.writeShortLE(worldData.silverTungstenTier);
        buf.writeShortLE(worldData.goldPlatinumTier);
        buf.writeShortLE(worldData.cobaltPalladiumTier);
        buf.writeShortLE(worldData.mythrilOrichalcumTier);
        buf.writeShortLE(worldData.adamantiteTitaniumTier);
        buf.writeByte(worldData.invasionType);
        buf.writeLongLE(worldData.lobbyId);
        buf.writeFloatLE(worldData.sandstormSeverity);
    }

    @Override
    public PacketType getType() {
        return PacketType.WorldInfo;
    }
}
