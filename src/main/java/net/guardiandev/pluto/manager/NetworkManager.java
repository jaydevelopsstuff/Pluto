package net.guardiandev.pluto.manager;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.guardiandev.pluto.network.channel.GeneralChannelHandler;

public class NetworkManager {
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    public boolean startNetwork() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new GeneralChannelHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1024, 1024 * 32, 1024*1024))
                    .childOption(ChannelOption.SO_KEEPALIVE, false);

            ChannelFuture channelFuture = bootstrap.bind(7777).sync();

            channelFuture.channel().closeFuture().addListener((ChannelFutureListener)future -> {
                closed();
            });
            return true;
        } catch(InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void shutdown() throws InterruptedException {
        bossGroup.shutdownGracefully().sync();
        workerGroup.shutdownGracefully().sync();
    }

    private void closed() {

    }
}
