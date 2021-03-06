package de.dieserfab.buildservermanager.commands;

import de.dieserfab.buildservermanager.commands.cmds.DifficultyCMD;
import de.dieserfab.buildservermanager.commands.cmds.GamemodeCMD;
import de.dieserfab.buildservermanager.commands.cmds.MapsCMD;
import de.dieserfab.buildservermanager.commands.cmds.NightvisionCMD;
import de.dieserfab.buildservermanager.utilities.Logger;

public class CommandManager {

    /**
     * No need to cache the Commands as no Instances from Command classes need to be
     * accessed directly
     */
    public CommandManager(){
        Logger.l("iLoading all Commands");
        try {
            new MapsCMD();
            new GamemodeCMD();
            new DifficultyCMD();
            new NightvisionCMD();
            Logger.l("iSuccessfully loaded all Commands.");
        }catch (Exception e){
            Logger.l("eError while loading all Commands:"+e.getMessage());
        }
    }

}
