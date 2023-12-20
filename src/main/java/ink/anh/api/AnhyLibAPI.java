package ink.anh.api;

import org.bukkit.plugin.java.JavaPlugin;

import ink.anh.api.utils.OtherUtils;
import ink.anh.api.utils.PluginReporter;

/**
 * Main class for the AnhyLibAPI Bukkit/Spigot plugin.
 * Utilizes a singleton pattern to provide a global point of access to the plugin instance
 * and initiates reporting of the plugin's name on plugin enable.
 */
public class AnhyLibAPI extends JavaPlugin {

    private static AnhyLibAPI instance;
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
     * Initializes the plugin by setting the singleton instance and reporting the plugin name.
     * This method is called by the Bukkit/Spigot server when the plugin is enabled.
     */
    @Override
    public void onEnable() {
        // Set the instance for global access.
        instance = this;

        // Report the plugin name using the PluginReporter utility class.
        new PluginReporter(this).reportPluginName();
        
        currentVersion = OtherUtils.getCurrentServerVersion();
    }

	public double getCurrentVersion() {
		return currentVersion;
	}
}
