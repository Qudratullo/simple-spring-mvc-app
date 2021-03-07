package com.qudratullo.springprojects.service;

import java.util.Random;

public class Generator {

    private Generator() {
    }

    private static int randomInt(int min, int max) {
        if (max < min) {
            return 0;
        }
        return new Random().nextInt(max - min + 1) + min;
    }

    public static String randomString() {
        return randomString(randomInt(5, 10));
    }

    public static String randomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            buffer.append((char) randomInt(leftLimit, rightLimit));
        }
        return buffer.toString();
    }
}
