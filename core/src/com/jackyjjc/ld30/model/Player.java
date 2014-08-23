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

    public Player(String name) {
        this.name = name;
        this.money = START_MONEY;
        this.researchRate = BASE_RESERACH_RATE;

        this.routes = new ArrayList<Route>();
    }
}
