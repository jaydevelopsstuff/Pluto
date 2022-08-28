package net.guardiandev.pluto.entity;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import io.netty.util.ReferenceCountUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.data.NetworkText;
import net.guardiandev.pluto.network.Client;
import net.guardiandev.pluto.network.handler.LoginHandler;
import net.guardiandev.pluto.network.packet.server.Disconnect;
import net.guardiandev.pluto.network.packet.server.KeepAlive;
import net.guardiandev.pluto.network.packet.server.ServerPacket;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;

@Data
public class Player {
    private final Channel channel;
    private final InetSocketAddress address;
    private PlayState playState = PlayState.Login;
    private int state = 0;
    private LoginHandler loginHandler = new LoginHandler();

    private final Timer timer = new Timer();

    private int playerId = -1;

    public void startKeepAliveTask() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sendKeepAlive();
            }
        }, 1000, 10000);
    }

    public ChannelFuture sendKeepAlive() {
        return sendPacket(new KeepAlive());
    }

    public ChannelFuture sendPacket(ServerPacket packet) {
        return sendPacket(packet, 256);
    }

    public ChannelFuture sendPacket(ServerPacket packet, int byteBufInitialCapacity) {
        ByteBuf buf = channel.alloc().buffer(byteBufInitialCapacity);
        buf.writerIndex(2);
        buf.writeByte((byte)packet.getType().ID);
        packet.writePacket(buf);
        int size = buf.writerIndex();
        buf.setShortLE(0, (short)(size));
        ByteBuf trimmedBuf = channel.alloc().buffer(size);
        buf.getBytes(0, trimmedBuf, size);
        buf.release();
        System.out.println("Send:" + packet.getType().name());
        return channel.writeAndFlush(trimmedBuf);
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
