package ink.anh.api.player;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

/**
 * Provides functionality to manage custom data for players using the Persistent Data API.
 */
public class PlayerData {

    private Plugin plugin;

    /**
     * Constructs a PlayerData instance associated with a specific plugin.
     *
     * @param plugin The plugin this PlayerData is associated with.
     */
    public PlayerData(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Sets custom data for a player as a serialized array of strings.
     *
     * @param player The player whose data is being set.
     * @param dataKey The key for the custom data.
     * @param values The array of string values to store.
     */
    public void setCustomData(Player player, String dataKey, String[] values) {
        NamespacedKey key = new NamespacedKey(plugin, dataKey);
        String combined = String.join(",", values);  // Serializing the array into a single string
        player.getPersistentDataContainer().set(key, PersistentDataType.STRING, combined);
    }

    /**
     * Retrieves custom data for a player as an array of strings.
     *
     * @param player The player whose data is being retrieved.
     * @param dataKey The key for the custom data.
     * @return An array of string values, or an empty array if no data is found.
     */
    public String[] getCustomData(Player player, String dataKey) {
        NamespacedKey key = new NamespacedKey(plugin, dataKey);
        String value = player.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        return value != null ? value.split(",") : new String[0];  // Deserializing the string back into an array
    }

    /**
     * Retrieves a string value of custom data for a player.
     *
     * @param player The player whose data is being retrieved.
     * @param dataKey The key for the custom data.
     * @return The string value of the custom data, or null if no data is found.
     */
    public String getStringData(Player player, String dataKey) {
        NamespacedKey key = new NamespacedKey(plugin, dataKey);
        return player.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }

    /**
     * Checks if a player has custom data associated with a given key.
     *
     * @param player The player to check for custom data.
     * @param dataKey The key for the custom data.
     * @return True if the player has custom data for the given key, false otherwise.
     */
    public boolean hasCustomData(Player player, String dataKey) {
        NamespacedKey key = new NamespacedKey(plugin, dataKey);
        return player.getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }

    /**
     * Removes custom data associated with a given key from a player.
     *
     * @param player The player from whom the custom data is being removed.
     * @param dataKey The key for the custom data to be removed.
     */
    public void removeCustomData(Player player, String dataKey) {
        NamespacedKey key = new NamespacedKey(plugin, dataKey);
        player.getPersistentDataContainer().remove(key);
    }
}
