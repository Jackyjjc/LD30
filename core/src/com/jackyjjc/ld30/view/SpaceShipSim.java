package com.jackyjjc.ld30.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.jackyjjc.ld30.model.RNG;

import java.util.LinkedList;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class SpaceShipSim {
    public LinkedList<Ship> ships;

    public SpaceShipSim() {
        this.ships = new LinkedList<>();
    }

    public void addRoute(ImageButton from, ImageButton to, int size) {
        float x1 = from.getCenterX();
        float y1 = from.getCenterY();
        float x2 = to.getCenterX();
        float y2 = to.getCenterY();
        float size1 = from.getWidth() / 8;
        float size2 = to.getWidth() / 8;

        for (int i = 0; i < size; i++) {
            this.ships.add(
                    new Ship(x1 + RNG.rand() * size1, y1 + RNG.rand() * size1,
                             x2 + RNG.rand() * size2, y2 + RNG.rand() * size2,
                             RNG.randInt(i * 10, (Ship.MAX_STEPS + i * 20 / 10))));
        }
    }

    public void tick() {
        for(Ship s : ships) {
            s.tick();
        }
    }

    public void drawAllShips(SpriteBatch batch) {
        for(Ship s : ships) {
            s.draw(batch);
        }
    }
}
