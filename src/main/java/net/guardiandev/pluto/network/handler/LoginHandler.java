package net.guardiandev.pluto.network.handler;

import io.netty.channel.Channel;
import lombok.Data;
import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.data.Character;
import net.guardiandev.pluto.data.NetworkText;
import net.guardiandev.pluto.data.item.Item;
import net.guardiandev.pluto.data.item.Prefix;
import net.guardiandev.pluto.entity.player.Player;
import net.guardiandev.pluto.network.packet.both.*;
import net.guardiandev.pluto.network.packet.client.*;
import net.guardiandev.pluto.network.packet.server.*;
import net.guardiandev.pluto.util.TColor;
import net.guardiandev.pluto.world.World;
import net.guardiandev.pluto.world.WorldData;

import java.util.UUID;

@Data
public class LoginHandler {
    private Channel channel;
    private int state = 0;

    public void handleConnectRequest(ConnectRequest packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());

        if(player.getState() != 0) return;
        Pluto.logger.info("Join with version {}", packet.version);

        player.setPlayerId(Pluto.playerManager.allocateAvailablePlayerId());
        if(player.getPlayerId() == -1) {
            ServerPacket disconnect = new Disconnect(new NetworkText("Server full", NetworkText.Mode.LITERAL));
            player.sendPacket(disconnect);
            return;
        }

        player.sendPacket(new ContinueConnecting((short)player.getPlayerId()));
        player.setState(1);
    }

    public void handlePlayerInfo(PlayerInfo packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());

        if(player.getState() != 1) return;
        Character character = new Character();
        character.setSkinVariant(packet.skinVariant);
        character.setHair(packet.hair);
        character.setName(packet.playerName);
        character.setHairDye(packet.hairDye);
        character.setHideVisuals(packet.hideVisuals);
        character.setHideVisuals2(packet.hideVisuals2);
        character.setHideMisc(packet.hideMisc);
        character.setHairColor(packet.hairColor);
        character.setSkinColor(packet.skinColor);
        character.setEyeColor(packet.eyeColor);
        character.setShirtColor(packet.shirtColor);
        character.setUndershirtColor(packet.undershirtColor);
        character.setPantsColor(packet.pantsColor);
        character.setShoeColor(packet.shoeColor);
        Character.Difficulty difficulty = null;
        for(Character.Difficulty difficulty1 : Character.Difficulty.values()) {
            if((packet.difficultyFlags & difficulty1.bit) == difficulty1.bit) difficulty = difficulty1;
        }
        if(difficulty == null) {
            System.out.println(packet.difficultyFlags);
            Pluto.logger.warn("Invalid difficulty flags, defaulting to softcore");
            difficulty = Character.Difficulty.Softcore;
        }
        character.setDifficulty(difficulty);
        character.setExtraAccessory((packet.difficultyFlags & (byte)0b0010000) == 0b00010000);
        character.setTorchFlags(packet.torchFlags);
        player.setCharacter(character);
    }

    public void handleClientUUID(ClientUUID packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());

        if(player.getState() != 1) return;
        player.setUuid(UUID.fromString(packet.uuidString));
    }

    public void handlePlayerHP(PlayerHP packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());

        if(player.getState() != 1) return;
        player.setHp(packet.hp);
        player.setMaxHp(packet.maxHp);
    }

    public void handlePlayerMana(PlayerMana packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());

        if(player.getState() != 1) return;
        player.setMana(packet.mana);
        player.setMaxMana(packet.manaMax);
    }

    public void handlePlayerBuffs(PlayerBuffs packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());

        if(player.getState() != 1) return;
        // TODO
    }

    public void handlePlayerSlot(PlayerSlot packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());

        System.out.println("Slot: " + packet.slotIndex);
        if(player.getState() != 1) return;
        player.getInventorySlots()[packet.slotIndex] = new Item(packet.itemNetId, Prefix.fromId(packet.itemPrefix), packet.stackSize);
    }

    public void handleRequestWorldData(RequestWorldData packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());
        if(player.getState() != 1) return;
        player.sendPacket(new WorldInfo(Pluto.world.getWorldData()));
        player.setState(2);
    }

    public void handleRequestEssentialTiles(RequestEssentialTiles packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());
        if(player.getState() != 2) return;
        World world = Pluto.world;
        WorldData worldData = world.getWorldData();

        // Send Tile Sections
        for(int i = 0; i < worldData.maxTilesX / 100; i++) {
            SendTileSection tileSection = new SendTileSection(channel, i * 100, 0, (short)100, (short)worldData.maxTilesY, world.getTiles(i * 100, 0, 100, worldData.maxTilesY));
            player.sendPacket(tileSection, 262144);
        }

        // Deprecated
        /*TileSectionFrame sectionFrame = new TileSectionFrame((short)0, (short)0, (short)(worldData.maxTilesX / 200), (short)(worldData.maxTilesY / 150));
        player.sendPacket(sectionFrame);*/

        player.sendPacket(new CompleteConnectionAndSpawn());
        player.setState(3);
    }

    public void handlePlayerSpawn(PlayerSpawn packet) {
        if(packet.playerSpawnContext != 1) return;
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());
        if(player.getState() != 3) return;
        player.sendPacket(new FinishedConnectingToServer());
        player.setPlayState(Player.PlayState.Play);
        player.setState(0);

        Pluto.playerManager.broadcast(packet, channel.id().asShortText());

        for(Player player1 : Pluto.playerManager.getConnectedPlayers().values()) {
            if(player1 == player) continue;
            player.fullSync(player1.getChannel());
            player1.fullSync(player.getChannel());
        }

        Pluto.logger.info("Player " + player.getCharacter().getName() + " (" + player.getPlayerId() + ") has joined.");
        Pluto.playerManager.broadcastText(new NetworkText(player.getCharacter().getName() + " has joined.", NetworkText.Mode.LITERAL), new TColor(0, 255, 0));
    }
}
