package com.jackyjjc.ld30.view;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jackyjjc.ld30.controller.*;
import com.jackyjjc.ld30.model.GameState;
import com.jackyjjc.ld30.model.GameUpdateListener;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class ActionPanel implements GameUpdateListener {

    private Table table;
    private Label moneyLabel;
    private Label numRouteLabel;
    private Label numPassLabel;

    public ActionPanel(final GameState model, final GameScreenController controller, final SpaceShipSim sim, final Stage stage, final Music music) {
        //creating rhs panel
        table = new Table();
        table.top();
        table.setSize(200, 600);
        table.setPosition(600, 0);
        //table.setDebug(true);

        Label l = new Label(model.curPlayer().name, Resources.getSkin());
        table.add(l).top().left().width(200).height(50).colspan(2).padLeft(15);
        table.row();

        l = new Label("Money: ", Resources.getSkin());
        table.add(l).padLeft(5);
        moneyLabel = new Label(model.curPlayer().money + "", Resources.getSkin());
        table.add(moneyLabel);
        table.row();

        l = new Label("Routes: ", Resources.getSkin());
        table.add(l).padLeft(5);
        numRouteLabel = new Label(model.curPlayer().routes.size() + "", Resources.getSkin());
        table.add(numRouteLabel);
        table.row();

        l = new Label("Passengers: ", Resources.getSkin());
        table.add(l).padLeft(5);
        numPassLabel = new Label(model.curPlayer().totalPass + "", Resources.getSkin());
        table.add(numPassLabel);
        table.row();

        l = new Label("Actions: ", Resources.getSkin());
        table.add(l).colspan(2).left().padTop(10).padLeft(15);
        table.row();

        final TextButton buildRouteBtn = new TextButton("Build Route", Resources.getSkin());
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(buildRouteBtn.getStyle());
        style.checked = style.down;
        buildRouteBtn.setStyle(style);
        buildRouteBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.inBuildMode = !controller.inBuildMode;
                buildRouteBtn.setChecked(controller.inBuildMode);
            }
        });
        controller.buildRouteBtn = buildRouteBtn;
        table.add(buildRouteBtn).colspan(2).center().width(100).padTop(15);
        table.row();

        TextButton editRouteBtn = new TextButton("Edit Route", Resources.getSkin());
        editRouteBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                EditRouteDialog dialog = new EditRouteDialog(model, sim);
                dialog.show(stage);
            }
        });
        table.add(editRouteBtn).colspan(2).center().width(100).padTop(15);
        table.row();

        TextButton shipBtn = new TextButton("Spaceships", Resources.getSkin());
        shipBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ShipMgmtDialog dialog = new ShipMgmtDialog(model);
                dialog.show(stage);
            }
        });
        table.add(shipBtn).colspan(2).center().padTop(15).width(100);
        table.row();

        TextButton raceBtn = new TextButton("Races", Resources.getSkin());
        raceBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                RaceDialog dialog = new RaceDialog(model);
                dialog.show(stage);
            }
        });
        table.add(raceBtn).colspan(2).center().padTop(15).width(100);
        table.row();

        TextButton endTurnBtn = new TextButton("End Turn", Resources.getSkin());
        endTurnBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                model.endTurn();
            }
        });
        table.add(endTurnBtn).colspan(2).center().padTop(15).width(100);
        table.row();

        TextButton helpBtn = new TextButton("Help", Resources.getSkin());
        helpBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                IntroDialog dialog = new IntroDialog();
                dialog.show(stage);
            }
        });
        table.add(helpBtn).colspan(2).center().padTop(40).width(100);
        table.row();

        final TextButton musicBtn = new TextButton("Music: Off", Resources.getSkin());
        style = new TextButton.TextButtonStyle(buildRouteBtn.getStyle());
        style.checked = style.down;
        musicBtn.setStyle(style);
        musicBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(!music.isPlaying()) {
                    music.play();
                    musicBtn.setText("Music: On");
                } else {
                    music.pause();
                    musicBtn.setText("Music: Off");
                }
            }
        });
        table.add(musicBtn).padTop(15).colspan(2).center().width(100);
        table.row();

        l = new Label("Volume: ", Resources.getSkin());
        table.add(l).width(20).padTop(15).left().padLeft(15);
        final Slider slider = new Slider(0, 0.8f, 0.01f, false, Resources.getSkin());
        slider.setValue(music.getVolume());
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                music.setVolume(slider.getValue());
            }
        });
        table.add(slider).width(100).left().padLeft(-20).padTop(15);
        table.row();

        model.addListener(this);
    }

    public Table getRootTable() {
        return table;
    }

    @Override
    public void notifyUpdate(GameState g) {
        moneyLabel.setText("" + g.curPlayer().money);
        numRouteLabel.setText("" + g.curPlayer().routes.size());
        numPassLabel.setText("" + g.curPlayer().totalPass);
    }
}
