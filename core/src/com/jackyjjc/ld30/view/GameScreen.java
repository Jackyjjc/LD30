package com.jackyjjc.ld30.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.jackyjjc.ld30.controller.GameScreenController;
import com.jackyjjc.ld30.controller.ReportDialog;
import com.jackyjjc.ld30.controller.WinDialog;
import com.jackyjjc.ld30.model.DataSource;
import com.jackyjjc.ld30.model.GameState;
import com.jackyjjc.ld30.model.Route;


/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class GameScreen implements Screen {

    private Music music;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;

    private GameState model;
    private GameScreenController controller;
    private SpaceShipSim spaceShipSim;

    private Texture actionPanel;
    private Texture background;
    private ImageButton[] planetButtons;

    public GameScreen() {
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        this.music = Gdx.audio.newMusic(Gdx.files.internal("music/music.ogg"));
        this.music.setLooping(true);
        this.music.setVolume(0.1f);
        this.background = new Texture(Gdx.files.internal("sprites/background.jpg"));
        this.actionPanel = Resources.get("actionPanel", Texture.class);

        //create the planets
        createPlanets();
        this.model = new GameState();
        this.spaceShipSim = new SpaceShipSim(planetButtons);
        this.controller = new GameScreenController(model, stage, spaceShipSim);

        ReportDialog reportDialog = new ReportDialog(stage);
        this.model.addListener(reportDialog);

        WinDialog winDialog = new WinDialog(stage);
        this.model.addWinListener(winDialog);

        //create planet detail panel
        Label planetDetail = new Label("", Resources.getSkin());
        planetDetail.setWrap(true);
        planetDetail.setAlignment(Align.left | Align.top);
        ScrollPane sp = new ScrollPane(planetDetail, Resources.getSkin());
        sp.setSize(600, 150);
        sp.setScrollBarPositions(true, true);
        sp.setVisible(false);
        stage.addActor(sp);
        controller.planetDetail = sp;

        ActionPanel rhsPanel = new ActionPanel(model, controller, spaceShipSim, stage, music);
        stage.addActor(rhsPanel.getRootTable());


        Dialog dialog = new Dialog("Goal", Resources.getSkin());
        Table t = dialog.getContentTable();
        Label l = new Label("Connect the galaxy and earn money!" +
                "\nYou need:\n\t - more than 10 routes" +
                "\n\t - more than 3000 passengers in total" +
                "\n\t - more than 100000 money", Resources.getSkin());
        t.add(l).pad(10);
        t.row();
        dialog.button("OK");
        dialog.setModal(true);
        dialog.setModal(true);
        dialog.show(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
            batch.draw(background, 0, 150);
            batch.draw(actionPanel, 600, 0);
        batch.end();

        //draw all the routes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(Route r : model.curPlayer().routes) {
            ImageButton from = planetButtons[r.from.id];
            ImageButton to = planetButtons[r.to.id];
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.line(from.getCenterX(), from.getCenterY(), to.getCenterX(), to.getCenterY());
        }
        shapeRenderer.end();

        spaceShipSim.tick();
        batch.begin();
            spaceShipSim.drawAllShips(batch);
        batch.end();

        stage.act();
        stage.draw();
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
        shapeRenderer.dispose();
        batch.dispose();
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
        style.up = Resources.get(name, SpriteDrawable.class);
        style.down = Resources.get(name + "_checked", SpriteDrawable.class);
        style.over = Resources.get(name + "_checked", SpriteDrawable.class);
        style.checked = Resources.get(name + "_checked", SpriteDrawable.class);

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
