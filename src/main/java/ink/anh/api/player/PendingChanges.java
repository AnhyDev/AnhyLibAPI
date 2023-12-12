package ink.anh.api.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

/**
 * Class for managing pending changes for players.
 * It's designed to store changes that are to be applied later, especially when the player is offline.
 * Currently, this class is a placeholder for future use.
 */
public class PendingChanges {

    private Plugin plugin;

    /**
     * Constructs a PendingChanges instance associated with a specific plugin.
     * 
     * @param plugin The plugin this PendingChanges is associated with.
     */
    public PendingChanges(Plugin plugin) {
        this.plugin = plugin;
    }
    
    private static final Map<UUID, Map<String, String>> pendingChanges = new HashMap<>();

    /**
     * Adds a pending change for a specific player.
     * 
     * @param playerUUID The UUID of the player.
     * @param dataKey The key associated with the change.
     * @param values The values to be stored for this change.
     */
    private void addChange(UUID playerUUID, String dataKey, String[] values) {
        String combined = String.join(",", values);
        pendingChanges.computeIfAbsent(playerUUID, k -> new HashMap<>()).put(dataKey, combined);
    }

    /**
     * Applies all pending changes to the specified player.
     * 
     * @param player The player to whom the changes should be applied.
     */
    public void applyChanges(Player player) {
        UUID playerUUID = player.getUniqueId();
        if (pendingChanges.containsKey(playerUUID)) {
            Map<String, String> changes = pendingChanges.get(playerUUID);
            for (Map.Entry<String, String> entry : changes.entrySet()) {
                NamespacedKey key = new NamespacedKey(plugin, entry.getKey());
                player.getPersistentDataContainer().set(key, PersistentDataType.STRING, entry.getValue());
            }
            pendingChanges.remove(playerUUID); // Removing the applied changes
        }
    }

    /**
     * Sets custom data for an offline player and schedules it for later application if the player is not online.
     * 
     * @param playerUUID The UUID of the offline player.
     * @param dataKey The key for the data to be set.
     * @param values The values to be set for the data key.
     */
    public void setOfflinePlayerCustomData(UUID playerUUID, String dataKey, String[] values) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUUID);
            if (offlinePlayer.hasPlayedBefore()) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    Player player = offlinePlayer.getPlayer();
                    if (player != null) {
                        // Player is online, apply changes directly
                        new PlayerData().setCustomData(player, dataKey, values);
                    } else {
                        // Player is offline, add changes to pending list
                        addChange(playerUUID, dataKey, values);
                    }
                });
            }
        });
    }
}
