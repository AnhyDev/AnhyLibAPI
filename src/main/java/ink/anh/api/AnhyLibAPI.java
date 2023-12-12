package ink.anh.api;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class serves as the main entry point for the AnhyLibAPI when used as a plugin.
 * It follows the singleton pattern to ensure that only one instance of this class is created during the plugin's lifecycle.
 */
public class AnhyLibAPI extends JavaPlugin {

    /**
     * The volatile keyword ensures that multiple threads handle the unique instance variable correctly.
     */
    private static volatile AnhyLibAPI instance;

    /**
     * Retrieves the singleton instance of the AnhyLibAPI.
     * If no instance exists, it initializes a new instance. This method is thread-safe.
     *
     * @return The singleton instance of AnhyLibAPI.
     */
    public static AnhyLibAPI getInstance() {
        if (instance == null) {
            synchronized (AnhyLibAPI.class) {
                if (instance == null) {
                    instance = new AnhyLibAPI();
                }
            }
        }
        return instance;
    }

    /**
     * Called when the plugin is enabled. If the instance is not already initialized, it sets the unique instance to 'this'.
     * The method is synchronized to prevent multiple threads from initializing the instance simultaneously in a multithreaded environment.
     */
    @Override
    public void onEnable() {
        if (instance == null) {
            synchronized (AnhyLibAPI.class) {
                if (instance == null) {
                    instance = this;
                }
            }
        }
    }
}
