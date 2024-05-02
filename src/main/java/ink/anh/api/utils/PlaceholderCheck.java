package ink.anh.api.utils;

import org.bukkit.Bukkit;

import ink.anh.api.AnhyLibAPI;
import ink.anh.api.messages.Logger;
import me.clip.placeholderapi.PlaceholderAPI;

public class PlaceholderCheck {

    // Method to check if PlaceholderAPI is enabled
    public static boolean checkPlaceholderAPI() {
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            Logger.warn(AnhyLibAPI.getInstance(), "PlaceholderAPI is not found, the extensions cannot be verified!");
            return false;
        }
        return true;
    }

    // Method to check for a specific PlaceholderAPI extension
    public static void checkPlaceholderExtension(String extensionId) {
        if (!checkPlaceholderAPI()) {
            return; // Exit if PlaceholderAPI is not present
        }

        // Check if the specific extension is registered
        boolean isRegistered = PlaceholderAPI.isRegistered(extensionId);

        if (!isRegistered) {
            Logger.info(AnhyLibAPI.getInstance(), "Extension " + extensionId + " not found. Please download and install the extension for proper functionality.");
        } else {
            Logger.warn(AnhyLibAPI.getInstance(), "Extension " + extensionId + " successfully found and activated.");
        }
    }
}
