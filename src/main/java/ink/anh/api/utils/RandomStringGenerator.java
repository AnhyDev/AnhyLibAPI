package ink.anh.api.utils;

import java.security.SecureRandom;
import java.time.Instant;

/**
 * This utility class provides a method to generate a random alphanumeric string.
 * It is useful for creating unique identifiers or tokens where a non-predictable string is required.
 */
public class RandomStringGenerator {

    /**
     * String constant representing all lowercase letters of the English alphabet.
     * Used as part of the pool of characters for generating random strings.
     */
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";

    /**
     * String constant representing the digits 0 through 9.
     * Used as part of the pool of characters for generating random strings.
     */
    private static final String NUMBER = "0123456789";
    
    /**
     * Combine the lower-case letters and numbers to form the data pool for the random string generation.
     */
    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + NUMBER;
    
    /**
     * SecureRandom is used for cryptographically strong random number generation.
    */
    private static final SecureRandom random = new SecureRandom();

    /**
     * Generates a random alphanumeric string of the specified length.
     * The string is composed of lower-case English letters and digits.
     *
     * @param length The desired length of the random string. Must be a positive integer.
     * @return A randomly generated alphanumeric string of the specified length.
     * @throws IllegalArgumentException If the specified length is less than 1.
     */
    public static String generateRandomString(int length) {
        if (length < 1) throw new IllegalArgumentException("Length must be a positive integer");

        // Use the current time in milliseconds as a seed 'salt' to enhance randomness.
        long salt = Instant.now().toEpochMilli();
        // XOR the generated salt with a random long to set as a new seed for SecureRandom.
        random.setSeed(random.nextLong() ^ salt);

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // Choose a random index from the data pool.
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            // Select the character at the random index.
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            // Append the character to the string builder.
            sb.append(rndChar);
        }

        // Convert the string builder to a string and return it.
        return sb.toString();
    }
}
