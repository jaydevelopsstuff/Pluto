package net.guardiandev.pluto.entity;

import io.netty.channel.ChannelId;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.guardiandev.pluto.network.Client;
import net.guardiandev.pluto.network.handler.LoginHandler;

import java.net.InetSocketAddress;

@Data
public class Player {
    private final ChannelId channelId;
    private final InetSocketAddress address;
    private PlayState playState = PlayState.Login;
    private LoginHandler loginHandler = new LoginHandler(this);

    public enum PlayState {
        Login,
        Play
    }
}
