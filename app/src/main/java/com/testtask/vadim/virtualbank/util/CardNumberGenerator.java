package com.testtask.vadim.virtualbank.util;

public class CardNumberGenerator {
    private static final long MINIMUM = 1_000_000_000_000_000L;
    private static final long MAXIMUM = 9_999_999_999_999_999L;

    public static String generate() {
        long newNumber = MINIMUM + (long) (Math.random() * (MAXIMUM - MINIMUM));

        return String.valueOf(newNumber);
    }
}
