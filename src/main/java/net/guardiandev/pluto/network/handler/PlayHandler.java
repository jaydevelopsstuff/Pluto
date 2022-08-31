package net.guardiandev.pluto.network.handler;

import io.netty.channel.Channel;
import lombok.Data;
import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.data.ItemsOne;
import net.guardiandev.pluto.data.ItemsThree;
import net.guardiandev.pluto.data.ItemsTwo;
import net.guardiandev.pluto.data.NetworkText;
import net.guardiandev.pluto.entity.player.Player;
import net.guardiandev.pluto.network.packet.both.*;
import net.guardiandev.pluto.util.TColor;
import net.guardiandev.pluto.world.tile.Block;

@Data
public class PlayHandler {
    private Channel channel;

    public void handlePlayerUpdate(PlayerUpdate packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());

        player.setPosX(packet.positionX);
        player.setPosY(packet.positionY);
    }

    public void handlePlayerHP(PlayerHP packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());

        player.setHp(packet.hp);
        player.setMaxHp(packet.maxHp);
    }

    public void handleNetModuleText(LoadNetModule.Text packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());

        // TODO
        /*if(packet.commandId.equals("Say")) {
            Pluto.playerManager.broadcast(new LoadNetModule(new LoadNetModule.Text((byte)player.getPlayerId(), new NetworkText(packet.content, NetworkText.Mode.LITERAL), new TColor(255, 255, 255))));
        }*/
    }

    public void handleModifyTile(ModifyTile packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());

        ModifyTile.Action action = ModifyTile.Action.fromId(packet.action);
        if(action == null) return;
        int tileX = packet.tileX;
        int tileY = packet.tileY;
        short data = packet.data;
        byte extraData = packet.extraData;
        switch(action) {
            case RemoveBlock -> {
                boolean failed = data == 1;
                if(failed) return;
                Pluto.world.removeBlock(tileX, tileY);
                Pluto.logger.info("Removed block at " + tileX + ", " + tileY);
            }
        }
    }
}
