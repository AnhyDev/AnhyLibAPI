package ink.anh.api.utils;

import org.bukkit.entity.Player;
import ink.anh.api.player.PlayerData;

/**
 * Utility class for handling language-related functionalities for players.
 */
public class LangUtils {

    /**
     * Retrieves the preferred language(s) of a player.
     * If the player has custom language data set, it returns that data.
     * Otherwise, it processes the player's locale to determine the language.
     *
     * @param player The player whose language preferences are being queried.
     * @param plugin The plugin instance.
     * @return An array of language codes representing the player's language preferences.
     */
    public static String[] getPlayerLanguage(Player player) {
        String[] lang;
        String langData = "Lingo";
        
        PlayerData data = new PlayerData();
        if (data.hasCustomData(player, langData)) {
            lang = data.getCustomData(player, langData);
        } else {
            lang = new String[]{processLocale(player)};
        }
        return lang.length != 0 ? lang : new String[]{"en"};
    }

    /**
     * Processes the locale of a player to determine their language code.
     * For example, "uk" is converted to "ua".
     *
     * @param player The player whose locale is being processed.
     * @return The processed language code.
     */
    public static String processLocale(Player player) {
        String locale = player.getLocale();
        String[] parts = locale.split("_");
        if (parts[0].equalsIgnoreCase("uk")) {
            return "ua";
        }
        return parts[0];
    }
}
