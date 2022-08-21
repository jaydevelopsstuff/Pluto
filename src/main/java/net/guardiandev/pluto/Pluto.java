package net.guardiandev.pluto;

import net.guardiandev.pluto.manager.PlayerManager;
import net.guardiandev.pluto.manager.NetworkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pluto {
    public static final String TerrariaVersion = "Terraria248";

    public static final Logger logger = LoggerFactory.getLogger("Pluto");

    public static final boolean[] playerIds = new boolean[256];

    public static final NetworkManager networkManager = new NetworkManager();
    public static final PlayerManager playerManager = new PlayerManager();


    public static void start() {
        logger.info("Starting server...");

        logger.info("Starting TCP server");
        networkManager.startNetwork();
    }
}
