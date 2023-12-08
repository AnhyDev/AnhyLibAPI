package ink.anh.api.nbt;

/**
 * Utility class for parsing values from strings with a specific format, 
 * especially for handling NBT (Named Binary Tag) data types in Minecraft.
 */
public class NBTValueParser {

    /**
     * Parses a string input with a prefix to determine the type of the value.
     * Examples of formats:
     *   "int:12345" -> for integers
     *   "double:3.14" -> for doubles
     *   "intarray:[1,2,3]" -> for integer arrays
     *   "string:Hello world!" -> for strings
     *
     * @param input The string input to parse.
     * @return The parsed object (Integer, Double, int[], String), or null if unable to parse.
     */
    public static Object parseValueByPrefix(String input) {
        if (input.contains(":")) {
            String[] parts = input.split(":", 2);
            String prefix = parts[0].toLowerCase();
            String value = parts[1];

            try {
                switch (prefix) {
                    case "int":
                        return Integer.parseInt(value);
                    case "double":
                        return Double.parseDouble(value);
                    case "intarray":
                        return parseIntArray(value);
                    case "string":
                        return value;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return parse(input);
    }

    /**
     * Automatically tries to determine the type of the value from the input string.
     * Examples of formats:
     *   "Hello world!" -> for strings
     *   "12345" -> for integers
     *   "3.14" -> for doubles
     *   "[1,2,3]" -> for integer arrays
     *
     * @param valueString The string input to parse.
     * @return The parsed object (Integer, Double, int[], String), or null if unable to parse.
     */
    private static Object parse(String valueString) {
        try {
            if (valueString.startsWith("\"") && valueString.endsWith("\"")) {
                return parseString(valueString);
            } else if (valueString.matches("-?\\d+")) {
                return parseInt(valueString);
            } else if (valueString.matches("-?\\d+\\.\\d+")) {
                return parseDouble(valueString);
            } else if (valueString.startsWith("[") && valueString.endsWith("]")) {
                return parseIntArray(valueString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; 
    }

    /**
     * Parses a string value by removing enclosing quotation marks.
     * This method is used for processing string values enclosed in quotes.
     *
     * @param value The string value to parse.
     * @return The string without enclosing quotation marks.
     */
    private static String parseString(String value) {
        return value.substring(1, value.length() - 1);
    }

    /**
     * Parses an integer value from a string.
     * This method is used for processing strings representing integer numbers.
     *
     * @param value The string value to parse as an integer.
     * @return The integer representation of the input string.
     */
    private static int parseInt(String value) {
        return Integer.parseInt(value);
    }

    /**
     * Parses a double value from a string.
     * This method is used for processing strings representing double numbers.
     *
     * @param value The string value to parse as a double.
     * @return The double representation of the input string.
     */
    private static double parseDouble(String value) {
        return Double.parseDouble(value);
    }

    /**
     * Parses an array of integers from a string.
     * This method is used for processing strings representing arrays of integers, formatted like "[1,2,3]".
     *
     * @param value The string value to parse as an array of integers.
     * @return An array of integers parsed from the input string.
     */
    private static int[] parseIntArray(String value) {
        String[] parts = value.substring(1, value.length() - 1).split(",");
        int[] array = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            array[i] = Integer.parseInt(parts[i].trim());
        }
        return array;
    }

}
