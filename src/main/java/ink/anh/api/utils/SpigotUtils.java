package ink.anh.api.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

/**
 * Utility class for handling serialization and comparison of Adventure API Components.
 */
public class SpigotUtils {

    private static final GsonComponentSerializer serializer = GsonComponentSerializer.gson();

    /**
     * Serializes Adventure API Component to JSON using GsonComponentSerializer.
     * 
     * @param component The Adventure Component to serialize.
     * @return JSON string representation of the component.
     */
    public static String serializeComponents(Component component) {
        try {
            return serializer.serialize(component);
        } catch (Throwable t) {
            // Fallback to empty string if serialization fails
            return "";
        }
    }

    /**
     * Compares two Adventure API Components for equality.
     * It checks the serialized JSON representation of the components.
     * 
     * @param a The first Adventure Component.
     * @param b The second Adventure Component.
     * @return True if the components are equal, false otherwise.
     */
    public static boolean compareComponents(Component a, Component b) {
        if (a == null && b != null || a != null && b == null) {
            return false;
        }
        if (a == null) {
            return true;
        }
        try {
            String jsonA = serializer.serialize(a);
            String jsonB = serializer.serialize(b);
            return jsonA.equals(jsonB);
        } catch (Throwable t) {
            return false;
        }
    }
}