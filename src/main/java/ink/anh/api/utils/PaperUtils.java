package ink.anh.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.papermc.paper.text.PaperComponents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

/**
 * Utility class for handling serialization of Adventure Components using Paper's GsonComponentSerializer.
 */
public class PaperUtils {

    /**
     * The GsonComponentSerializer instance from PaperComponents.
     * It is used for serialization and deserialization of Adventure's Component objects.
     */
    @SuppressWarnings("removal")
    private static final GsonComponentSerializer paperGsonComponentSerializer = PaperComponents.gsonSerializer();

    /**
     * A Gson instance configured with Paper's GsonComponentSerializer for serializing Adventure Components.
     */
    private static final Gson psrSerializer = paperGsonComponentSerializer.populator().apply(new GsonBuilder().disableHtmlEscaping()).create();

    /**
     * Gets the GsonComponentSerializer from PaperComponents.
     * This serializer is used for working with Adventure Components.
     *
     * @return The GsonComponentSerializer instance.
     */
    public static GsonComponentSerializer getPaperGsonComponentSerializer() {
        return paperGsonComponentSerializer;
    }

    /**
     * Serializes an Adventure Component into a JSON string using Paper's GsonComponentSerializer.
     *
     * @param component The Adventure Component to serialize.
     * @return A JSON string representing the serialized Component.
     */
    public static String serializeComponent(Component component) {
        return psrSerializer.toJson(component);
    }
}
