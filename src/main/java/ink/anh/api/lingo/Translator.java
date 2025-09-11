package ink.anh.api.lingo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ink.anh.api.LibraryManager;
import ink.anh.api.lingo.lang.LanguageManager;
import ink.anh.api.utils.StringUtils;

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
        String finalText = newText != null ? newText : text;
        return StringUtils.colorize(finalText);
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
            if (word == null || word.length() < 5) {
                newText.append(word).append(" ");
                continue;
            }

            String[] parts = extractLeadingPunctuation(word);
            String leadingPunctuation = parts[0];
            String remainingWord = parts[1];

            String[] trailingParts = extractTrailingPunctuation(remainingWord);
            String trailingPunctuation = trailingParts[0];
            String coreWord = trailingParts[1];

            String replacement = langMan.getData(coreWord, langs);
            if (replacement != null) {
                newText.append(leadingPunctuation).append(replacement).append(trailingPunctuation);
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

    private static String[] extractLeadingPunctuation(String word) {
        if (word.length() < 5 || word.length() > 4 && (Character.isLetterOrDigit(word.charAt(0)) || word.charAt(0) == '_')) {
            return new String[] {"", word};
        }

        Pattern pattern = Pattern.compile("^((?:[&§][\\da-fA-Fk-oK-OrRxX]|\\p{Punct})+)");
        Matcher matcher = pattern.matcher(word);

        String leadingPunctuation = "";
        if (matcher.find()) {
            leadingPunctuation = matcher.group();
        }

        String remainingWord = word.substring(leadingPunctuation.length());
        return new String[] {leadingPunctuation, remainingWord};
    }

    private static String[] extractTrailingPunctuation(String word) {
        if (word.length() < 5) {
            return new String[] {"", word};
        }
        Pattern pattern = Pattern.compile("((?:[&§][\\da-fA-Fk-oK-OrRxX]|\\p{Punct})+$)");
        
        Matcher matcher = pattern.matcher(word);

        String trailingPunctuation = "";
        if (matcher.find()) {
            trailingPunctuation = matcher.group();
        }

        String coreWord = word.substring(0, word.length() - trailingPunctuation.length());
        return new String[] {trailingPunctuation, coreWord};
    }
}