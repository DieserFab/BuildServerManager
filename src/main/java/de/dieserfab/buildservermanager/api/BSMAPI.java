package de.dieserfab.buildservermanager.api;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.config.configs.MapsConfig;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.mapselector.Category;
import de.dieserfab.buildservermanager.mapselector.Domain;
import de.dieserfab.buildservermanager.mapselector.Map;
import de.dieserfab.buildservermanager.utilities.FileUtilities;
import de.dieserfab.buildservermanager.utilities.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BSMAPI {

    @Getter
    private static BSMAPI instance;

    public BSMAPI() {
        instance = this;
        Logger.l("iEnabled the BSMAPI you can now safely access it.");
    }

    /**
     * Adds a Domain to the maps.yml file and to the cached Domains it returns true if it was successfully
     * or false if it somehow failed (e.G. Domain already exists)
     *
     * @param name is the name of the domain
     * @return
     */
    public boolean addDomain(String name) {
        MapsConfig mapsConfig = BSM.getInstance().getConfigManager().getMapsConfig();
        FileConfiguration config = mapsConfig.getConfig();
        if (config.isConfigurationSection("domains." + name.toLowerCase())) {
            Logger.l("eTried to create a domain with the name" + name + " that already exists.");
            return false;
        }
        BSM.getInstance().getDomainManager().getDomains().add(new Domain(name, getCategories(name)));
        config.set("domains." + name.toLowerCase() + ".displayname", name);
        mapsConfig.save();
        BSM.getInstance().getGuiManager().getGui("domainmenu").setTitle("§8§lDomain Menu (§a" + BSMAPI.getInstance().getDomains().size() + "§8§l)");
        BSM.getInstance().getGuiManager().getGui("domainmenu").setInventory(Bukkit.createInventory(null, AbstractGui.GuiType.SMALL_CHEST.getSize(), "§8§lDomain Menu (§a" + BSMAPI.getInstance().getDomains().size() + "§8§l)"));
        return true;
    }

    /**
     * Removes a Domain from the maps.yml file and from the cached Domains it return true if it was successfully
     * or false if it somehow failed (e.G. Domain doesnt exist)
     *
     * @param name
     * @return
     */
    public boolean removeDomain(String name) {
        MapsConfig mapsConfig = BSM.getInstance().getConfigManager().getMapsConfig();
        FileConfiguration config = mapsConfig.getConfig();
        if (!config.isConfigurationSection("domains." + name.toLowerCase())) {
            Logger.l("eTried to remove a domain with the name" + name + " that doesnt exists.");
            return false;
        }
        BSM.getInstance().getDomainManager().getDomains().remove(getDomain(name));
        config.set("domains." + name.toLowerCase(), null);
        mapsConfig.save();
        BSM.getInstance().getGuiManager().getGui("domainmenu").setTitle("§8§lDomain Menu (§a" + BSMAPI.getInstance().getDomains().size() + "§8§l)");
        BSM.getInstance().getGuiManager().getGui("domainmenu").setInventory(Bukkit.createInventory(null, AbstractGui.GuiType.SMALL_CHEST.getSize(), "§8§lDomain Menu (§a" + BSMAPI.getInstance().getDomains().size() + "§8§l)"));

        return true;
    }

    /**
     * Gets the Object of a Domain with the name from the constructor if theres no Domain with that name
     * it return null
     *
     * @param name is the name for the search
     * @return
     */
    public Domain getDomain(String name) {
        return BSM.getInstance().getDomainManager().getDomain(name);
    }

    /**
     * Returns a List<Domain> with all Domains currently in the maps.yml if no Domains are in the maps.yml file
     * it returns a empty List
     *
     * @return
     */
    public List<Domain> getDomains() {
        FileConfiguration config = BSM.getInstance().getConfigManager().getMapsConfig().getConfig();
        List<Domain> domains = new ArrayList<>();
        if (config.isConfigurationSection("domains")) {
            for (String domain : config.getConfigurationSection("domains").getKeys(false)) {
                domains.add(new Domain(getDisplayname("domains." + domain), getCategories(domain)));
            }
        }
        return domains;
    }

    /**
     * Adds a Category to the maps.yml file and to the Category cache it returns either true if it was successfull or false
     * if it somehow failed (e.G. Category already exist)
     *
     * @param domain is the name of the parent Domain
     * @param name   is the name of the Category
     * @return
     */
    public boolean addCategory(String domain, String name) {
        MapsConfig mapsConfig = BSM.getInstance().getConfigManager().getMapsConfig();
        FileConfiguration config = mapsConfig.getConfig();
        if (config.isConfigurationSection("domains." + domain.toLowerCase())) {
            if (config.isConfigurationSection("domains." + domain.toLowerCase() + ".categories." + name.toLowerCase())) {
                Logger.l("eFailed to add a category to the domain " + domain + " the domain already has a category with that name.");
                return false;
            }
            BSM.getInstance().getDomainManager().getDomain(domain).getCategories().add(new Category(name, getMaps(domain, name)));
            config.set("domains." + domain.toLowerCase() + ".categories." + name.toLowerCase() + ".displayname", name);
            mapsConfig.save();
            return true;
        }
        Logger.l("eFailed to add a category to the domain " + domain + " that domain doesnt exist.");
        return false;
    }

    /**
     * Removes a Category from the maps.yml file and from the Category cache it returns either true if it was successfully or false
     * if it somehow failed (e.G. Category doesnt exist)
     *
     * @param domain is the name of the parent Domain
     * @param name   is the name of the Category to remove
     * @return
     */
    public boolean removeCategory(String domain, String name) {
        MapsConfig mapsConfig = BSM.getInstance().getConfigManager().getMapsConfig();
        FileConfiguration config = mapsConfig.getConfig();
        String path = "domains." + domain.toLowerCase() + ".categories." + name.toLowerCase();
        if (!config.isConfigurationSection(path)) {
            Logger.l("eTried to remove a category with the name" + name + " that doesnt exists.");
            return false;
        }
        BSM.getInstance().getDomainManager().getDomain(domain).getCategories().remove(getCategory(domain, name));
        config.set(path, null);
        mapsConfig.save();
        return true;
    }

    /**
     * Returns the Object of a Category based on the parameter from the Constructor
     * it returns null if no Category was found
     *
     * @param domain is the name of the parent Domain
     * @param name   is the name of the Category
     * @return
     */
    public Category getCategory(String domain, String name) {
        return getDomain(domain).getCategory(name);
    }

    /**
     * Returns a List from all current Categories inside the maps.yml with the given Domain from the constructor file if no Categories are
     * present it returns a empty List
     *
     * @param domain is the name of the parent Domain
     * @return
     */
    public List<Category> getCategories(String domain) {
        FileConfiguration config = BSM.getInstance().getConfigManager().getMapsConfig().getConfig();
        List<Category> categories = new ArrayList<>();
        String path = "domains." + domain.toLowerCase() + ".categories";
        if (config.isConfigurationSection(path)) {
            for (String category : config.getConfigurationSection(path).getKeys(false)) {
                categories.add(new Category(getDisplayname(path + "." + category), getMaps(domain, category)));
            }
        }
        return categories;
    }

    /**
     * Adds a new Map to the maps.yml file and to the maps cache and it generates a new World based on the parameters
     *
     * @param domain   is the name of the Domain
     * @param category is the name of the Category
     * @param name     is the name of the Map
     * @param type     is the generator type to create the Map
     * @return
     */
    public boolean addMap(String domain, String category, String name, String type) {
        MapsConfig mapsConfig = BSM.getInstance().getConfigManager().getMapsConfig();
        FileConfiguration config = mapsConfig.getConfig();
        String path = "domains." + domain.toLowerCase() + ".categories." + category.toLowerCase() + ".maps." + name.toLowerCase();
        if (config.isConfigurationSection("domains." + domain.toLowerCase())) {
            if (config.isConfigurationSection("domains." + domain.toLowerCase() + ".categories." + category.toLowerCase())) {
                if (config.isConfigurationSection(path)) {
                    Logger.l("eFailed to add a map to the domain " + domain + " with the category " + category + " a map with the name " + name + " already exists");
                    return false;
                }
                try {
                    WorldCreator creator;
                    World world;
                    if (type.equalsIgnoreCase("void")) {
                        creator = new WorldCreator(name).generator(BSM.getInstance().getDefaultWorldGenerator(type, null));
                    } else if (type.equalsIgnoreCase("nether")) {
                        creator = new WorldCreator(name).environment(World.Environment.NETHER);
                    } else if (type.equalsIgnoreCase("end")) {
                        creator = new WorldCreator(name).environment(World.Environment.THE_END);
                    } else if (type.equalsIgnoreCase("normal")) {
                        creator = new WorldCreator(name);
                    } else {
                        creator = null;
                        Logger.l("eError wrong type entered try: void, nether, end, normal");
                        return false;
                    }
                    world = creator.createWorld();
                } catch (Exception e) {
                    Logger.l("eError while creating the world:" + e.getMessage());
                    return false;
                }
                BSM.getInstance().getDomainManager().getDomain(domain).getCategory(category).getMaps().add(new Map(name, type));
                config.set(path + ".displayname", name);
                config.set(path + ".type", type);
                mapsConfig.save();
                return true;
            }
            Logger.l("eFailed to add a map to the domain " + domain + " the domain doesnt have a category with the name " + category);
            return false;
        }
        Logger.l("eFailed to add a map to the domain " + domain + " that domain doesnt exist.");
        return false;
    }

    /**
     * Removes the Map from the maps.yml file and from the Map cache and it deletes the World from the Disk
     *
     * @param domain   is the name of the Maps Domain
     * @param category is the name of the Maps Category
     * @param name     is the name of the Map
     * @return
     */
    public boolean removeMap(String domain, String category, String name) {
        MapsConfig mapsConfig = BSM.getInstance().getConfigManager().getMapsConfig();
        FileConfiguration config = mapsConfig.getConfig();
        String path = "domains." + domain.toLowerCase() + ".categories." + category.toLowerCase() + ".maps." + name.toLowerCase();
        if (!config.isConfigurationSection(path)) {
            Logger.l("eTried to remove a map with the name" + name + " that doesnt exists.");
            return false;
        }
        BSM.getInstance().getDomainManager().getDomain(domain).getCategory(category).getMaps().remove(getMap(domain, category, name));
        config.set(path, null);
        mapsConfig.save();
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (p.getLocation().getWorld().getName().equalsIgnoreCase(name)) {
                p.teleport(Bukkit.getWorld("world").getSpawnLocation());
            }
        });
        Bukkit.unloadWorld(name, true);
        try {
            FileUtilities.deleteDirectory(new File(name));
        } catch (Exception e) {
            Logger.l("eError while deleting a Map:" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Returns the Object of a Map based on the parameter of the constructor it returns null if it was unable
     * to find a Map with the parameters
     *
     * @param domain   the name of the Domain
     * @param category the name of the Category
     * @param name     the name of the Map
     * @return
     */
    public Map getMap(String domain, String category, String name) {
        return getDomain(domain).getCategory(category).getMap(name);
    }

    /**
     * Returns a List of all Maps currently present in the maps.yml based on the parameters of the constructor
     * file if no maps are present it returns an empty List
     *
     * @param domain   the name of the Domain
     * @param category the name of the Category
     * @return
     */
    public List<Map> getMaps(String domain, String category) {
        FileConfiguration config = BSM.getInstance().getConfigManager().getMapsConfig().getConfig();
        List<Map> maps = new ArrayList<>();
        String path = "domains." + domain.toLowerCase() + ".categories." + category.toLowerCase() + ".maps";
        if (config.isConfigurationSection(path)) {
            for (String map : config.getConfigurationSection(path).getKeys(false)) {
                maps.add(new Map(getDisplayname(path + "." + map), getType(path + "." + map), true));
            }
        }
        return maps;
    }

    public String getDisplayname(String path) {
        FileConfiguration config = BSM.getInstance().getConfigManager().getMapsConfig().getConfig();
        return config.getString(path + ".displayname");
    }

    public String getType(String path) {
        FileConfiguration config = BSM.getInstance().getConfigManager().getMapsConfig().getConfig();
        return config.getString(path + ".type");
    }

}
