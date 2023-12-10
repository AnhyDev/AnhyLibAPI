package ink.anh.api.lingo;

import ink.anh.api.LibraryManager;
import ink.anh.api.lingo.lang.LanguageManager;

/**
 * Provides utilities for translating text using language management facilities.
 */
public class Translator {
	
    /**
     * Translates a given text and updates the provided ModificationState.
     * 
     * @param libraryManager The LibraryManager instance to access language data.
     * @param text The text to be translated.
     * @param langs An array of language codes to consider for translation.
     * @param state The ModificationState to be updated based on the translation.
     * @return The updated ModificationState after translation.
     */
    public static ModificationState translateKyeWorldModificationState(LibraryManager libraryManager, String text, String[] langs, ModificationState state) {
        String newText = translateKyeWorld(libraryManager, text, langs);
        if (!newText.equals(text)) {
            state.setModified(true);
            state.setTranslatedText(newText);
        }
        return state;
    }
	
    /**
     * Translates a given text using the specified languages.
     * 
     * @param libraryManager The LibraryManager instance to access language data.
     * @param text The text to be translated.
     * @param langs An array of language codes to consider for translation.
     * @return The translated text or the original text if no translation is found.
     */
    public static String translateKyeWorld(LibraryManager libraryManager, String text, String[] langs) {
        if (libraryManager == null) {
            return text;
        }

        LanguageManager langMan = libraryManager.getLanguageManager();

        if (langs == null || langs.length == 0) {
            langs = new String[]{libraryManager.getDefaultLang()};
        }

        if (langs == null || langs.length == 0) {
            langs = new String[]{"en"};
        }
        
        String newText = processText(langMan, text, langs);
        return newText != null ? newText : text;
    }

    /**
     * Processes the given text, translating each word based on the specified language preferences.
     * 
     * @param langMan The LanguageManager instance to use for translation.
     * @param text The text to process.
     * @param langs An array of language codes to consider for translation.
     * @return The translated text, or null if no translation is required.
     */
    public static String processText(LanguageManager langMan, String text, String[] langs) {
        boolean prependSpace = text.startsWith(" ");
        boolean appendSpace = text.endsWith(" ");

        String[] words = text.trim().split(" ");
        StringBuilder newText = new StringBuilder();

        if (prependSpace) {
            newText.append(" ");
        }

        boolean textModified = false;
        for (String word : words) {
            String replacement = langMan.getData(word, langs);
            if (replacement != null) {
                newText.append(replacement);
                textModified = true;
            } else {
                newText.append(word);
            }
            newText.append(" ");
        }

        if (!appendSpace && newText.length() > 0) {
            newText.setLength(newText.length() - 1);
        }

        return textModified ? newText.toString() : null;
    }
}
