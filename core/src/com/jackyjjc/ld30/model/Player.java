package com.jackyjjc.ld30.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class Player {
    private static final int START_MONEY = 2000;
    private static final int BASE_RESERACH_RATE = 3;

    public String name;
    public int money;
    public int researchRate;
    public List<Route> routes;
    public List<Tuple<SpaceShip, Integer>> spaceShips;

    public Player(String name) {
        this.name = name;
        this.money = START_MONEY;
        this.researchRate = BASE_RESERACH_RATE;

        this.routes = new ArrayList<>();
        this.spaceShips = new ArrayList<>();
        this.spaceShips.add(new Tuple<>(DataSource.get().spaceShips[0], 20));
        this.spaceShips.add(new Tuple<>(DataSource.get().spaceShips[1], 10));
        this.spaceShips.add(new Tuple<>(DataSource.get().spaceShips[2], 10));
        this.spaceShips.add(new Tuple<>(DataSource.get().spaceShips[3], 10));
        this.spaceShips.add(new Tuple<>(DataSource.get().spaceShips[4], 10));
        this.spaceShips.add(new Tuple<>(DataSource.get().spaceShips[5], 10));
        this.spaceShips.add(new Tuple<>(DataSource.get().spaceShips[6], 10));
        this.spaceShips.add(new Tuple<>(DataSource.get().spaceShips[7], 10));
        this.spaceShips.add(new Tuple<>(DataSource.get().spaceShips[8], 10));
        this.spaceShips.add(new Tuple<>(DataSource.get().spaceShips[9], 10));
    }
}
