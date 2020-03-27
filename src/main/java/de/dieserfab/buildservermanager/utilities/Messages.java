package de.dieserfab.buildservermanager.utilities;

import de.dieserfab.buildservermanager.annotations.LoadMessage;

import java.util.List;

public class Messages {


    public Messages() {
        Logger.l("iPopulating Message Class");
    }

    @LoadMessage(path = "commands.no_permission")
    public static String COMMANDS_NO_PERMISSION;
    @LoadMessage(path = "commands.fail_command")
    public static String COMMANDS_FAIL_COMMAND;
    @LoadMessage(path = "commands.adddomain.success")
    public static String COMMANDS_ADDDOMAIN_SUCCESS;
    @LoadMessage(path = "commands.adddomain.fail")
    public static String COMMANDS_ADDDOMAIN_FAIL;
    @LoadMessage(path = "commands.removedomain.success")
    public static String COMMANDS_REMOVEDOMAIN_SUCCESS;
    @LoadMessage(path = "commands.removedomain.fail")
    public static String COMMANDS_REMOVEDOMAIN_FAIL;
    @LoadMessage(path = "commands.addcategory.success")
    public static String COMMANDS_ADDCATEGORY_SUCCESS;
    @LoadMessage(path = "commands.addcategory.fail")
    public static String COMMANDS_ADDCATEGORY_FAIL;
    @LoadMessage(path = "commands.removecategory.success")
    public static String COMMANDS_REMOVECATEGORY_SUCCESS;
    @LoadMessage(path = "commands.removecategory.fail")
    public static String COMMANDS_REMOVECATEGORY_FAIL;
    @LoadMessage(path = "commands.addmap.success")
    public static String COMMANDS_ADDMAP_SUCCESS;
    @LoadMessage(path = "commands.addmap.fail")
    public static String COMMANDS_ADDMAP_FAIL;
    @LoadMessage(path = "commands.removemap.success")
    public static String COMMANDS_REMOVEMAP_SUCCESS;
    @LoadMessage(path = "commands.removemap.fail")
    public static String COMMANDS_REMOVEMAP_FAIL;
    @LoadMessage(path = "commands.tp.success")
    public static String COMMANDS_TP_SUCCESS;
    @LoadMessage(path = "commands.tp.fail")
    public static String COMMANDS_TP_FAIL;
    @LoadMessage(path = "guis.category_menu.back")
    public static String GUIS_CATEGORYMENU_BACK;
    @LoadMessage(path = "guis.category_menu.back_lore")
    public static List<String> GUIS_CATEGORYMENU_BACK_LORE;
    @LoadMessage(path = "guis.category_menu.create_category")
    public static String GUIS_CATEGORYMENU_CREATE_CATEGORY;
    @LoadMessage(path = "guis.category_menu.create_category_lore")
    public static List<String> GUIS_CATEGORYMENU_CREATE_CATEGORY_LORE;
    @LoadMessage(path = "guis.category_menu.wrong_usage")
    public static String GUIS_CATEGORYMENU_WRONG_USAGE;
    @LoadMessage(path = "guis.category_menu.create_msg")
    public static String GUIS_CATEGORYMENU_CREATE_MSG;
    @LoadMessage(path = "guis.category_menu.no_category")
    public static String GUIS_CATEGORYMENU_NO_CATEGORY;
    @LoadMessage(path = "guis.category_menu.no_category_lore")
    public static List<String> GUIS_CATEGORYMENU_NO_CATEGORY_LORE;
    @LoadMessage(path = "guis.domain_menu.back")
    public static String GUIS_DOMAINMENU_BACK;
    @LoadMessage(path = "guis.domain_menu.back_lore")
    public static List<String> GUIS_DOMAINMENU_BACK_LORE;
    @LoadMessage(path = "guis.domain_menu.create_domain")
    public static String GUIS_DOMAINMENU_CREATE_DOMAIN;
    @LoadMessage(path = "guis.domain_menu.create_domain_lore")
    public static List<String> GUIS_DOMAINMENU_CREATE_DOMAIN_LORE;
    @LoadMessage(path = "guis.domain_menu.wrong_usage")
    public static String GUIS_DOMAINMENU_WRONG_USAGE;
    @LoadMessage(path = "guis.domain_menu.create_msg")
    public static String GUIS_DOMAINMENU_CREATE_MSG;
    @LoadMessage(path = "guis.domain_menu.no_domain")
    public static String GUIS_DOMAINMENU_NO_DOMAIN;
    @LoadMessage(path = "guis.domain_menu.no_domain_lore")
    public static List<String> GUIS_DOMAINMENU_NO_DOMAIN_LORE;
    @LoadMessage(path = "guis.map_menu.back")
    public static String GUIS_MAPMENU_BACK;
    @LoadMessage(path = "guis.map_menu.back_lore")
    public static List<String> GUIS_MAPMENU_BACK_LORE;
    @LoadMessage(path = "guis.map_menu.create_map")
    public static String GUIS_MAPMENU_CREATE_MAP;
    @LoadMessage(path = "guis.map_menu.create_map_lore")
    public static List<String> GUIS_MAPMENU_CREATE_MAP_LORE;
    @LoadMessage(path = "guis.map_menu.wrong_usage")
    public static String GUIS_MAPMENU_WRONG_USAGE;
    @LoadMessage(path = "guis.map_menu.create_msg")
    public static String GUIS_MAPMENU_CREATE_MSG;
    @LoadMessage(path = "guis.map_menu.no_map")
    public static String GUIS_MAPMENU_NO_MAP;
    @LoadMessage(path = "guis.map_menu.no_map_lore")
    public static List<String> GUIS_MAPMENU_NO_MAP_LORE;
    @LoadMessage(path = "guis.map_menu.map_lore")
    public static List<String> GUIS_MAPMENU_MAP_LORE;
    @LoadMessage(path = "guis.map_menu.map_lore_not_loaded")
    public static List<String> GUIS_MAPMENU_MAP_NOT_LOADED_LORE;
    @LoadMessage(path = "guis.map_settings_menu.load.item")
    public static String GUIS_MAPSETTINGSMENU_LOAD;
    @LoadMessage(path = "guis.map_settings_menu.load.success")
    public static String GUIS_MAPSETTINGSMENU_LOAD_SUCCESS;
    @LoadMessage(path = "guis.map_settings_menu.load.fail")
    public static String GUIS_MAPSETTINGSMENU_LOAD_FAIL;
    @LoadMessage(path = "guis.map_settings_menu.load_lore")
    public static List<String> GUIS_MAPSETTINGSMENU_LOAD_LORE;
    @LoadMessage(path = "guis.map_settings_menu.unload.item")
    public static String GUIS_MAPSETTINGSMENU_UNLOAD;
    @LoadMessage(path = "guis.map_settings_menu.unload.success")
    public static String GUIS_MAPSETTINGSMENU_UNLOAD_SUCCESS;
    @LoadMessage(path = "guis.map_settings_menu.unload.fail")
    public static String GUIS_MAPSETTINGSMENU_UNLOAD_FAIL;
    @LoadMessage(path = "guis.map_settings_menu.unload_lore")
    public static List<String> GUIS_MAPSETTINGSMENU_UNLOAD_LORE;
    @LoadMessage(path = "guis.map_settings_menu.back")
    public static String GUIS_MAPSETTINGSMENU_BACK;
    @LoadMessage(path = "guis.map_settings_menu.back_lore")
    public static List<String> GUIS_MAPSETTINGSMENU_BACK_LORE;
    @LoadMessage(path = "guis.map_settings_menu.information")
    public static String GUIS_MAPSETTINGSMENU_INFORMATION;
    @LoadMessage(path = "guis.map_settings_menu.teleport")
    public static String GUIS_MAPSETTINGSMENU_TELEPORT;
    @LoadMessage(path = "guis.map_settings_menu.teleport_lore")
    public static List<String> GUIS_MAPSETTINGSMENU_TELEPORT_LORE;
    @LoadMessage(path = "guis.map_settings_menu.delete")
    public static String GUIS_MAPSETTINGSMENU_DELETE;
    @LoadMessage(path = "guis.map_settings_menu.delete_lore")
    public static List<String> GUIS_MAPSETTINGSMENU_DELETE_LORE;
    @LoadMessage(path = "guis.players_menu.back")
    public static String GUIS_PLAYERSMENU_BACK;
    @LoadMessage(path = "guis.players_menu.back_lore")
    public static List<String> GUIS_PLAYERSMENU_BACK_LORE;
    @LoadMessage(path = "guis.players_menu.teleport.success")
    public static String GUIS_PLAYERSMENU_TELEPORT_SUCCESS;
    @LoadMessage(path = "guis.players_menu.teleport.fail")
    public static String GUIS_PLAYERSMENU_TELEPORT_FAIL;
    @LoadMessage(path = "guis.map_settings_menu.player_lore")
    public static List<String> GUIS_PLAYERSMENU_PLAYER_LORE;
    @LoadMessage(path = "guis.main_menu.title")
    public static String GUIS_MAINMENU_TITLE;
    @LoadMessage(path = "guis.main_menu.map_selection")
    public static String GUIS_MAINMENU_MAP_SELECTION;
    @LoadMessage(path = "guis.main_menu.map_selection_lore")
    public static List<String> GUIS_MAINMENU_MAP_SELECTION_LORE;
    @LoadMessage(path = "guis.main_menu.players")
    public static String GUIS_MAINMENU_PLAYERS;
    @LoadMessage(path = "guis.main_menu.players_lore")
    public static List<String> GUIS_MAINMENU_PLAYERS_LORE;
    @LoadMessage(path = "guis.main_menu.players_error")
    public static String GUIS_MAINMENU_PLAYERS_ERROR;
    @LoadMessage(path = "guis.main_menu.information")
    public static String GUIS_MAINMENU_INFORMATION;
    @LoadMessage(path = "items.emerald.name")
    public static String ITEMS_EMERALD_NAME;
    @LoadMessage(path = "items.emerald.lore")
    public static List<String> ITEMS_EMERALD_LORE;
}
