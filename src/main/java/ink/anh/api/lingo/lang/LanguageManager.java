package ink.anh.api.lingo.lang;

import org.bukkit.configuration.file.FileConfiguration;
import ink.anh.api.LibraryManager;

import java.util.HashMap;
import java.util.Map;

public abstract class LanguageManager extends AbstractLanguage<String> {
	

	public LanguageManager(LibraryManager libraryManager, String directory) {
        super(libraryManager, directory);
    }

	@Override
	public Map<String, String> extractData(FileConfiguration langConfig) {
        Map<String, String> langMap = new HashMap<>();
        for (String key : langConfig.getKeys(false)) {
            String value = langConfig.getString(key);
            
            langMap.put(key, value);
        }
        return langMap;
    }

	@Override
	public Map<String, String> extractData(FileConfiguration langConfig, String lang) {
		return extractData(langConfig);
	}
}
