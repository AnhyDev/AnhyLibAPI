package ink.anh.api.lingo.item;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import ink.anh.api.LibraryManager;
import ink.anh.api.lingo.lang.AbstractLanguage;
import ink.anh.api.messages.Logger;
import ink.anh.api.utils.StringUtils;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class representing a language-specific collection of item configurations.
 * This class extends AbstractLanguage to specialize in handling localization for items.
 */
public class LanguageItemStack extends AbstractLanguage<ItemLang> {

    private static LanguageItemStack instance = null;
    private static final Object LOCK = new Object();
    private static final String DIRECTORY = "items";
    
    /**
     * Constructor for LanguageItemStack.
     * @param libraryManager The library manager instance managing this language stack.
     */
    public LanguageItemStack(LibraryManager libraryManager) {
        super(libraryManager, DIRECTORY);
    }

    /**
     * Singleton instance getter for LanguageItemStack.
     * @param libraryManager The library manager instance.
     * @return The singleton instance of LanguageItemStack.
     */
    public static LanguageItemStack getInstance(LibraryManager libraryManager) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new LanguageItemStack(libraryManager);
                }
            }
        }
        return instance;
    }

    /**
     * Extracts and constructs a mapping of item names to their localized data from the provided configuration.
     * @param langConfig The configuration file from which to extract item data.
     * @param lang The language code representing the language of the configuration.
     * @return A map of item names to their corresponding localized data.
     */
    @Override
    public Map<String, ItemLang> extractData(FileConfiguration langConfig, String lang) {
        Map<String, ItemLang> langMap = new HashMap<>();

        for (String key : langConfig.getKeys(false)) {
            if (langConfig.isConfigurationSection(key)) {
                ConfigurationSection section = langConfig.getConfigurationSection(key);

                ItemLang itemLang;
                if (section.contains("copy")) {
                    itemLang = processCopiedItem(section, langMap);
                    if (itemLang == null) {
                        // Skip this item if processCopiedItem returns null
                        continue;
                    }
                } else {
                    // Normal item parsing
                    String name = StringUtils.colorize(section.getString("name"));
                    List<String> loreList = section.getStringList("lore").stream()
                                        .map(StringUtils::colorize)
                                        .collect(Collectors.toList());

                    String[] lore = loreList.toArray(new String[0]);
                    itemLang = new ItemLang(name, lore);
                }
                if (itemLang != null) itemLang.setLang(lang);
                langMap.put(key, itemLang);
            }
        }

        return langMap;
    }

    /**
     * Processes a configuration section representing an item that copies its properties from another item.
     * @param section The configuration section of the copying item.
     * @param langMap The map containing the original item's data.
     * @return The processed ItemLang, or null if the base item is not found.
     */
    private ItemLang processCopiedItem(ConfigurationSection section, Map<String, ItemLang> langMap) {
        String baseKey = section.getString("copy");
        ItemLang baseItemLang = langMap.get(baseKey);

        if (baseItemLang == null) {
        	Logger.error(plugin, "Base item for copy not found: " + baseKey);
            return null; // Return null to avoid further processing
        }

        String name = baseItemLang.getName();
        List<String> loreList = new ArrayList<>(Arrays.asList(baseItemLang.getLore()));

        if (section.contains("lines")) {
            ConfigurationSection linesSection = section.getConfigurationSection("lines");
            for (String lineKey : linesSection.getKeys(false)) {
                int lineIndex = Integer.parseInt(lineKey) - 1; // Indexing starts from 0

                if (lineIndex >= loreList.size()) {
                    // Add new line at the end if index exceeds existing lore list
                    loreList.add(StringUtils.colorize(linesSection.getString(lineKey)));
                } else {
                    // Otherwise, replace existing line
                    loreList.set(lineIndex, StringUtils.colorize(linesSection.getString(lineKey)));
                }
            }
        }

        String[] lore = loreList.toArray(new String[0]);
        return new ItemLang(name, lore);
    }

    /**
     * This method is not implemented and returns null.
     * @param langConfig The file configuration for language.
     * @return Always returns null.
     */
    @Override
    public Map<String, ItemLang> extractData(FileConfiguration langConfig) {
        return null;
    }
}
