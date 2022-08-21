package net.guardiandev.pluto.network.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.entity.Player;
import net.guardiandev.pluto.network.packet.Packet;
import net.guardiandev.pluto.network.packet.PacketType;
import net.guardiandev.pluto.network.packet.client.ClientPacket;
import net.guardiandev.pluto.util.ByteBufUtil;

import java.net.InetSocketAddress;

public class GeneralChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Pluto.playerManager.newPlayer(ctx.channel().id().asShortText(), new Player(ctx.channel().id(), (InetSocketAddress)ctx.channel().remoteAddress()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object rawMsg) throws Exception {
        Player player = Pluto.playerManager.getPlayer(ctx.channel().id().asShortText());
        if(player == null) {
            ctx.close();
            return;
        }

        ByteBuf buf = (ByteBuf)rawMsg;

        int size = buf.readUnsignedShortLE();
        PacketType type = PacketType.fromID(buf.readUnsignedByte());
        if(type == null) {
            ctx.close();
            return;
        }
        Packet packet = type.instantiateClass();
        if(!(packet instanceof ClientPacket)) return;
        ClientPacket cpacket = (ClientPacket)packet;

        cpacket.readPacket(buf);
        cpacket.processPacket(player.getLoginHandler());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
