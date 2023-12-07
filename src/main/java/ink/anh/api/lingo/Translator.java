package ink.anh.api.lingo;

import ink.anh.api.LibraryManager;
import ink.anh.api.lingo.lang.LanguageManager;

public class Translator {
	
	public static ModificationState translateKyeWorldModificationState(LibraryManager libraryManager, String text, String[] langs, LanguageManager langMan, ModificationState state) {
		String newText = translateKyeWorld(libraryManager, text, langs);
		if (!newText.equals(text)) {
			state.setModified(true);
			state.setTranslatedText(newText);
		}
		return state;
	}
	
	public static String translateKyeWorld(LibraryManager libraryManager, String text, String[] langs) {
	    String newText = null;
	    
	    if (libraryManager == null) {
	        return text;
	    }
	    
	    LanguageManager langMan = libraryManager.getLanguageManager();

	    if ((langs == null || langs.length == 0)) {
	    	langs = new String[]{libraryManager.getDefaultLang()};
	    }

	    if ((langs == null || langs.length == 0)) {
	    	langs = new String[]{"en"};
	    }
	    
	    newText = processText(text, langMan, langs);
	    return newText != null ? newText : text;
	}

	public static String processText(String text, LanguageManager langMan, String[] langs) {
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
