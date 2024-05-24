package ink.anh.api;

import org.bukkit.plugin.Plugin;

import ink.anh.api.database.DatabaseManager;
import ink.anh.api.database.MySQLConfig;
import ink.anh.api.lingo.lang.LanguageManager;
import ink.anh.api.utils.PluginReporter;
/**
 * Abstract class that defines the management framework for various aspects of a plugin.
 * This includes localization, messaging systems, language management, and configuration settings.
 * Subclasses should provide concrete implementations for the abstract methods to tailor the functionality
 * to their specific plugin requirements.
 */
public abstract class LibraryManager {
    
    /**
     * Constructor for the LibraryManager. It automatically reports the name of the plugin using PluginReporter.
     *
     * @param plugin The plugin instance this manager is associated with.
     */
    public LibraryManager(Plugin plugin) {
        new PluginReporter(plugin).reportPluginName();
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

    /**
     * Gets the database configuration for the plugin.
     *
     * @return The database configuration.
     */
    public abstract MySQLConfig getMySQLConfig();

    /**
     * Gets the database manager for the plugin.
     *
     * @return The database manager.
     */
    public abstract DatabaseManager getDatabaseManager();
}
