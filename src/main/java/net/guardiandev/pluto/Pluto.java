package net.guardiandev.pluto;

import lombok.Getter;
import lombok.Setter;
import net.guardiandev.pluto.data.NetworkText;
import net.guardiandev.pluto.entity.player.Player;
import net.guardiandev.pluto.manager.ConsoleManager;
import net.guardiandev.pluto.manager.PlayerManager;
import net.guardiandev.pluto.manager.NetworkManager;
import net.guardiandev.pluto.util.FileUtil;
import net.guardiandev.pluto.world.World;
import net.guardiandev.pluto.world.WorldData;
import net.guardiandev.pluto.world.loader.ReLogicWorldLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Pluto {
    public static final String TerrariaVersion = "Terraria248";

    public static final Logger logger = LoggerFactory.getLogger("Pluto");

    public static final NetworkManager networkManager = new NetworkManager();
    public static final PlayerManager playerManager = new PlayerManager();
    public static final ConsoleManager consoleManger = new ConsoleManager();

    public static World world;
    public static void start() {
        logger.info("Starting server...");

        long timeBefore = System.currentTimeMillis();

        logger.info("Loading world...");
        try {
            ReLogicWorldLoader worldLoader = new ReLogicWorldLoader("./worlds/world.wld");
            worldLoader.loadWorld();
            world = worldLoader.build();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("Finished loading world");

        logger.info("Starting TCP server");
        networkManager.startNetwork();

        consoleManger.start();

        logger.info("Started server in " + ((System.currentTimeMillis() - timeBefore) / 1000D) + "s.");
    }

    public static void stop() throws InterruptedException {
        logger.info("Shutting down...");

        long timeBefore = System.currentTimeMillis();
        for(Player player : playerManager.getConnectedPlayers().values()) {
            player.disconnectGracefully(new NetworkText("Server shutting down", NetworkText.Mode.LITERAL));
            player.destroy();
        }

        networkManager.shutdown();
        consoleManger.shutdown();

        logger.info("Shut down in " + ((System.currentTimeMillis() - timeBefore) / 1000D) + "s.");
    }
}
