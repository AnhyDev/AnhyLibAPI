package ink.anh.api;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.plugin.java.JavaPlugin;

import ink.anh.api.utils.OtherUtils;
import ink.anh.api.utils.PluginReporter;

/**
 * The main class of the AnhyLibAPI Bukkit/Spigot plugin.
 * This class is responsible for initializing and managing key components of the library
 * and global data maps.
 */
public class AnhyLibAPI extends JavaPlugin {

    private static AnhyLibAPI instance;
    
    private Map<UUID, Map<String, Object>> globalDataMap;
   
    private double currentVersion;

    /**
     * Retrieves the active instance of the AnhyLibAPI plugin.
     * This static method allows for a global point of access to the plugin instance.
     *
     * @return The singleton instance of AnhyLibAPI.
     */
    public static AnhyLibAPI getInstance() {
        return instance;
    }

    /**
     * Called when the plugin is enabled. This method sets up the singleton instance
     * for global access, initializes the global data map, and reports the plugin name.
     */
    @Override
    public void onEnable() {
        // Set the instance for global access.
        instance = this;

        // Report the plugin name using the PluginReporter utility class.
        new PluginReporter(this).reportPluginName();
        
        currentVersion = OtherUtils.getCurrentServerVersion();
        setGlobalDataMap();
    }

    /**
     * Gets the current server version that was determined when the plugin was enabled.
     *
     * @return The current server version as a double.
     */
	public double getCurrentVersion() {
		return currentVersion;
	}

	/**
     * Gets the global data map which can be used across the plugin to store arbitrary data.
     * This map is thread-safe and can be used to store various data associated with players.
     *
     * @return The global ConcurrentHashMap for data storage.
     */
    public Map<UUID, Map<String, Object>> getGlobalDataMap() {
        return globalDataMap;
    }

    /**
     * Initializes the global data map. This method is called during the plugin's enable process.
     */
    private void setGlobalDataMap() {
        this.globalDataMap = new ConcurrentHashMap<>();
    }
}
