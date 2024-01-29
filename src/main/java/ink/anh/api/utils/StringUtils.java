package ink.anh.api.utils;

import org.bukkit.ChatColor;

/**
 * Utility class for string manipulation, particularly for colorizing strings in Minecraft.
 */
public class StringUtils {

    /**
     * Transforms Minecraft color codes from '&' prefix to the section symbol prefix.
     * This is used for color and formatting codes within the game.
     * For example, the string "&cHello" would be transformed to a string that begins with the section symbol followed by 'cHello'.
     *
     * @param text The text containing color codes prefixed with '&'.
     * @return The text with color codes now prefixed with the section symbol.
     */
    public static String colorize(String text) {
        // Use ChatColor to translate color codes from '&' to the section symbol
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Formats a string by replacing placeholders with specified replacement values, normalizes whitespace, 
     * and colorizes the resulting string. Placeholders in the template string are denoted by '%s'. If there 
     * are more placeholders than replacements, the extra placeholders are replaced with empty strings. 
     * Consecutive spaces (caused by empty placeholders or other reasons) are reduced to a single space. 
     * After processing the placeholders and normalizing spaces, any Minecraft color codes in the string 
     * (prefixed with '&') are transformed to the section symbol prefix used for color and formatting codes 
     * within the game.
     *
     * @param template The template string with placeholders.
     * @param replacements An array of replacement values to substitute into the template.
     * @return The formatted, whitespace-normalized, and colorized string with all placeholders replaced 
     *         by corresponding values.
     */
    public static String formatString(String template, String... replacements) {
        if (template == null) {
            return "";
        }

        // Count the number of placeholders in the template
        int placeholdersCount = template.split("%s", -1).length - 1;
        Object[] values = new Object[placeholdersCount];

        // Fill the values array with replacements or empty strings if insufficient replacements are provided
        for (int i = 0; i < placeholdersCount; i++) {
            values[i] = (i < replacements.length) ? replacements[i] : "";
        }

        String formattedString = String.format(template, values);
        // Remove double spaces that could have formed after replacing placeholders
        formattedString = formattedString.replaceAll("\\s{2,}", " ");

        return colorize(formattedString);
    }
}