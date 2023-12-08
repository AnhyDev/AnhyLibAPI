package ink.anh.api.messages;

import org.bukkit.plugin.Plugin;

/**
 * Utility class for logging messages with color coding for different log levels.
 */
public class Logger {

    /**
     * Logs an informational message with cyan color.
     *
     * @param plugin The plugin instance for which the log is being recorded.
     * @param message The message to be logged.
     */
    public static void info(Plugin plugin, String message) {
        plugin.getLogger().info(ANSIColors.CYAN_BRIGHT + message + ANSIColors.RESET);
    }

    /**
     * Logs a warning message with yellow color.
     *
     * @param plugin The plugin instance for which the log is being recorded.
     * @param message The message to be logged.
     */
    public static void warn(Plugin plugin, String message) {
        plugin.getLogger().warning(ANSIColors.YELLOW + message + ANSIColors.RESET);
    }

    /**
     * Logs an error message with red color.
     *
     * @param plugin The plugin instance for which the log is being recorded.
     * @param message The message to be logged.
     */
    public static void error(Plugin plugin, String message) {
        plugin.getLogger().severe(ANSIColors.RED + message  + ANSIColors.RESET);
    }
}
