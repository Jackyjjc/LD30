package com.jackyjjc.ld30;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class LDGame extends ApplicationAdapter {
	private Stage stage;
    SpriteBatch batch;
	
	@Override
	public void create () {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        //stage.setDebugAll(true);

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("sprites/ceres.png")));
        Sprite sprite1 = new Sprite(new Texture(Gdx.files.internal("sprites/ceres_c.png")));
        style.up = new SpriteDrawable(sprite);
        style.checked = new SpriteDrawable(sprite1);
        style.down = new SpriteDrawable(sprite1);
        style.over = new SpriteDrawable(sprite1);

        final ImageButton planet = new ImageButton(style);
        planet.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                planet.setChecked(planet.isChecked());
            }
        });
        planet.setSize(80, 80);
        planet.setPosition(153, 234);
        stage.addActor(planet);


        style = new ImageButton.ImageButtonStyle();
        sprite = new Sprite(new Texture(Gdx.files.internal("sprites/sun.png")));
        sprite1 = new Sprite(new Texture(Gdx.files.internal("sprites/sun_c.png")));
        style.up = new SpriteDrawable(sprite);
        style.checked = new SpriteDrawable(sprite1);
        style.down = new SpriteDrawable(sprite1);
        style.over = new SpriteDrawable(sprite1);

        final ImageButton p1 = new ImageButton(style);
        p1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                p1.setChecked(p1.isChecked());
            }
        });
        p1.setSize(80, 80);
        p1.setPosition(50, 180);
        stage.addActor(p1);



        Table table = new Table(skin);
        table.setSize(200, 600);
        table.setPosition(600, 0);

        Label label = new Label("Money: ", skin);
        table.add(label);
        Label labelMoney = new Label("20", skin);
        table.add(labelMoney);
        table.row();

        TextButton button = new TextButton("End Turn", skin);
        table.add(button);
        table.row();

        stage.addActor(table);

        batch = new SpriteBatch();

        DataSource db = DataSource.newInstance();
	}

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        this.stage.getViewport().update(width, height, true);
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

		//batch.begin();
		//batch.end();
	}

    @Override
    public void dispose() {
        super.dispose();
        this.stage.dispose();
        this.batch.dispose();
    }
}
