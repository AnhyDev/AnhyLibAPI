package ink.anh.api.player;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import ink.anh.api.AnhyLibAPI;

/**
 * Provides functionality to manage custom data for players using Bukkit's Persistent Data API.
 * This class handles the storage, retrieval, and removal of custom data, and also triggers 
 * custom events when player data is changed.
 */
public class PlayerData {

    private AnhyLibAPI anhyLibAPI;

    /**
     * Constructs a PlayerData instance associated with the AnhyLibAPI.
     * This instance will interact with Bukkit's Persistent Data API to manage custom data for players.
     */
    public PlayerData() {
        this.anhyLibAPI = AnhyLibAPI.getInstance();
    }

    /**
     * Sets custom data for a player as a serialized array of strings and triggers a PlayerDataChangedEvent.
     * The event is called after the data is set, allowing other parts of the plugin or external plugins
     * to react to the data change.
     *
     * @param player The player whose data is being set.
     * @param dataKey The key for the custom data.
     * @param values The array of string values to store.
     */
    public void setCustomData(Player player, String dataKey, String[] values) {
        NamespacedKey key = new NamespacedKey(anhyLibAPI, dataKey);
        String combined = String.join(",", values);  // Serializing the array into a single string
        player.getPersistentDataContainer().set(key, PersistentDataType.STRING, combined);

        // Triggering the custom event to notify about the data change
        PlayerDataChangedEvent dataChangedEvent = new PlayerDataChangedEvent(player, dataKey, values);
        Bukkit.getServer().getPluginManager().callEvent(dataChangedEvent);
    }

    /**
     * Retrieves custom data for a player as an array of strings.
     *
     * @param player The player whose data is being retrieved.
     * @param dataKey The key for the custom data.
     * @return An array of string values, or an empty array if no data is found.
     */
    public String[] getCustomData(Player player, String dataKey) {
        NamespacedKey key = new NamespacedKey(anhyLibAPI, dataKey);
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
        NamespacedKey key = new NamespacedKey(anhyLibAPI, dataKey);
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
        NamespacedKey key = new NamespacedKey(anhyLibAPI, dataKey);
        return player.getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }

    /**
     * Removes custom data associated with a given key from a player.
     *
     * @param player The player from whom the custom data is being removed.
     * @param dataKey The key for the custom data to be removed.
     */
    public void removeCustomData(Player player, String dataKey) {
        NamespacedKey key = new NamespacedKey(anhyLibAPI, dataKey);
        player.getPersistentDataContainer().remove(key);
    }
}
