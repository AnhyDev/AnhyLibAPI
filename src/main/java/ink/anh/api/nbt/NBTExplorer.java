package ink.anh.api.nbt;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.inventory.ItemStack;

/**
 * Utility class for exploring and manipulating NBT (Named Binary Tag) data on ItemStacks.
 */
public class NBTExplorer {

    /**
     * Retrieves the NBT data of an ItemStack.
     *
     * @param item The ItemStack to retrieve the NBT data from.
     * @return The NbtCompound of the item.
     */
    public static NbtCompound getNBT(ItemStack item) {
        return (NbtCompound) NbtFactory.fromItemTag(item);
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
        NbtCompound compound = getNBT(item);
        Object parsedValue = NBTValueParser.parseValueByPrefix(valueString);
        
        if (parsedValue != null) {
            if (parsedValue instanceof String) {
                compound.put(key, (String) parsedValue);
            } else if (parsedValue instanceof Integer) {
                compound.put(key, (Integer) parsedValue);
            } else if (parsedValue instanceof Double) {
                compound.put(key, (Double) parsedValue);
            } else if (parsedValue instanceof int[]) {
                compound.put(key, (int[]) parsedValue);
            }
        }
        setNBT(item, compound);
    }

    /**
     * Sets the NBT compound to an ItemStack.
     *
     * @param item The ItemStack to set the NBT data on.
     * @param compound The NbtCompound to be set.
     */
    public static void setNBT(ItemStack item, NbtCompound compound) {
        NbtFactory.setItemTag(item, compound);
    }

    /**
     * Retrieves an NBT value from an ItemStack.
     *
     * @param item The ItemStack to retrieve the NBT value from.
     * @param key The NBT key.
     * @return The string representation of the NBT value, or null if not present.
     */
    public static String getNBTValue(ItemStack item, String key) {
        NbtCompound compound = getNBT(item);
        if (compound.containsKey(key)) {
            return compound.getString(key);
        }
        return null;
    }

    /**
     * Sets a string NBT value on an ItemStack.
     *
     * @param item The ItemStack to set the NBT value on.
     * @param key The NBT key.
     * @param value The string value to be set.
     */
    public static void setNBTValue(ItemStack item, String key, String value) {
        NbtCompound compound = getNBT(item);
        compound.put(key, value);
        setNBT(item, compound);
    }

    /**
     * Removes an NBT value from an ItemStack.
     *
     * @param item The ItemStack to remove the NBT value from.
     * @param key The NBT key to be removed.
     */
    public static void removeNBTValue(ItemStack item, String key) {
        NbtCompound compound = getNBT(item);
        compound.remove(key);
        setNBT(item, compound);
    }

    /**
     * Clears all NBT data from an ItemStack.
     *
     * @param item The ItemStack to clear the NBT data from.
     */
    public static void clearNBT(ItemStack item) {
        setNBT(item, NbtFactory.ofCompound(""));
    }
    
    /**
     * Sets an NBT value to its default type-specific value if the key exists.
     * The default value is determined by the existing value's type.
     *
     * @param item The ItemStack to set the default NBT value on.
     * @param key The NBT key.
     */
    public static void setDefaultNBTValue(ItemStack item, String key) {
        NbtCompound compound = getNBT(item);
        
        if (!compound.containsKey(key)) {
            return; // No action if the key doesn't exist in NBT
        }
        
        Object currentValue = compound.getValue(key);
        
        // Resetting the value based on its type
        if (currentValue instanceof String) {
            compound.put(key, "");
        } else if (currentValue instanceof Integer) {
            compound.put(key, 0);
        } else if (currentValue instanceof Double) {
            compound.put(key, 0.0);
        } else if (currentValue instanceof Float) {
            compound.put(key, 0.0f);
        } else if (currentValue instanceof Long) {
            compound.put(key, 0L);
        } else if (currentValue instanceof Short) {
            compound.put(key, (short) 0);
        } else if (currentValue instanceof Byte) {
            compound.put(key, (byte) 0);
        } else if (currentValue instanceof int[]) {
            compound.put(key, new int[0]);
        } else {
            compound.putObject(key, null);
        }

        setNBT(item, compound);
    }
}
