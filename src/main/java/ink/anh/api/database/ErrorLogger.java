package ink.anh.api.database;

import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

/**
 * Utility class for logging errors related to the database.
 */
public class ErrorLogger {

    /**
     * Logs an error message along with the exception stack trace to the plugin's logger.
     *
     * @param plugin the plugin instance whose logger is used to log the message.
     * @param ex the exception that was thrown.
     * @param message the error message to be logged.
     */
    public static void log(Plugin plugin, Exception ex, String message) {
        plugin.getLogger().log(Level.SEVERE, message, ex);
    }
}
