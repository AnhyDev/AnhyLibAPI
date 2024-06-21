package ink.anh.api.enums;

/**
 * Enumeration representing different types of currencies.
 * Each currency type is associated with a language key, a color in hexadecimal format, 
 * and a Minecraft-specific color code.
 */
public enum Currency {
    /**
     * Represents a virtual currency.
     * Associated with the language key "anhy_currency_virtual", the color "#00FFFF",
     * and the Minecraft color code "§b".
     */
    VIRTUAL("anhy_currency_virtual", "#00FFFF", "§b"),
    
    /**
     * Represents an item-based currency.
     * Associated with the language key "anhy_currency_item", the color "#FFA500",
     * and the Minecraft color code "§6".
     */
    ITEM("anhy_currency_item", "#FFA500", "§6"),
    
    /**
     * Represents a cryptocurrency.
     * Associated with the language key "anhy_currency_crypto", the color "#800080",
     * and the Minecraft color code "§5".
     */
    CRYPTO("anhy_currency_crypto", "#800080", "§5");

    private final String langKey;
    private final String colorHex;
    private final String minecraftColor;

    /**
     * Constructs a Currency enumeration value.
     *
     * @param langKey the language key associated with the currency
     * @param colorHex the hexadecimal color code associated with the currency
     * @param minecraftColor the Minecraft-specific color code associated with the currency
     */
    Currency(String langKey, String colorHex, String minecraftColor) {
        this.langKey = langKey;
        this.colorHex = colorHex;
        this.minecraftColor = minecraftColor;
    }

    /**
     * Gets the language key associated with the currency.
     *
     * @return the language key of the currency
     */
    public String getLangKey() {
        return langKey;
    }

    /**
     * Gets the hexadecimal color code associated with the currency.
     *
     * @return the hexadecimal color code of the currency
     */
    public String getColorHex() {
        return colorHex;
    }

    /**
     * Gets the Minecraft-specific color code associated with the currency.
     *
     * @return the Minecraft color code of the currency
     */
    public String getMinecraftColor() {
        return minecraftColor;
    }
}
