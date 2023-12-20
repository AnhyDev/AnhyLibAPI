package ink.anh.api.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import ink.anh.api.messages.Logger;

/**
 * Utility class providing methods for interacting with and obtaining information about the Minecraft server.
 * This includes functionalities like fetching the server version, comparing versions, and logging server details.
 */
public class OtherUtils {

    /**
     * Retrieves the current version of the Minecraft server.
     * The version is parsed and converted into a double for easy comparison.
     *
     * @return The server version as a double, or 0 in case of a parsing error.
     */
	public static double getCurrentServerVersion() {
	    String versionString = Bukkit.getBukkitVersion().split("-")[0];
	    String[] splitVersion = versionString.split("\\.");

	    try {
	        int major = Integer.parseInt(splitVersion[0]);
	        int minor = splitVersion.length > 1 ? Integer.parseInt(splitVersion[1]) : 0;
	        double version = major + minor / (minor >= 10 ? 100.0 : 10.0);
	        return version;
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        return 0;
	    }
	}

    /**
     * Compares the current server version with a specified version.
     *
     * @param versionToCompare The version to compare against the current server version.
     * @return 0 if the current version is less than the specified version,
     *         1 if it's equal, and 2 if it's greater.
     */
    public static int compareServerVersion(double versionToCompare) {
        double currentVersion = getCurrentServerVersion();
        if (currentVersion < versionToCompare) {
            return 0;
        } else if (currentVersion == versionToCompare) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Logs the current version of the server using the specified plugin's logger.
     *
     * @param plugin The plugin instance used for logging.
     */
    public static void logCurrentServerVersion(Plugin plugin) {
        double currentVersion = getCurrentServerVersion();
        Logger.info(plugin, "Current server version: " + currentVersion);
    }
}
