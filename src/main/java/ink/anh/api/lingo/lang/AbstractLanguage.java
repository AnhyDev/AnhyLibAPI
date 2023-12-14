package ink.anh.api.lingo.lang;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import ink.anh.api.LibraryManager;
import ink.anh.api.messages.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Abstract base class for handling language-specific data.
 * 
 * @param <T> The type of data this language handler will manage.
 */
public abstract class AbstractLanguage<T> {

    /**
     * The plugin associated with this language handler.
     */
    protected Plugin plugin;

    /**
     * A map holding the language-specific data. Each key corresponds to a unique identifier
     * which maps to another map that holds the language codes and their associated data.
     */
    protected Map<String, Map<String, T>> data = new HashMap<>();
    private String directory;
    private LibraryManager libraryManager;

    /**
     * Constructs an AbstractLanguage instance with specified library manager and directory.
     * 
     * @param libraryManager The library manager associated with this language handler.
     * @param directory The directory where language files are stored.
     */
    public AbstractLanguage(LibraryManager libraryManager, String directory) {
        this.plugin = libraryManager.getPlugin();
        this.directory = directory;
        this.libraryManager = libraryManager;
        saveDefaultLang();
        loadLanguages();
    }

    /**
     * Abstract method to extract data from a given FileConfiguration and language.
     * Implementations should define how to extract and structure the data.
     *
     * @param langConfig The file configuration to extract data from.
     * @param lang The language identifier for the data being extracted.
     * @return A map of keys to their corresponding data items.
     */
    public abstract Map<String, T> extractData(FileConfiguration langConfig, String lang);

    /**
     * Abstract method to extract data from a given FileConfiguration.
     * This method is intended to be implemented if required.
     *
     * @param langConfig The file configuration to extract data from.
     * @return A map of keys to their corresponding data items.
     */
    public abstract Map<String, T> extractData(FileConfiguration langConfig);

    /**
     * Gets the directory where language files are stored.
     * 
     * @return The directory as a String.
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Saves the default language files from the plugin's resources to the plugin's data folder.
     */
    private void saveDefaultLang() {
        File dir = new File(plugin.getDataFolder() + File.separator + getDirectory());
        if (!dir.exists()) dir.mkdirs();
        String[] files;
        try {
            files = getResourceFiles(getDirectory());
            for (String filename : files) {
                String resourcePath = filename.startsWith("/") ? filename.substring(1) : filename;
                File file = new File(plugin.getDataFolder(), resourcePath);
                if (!file.exists()) {
                    plugin.saveResource(resourcePath, false);
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads language files from the specified directory and processes them.
     * The method identifies and processes all .yml files matching the language file pattern.
     */
    private void loadLanguages() {
        File dir = new File(plugin.getDataFolder() + File.separator + getDirectory());
        String regex = ".*_[a-zA-Z]{2}\\.yml";
        FilenameFilter filter = (dir1, name) -> name.matches(regex);

        File[] files = dir.listFiles(filter);

        if (files == null || files.length == 0) {
            Logger.info(plugin, "No language files found in directory: " + dir);
            return;
        }

        for (File file : files) {
            String fileName = file.getName();
            String lang = fileName.substring(fileName.lastIndexOf('_') + 1, fileName.lastIndexOf('.'));

            FileConfiguration langConfig = YamlConfiguration.loadConfiguration(file);
            Map<String, T> extractedData = extractData(langConfig, lang);

            for (Map.Entry<String, T> entry : extractedData.entrySet()) {
                String key = entry.getKey();
                T value = entry.getValue();
                
                data.computeIfAbsent(key, k -> new HashMap<>()).put(lang, value);
            }
        }
    }

    /**
     * Clears and reloads all language data from the language files.
     */
    public void reloadLanguages() {
        data.clear();
        loadLanguages();
    }

    /**
     * Gets the entire data map containing all language-specific data.
     * 
     * @return The data map.
     */
    public Map<String, Map<String, T>> getDataMap() {
        return data;
    }

    /**
     * Retrieves the data for a specific key across multiple languages.
     * Tries to find the data in the order of the provided languages, then the default language, followed by English, 
     * and finally any available language if none of the specified languages have data.
     * 
     * @param key The key for which to retrieve the data.
     * @param langs The array of language codes in the order of preference.
     * @return The data for the given key, or null if not found in any of the specified languages.
     */
    public T getData(String key, String[] langs) {
        Map<String, T> dataMap = data.get(key);
        if (dataMap == null) {
            return null;
        }

        String defaultLanguage = libraryManager.getDefaultLang();
        
        if (langs == null || langs.length == 0) {
        	langs = new String[] {defaultLanguage};
        }
        
        boolean defaultLangChecked = false;
        boolean englishLangChecked = false;

        for (String lang : langs) {
            if (lang.equals(defaultLanguage)) {
                defaultLangChecked = true;
            }
            if (lang.equals("en")) {
                englishLangChecked = true;
            }

            T dataValue = dataMap.get(lang);
            if (dataValue != null) {
                return dataValue;
            }
        }

        if (!defaultLangChecked) {
            T dataValue = dataMap.get(defaultLanguage);
            if (dataValue != null) {
                return dataValue;
            }
        }

        if (!englishLangChecked && !defaultLanguage.equals("en")) {
            T dataValue = dataMap.get("en");
            if (dataValue != null) {
                return dataValue;
            }
        }

        for (String availableLang : dataMap.keySet()) {
            if (!Arrays.asList(langs).contains(availableLang) && !availableLang.equals(defaultLanguage) && !availableLang.equals("en")) {
                return dataMap.get(availableLang);
            }
        }

        return null;
    }

    /**
     * Retrieves the translation for a specific key in a specific language.
     * 
     * @param key The key for which to retrieve the translation.
     * @param lang The language code.
     * @return The translation data for the given key and language, or null if not found.
     */
    public T getTranslate(String key, String lang) {
        Map<String, T> dataMap = data.get(key);
        return dataMap.get(lang);
    }

    /**
     * Checks if data for a specific key is available in any of the provided languages.
     * 
     * @param key The key to check for data availability.
     * @param langs The array of language codes to check.
     * @return True if data is available in any of the provided languages, false otherwise.
     */
    public boolean dataContainsKey(String key, String[] langs) {
        return getData(key, langs) != null;
    }

    /**
     * Retrieves a list of resource files within a given folder path inside the jar.
     * 
     * @param folderPath The path of the folder within the jar.
     * @return An array of file names found in the specified folder.
     * @throws IOException If an I/O error occurs.
     * @throws URISyntaxException If the URI syntax is incorrect.
     */
    public String[] getResourceFiles(String folderPath) throws IOException, URISyntaxException {
        URL jarUrl = getClass().getProtectionDomain().getCodeSource().getLocation();
        List<String> filenames = new ArrayList<>();

        URI jarUri = new URI("jar:" + jarUrl.toURI().toString());

        try (FileSystem fileSystem = FileSystems.newFileSystem(jarUri, Collections.emptyMap())) {
            Path path = fileSystem.getPath(folderPath.startsWith("/") ? folderPath : "/" + folderPath);

            Files.walk(path)
                 .filter(Files::isRegularFile)
                 .forEach(filePath -> filenames.add(filePath.toString()));
        }
        
        return filenames.toArray(new String[0]);
    }
}
