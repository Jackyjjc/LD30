package com.jackyjjc.ld30.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class Player {
    private static final int START_MONEY = 20000;
    private static final int BASE_RESERACH_RATE = 3;

    public String name;
    public int money;
    public int researchRate;
    public List<Route> routes;
    public int[] spaceShips;

    public Player(String name) {
        this.name = name;
        this.money = START_MONEY;
        this.researchRate = BASE_RESERACH_RATE;

        this.routes = new ArrayList<>();
        this.spaceShips = new int[DataSource.get().spaceShips.length];
        this.spaceShips[0] = 20;
        this.spaceShips[5] = 20;
        this.spaceShips[9] = 20;
    }
}
