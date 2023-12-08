package ink.anh.api.lingo;

/**
 * Represents the state of a modification, particularly in the context of localization and translation.
 * This class holds information about whether a piece of text has been modified and its translated form.
 */
public class ModificationState {
    private boolean orModified;
    private String translatedText;

    /**
     * Checks if the text has been modified.
     *
     * @return True if the text has been modified, false otherwise.
     */
    public boolean isModified() {
        return orModified;
    }

    /**
     * Sets the modification state of the text.
     *
     * @param modified A boolean indicating whether the text has been modified.
     */
    public void setModified(boolean modified) {
        this.orModified = modified;
    }

    /**
     * Gets the translated text.
     * 
     * @return The translated text as a String.
     */
    public String getTranslatedText() {
        return translatedText;
    }

    /**
     * Sets the translated text.
     * 
     * @param translatedText The translated text to be set.
     */
    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}
