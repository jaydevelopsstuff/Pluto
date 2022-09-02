package net.guardiandev.pluto.manager;

import net.guardiandev.pluto.Pluto;
import net.guardiandev.pluto.console.command.HelpCommand;
import net.guardiandev.pluto.console.command.StopCommand;
import net.guardiandev.pluto.data.NetworkText;
import net.guardiandev.pluto.entity.player.Player;
import net.jay.solo.Command;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsoleManager {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;

    private final Command[] commands = new Command[]{
            new HelpCommand(), new StopCommand()
    };

    public void start() {
        executor.execute(() -> {
            String line;
            while(running && (line = scanner.nextLine()) != null) {
                for(Command command : commands) {
                    if(!line.toLowerCase().startsWith(command.getName().toLowerCase())) continue;
                    String[] args = Arrays.stream(line.split(" ")).skip(1).toArray(String[]::new);

                    command.execute(line, args);
                }
            }
        });
    }

    public void shutdown() {
        running = false;
        executor.shutdown();
    }
}
