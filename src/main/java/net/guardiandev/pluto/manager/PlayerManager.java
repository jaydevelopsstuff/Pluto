package net.guardiandev.pluto.manager;

import lombok.Getter;
import net.guardiandev.pluto.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    @Getter
    private final Map<String, Player> connectedPlayers = new HashMap<>();

    public Player getPlayer(String shortChannelId) {
        return connectedPlayers.get(shortChannelId);
    }

    public void newPlayer(String shortChannelId, Player player) {
        connectedPlayers.put(shortChannelId, player);
    }

    public void deletePlayer(String shortChannelId) {
        connectedPlayers.remove(shortChannelId);
    }
}
