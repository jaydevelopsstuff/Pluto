package net.guardiandev.pluto;

import lombok.Getter;
import lombok.Setter;
import net.guardiandev.pluto.manager.ConsoleManager;
import net.guardiandev.pluto.manager.PlayerManager;
import net.guardiandev.pluto.manager.NetworkManager;
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

    @Getter
    @Setter
    private static World world;
    public static void start() {
        logger.info("Starting server...");

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
    }
}
