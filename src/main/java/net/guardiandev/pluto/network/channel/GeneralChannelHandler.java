package net.guardiandev.pluto.network.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.data.item.Item;
import net.guardiandev.pluto.entity.player.Player;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.network.packet.server.PlayerActive;

import java.net.InetSocketAddress;

public class GeneralChannelHandler extends ChannelInboundHandlerAdapter {
    private int previousSize = 0;
    private byte[] overflow = new byte[0];

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Player player = new Player(ctx.channel(), (InetSocketAddress)ctx.channel().remoteAddress());
        player.getLoginHandler().setChannel(ctx.channel());
        player.getPlayHandler().setChannel(ctx.channel());
        player.startKeepAliveTask();
        player.setInventorySlots(new Item[350]);
        Pluto.playerManager.newPlayer(ctx.channel().id().asShortText(), player);
        Pluto.logger.info("New player connecting");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Player player = Pluto.playerManager.getPlayer(ctx.channel().id().asShortText());
        player.destroy();
        Pluto.playerManager.broadcast(new PlayerActive((byte)player.getPlayerId(), false), ctx.channel().id().asShortText());
        Pluto.logger.info("Destroyed player");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object rawMsg) throws Exception {
        Player player = Pluto.playerManager.getPlayer(ctx.channel().id().asShortText());
        if(player == null) {
            ctx.close();
            return;
        }

        ByteBuf buf = (ByteBuf)rawMsg;
        int initAvailable = buf.readableBytes();

        int available = initAvailable;

        while(available > 0) {
           /* if(available < 2) {
                System.out.println("<2 overflow");
                overflow = new byte[1];
                buf.writeBytes(overflow);
                buf.release();
                previousSize = -1;
                return;
            }*/

            int size = buf.readShortLE();

            /*if(size > available) {
                System.out.println("size>available overflow");
                int overflowCount = size - available;
                overflow = new byte[overflowCount];
                buf.writeBytes(overflow);
                buf.release();
                previousSize = size;
                return;
            }*/

            ByteBuf packetBuf = ctx.alloc().buffer(size - 2);
            buf.readBytes(packetBuf);

            handlePacket(size - 2, packetBuf, player);

            available = buf.readableBytes();
        }
        buf.release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    private void handlePacket(int size, ByteBuf buf, Player player) throws Exception {
        int rawId = buf.readByte() & 0xFF;
        PacketType type = PacketType.fromID(rawId);
        if(type == null) {
            System.out.println("Invalid packet id: " + rawId);
            return;
        }
        if(type != PacketType.PlayerSlot) System.out.println("Read:" + type.name() + " [" + rawId + "]");

        ClientPacket packet = (ClientPacket)type.instantiateClass();

        if(packet == null) {
            System.out.println("Ignored: " + type.name() + " [" + rawId + "]");
            buf.release();
            return;
        }

        packet.readPacket(buf);
        if(player.getPlayState() == Player.PlayState.Login) {
            packet.processPacket(player.getLoginHandler());
        } else {
            packet.processPacket(player.getPlayHandler());
        }
        if(buf.writerIndex() != size) {
            Pluto.logger.warn("Incomplete reading of packet " + type.name());
        }
        buf.release();
    }
}
