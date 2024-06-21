package ink.anh.api.enums;

/**
 * Enumeration representing different levels of access with associated properties.
 * Each access level has a corresponding language key, a hexadecimal color code, 
 * and a Minecraft color code.
 */
public enum Access {
    /**
     * Access level allowing permission.
     * Language key: "anhy_access_allow"
     * Hexadecimal color code: "#00FF00"
     * Minecraft color code: "§a"
     */
    TRUE("anhy_access_allow", "#00FF00", "§a"),
    
    /**
     * Access level denying permission.
     * Language key: "anhy_access_deny"
     * Hexadecimal color code: "#FF0000"
     * Minecraft color code: "§c"
     */
    FALSE("anhy_access_deny", "#FF0000", "§c"),
    
    /**
     * Default access level.
     * Language key: "anhy_access_default"
     * Hexadecimal color code: "#FFFF00"
     * Minecraft color code: "§e"
     */
    DEFAULT("anhy_access_default", "#FFFF00", "§e");

    private final String langKey;
    private final String colorHex;
    private final String minecraftColor;

    /**
     * Constructor for Access enum.
     *
     * @param langKey the language key associated with the access level
     * @param colorHex the hexadecimal color code associated with the access level
     * @param minecraftColor the Minecraft color code associated with the access level
     */
    Access(String langKey, String colorHex, String minecraftColor) {
        this.langKey = langKey;
        this.colorHex = colorHex;
        this.minecraftColor = minecraftColor;
    }

    /**
     * Gets the language key associated with the access level.
     *
     * @return the language key
     */
    public String getLangKey() {
        return langKey;
    }

    /**
     * Gets the hexadecimal color code associated with the access level.
     *
     * @return the hexadecimal color code
     */
    public String getColorHex() {
        return colorHex;
    }

    /**
     * Gets the Minecraft color code associated with the access level.
     *
     * @return the Minecraft color code
     */
    public String getMinecraftColor() {
        return minecraftColor;
    }
}
