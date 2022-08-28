package net.guardiandev.pluto.network.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.Data;
import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.data.NetworkText;
import net.guardiandev.pluto.entity.Player;
import net.guardiandev.pluto.manager.PlayerManager;
import net.guardiandev.pluto.network.packet.client.ConnectRequest;
import net.guardiandev.pluto.network.packet.client.PlayerSpawn;
import net.guardiandev.pluto.network.packet.client.RequestEssentialTiles;
import net.guardiandev.pluto.network.packet.client.RequestWorldData;
import net.guardiandev.pluto.network.packet.server.*;
import net.guardiandev.pluto.world.World;
import net.guardiandev.pluto.world.WorldData;

@Data
public class LoginHandler {
    private Channel channel;
    private int state = 0;

    public void handleConnectRequest(ConnectRequest packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());

        if(player.getState() != 0) return;
        player.setPlayerId(Pluto.playerManager.allocateAvailablePlayerId());
        if(player.getPlayerId() == -1) {
            ServerPacket disconnect = new Disconnect(new NetworkText("Server full", NetworkText.Mode.LITERAL));
            player.sendPacket(disconnect);
            return;
        }

        //player.disconnectGracefully(new NetworkText("Disconnected: Shut the fuck up", NetworkText.Mode.LITERAL));
        player.sendPacket(new ContinueConnecting((short)player.getPlayerId()));
        player.setState(1);
    }

    public void handleRequestWorldData(RequestWorldData packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());
        if(player.getState() != 1) return;
        player.sendPacket(new WorldInfo(Pluto.getWorld().getWorldData()));
        player.setState(2);
    }

    public void handleRequestEssentialTiles(RequestEssentialTiles packet) {
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());
        if(player.getState() != 2) return;
        World world = Pluto.getWorld();
        WorldData worldData = world.getWorldData();

        // Send Tile Sections
        for(int i = 0; i < worldData.maxTilesX / 100; i++) {
            SendTileSection tileSection = new SendTileSection(channel, true, i * 100, 0, (short)100, (short)worldData.maxTilesY, world.getTiles(i * 100, 0, 100, worldData.maxTilesY));
            player.sendPacket(tileSection, 262144);
        }

        TileSectionFrame sectionFrame = new TileSectionFrame((short)0, (short)0, (short)(worldData.maxTilesX / 200), (short)(worldData.maxTilesY / 150));
        player.sendPacket(sectionFrame);

        player.sendPacket(new CompleteConnectionAndSpawn());
        player.setState(3);
    }

    public void handlePlayerSpawn(PlayerSpawn packet) {
        if(packet.playerSpawnContext != 1) return;
        Player player = Pluto.playerManager.getPlayer(channel.id().asShortText());
        if(player.getState() != 3) return;
        player.sendPacket(new FinishedConnectingToServer());
    }
}
