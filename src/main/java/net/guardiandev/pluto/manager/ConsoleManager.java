package net.guardiandev.pluto.manager;

import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.data.NetworkText;
import net.guardiandev.pluto.entity.Player;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ConsoleManager {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;

    public void start() {
        executor.execute(() -> {
            String line = null;
            while(running && (line = scanner.nextLine()) != null) {
                String command = line.split(" ")[0].trim();
                String[] args = Arrays.stream(line.split(" ")).skip(1).toArray(String[]::new);

                if(command.equalsIgnoreCase("p")) {
                    for(Player player : Pluto.playerManager.getConnectedPlayers().values()) {
                        System.out.println(player.getAddress().getHostName() + ": " + player.getPlayerId());
                    }
                }

                if(command.equalsIgnoreCase("dc")) {
                    if(args.length < 1) continue;
                    int id = Integer.parseInt(args[0]);
                    for(Player player : Pluto.playerManager.getConnectedPlayers().values()) {
                        if(player.getPlayerId() == id) player.disconnectGracefully(new NetworkText("Get fucked!", NetworkText.Mode.LITERAL));
                    }
                }
            }
        });
    }

    public void shutdown() {
        running = false;
    }
}
