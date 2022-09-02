package net.guardiandev.pluto.console.command;

import net.guardiandev.pluto.Pluto;
import net.jay.solo.Command;

public class StopCommand extends Command {
    public StopCommand() {
        super("Stop", "Shuts down the server.");
    }

    @Override
    public boolean execute(String raw, String[] args) {
        try {
            Pluto.stop();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
