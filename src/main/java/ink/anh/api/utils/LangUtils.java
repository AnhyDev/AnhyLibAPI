package ink.anh.api.utils;

import java.util.Arrays;
import java.util.Locale;

import org.bukkit.entity.Player;

import ink.anh.api.player.PlayerData;

/**
 * Utility class for managing and processing language preferences of players in a Bukkit server.
 * This class offers functionalities to set, get, and reset a player's language preferences,
 * as well as utility methods to process and interpret player locale information.
 * 
 * <p>Language codes are expected to be in ISO 639-1 format, with special handling for certain cases,
 * such as converting 'uk' to 'ua'. The class interacts with player data to store and retrieve 
 * language preferences, making it a central point for language management in plugins.</p>
 */
public class LangUtils {

    /**
     * The key used for storing and retrieving player-specific language data.
     * This constant is used as an identifier in the player's data storage.
     */
    public static final String LANG_DATA = "Lingo";

    /**
     * Sets the language preference for a player.
     *
     * @param player The player whose language setting is to be configured.
     * @param langs An array of language codes to be set for the player.
     *              The language codes should be in ISO 639-1 format.
     * @return An integer indicating the result: 0 for invalid length, 2 for invalid language code,
     *         and 1 for successful setting of the language.
     */
    public static int setLangs(Player player, String[] langs) {
        String ua = "ua";
        String uk = "uk";

        String[] isoLanguages = Locale.getISOLanguages();
        
        for (int i = 0; i < langs.length; i++) {
            String lang = langs[i].toLowerCase();
            
            if (lang.length() != 2) {
                return 0;
            }

            if (!Arrays.asList(isoLanguages).contains(lang) && !lang.equals(ua)) {
                return 2;
            }

            if (lang.equals(uk)) {
                langs[i] = ua;
            }
        }

        new PlayerData().setCustomData(player, LANG_DATA, langs);
        return 1;
    }

    /**
     * Retrieves the current language settings of a player.
     *
     * @param player The player whose language settings are to be retrieved.
     * @return An array of language codes set for the player, or null if none are set.
     */
    public static String[] getLangs(Player player) {
        PlayerData data = new PlayerData();
        if (data.hasCustomData(player, LANG_DATA)) {
            return data.getCustomData(player, LANG_DATA);
        }
        return null;
    }

    /**
     * Resets the language settings for a player.
     *
     * @param player The player whose language settings are to be reset.
     * @return An integer indicating the result: 1 for successful reset, 0 if no language was set.
     */
    public static int resetLangs(Player player) {
        PlayerData data = new PlayerData();
        if (data.hasCustomData(player, LANG_DATA)) {
            data.removeCustomData(player, LANG_DATA);
            return 1;
        }
        return 0;
    }

    /**
     * Retrieves the preferred language(s) of a player.
     * If the player has custom language data set, it returns that data.
     * Otherwise, it processes the player's locale to determine the language.
     *
     * @param player The player whose language preferences are being queried.
     * @return An array of language codes representing the player's language preferences.
     */
    public static String[] getPlayerLanguage(Player player) {
        String[] lang;
        
        lang = getLangs(player);
        
        if (lang == null) {
            lang = new String[]{processLocale(player)};
        }
        return (lang != null && lang.length != 0) ? lang : new String[]{"en"};
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
