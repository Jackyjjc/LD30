package com.jackyjjc.ld30.model;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class GameStrings {
    public static final int ERR_ROUTE_EXIST = 0;
    public static final int ERR_INSUF_FUND = 1;
    public static final int ERR_NO_SHIP = 2;
    public static final int ERR_SHIP_NOT_SUIT = 3;

    public static String[] values = new String[] {
        "Route Existed.",
        "Insufficient Fund.",
        "Did not assign enough spaceships.",
        "This type of the spaceship does not have enough range.",
    };
}
