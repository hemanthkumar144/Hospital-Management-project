package com.crimsonlogic.hospitalmanagement.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class for generating random IDs.
 *
 * Examples:
 * PAT5832
 * DOC9147
 * BIL2468
 * PAY7315
 */
public class IdGenerator {

    /**
     * Generates a random ID with a prefix
     * followed by 4 random digits.
     *
     * @param prefix Prefix of entity
     * @return Generated ID
     */
    public static String generateRandomId(String prefix) {

        if (prefix == null || prefix.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Prefix cannot be empty");
        }

        int number =
                ThreadLocalRandom.current()
                        .nextInt(100000, 1000000);

        return prefix + number;
    }
}