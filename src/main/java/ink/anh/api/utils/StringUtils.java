package ink.anh.api.utils;

/**
 * Utility class for string manipulation, particularly for colorizing strings in Minecraft.
 */
public class StringUtils {

    /**
     * Transforms Minecraft color codes from '&amp;' prefix to the section symbol prefix.
     * This is used for color and formatting codes within the game.
     * For example, the string "&amp;cHello" would be transformed to a string that begins with the section symbol followed by 'cHello'.
     *
     * @param text The text containing color codes prefixed with '&amp;'.
     * @return The text with color codes now prefixed with the section symbol.
     */
    public static String colorize(String text) {
        // Use the actual section symbol character in code to avoid issues in JavaDoc
        return text.replace("&", "\u00A7");
    }

    // Additional string processing methods can be added here
}
