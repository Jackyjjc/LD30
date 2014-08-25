package com.jackyjjc.ld30.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class TitleScreen implements Screen {
    public SpriteBatch batch;
    private LDGame g;
    public TitleScreen(LDGame game) {
        this.batch = new SpriteBatch();
        this.g = game;
        Music m = Resources.get("music", Music.class);
        m.setVolume(0.1f);
        m.play();
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isTouched(Input.Buttons.LEFT)) {
            g.setScreen(new GameScreen());
        }
        batch.begin();
            batch.draw(Resources.get("title", Texture.class), 0, 0);
        batch.end();
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
        batch.dispose();
    }
}
