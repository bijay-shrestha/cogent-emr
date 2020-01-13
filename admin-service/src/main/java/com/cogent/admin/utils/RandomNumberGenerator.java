package com.cogent.admin.utils;

import java.util.Random;
import java.util.UUID;

public class RandomNumberGenerator {

    private static int[] sizeTable = {0, 9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};

    private static Random random = new Random();

    public static String generateRandomNumber(int digit) {
        return String.valueOf(random.nextInt(sizeTable[digit] + 1));
    }

    public static String generateRandomToken() {
        return UUID.randomUUID().toString();
    }

}
