package net.guardiandev.pluto.manager;

import lombok.Data;
import net.guardiandev.pluto.entity.player.Player;
import net.guardiandev.pluto.network.packet.server.ServerPacket;

import java.util.HashMap;
import java.util.Map;

@Data
public class PlayerManager {
    private final Map<String, Player> connectedPlayers = new HashMap<>();
    private final boolean[] playerIds = new boolean[256];

    public void broadcast(ServerPacket packet) {
        connectedPlayers.forEach((channelId, player) -> player.sendPacket(packet));
    }

    public void broadcast(ServerPacket packet, String excludeId) {
        connectedPlayers.forEach((channelId, player) -> {
            if(!channelId.equals(excludeId)) player.sendPacket(packet);
        });
    }

    public int allocateAvailablePlayerId() {
        for(int i = 0; i < playerIds.length; i++) {
            if(playerIds[i]) continue;
            playerIds[i] = true;
            return i;
        }
        return -1;
    }

    public void freePlayerId(int playerId) {
        if(playerId < 0 || playerId > 255) throw new IllegalArgumentException();
        playerIds[playerId] = false;
    }

    public Player getPlayer(String shortChannelId) {
        return connectedPlayers.get(shortChannelId);
    }

    public Player getPlayer(int playerId) {
        for(Player player : connectedPlayers.values()) {
            if(player.getPlayerId() == playerId) return player;
        }
        return null;
    }

    public Player newPlayer(String shortChannelId, Player player) {
        connectedPlayers.put(shortChannelId, player);
        return connectedPlayers.get(shortChannelId);
    }

    public void deletePlayer(String shortChannelId) {
        connectedPlayers.remove(shortChannelId);
    }
}
