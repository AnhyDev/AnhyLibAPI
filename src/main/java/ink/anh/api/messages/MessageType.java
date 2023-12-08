package ink.anh.api.messages;

/**
 * Enum representing different types of messages with associated color codes.
 * This enum provides both hexadecimal color codes for GUI elements and ANSI color codes for console output.
 */
public enum MessageType {
    /** Normal message, represented by cyan color. */
    NORMAL("#0bdebb", ANSIColors.CYAN_BRIGHT),

    /** Special message, represented by bright green color. */
    ESPECIALLY("#06d44e", ANSIColors.GREEN_BRIGHT),

    /** Important message, represented by bright yellow color. */
    IMPORTANT("#FFFF00", ANSIColors.YELLOW_BRIGHT),

    /** Warning message, represented by yellow color. */
    WARNING("#f54900", ANSIColors.YELLOW),

    /** Error message, represented by bright red color. */
    ERROR("#FF0000", ANSIColors.RED_BRIGHT),

    /** Critical error message, represented by dark red color. */
    CRITICAL_ERROR("#8B0000", ANSIColors.RED);

    private final String hexColor;
    private final String consoleColor;

    /**
     * Constructs a MessageType instance with specified color codes.
     *
     * @param hexColor The hexadecimal color code for GUI usage.
     * @param consoleColor The ANSI color code for console output.
     */
    MessageType(String hexColor, String consoleColor) {
        this.hexColor = hexColor;
        this.consoleColor = consoleColor;
    }

    /**
     * Gets the appropriate color code based on the context (player or console).
     *
     * @param forPlayer If true, returns the hexadecimal color code for GUI, otherwise returns the ANSI color code for console.
     * @return The corresponding color code as a String.
     */
    public String getColor(boolean forPlayer) {
        return forPlayer ? hexColor : consoleColor;
    }

    /**
     * Formats a message for console output with the MessageType's color.
     *
     * @param message The message to be formatted.
     * @return The formatted message with the MessageType's console color and reset after the message.
     */
    public String formatConsoleColor(String message) {
        return this.consoleColor + message + ANSIColors.RESET;
    }
}
