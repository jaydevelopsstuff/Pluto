package net.guardiandev.pluto.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import net.guardiandev.pluto.network.packet.server.ServerPacket;

public class NetworkUtil {
    public static ChannelFuture sendPacket(ServerPacket packet, Channel dest) {
        return sendPacket(packet, dest, 256);
    }

    public static ChannelFuture sendPacket(ServerPacket packet, Channel dest, int byteBufInitialCapacity) {
        ByteBuf buf = dest.alloc().buffer(byteBufInitialCapacity);
        buf.writerIndex(2);
        buf.writeByte((byte)packet.getType().ID);
        packet.writePacket(buf);
        int size = buf.writerIndex();
        buf.setShortLE(0, (short)(size));
        ByteBuf trimmedBuf = dest.alloc().buffer(size);
        buf.getBytes(0, trimmedBuf, size);
        buf.release();
        return dest.writeAndFlush(trimmedBuf);
    }
}
