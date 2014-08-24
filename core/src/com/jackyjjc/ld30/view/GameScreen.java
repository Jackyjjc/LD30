package com.jackyjjc.ld30.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jackyjjc.ld30.controller.GameScreenController;
import com.jackyjjc.ld30.model.DataSource;
import com.jackyjjc.ld30.model.GameState;
import com.jackyjjc.ld30.model.Route;


/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class GameScreen implements Screen {

    private Stage stage;
    private ShapeRenderer shapeRenderer;

    private GameState model;
    private GameScreenController controller;

    private ImageButton[] planetButtons;

    public GameScreen() {
        this.shapeRenderer = new ShapeRenderer();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        this.model = new GameState();
        this.controller = new GameScreenController(model, stage);

        Image image = new Image(new Texture(Gdx.files.internal("sprites/background.jpg")));
        image.setPosition(0, 150);
        stage.addActor(image);

        //create the planets
        createPlanets();

        //create planet detail panel
        Label planetDetail = new Label("", Resources.getSkin());
        planetDetail.setWrap(true);
        ScrollPane sp = new ScrollPane(planetDetail, Resources.getSkin());
        sp.setSize(600, 150);
        sp.setScrollBarPositions(true, true);
        sp.setVisible(false);
        stage.addActor(sp);
        controller.planetDetail = sp;

        ActionPanel rhsPanel = new ActionPanel(model, controller);
        stage.addActor(rhsPanel.getRootTable());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        //draw all the routes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(Route r : model.curPlayer().routes) {
            ImageButton from = planetButtons[r.from.id];
            ImageButton to = planetButtons[r.to.id];
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.line(from.getCenterX(), from.getCenterY(), to.getCenterX(), to.getCenterY());
        }
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        this.stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }

    private void createPlanets() {
        this.planetButtons = new ImageButton[DataSource.get().planets.length];
        int[][] locations = new int[][] {
                {189, 256, 70},
                {16, 381, 47},
                {400, 520, 70},
                {436, 172, 39},
                {110, 467, 35},
                {30, 490, 83},
                {314, 339, 60},
                {257, 516, 48},
                {93, 342, 76},
                {317, 422, 30},
                {419, 250, 58},
                {518, 184, 79},
                {350, 237, 44},
                {470, 322, 52},
                {524, 516, 24},
                {274, 226, 40},
                {453, 440, 83},
                {224, 389, 36},
                {82, 192, 42},
                {531, 413, 47},
        };

        for(int i = 0; i < DataSource.get().planets.length; i++) {
            this.planetButtons[i] = createPlanetButton(i,
                    locations[i][0], locations[i][1], locations[i][2]);
            stage.addActor(planetButtons[i]);
        }
    }

    private ImageButton createPlanetButton(int id, int x, int y, int size) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        String name = "planet" + id;
        style.up = Resources.getSpriteDrawable(name);
        style.down = Resources.getSpriteDrawable(name + "_checked");
        style.over = Resources.getSpriteDrawable(name + "_checked");
        style.checked = Resources.getSpriteDrawable(name + "_checked");

        final ImageButton planet = new ImageButton(style);
        planet.setName(String.valueOf(id));
        planet.setSize(size, size);
        planet.setPosition(x, y);
        planet.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.selectPlanet(planet);
            }
        });

        return planet;
    }
}
