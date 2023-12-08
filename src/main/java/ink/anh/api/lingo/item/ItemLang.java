package ink.anh.api.lingo.item;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents the localized information for an item, including its name and lore (descriptive text).
 */
public class ItemLang {

    private String lang;
    private String name;
    private String[] lore;

    /**
     * Constructs an ItemLang instance with just a name.
     *
     * @param name The localized name of the item.
     */
    public ItemLang(String name) {
        this.name = name;
    }

    /**
     * Constructs an ItemLang instance with a name and lore.
     *
     * @param name The localized name of the item.
     * @param lore The localized lore (description) of the item, as an array of strings.
     */
    public ItemLang(String name, String[] lore) {
        this.name = name;
        this.lore = lore;
    }

    /**
     * Gets the name of the item.
     *
     * @return The localized name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the lore of the item.
     *
     * @return The localized lore (description) of the item as an array of strings.
     */
    public String[] getLore() {
        return lore;
    }

    /**
     * Sets the name of the item.
     *
     * @param name The new localized name to be set for the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the lore of the item.
     *
     * @param lore The new localized lore (description) to be set for the item, as an array of strings.
     */
    public void setLore(String[] lore) {
        this.lore = lore;
    }

    /**
     * Gets the language code of this item localization.
     *
     * @return The language code.
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the language code for this item localization.
     *
     * @param lang The language code to be set.
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Generates a hash code for this ItemLang object.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(lore);
        result = prime * result + Objects.hash(name);
        return result;
    }

    /**
     * Compares this ItemLang object to another object for equality.
     *
     * @param obj The object to be compared with this ItemLang object.
     * @return True if the given object is equivalent to this ItemLang object, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ItemLang other = (ItemLang) obj;
        return Arrays.equals(lore, other.lore) && Objects.equals(name, other.name);
    }

    /**
     * Converts this ItemLang object to a string representation.
     * The name is followed by each line of the lore, each on a new line with an indentation.
     *
     * @return A string representation of this ItemLang object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");

        if (lore != null) {
            for (String line : lore) {
                sb.append("  ").append(line).append("\n"); // Two spaces for indentation
            }
        }

        return sb.toString().trim(); // Trimming to remove any extra newline at the end
    }
}
