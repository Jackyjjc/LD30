package com.jackyjjc.ld30.view;

import java.util.LinkedList;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class SpaceShipSim {

    public LinkedList<Ship> ships;

    public void drawAllShips() {
        for(Ship s : ships) {
            s.draw();
        }
    }
}
