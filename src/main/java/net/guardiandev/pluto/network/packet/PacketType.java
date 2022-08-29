package net.guardiandev.pluto.network.packet;

import net.guardiandev.pluto.network.packet.both.PlayerHP;
import net.guardiandev.pluto.network.packet.both.PlayerUpdate;
import net.guardiandev.pluto.network.packet.client.PlayerBuffs;

import java.lang.reflect.InvocationTargetException;

/**
 * An enum for all the Packet Types in Terraria
 * @author Jay
 */
public enum PacketType {
    KeepAlive(0, From.SERVER), // Technically not a real packet
    ConnectRequest(1, From.CLIENT, net.guardiandev.pluto.network.packet.client.ConnectRequest.class),
    Disconnect(2, From.SERVER, net.guardiandev.pluto.network.packet.server.Disconnect.class),
    ContinueConnecting(3, From.SERVER, net.guardiandev.pluto.network.packet.server.ContinueConnecting.class),
    PlayerInfo(4, From.BOTH, net.guardiandev.pluto.network.packet.client.PlayerInfo.class),
    PlayerSlot(5, From.BOTH, net.guardiandev.pluto.network.packet.both.PlayerSlot.class),
    RequestWorldData(6, From.CLIENT, net.guardiandev.pluto.network.packet.client.RequestWorldData.class), // AKA ContinueConnecting2
    WorldInfo(7, From.SERVER, net.guardiandev.pluto.network.packet.server.WorldInfo.class),
    RequestEssentialTiles(8, From.CLIENT, net.guardiandev.pluto.network.packet.client.RequestEssentialTiles.class),
    Status(9, From.SERVER),
    SendTileSection(10, From.SERVER),
    TileSectionFrame(11, From.SERVER),
    PlayerSpawn(12, From.CLIENT, net.guardiandev.pluto.network.packet.client.PlayerSpawn.class),
    PlayerUpdate(13, From.BOTH, net.guardiandev.pluto.network.packet.both.PlayerUpdate.class),
    PlayerActive(14, From.SERVER),
    PlayerHp(16, From.BOTH, PlayerHP.class),
    ModifyTile(17, From.BOTH),
    TimeSet(18, From.SERVER),
    DoorUse(19, From.BOTH),
    TileSendSquare(20, From.BOTH),
    ItemDrop(21, From.BOTH),
    ItemOwner(22, From.BOTH),
    NpcUpdate(23, From.SERVER),
    NpcItemStrike(24, From.BOTH),
    ProjectileNew(27, From.BOTH),
    NpcStrike(28, From.BOTH),
    ProjectileDestroy(29, From.BOTH),
    TogglePvp(30, From.BOTH),
    OpenChest(31, From.CLIENT),
    UpdateChestItem(32, From.BOTH),
    SyncActiveChest(33, From.BOTH),
    PlaceChest(34, From.BOTH),
    EffectHeal(35, From.BOTH),
    PlayerZone(36, From.BOTH),
    PasswordRequired(37, From.SERVER, net.guardiandev.pluto.network.packet.server.PasswordRequired.class),
    PasswordSend(38, From.CLIENT, net.guardiandev.pluto.network.packet.client.PasswordSend.class),
    RemoveItemOwner(39, From.SERVER),
    NpcTalk(40, From.BOTH),
    PlayerItemAnimation(41, From.BOTH),
    PlayerMana(41, From.BOTH),
    ManaEffect(42, From.BOTH, net.guardiandev.pluto.network.packet.both.ManaEffect.class),
    PlayerTeam(45, From.BOTH),
    SignRead(46, From.CLIENT),
    SignNew(47, From.BOTH), // This guy's weird
    LiquidSet(48, From.BOTH),
    CompleteConnectionAndSpawn(49, From.SERVER),
    PlayerBuffs(50, From.BOTH, PlayerBuffs.class),
    NpcSpecialEffect(51, From.BOTH),
    ChestUnlock(52, From.BOTH),
    NpcAddBuff(53, From.BOTH),
    NpcUpdateBuff(54, From.SERVER),
    PlayerAddBuff(55, From.BOTH),
    UpdateNPCName(56, From.BOTH),
    UpdateGoodEvil(57, From.SERVER),
    PlayMusicItem(58, From.BOTH),
    HitSwitch(59, From.BOTH),
    UpdateNPCHome(60, From.BOTH),
    SpawnBossInvasion(61, From.CLIENT),
    PlayerDodge(62, From.BOTH),
    PaintTile(63, From.BOTH),
    PaintWall(64, From.BOTH),
    Teleport(65, From.BOTH),
    PlayerHealOther(66, From.BOTH),
    Placeholder(67, From.BOTH), // Special packet type
    ClientUUID(68, From.CLIENT, net.guardiandev.pluto.network.packet.client.ClientUUID.class),
    GetChestName(69, From.BOTH),
    CatchNPC(70, From.CLIENT),
    ReleaseNPC(71, From.CLIENT),
    TravellingMerchantInventory(72, From.SERVER),
    TeleportationPotion(73, From.BOTH),
    AnglerQuest(74, From.SERVER),
    CompleteAnglerQuest(75, From.CLIENT),
    NumberOfAnglerQuestsCompleted(76, From.CLIENT),
    CreateTemporaryAnimation(77, From.SERVER),
    ReportInvasionProgress(78, From.SERVER),
    PlaceObject(79, From.BOTH),
    SyncPlayerChestIndex(80, From.SERVER),
    CreateCombatText(81, From.SERVER),
    LoadNetModule(82, From.CLIENT),
    NpcKillCount(83, From.SERVER),
    PlayerStealth(84, From.BOTH),
    ForceItemIntoNearestChest(85, From.CLIENT),
    UpdateTileEntity(86, From.SERVER),
    PlaceTileEntity(87, From.CLIENT),
    TweakItem(88, From.SERVER),
    PlaceItemFrame(89, From.CLIENT),
    UpdateItemDrop(90, From.BOTH),
    EmoteBubble(91, From.SERVER),
    SyncExtraValue(92, From.BOTH),
    SocialHandshake(93, From.BOTH), // Unknown and unused
    Deprecated(94, From.BOTH), // Unknown and unused
    KillPortal(95, From.CLIENT),
    PlayerTeleportPortal(96, From.BOTH),
    NotifyPlayerNpcKilled(97, From.SERVER),
    NotifyPlayerOfEvent(98, From.SERVER),
    UpdateMinionTarget(99, From.BOTH),
    NpcTeleportPortal(100, From.BOTH),
    UpdateShieldStrengths(101, From.SERVER),
    NebulaLevelUp(102, From.BOTH),
    MoonLordCountdown(103, From.SERVER),
    NpcShopItem(104, From.SERVER),
    GemLockToggle(105, From.CLIENT),
    PoofOfSmoke(106, From.SERVER),
    SmartTextMessage(107, From.SERVER),
    WiredCannonShot(108, From.SERVER),
    MassWireOperation(109, From.CLIENT),
    MassWireOperationPay(110, From.SERVER),
    ToggleParty(111, From.CLIENT),
    TreeGrowFX(112, From.BOTH),
    CrystalInvasionStart(113, From.CLIENT),
    CrystalInvasionWipeAll(114, From.SERVER),
    MinionAttackTargetUpdate(115, From.CLIENT),
    CrystalInvasionSendWaitTime(116, From.SERVER),
    PlayerHurtV2(117, From.CLIENT),
    PlayerDeathV2(118, From.CLIENT),
    CreateCombatTextExtended(119, From.BOTH),
    Emoji(120, From.CLIENT),
    TileEntityDisplayDollItemSync(121, From.BOTH),
    RequestTileEntityInteraction(122, From.BOTH),
    WeaponsRackTryPlacing(123, From.CLIENT),
    TileEntityHatRackItemSync(124, From.BOTH),
    SyncTilePicking(125, From.BOTH),
    SyncRevengeMarker(126, From.SERVER),
    RemoveRevengeMarker(127, From.SERVER),
    LandGolfBallInCup(128, From.BOTH),
    FinishedConnectingToServer(129, From.SERVER),
    FishOutNPC(130, From.CLIENT),
    TamperWithNPC(131, From.SERVER),
    PlayLegacySound(132, From.SERVER),
    FoodPlatterTryPlacing(133, From.CLIENT),
    UpdatePlayerLuckFactors(134, From.BOTH),
    DeadPlayer(135, From.SERVER),
    SyncCavernMonsterType(136, From.BOTH),
    RequestNPCBuffRemoval(137, From.CLIENT),
    ClientSyncedInventory(138, From.CLIENT),
    SetCountsAsHostForGameplay(139, From.SERVER);

    /** The Packet's ID in the game */
    public final int ID;
    /**
     * Where the packet can come from
     * @see From
     */
    public final From from;
    public final Class<? extends Packet> packetClass;

    PacketType(int ID, From from) {
        this.ID = ID;
        this.from = from;
        this.packetClass = null;
    }

    PacketType(int ID, From from, Class<? extends Packet> packetClass) {
        this.ID = ID;
        this.from = from;
        this.packetClass = packetClass;
    }

    public Packet instantiateClass() {
        if(packetClass == null) return null;
        try {
            return packetClass.getDeclaredConstructor().newInstance();
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return null;
        }
    }

    public static PacketType fromID(int ID) {
        for(PacketType packet : values()) {
            if(packet.ID == ID) return packet;
        }
        return null;
    }

    public enum From {
        CLIENT,
        SERVER,
        BOTH
    }
}
