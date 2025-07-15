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
     *
     * @return The server version as a string, or 0 in case of a parsing error.
     */
	public static String getCurrentServerVersion() {
        String versionString = Bukkit.getBukkitVersion().split("-")[0];
        String[] splitVersion = versionString.split("\\.");

        try {
            int major = Integer.parseInt(splitVersion[0]);
            int minor = splitVersion.length > 1 ? Integer.parseInt(splitVersion[1]) : 0;
            int patch = splitVersion.length > 2 ? Integer.parseInt(splitVersion[2]) : 0;
            return major + "." + minor + "." + patch;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.0.0";
        }
    }

    public static boolean isServerVersionLowerThan(String targetVersion) {
        String currentVersion = getCurrentServerVersion();
        String[] currentParts = currentVersion.split("\\.");
        String[] targetParts = targetVersion.split("\\.");

        try {
            int currentMajor = Integer.parseInt(currentParts[0]);
            int currentMinor = currentParts.length > 1 ? Integer.parseInt(currentParts[1]) : 0;
            int currentPatch = currentParts.length > 2 ? Integer.parseInt(currentParts[2]) : 0;

            int targetMajor = Integer.parseInt(targetParts[0]);
            int targetMinor = targetParts.length > 1 ? Integer.parseInt(targetParts[1]) : 0;
            int targetPatch = targetParts.length > 2 ? Integer.parseInt(targetParts[2]) : 0;

            if (currentMajor < targetMajor) return true;
            if (currentMajor > targetMajor) return false;
            if (currentMinor < targetMinor) return true;
            if (currentMinor > targetMinor) return false;
            return currentPatch < targetPatch;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Compares the current server version with a specified version.
     *
     * @param versionToCompare The version to compare against the current server version.
     * @return 0 if the current version is less than the specified version,
     *         1 if it's equal, and 2 if it's greater.
     */
    public static int compareServerVersion(String versionToCompare) {
        String currentVersion = getCurrentServerVersion();
        if (isServerVersionLowerThan(versionToCompare)) {
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
        String currentVersion = getCurrentServerVersion();
        Logger.info(plugin, "Current server version: " + currentVersion);
    }
}
