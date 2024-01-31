package ink.anh.api.messages;

/**
 * Represents a message template for formatting, including the template string and any replacements.
 * This class is used for handling messages that require dynamic content insertion.
 */
public class MessageForFormatting {

    private String template;
    private String[] replacements;

    /**
     * Constructs a new MessageForFormatting object.
     *
     * @param template     The message template string. This string can contain placeholders
     *                     that will be replaced by the values in the replacements array.
     * @param replacements An array of strings that will be used to replace placeholders in the template.
     */
    public MessageForFormatting(String template, String[] replacements) {
        this.template = template;
        this.replacements = replacements;
    }

    /**
     * Gets the template string of the message.
     *
     * @return The template string.
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Gets the array of replacement strings for the template.
     *
     * @return An array of strings to be used for replacing placeholders in the template.
     */
    public String[] getReplacements() {
        return replacements;
    }

    /**
     * Sets the template string for the message.
     *
     * @param template The new template string to be set.
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * Sets the array of replacement strings for the template.
     *
     * @param replacements The new array of strings to be used as replacements.
     */
    public void setReplacements(String[] replacements) {
        this.replacements = replacements;
    }
}
