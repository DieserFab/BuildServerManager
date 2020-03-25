package de.dieserfab.buildservermanager.commands;

import de.dieserfab.buildservermanager.commands.cmds.MapsCMD;

public class CommandManager {

    /**
     * No need to cache the Commands as no Objects from Command classes need to be
     * accessed directly
     */
    public CommandManager(){
        new MapsCMD();
    }

}
