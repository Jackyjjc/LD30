package com.jackyjjc.ld30.model;

import java.util.Random;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class RNG {
    private static Random random = new Random();

    public static int randInt(int min, int max) {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return random.nextInt((max - min) + 1) + min;
    }

    public static float rand() {
        return (float) random.nextGaussian();
    }
}
