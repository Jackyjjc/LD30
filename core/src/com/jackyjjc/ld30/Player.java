package com.jackyjjc.ld30;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class Player {
    private static final int START_MONEY = 2000;
    private static final int BASE_RESERACH_RATE = 3;

    String name;
    int money;
    int researchRate;

    public Player(String name) {
        this.name = name;
        this.money = START_MONEY;
        this.researchRate = BASE_RESERACH_RATE;
    }
}
