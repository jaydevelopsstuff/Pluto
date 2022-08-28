package net.guardiandev.pluto.manager;

import lombok.Data;
import lombok.Getter;
import net.guardiandev.pluto.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Data
public class PlayerManager {
    private final Map<String, Player> connectedPlayers = new HashMap<>();
    private final boolean[] playerIds = new boolean[256];

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

    public Player newPlayer(String shortChannelId, Player player) {
        connectedPlayers.put(shortChannelId, player);
        return connectedPlayers.get(shortChannelId);
    }

    public void deletePlayer(String shortChannelId) {
        connectedPlayers.remove(shortChannelId);
    }
}
