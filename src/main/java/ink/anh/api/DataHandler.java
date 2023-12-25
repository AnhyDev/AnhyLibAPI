package ink.anh.api;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract class that serves as a handler for global data associated with players or entities within the plugin.
 * This class provides methods to add, remove, retrieve, and clear custom data.
 */
public abstract class DataHandler {

    private AnhyLibAPI plugin;

    /**
     * Constructs a DataHandler with a reference to the AnhyLibAPI plugin.
     */
    public DataHandler() {
        this.plugin = AnhyLibAPI.getInstance();
    }

    /**
     * Adds or updates data for a specific UUID and subKey.
     *
     * @param uuid   The UUID to associate the data with.
     * @param subKey The subkey under which the data is stored.
     * @param value  The data value to store.
     */
    public void addData(UUID uuid, String subKey, Object value) {
        plugin.getGlobalDataMap().computeIfAbsent(uuid, k -> new ConcurrentHashMap<>()).put(subKey, value);
    }

    /**
     * Removes data for a specific UUID and subKey.
     *
     * @param uuid   The UUID from which the data is to be removed.
     * @param subKey The subkey under which the data is stored.
     */
    public void removeData(UUID uuid, String subKey) {
        Map<String, Object> subMap = plugin.getGlobalDataMap().get(uuid);

        if (subMap != null) {
            subMap.remove(subKey);

            if (subMap.isEmpty()) {
                plugin.getGlobalDataMap().remove(uuid);
            }
        }
    }

    /**
     * Retrieves data for a specific UUID and subKey.
     *
     * @param uuid   The UUID for which the data is to be retrieved.
     * @param subKey The subkey under which the data is stored.
     * @return The data associated with the UUID and subKey, or null if no data exists.
     */
    public Object getData(UUID uuid, String subKey) {
        return plugin.getGlobalDataMap().getOrDefault(uuid, Collections.emptyMap()).get(subKey);
    }

    /**
     * Clears all data associated with a specific UUID.
     *
     * @param uuid The UUID for which all associated data is to be cleared.
     */
    public void clearData(UUID uuid) {
        plugin.getGlobalDataMap().remove(uuid);
    }
}
