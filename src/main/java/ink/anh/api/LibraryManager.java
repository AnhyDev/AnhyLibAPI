package ink.anh.api;

import org.bukkit.plugin.Plugin;

import ink.anh.api.lingo.lang.LanguageManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;

/**
 * Abstract class that defines the management of various aspects of a plugin, such as localization,
 * configuration, and user interfaces. This class should be extended by plugins that utilize the AnhyLibAPI library,
 * providing concrete implementations of the abstract methods defined herein.
 */
public abstract class LibraryManager {

    /**
     * Constructor for LibraryManager. It ensures that an instance of AnhyLibAPI is initialized when
     * a subclass of LibraryManager is instantiated.
     */
	public LibraryManager() {
		AnhyLibAPI.getInstance();
	}
	
    /**
     * Gets the plugin instance.
     *
     * @return The plugin instance.
     */
    public abstract Plugin getPlugin();

    /**
     * Retrieves the name of the plugin.
     *
     * @return The name of the plugin.
     */
    public abstract String getPluginName();

    /**
     * Manages user interfaces and messaging systems.
     *
     * @return An instance of BukkitAudiences for handling messaging and user interfaces.
     */
    public abstract BukkitAudiences getBukkitAudiences();

    /**
     * Handles localization and language management.
     *
     * @return An instance of LanguageManager for managing languages.
     */
    public abstract LanguageManager getLanguageManager();

    /**
     * Retrieves the default language set for the plugin.
     *
     * @return The default language code.
     */
    public abstract String getDefaultLang();

    /**
     * Manages settings and configuration, specifically for debug purposes.
     *
     * @return True if the debug mode is enabled, false otherwise.
     */
    public abstract boolean isDebug();
}
