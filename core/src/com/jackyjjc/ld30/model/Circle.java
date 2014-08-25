package com.jackyjjc.ld30.model;

import java.util.Random;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class Circle {

    public static void main(String[] args) {

        genAdjMatrix();

    }

    private static void genAdjMatrix() {
        int[][] planets = new int[][] {
                {189, 256, 70},
                {16, 381, 47},
                {400, 520, 70},
                {436, 172, 39},
                {110, 467, 35},
                {30, 490, 83},
                {314, 339, 60},
                {257, 516, 48},
                {93, 342, 76},
                {317, 422, 30},
                {419, 250, 58},
                {518, 184, 79},
                {350, 237, 44},
                {470, 322, 52},
                {524, 516, 24},
                {274, 226, 40},
                {453, 440, 83},
                {224, 389, 36},
                {82, 192, 42},
                {531, 413, 47},
        };

        int[][] adjMatrix = new int[20][20];

        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                double ed = edist(planets[i][0], planets[i][1], planets[j][0], planets[j][1]);
                double r = (planets[i][2] + planets[j][2]) / 2.0;
                double result = ed == 0 ? ed : ed - r;

                adjMatrix[i][j] = (int) (result / 10.0);
            }
            System.out.print("\"distance\": [");
            for(int j = 0; j < 20; j++) {
                if (j != 19) {
                    System.out.print(adjMatrix[i][j] + ", ");
                } else {
                    System.out.print(adjMatrix[i][j]);
                }
            }
            System.out.println("]");
        }
    }

    private static void genMap() {
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

    private static double edist(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private static boolean circleOverlap(int x1, int y1, int s1, int x2, int y2, int s2) {
        double dist = edist(x1, y1, x2, y2);
        double r1 = s1/2.0;
        double r2 = s2/2.0;

        return (dist <= (r1 + r2) || dist <= 70);
    }
}
