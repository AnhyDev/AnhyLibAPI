package ink.anh.api.lingo.lang;

import org.bukkit.configuration.file.FileConfiguration;
import ink.anh.api.LibraryManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class extending AbstractLanguage for managing language-specific strings.
 * This class is specifically designed for handling simple key-value pairs in language files.
 */
public abstract class LanguageManager extends AbstractLanguage<String> {
    
    /**
     * Constructs a LanguageManager instance with specified library manager and directory.
     * 
     * @param libraryManager The library manager associated with this language manager.
     * @param directory The directory where language files are stored.
     */
    public LanguageManager(LibraryManager libraryManager, String directory) {
        super(libraryManager, directory);
    }

    /**
     * Extracts simple key-value pairs from the provided FileConfiguration.
     * This method is used when language-specific data is not required and only a simple
     * key-value mapping is needed.
     *
     * @param langConfig The file configuration to extract data from.
     * @return A map of keys to their corresponding string values.
     */
    @Override
    public Map<String, String> extractData(FileConfiguration langConfig) {
        Map<String, String> langMap = new HashMap<>();
        for (String key : langConfig.getKeys(false)) {
            String value = langConfig.getString(key);
            
            langMap.put(key, value);
        }
        return langMap;
    }

    /**
     * Extracts language-specific data from the provided FileConfiguration.
     * In this implementation, it simply delegates to the extractData method that does not require a language parameter.
     *
     * @param langConfig The file configuration to extract data from.
     * @param lang The language identifier (not used in this implementation).
     * @return A map of keys to their corresponding string values.
     */
    @Override
    public Map<String, String> extractData(FileConfiguration langConfig, String lang) {
        return extractData(langConfig);
    }
}
