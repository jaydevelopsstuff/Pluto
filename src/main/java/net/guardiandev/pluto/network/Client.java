package net.guardiandev.pluto.network;

import lombok.Data;
import net.guardiandev.pluto.entity.Player;

import java.net.InetSocketAddress;

@Data
public class Client {
    private final Player parent;
    private InetSocketAddress address;
}
