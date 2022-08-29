package net.guardiandev.pluto.world.loader;

import net.guardiandev.pluto.util.UnsignedByte;
import net.guardiandev.pluto.world.WorldData;
import net.guardiandev.pluto.world.WorldDifficulty;
import net.guardiandev.pluto.world.WorldEvil;
import net.guardiandev.pluto.world.tile.*;
import net.guardiandev.pluto.world.tracker.DownedTracker;
import net.guardiandev.pluto.world.World;
import net.guardiandev.pluto.world.tracker.SavedTracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class ReLogicWorldLoader extends AbstractWorldLoader {
    private int versionNumber;

    private long revision;
    private boolean favorited;
    private int[] positions;
    private boolean[] importance;
    private String name;
    private String rawSeed;
    private long worldGenVersion;
    private UUID uuid;
    private int worldID;
    private int leftWorld;
    private int rightWorld;
    private int topWorld;
    private int bottomWorld;
    private int maxTilesX;
    private int maxTilesY;
    private WorldDifficulty difficulty;
    private boolean drunk;
    private boolean getGood;
    private boolean tenthAnniversary;
    private boolean dontStarveWorld;
    private boolean notTheBeesWorld;

    // Some of this could be converted to a more readable format perhaps?

    private byte moonType;
    private int treeX0;
    private int treeX1;
    private int treeX2;
    private int treeStyle0;
    private int treeStyle1;
    private int treeStyle2;
    private int treeStyle3;
    private int caveBackX0;
    private int caveBackX1;
    private int caveBackX2;
    private int caveBackStyle0;
    private int caveBackStyle1;
    private int caveBackStyle2;
    private int caveBackStyle3;
    private int iceBackStyle;
    private int jungleBackStyle;
    private int hellBackStyle;
    private int spawnTileX;
    private int spawnTileY;
    private double worldSurface;
    private double rockLayer;
    private double time;
    private boolean daytime;
    private int moonPhase;
    private boolean bloodMoon;
    private boolean eclipse;
    private int dungeonX;
    private int dungeonY;
    private WorldEvil evil;
    // Gotta figure out what these are
    private boolean downedBoss1;
    private boolean downedBoss2;
    private boolean downedBoss3;
    private boolean downedQueenBee;
    private boolean downedMech1;
    private boolean downedMech2;
    private boolean downedMech3;
    private boolean downedAnyMech;
    private boolean downedPlantera;
    private boolean downedGolem;
    private boolean downedKingSlime;
    private boolean savedGoblin;
    private boolean savedWizard;
    private boolean savedMechanic;
    private boolean downedGoblins;
    private boolean downedClown;
    private boolean downedFrostMoon;
    private boolean downedPirates;
    private boolean shadowOrbSmashed;
    private boolean spawnMeteor;
    private byte shadowOrbCount;
    private int altarCount;
    private boolean hardmode;
    private int invasionDelay;
    private int invasionSize;
    private int invasionType;
    private double invasionX;
    private double slimeRainTime;
    // Can be byte?
    private short sundialCooldown;
    private boolean raining;
    private int rainTime;
    private float maxRain;
    private int hardmodeOre1TileID;
    private int hardmodeOre2TileID;
    private int hardmodeore3TileID;
    private int bgTree;
    private int bgCorruption;
    private int bgJungle;
    private int bgSnow;
    private int bgHallow;
    private int bgEvil;
    private int bgDesert;
    private int bgOcean;
    private int cloudBackgroundActive;
    private short numClouds;
    private float windSpeedTarget;
    private boolean savedAngler;
    private int anglerQuest;
    private boolean savedStylist;
    private boolean savedTaxCollecter;
    private boolean savedGolfer;
    private int invasionStartSize;
    private int cultistDelay;
    private boolean fastForwardTime;
    private boolean downedFishron;
    private boolean downedMartians;
    private boolean downedAncientCultist;
    private boolean downedMoonLord;
    private boolean downedPumpking;
    private boolean downedMourningWood;
    private boolean downedIceQueen;
    private boolean downedSantank;
    private boolean downedEverscream;
    private boolean downedSolarPillar;
    private boolean downedVortexPillar;
    private boolean downedNebulaPillar;
    private boolean downedStardustPillar;
    private boolean solarPillarActive;
    private boolean vortexPillarActive;
    private boolean nebulaPillarActive;
    private boolean stardustPillarActive;
    private boolean lunarApocalypse;
    private boolean partyManual;
    private boolean partyGenuine;
    private int partyCooldown;
    private boolean sandstormActive;
    private int sandstormTimeLeft;
    private float sandstormSeverity;
    private float sandstormIntendedSeverity;
    private boolean savedBartender;
    private boolean downedDD2InvasionT1;
    private boolean downedDD2InvasionT2;
    private boolean downedDD2InvasionT3;
    private int bgMushroom;
    private int bgUnderworld;
    private int bgTree2;
    private int bgTree3;
    private int bgTree4;
    private boolean combatBookUsed;
    private int lanternNightCooldown;
    private boolean lanternNightGenuine;
    private boolean lanternNightManual;
    private boolean lanternNightNextGenuine;
    private int[] treeTops = new int[13];
    private boolean forceHalloweenForToday;
    private boolean forceXMasForToday;
    private int ore1TileID;
    private int ore2TileID;
    private int ore3TileID;
    private int ore4TileID;
    private boolean boughtCat;
    private boolean boughtDog;
    private boolean boughtBunny;
    private boolean downedEmpressOfLight;
    private boolean downedQueenSlime;
    private boolean downedDeerclops;

    private int tempRle;

    /** All the tiles in this world, this should always eventually be disposed of */
    private Tile[][] tiles;
    //private Chest[] chests;
    //private Sign[] signs;
    //private NPC[] npcs;
    //private Mob[] mobs;
    //private TileEntity[] tileEntities;
    //private PressurePlate[] pressurePlates;

    public ReLogicWorldLoader(String fullPathToWorld) throws FileNotFoundException {
        super(fullPathToWorld);
    }

    public ReLogicWorldLoader(String directory, String worldName) throws FileNotFoundException {
        super(directory, worldName);
    }

    @Override
    public void loadWorld() throws IOException {
        loadFileFormatHeader();
        loadHeader();
        loadTiles();
        /*loadChests();
        loadSigns();
        loadNPCs();
        if(versionNumber >= 140) {
            loadMobs();

            loadTileEntities();
        }
        if(versionNumber >= 170) loadPressurePlates();
        if(versionNumber >= 189) loadNPCHomes();
        if(versionNumber >= 210) loadBestiary();
        if(versionNumber >= 220) loadCreativePowers();
        loadFooter();*/
    }

    @Override
    public World build() {
        DownedTracker downedTracker = new DownedTracker();
        downedTracker.setClownDowned(downedClown);
        downedTracker.setKingSlimeDowned(downedKingSlime);
        downedTracker.setGoblinsDowned(downedGoblins);
        downedTracker.setQueenBeeDowned(downedQueenBee);
        // Hmm
        downedTracker.setWallOfFleshDowned(hardmode);
        downedTracker.setQueenSlimeDowned(downedQueenSlime);
        downedTracker.setDestroyerDowned(downedMech1);
        // 2 other mechs need to be found
        downedTracker.setPiratesDowned(downedPirates);
        downedTracker.setPlanteraDowned(downedPlantera);
        downedTracker.setGolemDowned(downedGolem);
        downedTracker.setMourningWoodDowned(downedMourningWood);
        downedTracker.setPumpkingDowned(downedPumpking);
        downedTracker.setEverscreamDowned(downedEverscream);
        downedTracker.setSantankDowned(downedSantank);
        downedTracker.setIceQueenDowned(downedIceQueen);
        downedTracker.setDukeFishronDowned(downedFishron);
        downedTracker.setDD2T1Downed(downedDD2InvasionT1);
        downedTracker.setDD2T2Downed(downedDD2InvasionT2);
        downedTracker.setDD2T3Downed(downedDD2InvasionT3);
        downedTracker.setEmpressOfLightDowned(downedEmpressOfLight);
        downedTracker.setMartiansDowned(downedMartians);
        downedTracker.setLunaticCultistDowned(downedAncientCultist);
        downedTracker.setSolarPillarDowned(downedSolarPillar);
        downedTracker.setVortexPillarDowned(downedVortexPillar);
        downedTracker.setNebulaPillarDowned(downedNebulaPillar);
        downedTracker.setStardustPillarDowned(downedStardustPillar);
        downedTracker.setMoonLordDowned(downedMoonLord);

        SavedTracker savedTracker = new SavedTracker();
        savedTracker.setAnglerSaved(savedAngler);
        savedTracker.setGoblinSaved(savedGoblin);
        savedTracker.setMechanicSaved(savedMechanic);
        savedTracker.setGolferSaved(savedGolfer);
        savedTracker.setBartenderSaved(savedBartender);
        savedTracker.setWizardSaved(savedWizard);
        savedTracker.setStylistSaved(savedStylist);
        savedTracker.setTaxCollectorSaved(savedTaxCollecter);

        WorldData wd = new WorldData();
        wd.importantTiles = importance;
        wd.time = time;
        wd.dayTime = daytime;
        wd.bloodMoon = bloodMoon;
        wd.eclipse = eclipse;
        wd.difficulty = difficulty.ID;
        wd.spawnX = spawnTileX;
        wd.spawnY = spawnTileY;
        wd.maxTilesX = maxTilesX;
        wd.maxTilesY = maxTilesY;
        wd.worldGenVersion = worldGenVersion;
        wd.worldName = name;
        wd.worldId = worldID;
        wd.worldUuid = uuid;
        wd.moonType = moonType;
        wd.moonPhase = (byte)moonPhase;
        wd.treeX1 = treeX0;
        wd.treeX2 = treeX1;
        wd.treeX3 = treeX2;
        wd.treeStyle1 = (byte)treeStyle0;
        wd.treeStyle2 = (byte)treeStyle1;
        wd.treeStyle3 = (byte)treeStyle2;
        wd.treeStyle4 = (byte)treeStyle3;
        wd.caveBackX1 = (byte)caveBackX0;
        wd.caveBackX2 = (byte)caveBackX1;
        wd.caveBackX3 = (byte)caveBackX2;
        wd.iceBackStyle = (byte)iceBackStyle;
        wd.jungleBackStyle = (byte)jungleBackStyle;
        wd.hellBackStyle = (byte)hellBackStyle;
        wd.caveBackStyle1 = (byte)caveBackStyle0;
        wd.caveBackStyle2 = (byte)caveBackStyle1;
        wd.caveBackStyle3 = (byte)caveBackStyle2;
        wd.caveBackStyle4 = (byte)caveBackStyle3;
        wd.surfaceLayer = (short)worldSurface;
        wd.rockLayer = (short)rockLayer;
        wd.maxRain = maxRain;
        wd.copperTinTier = (short)ore1TileID;
        wd.ironLeadTier = (short)ore2TileID;
        wd.silverTungstenTier = (short)ore3TileID;
        wd.goldPlatinumTier = (short)ore4TileID;
        wd.cobaltPalladiumTier = (short)hardmodeOre1TileID;
        wd.mythrilOrichalcumTier = (short)hardmodeOre2TileID;
        wd.adamantiteTitaniumTier = (short)hardmodeore3TileID;
        wd.invasionType = (byte)invasionType;
        wd.cloudCount = (byte)numClouds;
        wd.windSpeedTarget = windSpeedTarget;
        wd.sandstormSeverity = sandstormSeverity;
        wd.topWorld = topWorld;
        wd.bottomWorld = bottomWorld;
        wd.leftWorld = leftWorld;
        wd.rightWorld = rightWorld;
        wd.treeBackground1 = (byte)bgTree;
        wd.treeBackground2 = (byte)bgTree2;
        wd.treeBackground3 = (byte)bgTree3;
        wd.treeBackground4 = (byte)bgTree4;
        wd.corruptionBackground = (byte)bgCorruption;
        wd.jungleBackground = (byte)bgJungle;
        wd.snowBackground = (byte)bgSnow;
        wd.hallowBackground = (byte)bgHallow;
        wd.crimsonBackground = (byte)bgEvil;
        wd.desertBackground = (byte)bgDesert;
        wd.oceanBackground = (byte)bgOcean;
        wd.mushroomBackground = (byte)bgMushroom;
        wd.underworldBackground = (byte)bgUnderworld;
        wd.forest1TreeTopStyle = (byte)treeTops[0];
        wd.forest2TreeTopStyle = (byte)treeTops[1];
        wd.forest3TreeTopStyle = (byte)treeTops[2];
        wd.forest4TreeTopStyle = (byte)treeTops[3];
        wd.corruptionTreeTopStyle = (byte)treeTops[4];
        wd.jungleTreeTopStyle = (byte)treeTops[5];
        wd.snowTreeTopStyle = (byte)treeTops[6];
        wd.hallowTreeTopStyle = (byte)treeTops[7];
        wd.crimsonTreeTopStyle = (byte)treeTops[8];
        wd.desertTreeTopStyle = (byte)treeTops[9];
        wd.oceanTreeTopStyle = (byte)treeTops[10];
        wd.mushroomTreeTopStyle = (byte)treeTops[11];
        wd.underworldTreeTopStyle = (byte)treeTops[12];
        /* boringWorldInfo.setTreeTops(treeTops);
        boringWorldInfo.setTileImportants(importance);
        boringWorldInfo.setSpawnMeteor(spawnMeteor);
        boringWorldInfo.setShadowOrbSmashed(shadowOrbSmashed);
        boringWorldInfo.setShadowOrbCount(shadowOrbCount);
        boringWorldInfo.setAltarCount(altarCount);
        boringWorldInfo.setInvasionDelay(invasionDelay);
        boringWorldInfo.setInvasionStartSize(invasionStartSize);
        boringWorldInfo.setInvasionSize(invasionSize);
        boringWorldInfo.setInvasionType(invasionType);
        boringWorldInfo.setInvasionX(invasionX);
        boringWorldInfo.setSlimeRainTime(slimeRainTime);
        boringWorldInfo.setSundialCooldown(sundialCooldown);
        boringWorldInfo.setRainTime(rainTime);
        boringWorldInfo.setBgTree(bgTree);
        boringWorldInfo.setBgCorruption(bgCorruption);
        boringWorldInfo.setBgJungle(bgJungle);
        boringWorldInfo.setBgSnow(bgSnow);
        boringWorldInfo.setBgHallow(bgHallow);
        boringWorldInfo.setBgCrimson(bgEvil);
        boringWorldInfo.setBgDesert(bgDesert);
        boringWorldInfo.setBgOcean(bgOcean);
        boringWorldInfo.setCloudBackgroundActive(cloudBackgroundActive);
        boringWorldInfo.setNumClouds(numClouds);
        boringWorldInfo.setWindSpeedTarget(windSpeedTarget);
        boringWorldInfo.setAnglerQuest(anglerQuest);
        boringWorldInfo.setCultistDelay(cultistDelay);
        boringWorldInfo.setFastForwardTime(fastForwardTime);
        boringWorldInfo.setSolarPillarActive(solarPillarActive);
        boringWorldInfo.setVortexPillarActive(vortexPillarActive);
        boringWorldInfo.setNebulaPillarActive(nebulaPillarActive);
        boringWorldInfo.setStardustPillarActive(stardustPillarActive);
        boringWorldInfo.setLunarApocalypse(lunarApocalypse);
        boringWorldInfo.setPartyManual(partyManual);
        boringWorldInfo.setPartyGenuine(partyGenuine);
        boringWorldInfo.setPartyCooldown(partyCooldown);
        boringWorldInfo.setSandstormActive(sandstormActive);
        boringWorldInfo.setSandstormTimeLeft(sandstormTimeLeft);
        boringWorldInfo.setSandstormIntendedSeverity(sandstormIntendedSeverity);
        boringWorldInfo.setBgMushroom(bgMushroom);
        boringWorldInfo.setBgUnderworld(bgUnderworld);
        boringWorldInfo.setBgTree2(bgTree2);
        boringWorldInfo.setBgTree3(bgTree3);
        boringWorldInfo.setBgTree4(bgTree4);
        boringWorldInfo.setCombatBookUsed(combatBookUsed);
        boringWorldInfo.setLanternNightCooldown(lanternNightCooldown);
        boringWorldInfo.setLanternNightGenuine(lanternNightGenuine);
        boringWorldInfo.setLanternNightManual(lanternNightManual);
        boringWorldInfo.setLanternNightNextGenuine(lanternNightNextGenuine);
        boringWorldInfo.setForceHalloweenForToday(forceHalloweenForToday);
        boringWorldInfo.setForceXMasForToday(forceXMasForToday);
        boringWorldInfo.setBoughtCat(boughtCat);
        boringWorldInfo.setBoughtDog(boughtDog);
        boringWorldInfo.setBoughtBunny(boughtBunny);*/

        World world = new World(/*, uuid, worldID, name, rawSeed, worldGenVersion, maxTilesX, maxTilesY, difficulty, evil, spawnTileX, spawnTileY, dungeonX, dungeonY, hardmode, daytime, time, bloodMoon, eclipse, raining, downedTracker, savedTracker, tiles*/);
        world.setWorldData(wd);
        world.setTiles(tiles);
        // Get rid of dead memory
        tiles = null;
        return world;
    }


    protected void loadFileFormatHeader() throws IOException {
        versionNumber = reader.readInt();

        if(versionNumber >= 135) {
            // This spells out relogic in correct file format :')
            long relogicWatermark = reader.readLong();
            if((relogicWatermark & 0xFFFFFFFFFFFFFFL) != 27981915666277746L) throw new RuntimeException("expected Re-Logic file format");

            byte fileFormatByte = (byte)((relogicWatermark >> 56) & 0xFF);

            if(fileFormatByte != 2) throw new RuntimeException("expected world file type");

            revision = Integer.toUnsignedLong(reader.readInt());
            favorited = (reader.readLong() & 1) == 1;
        }

        short positionsLength = reader.readShort();
        positions = new int[positionsLength];
        for(int i = 0; i < positionsLength; i++) {
            positions[i] = reader.readInt();
        }

        short importanceLength = reader.readShort();
        importance = new boolean[importanceLength];
        // Bit packed
        UnsignedByte b = new UnsignedByte(0);
        UnsignedByte b2 = new UnsignedByte(128);
        for(int i = 0; i < importanceLength; i++) {
            if(b2.readReal() == 128) {
                b.setRaw(reader.readByte());
                b2.setRaw((byte)1);
            } else {
                b2.setRaw((byte)(b2.readRaw() << 1));
            }
            if((b.readRaw() & b2.readRaw()) == b2.readRaw()) {
                importance[i] = true;
            }
        }
    }

    protected void loadHeader() throws IOException {
        name = reader.readString();

        if(versionNumber >= 179) {
            rawSeed = versionNumber != 179 ? reader.readString() : String.valueOf(reader.readInt());
            worldGenVersion = reader.readLong();
        }

        uuid = versionNumber >= 181 ? new UUID(reader.readLong(), reader.readLong()) : UUID.randomUUID();

        worldID = reader.readInt();
        leftWorld = reader.readInt();
        rightWorld = reader.readInt();
        topWorld = reader.readInt();
        bottomWorld = reader.readInt();
        maxTilesY = reader.readInt();
        maxTilesX = reader.readInt();

        tiles = new Tile[maxTilesX][maxTilesY];

        if(versionNumber >= 209) {
            difficulty = WorldDifficulty.fromID(reader.readInt());
            if(versionNumber >= 222) drunk = reader.readBoolean();
            if(versionNumber >= 227) getGood = reader.readBoolean();
            if(versionNumber >= 238) tenthAnniversary = reader.readBoolean();
            if(versionNumber >= 239) dontStarveWorld = reader.readBoolean();
            if(versionNumber >= 241) notTheBeesWorld = reader.readBoolean();
        } else {
            if(versionNumber >= 112) difficulty = reader.readBoolean() ? WorldDifficulty.Expert : WorldDifficulty.Classic;
            else difficulty = WorldDifficulty.Classic;
            if(versionNumber == 208 && reader.readBoolean()) difficulty = WorldDifficulty.Master;
        }

        // TODO Read creation time
        if(versionNumber >= 141) reader.readLong();

        moonType = reader.readByte();
        treeX0 = reader.readInt();
        treeX1 = reader.readInt();
        treeX2 = reader.readInt();
        treeStyle0 = reader.readInt();
        treeStyle1 = reader.readInt();
        treeStyle2 = reader.readInt();
        treeStyle3 = reader.readInt();
        caveBackX0 = reader.readInt();
        caveBackX1 = reader.readInt();
        caveBackX2 = reader.readInt();
        caveBackStyle0 = reader.readInt();
        caveBackStyle1 = reader.readInt();
        caveBackStyle2 = reader.readInt();
        caveBackStyle3 = reader.readInt();
        iceBackStyle = reader.readInt();
        jungleBackStyle = reader.readInt();
        hellBackStyle = reader.readInt();

        spawnTileX = reader.readInt();
        spawnTileY = reader.readInt();
        worldSurface = reader.readDouble();
        rockLayer = reader.readDouble();
        time = reader.readDouble();
        daytime = reader.readBoolean();
        moonPhase = reader.readInt();
        bloodMoon = reader.readBoolean();
        eclipse = reader.readBoolean();
        dungeonX = reader.readInt();
        dungeonY = reader.readInt();

        evil = WorldEvil.fromBoolean(reader.readBoolean());

        downedBoss1 = reader.readBoolean();
        downedBoss2 = reader.readBoolean();
        downedBoss3 = reader.readBoolean();
        downedQueenBee = reader.readBoolean();
        downedMech1 = reader.readBoolean();
        downedMech2 = reader.readBoolean();
        downedMech3 = reader.readBoolean();
        downedAnyMech = reader.readBoolean();
        downedPlantera = reader.readBoolean();
        downedGolem = reader.readBoolean();

        if(versionNumber >= 118) downedKingSlime = reader.readBoolean();

        savedGoblin = reader.readBoolean();
        savedWizard = reader.readBoolean();
        savedMechanic = reader.readBoolean();
        downedGoblins = reader.readBoolean();
        downedClown = reader.readBoolean();
        downedFrostMoon = reader.readBoolean();
        downedPirates = reader.readBoolean();

        shadowOrbSmashed = reader.readBoolean();
        spawnMeteor = reader.readBoolean();
        shadowOrbCount = reader.readByte();
        altarCount = reader.readInt();
        hardmode = reader.readBoolean();
        invasionDelay = reader.readInt();
        invasionSize = reader.readInt();
        invasionType = reader.readInt();
        invasionX = reader.readDouble();

        if(versionNumber >= 118) {
            slimeRainTime = reader.readDouble();
        }

        if(versionNumber >= 113) {
            sundialCooldown = reader.readByte();
        }

        raining = reader.readBoolean();
        rainTime = reader.readInt();
        maxRain = reader.readFloat();
        hardmodeOre1TileID = reader.readInt();
        hardmodeOre2TileID = reader.readInt();
        hardmodeore3TileID = reader.readInt();
        bgTree = reader.readByte();
        bgCorruption = reader.readByte();
        bgJungle = reader.readByte();
        bgSnow = reader.readByte();
        bgHallow = reader.readByte();
        bgEvil = reader.readByte();
        bgDesert = reader.readByte();
        bgOcean = reader.readByte();
        cloudBackgroundActive = reader.readInt();
        numClouds = reader.readShort();
        windSpeedTarget = reader.readFloat();

        if(versionNumber >= 95) {
            for(int i = reader.readInt(); i > 0; i--) {
                reader.readString();
            }
        }

        if(versionNumber < 99) return;

        savedAngler = reader.readBoolean();
        if(versionNumber < 101) return;
        anglerQuest = reader.readInt();

        if(versionNumber < 104) return;

        savedStylist = reader.readBoolean();

        if(versionNumber >= 129) savedTaxCollecter = reader.readBoolean();

        if(versionNumber >= 201) savedGolfer = reader.readBoolean();

        if(versionNumber >= 107) invasionStartSize = reader.readInt();
        cultistDelay = versionNumber >= 108 ? reader.readInt() : 86400;
        int numberOfMobs = reader.readShort();
        for(int i = 0; i < numberOfMobs; i++) {
            if(i < 663) reader.readInt();
            else reader.readInt();
        }

        fastForwardTime = reader.readBoolean();
        downedFishron = reader.readBoolean();
        downedMartians = reader.readBoolean();
        downedAncientCultist = reader.readBoolean();
        downedMoonLord = reader.readBoolean();
        downedPumpking = reader.readBoolean();
        downedMourningWood = reader.readBoolean();
        downedIceQueen = reader.readBoolean();
        downedSantank = reader.readBoolean();
        downedEverscream = reader.readBoolean();
        downedSolarPillar = reader.readBoolean();
        downedVortexPillar = reader.readBoolean();
        downedNebulaPillar = reader.readBoolean();
        downedStardustPillar = reader.readBoolean();
        solarPillarActive = reader.readBoolean();
        vortexPillarActive = reader.readBoolean();
        nebulaPillarActive = reader.readBoolean();
        stardustPillarActive = reader.readBoolean();
        lunarApocalypse = reader.readBoolean();

        if(versionNumber >= 170) {
            partyManual = reader.readBoolean();
            partyGenuine = reader.readBoolean();
            partyCooldown = reader.readInt();
            int numparty = reader.readInt();
            for(int counter = 0; counter < numparty; counter++) {
                reader.readInt();
            }
        }
        if(versionNumber >= 174) {
            sandstormActive = reader.readBoolean();
            sandstormTimeLeft = reader.readInt();
            sandstormSeverity = reader.readFloat();
            sandstormIntendedSeverity = reader.readFloat();
        }
        if(versionNumber >= 178) {
            savedBartender = reader.readBoolean();
            downedDD2InvasionT1 = reader.readBoolean();
            downedDD2InvasionT2 = reader.readBoolean();
            downedDD2InvasionT3 = reader.readBoolean();
        }

        // 1.4 Journey's End
        if(versionNumber > 194) bgMushroom = reader.readByte();

        if(versionNumber >= 215) bgUnderworld = reader.readByte();

        if(versionNumber >= 195) {
            bgTree2 = reader.readByte();
            bgTree3 = reader.readByte();
            bgTree4 = reader.readByte();
        }

        if(versionNumber >= 204) combatBookUsed = reader.readBoolean();

        if(versionNumber >= 207) {
            lanternNightCooldown = reader.readInt();
            lanternNightGenuine = reader.readBoolean();
            lanternNightManual = reader.readBoolean();
            lanternNightNextGenuine = reader.readBoolean();
        }

        // Tree tops
        if(versionNumber >= 211) {
            int numTrees = reader.readInt();

            for(int i = 0; i < numTrees; i++) {
                treeTops[i] = reader.readInt();
            }
        } else {
            treeTops[0] = treeStyle0;
            treeTops[1] = treeStyle1;
            treeTops[2] = treeStyle2;
            treeTops[3] = treeStyle3;
            treeTops[4] = bgCorruption;
            treeTops[5] = jungleBackStyle;
            treeTops[6] = bgSnow;
            treeTops[7] = bgHallow;
            treeTops[8] = bgEvil;
            treeTops[9] = bgDesert;
            treeTops[10] = bgOcean;
            treeTops[11] = bgMushroom;
            treeTops[12] = bgUnderworld;
        }

        if(versionNumber >= 212) {
            forceHalloweenForToday = reader.readBoolean();
            forceXMasForToday = reader.readBoolean();
        }

        if(versionNumber >= 216) {
            ore1TileID = reader.readInt();
            ore2TileID = reader.readInt();
            ore3TileID = reader.readInt();
            ore4TileID = reader.readInt();
        }

        if(versionNumber >= 217) {
            boughtCat = reader.readBoolean();
            boughtDog = reader.readBoolean();
            boughtBunny = reader.readBoolean();
        }

        if(versionNumber >= 223) {
            downedEmpressOfLight = reader.readBoolean();
            downedQueenSlime = reader.readBoolean();
        }

        if(versionNumber >= 240) {
            downedDeerclops = reader.readBoolean();
        }
    }

    protected void loadTiles() throws IOException {
        for(int x = 0; x < maxTilesX; x++) {
            for(int y = 0; y < maxTilesY; y++) {
                Tile tile = deserializeTileData(x, y);

                tiles[x][y] = tile;

                while(tempRle > 0) {
                    y++;

                    if (y >= maxTilesY) throw new RuntimeException("RLE compression out of bounds: " + x);
                    tiles[x][y] = tile.copy(y);
                    tempRle--;
                }
                // Maybe add some recovery in case of error like TEDIT?
            }
        }
    }

    /*protected void loadChests() throws IOException {
        short chestCount = reader.readShort();

        chests = new Chest[chestCount];

        short maxItems = reader.readShort();

        int itemsPerChest = maxItems;
        int overflowItems = 0;
        if(maxItems > Chest.DefaultSize) {
            itemsPerChest = Chest.DefaultSize;
            overflowItems = maxItems - Chest.DefaultSize;
        }

        for(int chestNum = 0; chestNum < chestCount; chestNum++) {
            chests[chestNum] = deserializeChestData(itemsPerChest, overflowItems);
        }
    }*/

    /*protected void loadSigns() throws IOException {
        short signCount = reader.readShort();

        signs = new Sign[signCount];

        for(int signNum = 0; signNum < signCount; signNum++) signs[signNum] = deserializeSignData();
    }*/

    /*protected void loadNPCs() throws IOException {
        List<NPC> npcList = new ArrayList<>();

        for(boolean i = reader.readBoolean(); i; i = reader.readBoolean()) npcList.add(deserializeNPCData());

        npcs = npcList.toArray(new NPC[0]);
    }

    protected void loadMobs() throws IOException {
        List<Mob> mobList = new ArrayList<>();

        for(boolean i = reader.readBoolean(); i; i = reader.readBoolean()) mobList.add(deserializeMobData());

        mobs = mobList.toArray(new Mob[0]);
    }

    protected void loadTileEntities() throws IOException {
        int tileEntitiesCount = reader.readInt();

        tileEntities = new TileEntity[tileEntitiesCount];

        for(int tileEntityNum = 0; tileEntityNum < tileEntitiesCount; tileEntityNum++) tileEntities[tileEntityNum] = deserializeTileEntityData();
    }

    protected void loadPressurePlates() throws IOException {
        int pressurePlateCount = reader.readInt();

        pressurePlates = new PressurePlate[pressurePlateCount];

        for(int pressurePlateNum = 0; pressurePlateNum < pressurePlateCount; pressurePlateNum++) pressurePlates[pressurePlateNum] = deserializePressurePlateData();
    }

    protected void loadNPCHomes() throws IOException {
        int homeCount = reader.readInt();

        for(int homeNum = 0; homeNum < homeCount; homeNum++) {
            // NPC ID
            reader.readInt();
            // Position
            reader.readInt();
            reader.readInt();
        }
    }

    protected void loadBestiary() throws IOException {
        int killsCount = reader.readInt();
        for(int killsNum = 0; killsNum < killsCount; killsNum++) {
            reader.readString();
            reader.readInt();
        }
        int seenCount = reader.readInt();
        for(int seenNum = 0; seenNum < seenCount; seenNum++) {
            reader.readString();
        }
        int chatCount = reader.readInt();
        for(int chatNum = 0; chatNum < chatCount; chatNum++) {
            reader.readString();
        }
    }

    protected void loadCreativePowers() throws IOException {
        while(reader.readBoolean()) {
            short powerID = reader.readShort();

            CreativePowers creativePower = CreativePowers.fromID(powerID);

            if(creativePower == null) continue;

            switch(creativePower) {
                case TimeSetFrozen -> reader.readBoolean();
                case GodMode -> reader.readBoolean();
                case TimeSetSpeed -> reader.readFloat();
                case RainSetFrozen -> reader.readBoolean();
                case WindSetFrozen -> reader.readBoolean();
                case IncreasePlacementRange -> reader.readBoolean();
                case SetDifficulty -> reader.readFloat();
                case BiomeSpreadSetFrozen -> reader.readBoolean();
                case SetSpawnRate -> reader.readFloat();
            }
        }
    }

    protected void loadFooter() throws WorldLoadingException, IOException {
        if(!reader.readBoolean()) throw new WorldLoadingException("invalid footer");
        if(!reader.readString().equals(name)) throw new WorldLoadingException("invalid footer");
        if(reader.readInt() != worldID) throw new WorldLoadingException("invalid footer");
    }*/

    protected Tile deserializeTileData(int tileX, int tileY) throws IOException {
        byte header1 = reader.readByte();
        byte header2 = 0;
        byte header3 = 0;

        short tileBlockID = -1;
        short blockU = -1;
        short blockV = -1;
        byte blockColor = 0;

        short tileWallID = -1;
        byte wallColor = 0;

        byte liquidAmount = 0;
        Liquid liquidType = null;

        Block.Shape shape = Block.Shape.Solid;
        WireType wire = null;

        boolean hasActuator = false;
        boolean isActuated = false;

        // Check 1st bit on 1st header to see if we need a 2nd header
        if((header1 & 1) == 1) {
            header2 = reader.readByte();

            // Check 1st bit on 2nd header to see if we need a 3rd header
            if((header2 & 1) == 1) header3 = reader.readByte();
        }

        // Check if we have a block
        if((header1 & 2) == 2)
        {

            // Read tile ID
            if((header1 & 32) != 32) tileBlockID = reader.readUnsignedByte(); // Tile ID is smaller than or equal to 255 and can be read as a byte
            else {
                // Tile ID is larger than 255 and needs to be read as a short
                short lowerByte = reader.readUnsignedByte();
                short upperByte = reader.readUnsignedByte();

                tileBlockID = (short)(upperByte << 8 | lowerByte);
            }

            // Check if tile ID is important
            if(importance[tileBlockID]) {
                // Read it's UV coords
                blockU = reader.readShort();
                blockV = reader.readShort();

                // reset timers
                /*if (tile.Type == (int)TileType.Timer)
                {
                    tile.V = 0;
                }*/
            }

            // Check for block color and read it if it exists
            if((header3 & 8) == 8) blockColor = reader.readByte();
        }

        // Check if we have a wall
        if((header1 & 4) == 4) {
            // Read wall ID
            tileWallID = reader.readUnsignedByte();

            // Check for wall color and read it if it exists
            if((header3 & 16) == 16) wallColor = reader.readByte();
        }

        // Check for liquids
        byte liquidTypeRaw = (byte)((header1 & 24) >> 3);
        if(liquidTypeRaw != 0) {
            liquidAmount = reader.readByte();
            if(liquidTypeRaw == 1) {
                liquidType = Liquid.Water;
            } else if(liquidTypeRaw == 2) {
                liquidType = Liquid.Lava;
            } else {
                liquidType = Liquid.Honey;
            }
        }

        // Check if we have data in header 2 other than just telling us we have header 3
        if(header2 > 1) {
            // Red wire
            if ((header2 & 2) == 2) wire = WireType.Red;
            // Blue wire
            if ((header2 & 4) == 4) wire = WireType.Blue;
            // Green wire
            if ((header2 & 8) == 8) wire = WireType.Green;

            // Get Modification
            byte rawModification = (byte)((header2 & 112) >> 4);
            // Maybe check if this block is allowed to have a modification
            shape = Block.Shape.fromID(rawModification);
            // If the read modification was invalid default to solid
            if(shape == null) shape = Block.Shape.Solid;
        }

        // Check if we have data in header 3
        if (header3 > 0) {
            // Check if actuator is on this tile
            if((header3 & 2) == 2) hasActuator = true;

            // Check if actuated
            if((header3 & 4) == 4) isActuated = true;

            // Yellow wire
            if((header3 & 32) == 32) wire = WireType.Yellow;

            if(versionNumber >= 222) {
                if((header3 & 64) == 64) tileWallID = (short)(reader.readUnsignedByte() << 8 | tileWallID);
            }
        }

        // Read the RLE compression
        byte rleStorageType = (byte)((header1 & 192) >> 6);
        switch (rleStorageType) {
            case 0 -> tempRle = 0;
            case 1 -> tempRle = reader.readUnsignedByte();
            default -> tempRle = reader.readShort();
        }

        Block block = tileBlockID != -1 ? new Block(tileBlockID) : null;
        Wall wall = tileWallID != -1 ? new Wall(tileWallID) : null;

        if(block != null) {
            block.setFrameX(blockU);
            block.setFrameY(blockV);
            block.setColor(blockColor);
            block.setShapeEnum(shape);
        }

        if(wall != null) wall.setColor(wallColor);

        Tile tile = new Tile(block, wall, tileX, tileY);

        tile.setLiquid(liquidType);
        tile.setLiquidAmount(liquidAmount);
        tile.setWire(wire);
        tile.setHasActuator(hasActuator);
        tile.setActuated(isActuated);

        return tile;
    }

    /*protected Chest deserializeChestData(int itemsPerChest, int overflowItems) throws IOException {
        if(itemsPerChest < 0 || itemsPerChest > Chest.DefaultSize) throw new IllegalArgumentException("Items per chest cannot be less than 0 or more than chest default size (" + Chest.DefaultSize + ")");
        Item[] items = new Item[Chest.DefaultSize];
        Arrays.fill(items, Item.Air);

        short x = (short)reader.readInt();
        short y = (short)reader.readInt();
        String name = reader.readString();

        // Read items
        for(int slot = 0; slot < itemsPerChest; slot++) {
            short stackSize = reader.readShort();

            if(stackSize <= 0) continue;

            short id = (short)reader.readInt();
            short prefix = reader.readUnsignedByte();

            items[slot] = new Item(id, prefix, stackSize);
        }

        // Dump overflow items
        for(int overflow = 0; overflow < overflowItems; overflow++) {
            short stackSize = reader.readShort();

            if(stackSize <= 0) continue;

            reader.readInt();
            reader.readUnsignedByte();
        }

        return new Chest(name, x, y, items);
    }

    protected Sign deserializeSignData() throws IOException {
        return new Sign(reader.readString(), reader.readInt(), reader.readInt());
    }

    protected NPC deserializeNPCData() throws IOException {
        NPC npc = new NPC();

        if(versionNumber >= 190) npc.setSpriteID(reader.readInt());
        else npc.setName(reader.readString());

        npc.setDisplayName(reader.readString());
        npc.setPosition(new Vector2(reader.readFloat(), reader.readFloat()));
        npc.setHomeless(reader.readBoolean());
        // Could break smthn
        npc.setHomeX(reader.readInt());
        npc.setHomeY(reader.readInt());

        if(versionNumber >= 213 && (reader.readByte() & 1) == 1) npc.setTownNpcVariationIndex(reader.readInt());

        return npc;
    }

    protected Mob deserializeMobData() throws IOException {
        Mob mob = new Mob();

        if(versionNumber >= 190) mob.setSpriteID(reader.readInt());
        else mob.setName(reader.readString());

        mob.setPosition(new Vector2(reader.readFloat(), reader.readFloat()));

        return mob;
    }

    protected TileEntity deserializeTileEntityData() throws IOException {
        short type = reader.readUnsignedByte();
        int ID = reader.readInt();
        short x = reader.readShort();
        short y = reader.readShort();

        switch(type) {
            case 0 -> {
                return new TrainingDummy(ID, x, y, reader.readShort());
            }
            case 1 -> {
                return new ItemFrame(ID, x, y, new Item(reader.readShort(), reader.readUnsignedByte(), reader.readShort()));
            }
            case 2 -> {
                return new LogicSensor(ID, x, y, reader.readByte(), reader.readBoolean());
            }
            case 3 -> {
                byte slots = reader.readByte();

                Item[] items = new Item[8];
                Item[] dyes = new Item[8];

                for(int i = 0; i < 8; i++ ) {
                    if(((slots >> i) & 1) == 1) items[i] = new Item(reader.readShort(), reader.readUnsignedByte(), reader.readShort());
                }

                for(int i = 0; i < 8; i++ ) {
                    if(((slots >> i + 2) & 1) == 1) dyes[i] = new Item(reader.readShort(), reader.readUnsignedByte(), reader.readShort());
                }

                return new DisplayDoll(ID, x, y, items, dyes);
            }
            case 4 -> {
                return new WeaponsRack(ID, x, y, new Item(reader.readShort(), reader.readUnsignedByte(), reader.readShort()));
            }
            case 5 -> {
                byte slots = reader.readByte();

                Item[] items = new Item[2];
                Item[] dyes = new Item[2];

                for(int i = 0; i < 2; i++ ) {
                    if(((slots >> i) & 1) == 1) items[i] = new Item(reader.readShort(), reader.readUnsignedByte(), reader.readShort());
                }

                for(int i = 0; i < 2; i++ ) {
                    if(((slots >> i + 2) & 1) == 1) dyes[i] = new Item(reader.readShort(), reader.readUnsignedByte(), reader.readShort());
                }

                return new DisplayDoll(ID, x, y, items, dyes);
            }
            case 6 -> {
                return new FoodPlatter(ID, x, y, new Item(reader.readShort(), reader.readUnsignedByte(), reader.readShort()));
            }
            case 7 -> {
                return new TeleportationPylon(ID, x, y);
            }
            default -> {
                return null;
            }
        }
    }

    protected PressurePlate deserializePressurePlateData() throws IOException {
        return new PressurePlate(reader.readInt(), reader.readInt());
    }*/

    public static int progressPercentage(int index, int total)
    {
        int val = (int)((float)index / total * 100.0f);

        if (val > 100)
            val = 100;
        if (val < 0)
            val = 0;

        return val;
    }
}
