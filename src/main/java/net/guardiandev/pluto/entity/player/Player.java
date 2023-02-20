package net.guardiandev.pluto.entity.player;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.Data;
import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.data.Character;
import net.guardiandev.pluto.data.NetworkText;
import net.guardiandev.pluto.data.item.Item;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.handler.PlayHandler;
import net.guardiandev.pluto.network.packet.both.*;
import net.guardiandev.pluto.network.packet.server.Disconnect;
import net.guardiandev.pluto.network.packet.server.KeepAlive;
import net.guardiandev.pluto.network.packet.server.PlayerActive;
import net.guardiandev.pluto.network.packet.server.ServerPacket;
import net.guardiandev.pluto.util.NetworkUtil;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.UUID;

@Data
public class Player {
    private final Channel channel;
    private final InetSocketAddress address;
    private LoginHandler loginHandler = new LoginHandler();
    private PlayHandler playHandler = new PlayHandler();
    private final Timer timer = new Timer();

    private int playerId = -1;
    private PlayState playState = PlayState.Login;
    private int state = 0;

    private Character character;
    private UUID uuid;

    private double posX;
    private double posY;

    private int hp;
    private int maxHp;
    private int mana;
    private int maxMana;

    private Item[] inventorySlots;

    public void startKeepAliveTask() {
        /*timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendKeepAlive();
            }
        }, 500, 30000);*/
    }

    public void fullSync(Channel dest) {
        NetworkUtil.sendPacket(new PlayerActive((byte)playerId, true), dest);
        NetworkUtil.sendPacket(new PlayerInfo((byte)playerId, character), dest);
        // Update Player
        NetworkUtil.sendPacket(new PlayerUpdate((byte)getPlayerId(), (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (float)posX, (float)posY, (float)0, (float)0, (float)0, (float)0, (float)0, (float)0), dest);
        NetworkUtil.sendPacket(new PlayerHP((byte)playerId, (short)hp, (short)maxHp), dest);
        // Toggle PVP
        // Player Team
        NetworkUtil.sendPacket(new PlayerMana((byte)playerId, (short)mana, (short)maxMana), dest);

        // Items
        int index = 0;
        for(Item slot : inventorySlots) {
            System.out.println(index);
            NetworkUtil.sendPacket(new PlayerSlot((byte)playerId, (short)index, (short)slot.getStack(), (byte)(slot.getPrefix() == null ? 0 : slot.getPrefix().id), (short)slot.getItemId()), dest);
            index++;
        }
    }

    public ChannelFuture sendKeepAlive() {
        return sendPacket(new KeepAlive());
    }

    public ChannelFuture sendPacket(ServerPacket packet) {
        return sendPacket(packet, 256);
    }

    public ChannelFuture sendPacket(ServerPacket packet, int byteBufInitialCapacity) {
        System.out.println("Send:" + packet.getType().name());
        return NetworkUtil.sendPacket(packet, channel, byteBufInitialCapacity);
    }

    public ChannelFuture disconnectGracefully(NetworkText reason) {
        sendPacket(new Disconnect(reason));
        return disconnect();
    }

    public ChannelFuture disconnect() {
        return channel.close();
    }

    public void destroy() {
        if(channel.isOpen()) disconnect();
        timer.cancel();
        Pluto.playerManager.deletePlayer(channel.id().asShortText());
        Pluto.playerManager.freePlayerId(playerId);
    }

    public enum PlayState {
        Login,
        Play
    }
}
