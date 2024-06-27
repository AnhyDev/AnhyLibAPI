package ink.anh.api.nbt;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import ink.anh.api.AnhyLibAPI;

/**
 * Utility class for exploring and manipulating NBT (Named Binary Tag) data on ItemStacks.
 */
public class NBTExplorer {

    private static AnhyLibAPI plugin;

    /**
     * Initializes the NBTExplorer with the plugin instance.
     *
     * @param pluginInstance The instance of the plugin.
     */
    public static void initialize(AnhyLibAPI pluginInstance) {
        plugin = pluginInstance;
    }

    /**
     * Retrieves the NBT value of an ItemStack.
     *
     * @param item The ItemStack to retrieve the NBT value from.
     * @param key The NBT key.
     * @return The NBT value as a string, or null if not present.
     */
    public static String getNBTValue(ItemStack item, String key) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
        return dataContainer.has(namespacedKey, PersistentDataType.STRING) ? dataContainer.get(namespacedKey, PersistentDataType.STRING) : null;
    }

    /**
     * Sets an NBT value on an ItemStack.
     *
     * @param item The ItemStack to set the NBT value on.
     * @param key The NBT key.
     * @param value The string value to be set.
     */
    public static void setNBTValue(ItemStack item, String key, String value) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
        dataContainer.set(namespacedKey, PersistentDataType.STRING, value);
        item.setItemMeta(itemMeta);
    }

    /**
     * Removes an NBT value from an ItemStack.
     *
     * @param item The ItemStack to remove the NBT value from.
     * @param key The NBT key to be removed.
     */
    public static void removeNBTValue(ItemStack item, String key) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
        dataContainer.remove(namespacedKey);
        item.setItemMeta(itemMeta);
    }

    /**
     * Clears all NBT data from an ItemStack.
     *
     * @param item The ItemStack to clear the NBT data from.
     */
    public static void clearNBT(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        dataContainer.getKeys().forEach(dataContainer::remove);
        item.setItemMeta(itemMeta);
    }

    /**
     * Sets an NBT value to its default type-specific value if the key exists.
     * The default value is determined by the existing value's type.
     *
     * @param item The ItemStack to set the default NBT value on.
     * @param key The NBT key.
     */
    public static void setDefaultNBTValue(ItemStack item, String key) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);

        if (dataContainer.has(namespacedKey, PersistentDataType.STRING)) {
            dataContainer.set(namespacedKey, PersistentDataType.STRING, "");
        }

        item.setItemMeta(itemMeta);
    }

    /**
     * Sets an NBT value on an ItemStack from a string representation.
     * The value type is determined by the prefix in the value string.
     *
     * @param item The ItemStack to set the NBT value on.
     * @param key The NBT key.
     * @param valueString The string representation of the NBT value, prefixed with its type.
     */
    public static void setNBTValueFromString(ItemStack item, String key, String valueString) {
        if (valueString.startsWith("string:")) {
            setNBTValue(item, key, valueString.substring(7));
        } else if (valueString.startsWith("int:")) {
            setNBTValue(item, key, valueString.substring(4));
        } else if (valueString.startsWith("double:")) {
            setNBTValue(item, key, valueString.substring(7));
        } else if (valueString.startsWith("float:")) {
            setNBTValue(item, key, valueString.substring(6));
        } else if (valueString.startsWith("long:")) {
            setNBTValue(item, key, valueString.substring(5));
        } else {
            throw new IllegalArgumentException("Unsupported value type: " + valueString);
        }
    }
}
