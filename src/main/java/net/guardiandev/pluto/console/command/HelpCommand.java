package net.guardiandev.pluto.console.command;

import net.guardiandev.pluto.util.LogUtil;
import net.jay.solo.Command;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("Help", "");
    }

    @Override
    public boolean execute(String raw, String[] args) {
        LogUtil.sysOut("Pluto is an open source Terraria server written in Java.");
        return true;
    }
}
