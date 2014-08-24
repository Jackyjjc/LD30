package com.jackyjjc.ld30.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class Ship {
    public static final int MAX_STEPS = 800;
    private static final int STEP = 1;

    private float dx;
    private float dy;
    private int progress;
    private boolean back;

    private Sprite sprite;

    public Ship(float x1, float y1, float x2, float y2, int delay) {
        this.sprite = new Sprite(Resources.get("ship", Texture.class));
        this.sprite.setSize(10, 10);
        this.sprite.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        this.sprite.setPosition(x1, y1);
        this.sprite.setRotation((float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1)) - 90);

        this.dx = (STEP / ((float)MAX_STEPS)) * (x2 - x1);
        this.dy = (STEP / ((float)MAX_STEPS)) * (y2 - y1);

        this.progress = -delay;
        this.back = false;
    }

    public void draw(SpriteBatch batch) {
        //System.out.println("draw");
        this.sprite.draw(batch);
    }

    public void tick() {
        if(progress >= MAX_STEPS) {
            progress = 0;
            back = !back;
            dx = -dx;
            dy = -dy;

            float degree = this.sprite.getRotation();
            degree = (degree + 180) % 360;
            this.sprite.setRotation(degree);
        }

        if (progress >= 0) {
            float x = sprite.getX();
            float y = sprite.getY();
            this.sprite.setPosition(x + dx, y + dy);
        }

        progress += STEP;
    }
}
