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
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        
        if (!packageName.equals("org.bukkit.craftbukkit")) {
            String version = packageName.substring(packageName.lastIndexOf('.') + 1);
            if (version.matches("v\\d+_\\d+_R\\d+")) {
                return version;
            }
        }
        
        String bukkitVersion = Bukkit.getServer().getBukkitVersion().replaceAll("-R0.1-SNAPSHOT", "");
        String[] parts = bukkitVersion.split("\\.");
        
        if (parts.length < 2) {
            return "v1_0_R0";
        }
        
        String nmsVersion = "v" + parts[0] + "_" + parts[1] + "_R" + (parts.length > 2 ? Integer.parseInt(parts[2]) - 1 : 0);
        
        if (nmsVersion.equals("v1_21_R6")) {
            nmsVersion = "v1_21_R5";
        }
        
        return nmsVersion;
    }

    public static String getCurrentVersion() {
        String bukkitVersion = Bukkit.getServer().getBukkitVersion().replaceAll("-R0.1-SNAPSHOT", "");
        String[] parts = bukkitVersion.split("\\.");
        
        if (parts.length < 2) {
            return "1.0.0";
        }
        
        String major = parts[0];
        String minor = parts[1];
        String patch = parts.length > 2 ? parts[2] : "0";
        
        return major + "." + minor + "." + patch;
    }

    public static boolean isServerVersionHigher(String targetVersion) {
        String serverVersion = getCurrentVersion();
        
        // Розбиваємо версії на частини
        String[] serverParts = serverVersion.split("\\.");
        String[] targetParts = targetVersion.split("\\.");
        
        int serverMajor = Integer.parseInt(serverParts[0]);
        int serverMinor = serverParts.length > 1 ? Integer.parseInt(serverParts[1]) : 0;
        int serverPatch = serverParts.length > 2 ? Integer.parseInt(serverParts[2]) : 0;
        
        int targetMajor = Integer.parseInt(targetParts[0]);
        int targetMinor = targetParts.length > 1 ? Integer.parseInt(targetParts[1]) : 0;
        int targetPatch = targetParts.length > 2 ? Integer.parseInt(targetParts[2]) : 0;
        
        // Порівнюємо основні версії
        if (serverMajor > targetMajor) return true;
        if (serverMajor < targetMajor) return false;
        if (serverMinor > targetMinor) return true;
        if (serverMinor < targetMinor) return false;
        
        // Порівнюємо патч
        return serverPatch > targetPatch;
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
