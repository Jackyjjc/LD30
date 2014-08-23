package com.jackyjjc.ld30.model;

import java.util.Random;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class Circle {

    public static void main(String[] args) {

        Random random = new Random();
        int[][] circles = new int[20][3];

        for(int i = 0; i < 20; i++) {
            int x, y, size;
            do {
                x = random.nextInt(540);
                y = 170 + random.nextInt(370);

                do {
                   size = (int) (50 * random.nextGaussian()) + 50;
                } while(size <= 22 || size >= 85);

            } while (overlap(circles, i, x, y, size));
            circles[i][0] = x;
            circles[i][1] = y;
            circles[i][2] = size;
            System.out.println("{" + x + ", " + y + ", " + size + "},");
        }


    }

    private static boolean overlap(int[][] circles, int upto, int x, int y, int size) {
        boolean overlap = false;
        for (int i = 0; i < upto; i++) {
            int[] a = circles[i];
            if (circleOverlap(a[0], a[1], a[2], x, y, size)) {
                overlap = true;
                break;
            }
        }
        return overlap;
    }

    private static boolean circleOverlap(int x1, int y1, int s1, int x2, int y2, int s2) {
        double dist = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        double r1 = s1/2.0;
        double r2 = s2/2.0;

        return (dist <= (r1 + r2) || dist <= 70);
    }
}
