package ink.anh.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

/**
 * Utility class for string manipulation, particularly for colorizing strings in Minecraft.
 */
public class StringUtils {

    /**
     * Transforms Minecraft color codes from '&amp;' prefix to the section symbol prefix, and also handles HEX color codes.
     * This method is used for color and formatting codes within the game. It translates standard color codes and
     * formats HEX color codes correctly for Minecraft usage.
     * For example, the string "&amp;cHello" would be transformed to a string that begins with the section symbol followed by 'cHello',
     * and a HEX color code like "&amp;#FFFFFF" would be formatted to be recognized as a white color in Minecraft.
     *
     * @param text The text containing color codes prefixed with '&amp;' and HEX color codes prefixed with '&amp;#'.
     * @return The text with standard color codes now prefixed with the section symbol and HEX color codes formatted properly.
     */
    public static String colorize(String text) {
        // First translate the standard Minecraft color codes
        text = ChatColor.translateAlternateColorCodes('&', text);

        // Now handle the HEX color codes properly
        Pattern pattern = Pattern.compile("&#([0-9a-fA-F]{6})"); // This pattern ensures exactly 6 hex digits
        Matcher matcher = pattern.matcher(text);

        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String colorCode = matcher.group(1); // Extract the HEX code without '&#'
            if (colorCode.length() == 6) { // Check if the HEX code is exactly 6 characters
                StringBuilder replacement = new StringBuilder("§x"); // Start with §x which is needed for HEX colors in Minecraft
                // Append each character of the HEX code preceded by '§'
                for (char c : colorCode.toCharArray()) {
                    replacement.append('§').append(c);
                }
                // Replace the match in the original text with the formatted string
                matcher.appendReplacement(buffer, replacement.toString());
            }
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }


    /**
     * Formats a string by replacing placeholders with specified replacement values and colorizes the resulting string.
     * Placeholders in the template string are denoted by '%s'. If there are more placeholders than replacements,
     * the extra placeholders are replaced with empty strings. After processing the placeholders, 
     * any Minecraft color codes in the string (prefixed with '&amp;') are transformed to the section symbol prefix used 
     * for color and formatting codes within the game.
     *
     * @param template The template string with placeholders.
     * @param replacements An array of replacement values to substitute into the template.
     * @return The formatted and colorized string with all placeholders replaced by corresponding values.
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
        return colorize(formattedString);
    }
  
    /**
     * Removes consecutive spaces from a given string, replacing them with a single space.
     * This method is useful for normalizing text where multiple spaces may occur inadvertently, 
     * such as after replacing placeholders in a formatted string.
     *
     * @param text The string from which to remove double spaces.
     * @return The string with consecutive spaces replaced by a single space.
     */
    public static String removeDoubleSpaces(String text) {
        return text.replaceAll("\\s{2,}", " ");
    }
}
