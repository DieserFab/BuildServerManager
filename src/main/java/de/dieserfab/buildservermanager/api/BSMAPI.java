package de.dieserfab.buildservermanager.api;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.config.configs.MapsConfig;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an API class which provides many utility method to manage the functionality of this Plugin
 * It can also be interfered from an extern plugin by retrieving an instance of this plugin with
 * BSMAPI api = BSMAPI.getInstance();
 */

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
            BSM.getInstance().getDomainManager().getDomain(domain).getCategories().add(new Category(name, getMaps(domain, name), domain));
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
                categories.add(new Category(getDisplayname(path + "." + category), getMaps(domain, category), domain));
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
                BSM.getInstance().getDomainManager().getDomain(domain).getCategory(category).getMaps().add(new Map(name, type, domain, category));
                config.set(path + ".displayname", name);
                config.set(path + ".type", type);
                mapsConfig.save();

                try {
                    WorldCreator creator;
                    switch (type.toLowerCase()) {
                        case "void":
                            creator = new WorldCreator(name).generator(BSM.getInstance().getDefaultWorldGenerator(null, null));
                            break;
                        case "nether":
                            creator = new WorldCreator(name).environment(World.Environment.NETHER);
                            break;
                        case "end":
                            creator = new WorldCreator(name).environment(World.Environment.THE_END);
                            break;
                        case "normal":
                            creator = new WorldCreator(name);
                            break;
                        default:
                            creator = null;
                            Logger.l("eError wrong type entered try: void, nether, end, normal");
                            return false;
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            creator.createWorld();
                        }
                    }.runTask(BSM.getInstance());

                } catch (Exception e) {
                    Logger.l("eError while creating the world:" + e.getMessage());
                    return false;
                }
                return true;
            }
            Logger.l("eFailed to add a map to the domain " + domain + " the domain doesnt have a category with the name " + category);
            return false;
        }
        Logger.l("eFailed to add a map to the domain " + domain + " that domain doesnt exist.");
        return false;
    }

    /**
     * Adds a new Map to the maps.yml file and to the maps cache
     *
     * @param domain   is the name of the Domain
     * @param category is the name of the Category
     * @param name     is the name of the Map
     * @return
     */
    public boolean addMap(String domain, String category, String name) {
        MapsConfig mapsConfig = BSM.getInstance().getConfigManager().getMapsConfig();
        FileConfiguration config = mapsConfig.getConfig();
        String path = "domains." + domain.toLowerCase() + ".categories." + category.toLowerCase() + ".maps." + name.toLowerCase();
        if (config.isConfigurationSection("domains." + domain.toLowerCase())) {
            if (config.isConfigurationSection("domains." + domain.toLowerCase() + ".categories." + category.toLowerCase())) {
                if (config.isConfigurationSection(path)) {
                    Logger.l("eFailed to add a map to the domain " + domain + " with the category " + category + " a map with the name " + name + " already exists");
                    return false;
                }
                BSM.getInstance().getDomainManager().getDomain(domain).getCategory(category).getMaps().add(new Map(name, "Unknown", domain, category));
                config.set(path + ".displayname", name);
                config.set(path + ".type", "Unknown");
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
     * Returns the Object of a Map based on the parameter of the constructor it returns null if it was unable
     * to find a Map with the name
     *
     * @param name the name of the Map
     * @return
     */
    public Map getMap(String name) {
        for (Domain domain : BSM.getInstance().getDomainManager().getDomains()) {
            for (Category category : domain.getCategories()) {
                for (Map map : category.getMaps()) {
                    if (map.getName().equalsIgnoreCase(name))
                        return map;
                }
            }
        }
        return null;
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
                maps.add(new Map(getDisplayname(path + "." + map), getType(path + "." + map), domain, category, true));
            }
        }
        return maps;
    }

    /**
     * Returns a List with String of all Maps that got declared in the maps.yml file
     *
     * @return
     */
    public List<String> getAllDeclaredMaps() {
        FileConfiguration config = BSM.getInstance().getConfigManager().getMapsConfig().getConfig();
        List<String> maps = new ArrayList<>();
        if (!config.isConfigurationSection("domains"))
            return maps;
        for (String domain : config.getConfigurationSection("domains").getKeys(false)) {
            if (!config.isConfigurationSection("domains." + domain + ".categories"))
                continue;
            for (String category : config.getConfigurationSection("domains." + domain + ".categories").getKeys(false)) {
                if (!config.isConfigurationSection("domains." + domain + ".categories." + category + ".maps"))
                    continue;
                for (String map : config.getConfigurationSection("domains." + domain + ".categories." + category + ".maps").getKeys(false)) {
                    maps.add(config.getString("domains." + domain + ".categories." + category + ".maps." + map + ".displayname"));
                }
            }
        }
        return maps;
    }

    /**
     * Returns a List of String with names of Maps that arent categorized in the maps.yml file but need to
     *
     * @return
     */
    public List<String> getMapsToClassify() {
        List<String> maps = new ArrayList<>();
        for (String string : Bukkit.getWorldContainer().list()) {
            if (BSM.getInstance().getConfig().getStringList("exempt_classify").stream().anyMatch(string::equalsIgnoreCase))
                continue;
            File file = new File(string);
            if (file.isDirectory()) {
                if (new File(string + "/level.dat").exists() && !getAllDeclaredMaps().contains(string)) {
                    maps.add(string);
                }
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
