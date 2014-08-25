package com.jackyjjc.ld30.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.text.DecimalFormat;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class LoadScreen implements Screen {

    private static final String LOADING_TEXT = "Loading";
    private final DecimalFormat format;
    private String loadingStr;
    private int numDots;

    private SpriteBatch batch;
    private BitmapFont font;

    private float x;
    private float y;

    private LDGame g;

    public LoadScreen(LDGame game) {
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.g = game;

        this.numDots = 1;
        this.format = new DecimalFormat("#0.00");
        this.loadingStr = LOADING_TEXT;

        BitmapFont.TextBounds t = getTextBound(LOADING_TEXT + "   100.00%...");
        this.x = mid(Gdx.graphics.getWidth(),  t.width);
        this.y =  mid(Gdx.graphics.getHeight(), t.height);
    }

    @Override
    public void render(float delta) {
        /*Load all the sprite and external resources first*/
        float percentage = Resources.load() * 100;

        if(percentage >= 100) {
            g.setScreen(new TitleScreen(g));
        }

        this.loadingStr = LOADING_TEXT;
        this.loadingStr += "   " + format.format(percentage) + "%";
        for (int i = 0; i < numDots; i++) {
            loadingStr += ".";
        }
        this.numDots = (numDots == 3) ? 1 : (numDots + 1);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
            font.draw(batch, loadingStr, x, y);
        batch.end();

        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
    }

    private BitmapFont.TextBounds getTextBound(String text) {
        return this.font.getBounds(text);
    }

    private float mid(float container, float self) {
        return (container - self) / 2;
    }
}
