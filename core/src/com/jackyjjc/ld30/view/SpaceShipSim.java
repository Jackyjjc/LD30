package com.jackyjjc.ld30.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.jackyjjc.ld30.model.RNG;
import com.jackyjjc.ld30.model.Route;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class SpaceShipSim {
    private HashMap<Route, LinkedList<Ship>> map;
    private ImageButton[] buttons;

    public SpaceShipSim(ImageButton[] buttons) {
        this.buttons = buttons;
        this.map = new HashMap<>();
    }

    public void addRoute(Route route, ImageButton from, ImageButton to, int size) {
        LinkedList<Ship> ships = new LinkedList<>();
        map.put(route, ships);
        addShips(from, to, ships, size);
    }
    public void addShips(ImageButton from, ImageButton to, LinkedList<Ship> ships, int size) {
        float x1 = from.getCenterX();
        float y1 = from.getCenterY();
        float x2 = to.getCenterX();
        float y2 = to.getCenterY();
        float size1 = from.getWidth() / 8;
        float size2 = to.getWidth() / 8;

        for (int i = 0; i < size; i++) {
            ships.add(new Ship(x1 + RNG.rand() * size1, y1 + RNG.rand() * size1,
                    x2 + RNG.rand() * size2, y2 + RNG.rand() * size2,
                    RNG.randInt(i * 10, (Ship.MAX_STEPS + i * 20 / 10))));
        }
    }


    public void addShips(Route route, LinkedList<Ship> ships, int size) {
        ImageButton from = buttons[route.from.id];
        ImageButton to = buttons[route.to.id];
        addShips(from, to, ships, size);
    }

    public void tick() {
        for(LinkedList<Ship> l : map.values()) {
            for(Ship s : l) {
                s.tick();;
            }
        }
    }

    public void drawAllShips(SpriteBatch batch) {
        for(LinkedList<Ship> l : map.values()) {
            for(Ship s : l) {
                s.draw(batch);
            }
        }
    }

    public void editRoute(Route curRoute, int newAmount) {
        LinkedList<Ship> ships = map.get(curRoute);
        changeAmount(curRoute, ships, newAmount);
    }

    public void deleteRoute(Route curRoute) {
        this.map.remove(curRoute);
    }

    private void changeAmount(Route route, LinkedList<Ship> ships, int newAmount) {
        int diff = newAmount - ships.size();

        if(diff == 0) {
            return;
        } else if (diff > 0) {
            addShips(route, ships, diff);
        } else {
            for(int i = 0; i < -diff; i++) {
                ships.removeFirst();
            }
        }
    }
}
