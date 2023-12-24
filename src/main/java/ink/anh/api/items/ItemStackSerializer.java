package ink.anh.api.items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Base64;

/**
 * Class providing methods to serialize and deserialize ItemStacks to and from YAML and Base64 formats.
 */
public class ItemStackSerializer {

    /**
     * Serializes an ItemStack into a YAML string.
     *
     * @param itemStack The ItemStack to serialize.
     * @return A YAML string representation of the ItemStack.
     */
    public static String serializeItemStackToYaml(ItemStack itemStack) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.set("item", itemStack);
        return yamlConfiguration.saveToString();
    }

    /**
     * Deserializes a YAML string back into an ItemStack.
     *
     * @param serializedItemStack The YAML string to deserialize.
     * @return The deserialized ItemStack, or null if deserialization fails.
     */
    public static ItemStack deserializeItemStackFromYaml(String serializedItemStack) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.loadFromString(serializedItemStack);
            return yamlConfiguration.getItemStack("item");
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Serializes an ItemStack into a Base64 encoded string.
     *
     * @param itemStack The ItemStack to serialize.
     * @return A Base64 encoded string representing the ItemStack.
     */
    public static String serializeItemStackToBase64(ItemStack itemStack) {
        String serializedYaml = serializeItemStackToYaml(itemStack);
        return Base64.getEncoder().encodeToString(serializedYaml.getBytes());
    }

    /**
     * Deserializes a Base64 encoded string back into an ItemStack.
     *
     * @param base64SerializedItemStack The Base64 encoded string to deserialize.
     * @return The deserialized ItemStack, or null if deserialization fails.
     */
    public static ItemStack deserializeItemStackFromBase64(String base64SerializedItemStack) {
        String decoded = new String(Base64.getDecoder().decode(base64SerializedItemStack));
        return deserializeItemStackFromYaml(decoded);
    }

    /**
     * General method for deserializing an ItemStack from either a Base64 or YAML encoded string.
     *
     * @param serializedItemStack The serialized string, either Base64 or YAML format.
     * @return The deserialized ItemStack, or null if deserialization fails.
     */
    public static ItemStack deserializeItemStack(String serializedItemStack) {
        try {
            return deserializeItemStackFromBase64(serializedItemStack);
        } catch (IllegalArgumentException e) {
            try {
                return deserializeItemStackFromYaml(serializedItemStack);
            } catch (Exception e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }

    /**
     * General method for serializing an ItemStack into a Base64 encoded string.
     *
     * @param itemStack The ItemStack to serialize.
     * @return A Base64 encoded string representing the ItemStack.
     */
    public static String serializeItemStack(ItemStack itemStack) {
        return serializeItemStackToBase64(itemStack);
    }
}
