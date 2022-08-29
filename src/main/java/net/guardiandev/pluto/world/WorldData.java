package net.guardiandev.pluto.world;

import java.util.UUID;

public class WorldData {
    public boolean[] importantTiles;
    public double time;
    public boolean dayTime;
    public boolean bloodMoon;
    public boolean eclipse;
    public byte moonPhase;
    public int maxTilesX;
    public int maxTilesY;
    public int spawnX;
    public int spawnY;
    public short surfaceLayer;
    public short rockLayer;
    public int worldId;
    public String worldName;
    public byte difficulty;
    public UUID worldUuid;
    public long worldGenVersion;
    public byte moonType;
    public byte treeBackground1;
    public byte corruptionBackground, jungleBackground, snowBackground, hallowBackground, crimsonBackground, desertBackground, oceanBackground, mushroomBackground, underworldBackground;
    public byte treeBackground2, treeBackground3, treeBackground4;
    public byte iceBackStyle;
    public byte jungleBackStyle;
    public byte hellBackStyle;
    public float windSpeedTarget;
    public byte cloudCount;
    public int treeX1, treeX2, treeX3;
    public byte treeStyle1, treeStyle2, treeStyle3, treeStyle4;
    public byte caveBackX1, caveBackX2, caveBackX3;
    public byte caveBackStyle1, caveBackStyle2, caveBackStyle3, caveBackStyle4;
    public byte forest1TreeTopStyle, forest2TreeTopStyle, forest3TreeTopStyle, forest4TreeTopStyle;
    public byte corruptionTreeTopStyle, jungleTreeTopStyle, snowTreeTopStyle, hallowTreeTopStyle, crimsonTreeTopStyle, desertTreeTopStyle, oceanTreeTopStyle, mushroomTreeTopStyle, underworldTreeTopStyle;
    public float maxRain;
    public byte eventInfo1, eventInfo2, eventInfo3, eventInfo4, eventInfo5, eventInfo6, eventInfo7;
    public short copperTinTier, ironLeadTier, silverTungstenTier, goldPlatinumTier, cobaltPalladiumTier, mythrilOrichalcumTier, adamantiteTitaniumTier;
    public byte invasionType;
    public long lobbyId;
    public float sandstormSeverity;
    public int leftWorld;
    public int rightWorld;
    public int topWorld;
    public int bottomWorld;
}
