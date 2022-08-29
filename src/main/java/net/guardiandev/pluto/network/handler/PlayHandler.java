package net.guardiandev.pluto.network.handler;

import io.netty.channel.Channel;
import lombok.Data;
import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.entity.player.Player;
import net.guardiandev.pluto.network.packet.both.PlayerUpdate;

@Data
public class PlayHandler {
    private Channel channel;

    public void handlePlayerUpdate(PlayerUpdate packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());

        player.setPosX(packet.positionX);
        player.setPosY(packet.positionY);
    }
}
