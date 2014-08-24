package com.jackyjjc.ld30.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.jackyjjc.ld30.model.*;
import com.jackyjjc.ld30.view.Resources;
import com.jackyjjc.ld30.view.SpaceShipSim;

import java.util.HashMap;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class NewRouteDialog {

    private final GameState g;
    private final Planet from;
    private final Planet to;

    private Label errLabel;
    private SelectBox<String> spaceShipSB;
    private HashMap<String, Integer> nameIdMap;
    private Slider shipNumSlider;
    private TextButton confirmBtn;

    private Dialog dialog;

    public NewRouteDialog(final GameState g, final SpaceShipSim sim, final ImageButton selectedPlanet, final ImageButton planet) {
        this.g = g;
        this.from = DataSource.getPlanet(selectedPlanet.getName());
        this.to = DataSource.getPlanet(planet.getName());

        this.dialog = new Dialog("New Route", Resources.getSkin());
        Table t = dialog.getContentTable();
        t.columnDefaults(1).left();
        t.top().left();

        Label l = new Label("Between: ", Resources.getSkin());
        t.add(l);
        l = new Label(from.name
                + "  (p:" + from.population
                + "  t:" + from.travel
                + "  s:" + from.strategic
                + "  b:" + from.business + ")", Resources.getSkin());
        t.add(l).colspan(3);
        t.row().left();

        l = new Label("    ", Resources.getSkin());
        t.add(l);
        l = new Label(to.name
                + "  (p:" + to.population
                + "  t:" + to.travel
                + "  s:" + to.strategic
                + "  b:" + to.business + ")", Resources.getSkin());
        t.add(l).colspan(3);
        t.row();

        l = new Label("Relationship: ", Resources.getSkin());
        t.add(l);
        int relation = DataSource.getRace(from.race).relation[to.race];
        l = new Label(" " + relation, Resources.getSkin());
        if (relation > 0) {
            l.setColor(Color.GREEN);
        } else if (relation < 0) {
            l.setColor(Color.RED);
        }
        t.add(l).left().colspan(2);
        t.row();

        l = new Label("Distance: ", Resources.getSkin());
        t.add(l);
        l = new Label(from.distance[to.id] + " light years", Resources.getSkin());
        t.add(l).colspan(3);
        t.row();

        nameIdMap = new HashMap<>();
        Array<String> shipNames = new Array<>();
        for(SpaceShip ship : DataSource.get().spaceShips) {
            if (g.curPlayer().spaceShips[ship.id] > 0) {
                nameIdMap.put(ship.name, ship.id);
                shipNames.add(ship.name);
            }
        }

        final Label mLabel = new Label("0", Resources.getSkin());
        final Label numShipLabel = new Label("0", Resources.getSkin());

        shipNumSlider = new Slider(0, 0, 1, false, Resources.getSkin());
        if(shipNames.size > 0) {
            shipNumSlider.setRange(0, g.curPlayer().spaceShips[nameIdMap.get(shipNames.first())]);
        }

        spaceShipSB = new SelectBox<>(Resources.getSkin());
        spaceShipSB.setItems(shipNames);
        spaceShipSB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shipNumSlider.setValue(0);
                shipNumSlider.setRange(0, g.curPlayer().spaceShips[nameIdMap.get(spaceShipSB.getSelected())]);
            }
        });


        shipNumSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                numShipLabel.setText(Integer.toString((int) shipNumSlider.getValue()));
                SpaceShip s = DataSource.get().spaceShips[nameIdMap.get(spaceShipSB.getSelected())];
                double num = shipNumSlider.getValue();
                int cost = (int) (s.maintenance * num);

                mLabel.setText(Integer.toString(cost) + " / turn");
                legalCheck();
            }
        });

        l = new Label("Ship: ", Resources.getSkin());
        t.add(l);
        t.add(spaceShipSB);
        t.add(shipNumSlider).width(100).left();
        t.add(numShipLabel).left();
        t.row();

        l = new Label("Setup Cost: ", Resources.getSkin());
        t.add(l);
        l = new Label("" + Route.getSetupCost(from, to), Resources.getSkin());
        if(Route.getSetupCost(from, to) > g.curPlayer().money) {
            l.setColor(Color.RED);
        }
        t.add(l).colspan(3);
        t.row();

        l = new Label("Maintenance: ", Resources.getSkin());
        t.add(l);
        t.add(mLabel).colspan(3);
        t.row();

        errLabel = new Label("", Resources.getSkin());
        errLabel.setColor(Color.RED);
        errLabel.setVisible(false);
        t.add(errLabel).colspan(4).center();
        t.row();

        confirmBtn = new TextButton("Confirm", Resources.getSkin());
        confirmBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(!confirmBtn.isDisabled()) {
                    int size = (int) shipNumSlider.getValue();
                    Route r = g.addRoute(from, to, spaceShipSB.getSelectedIndex(), size);
                    sim.addRoute(r, selectedPlanet, planet, size);
                }
            }
        });
        dialog.button(confirmBtn);
        dialog.button("Cancel");

        legalCheck();

        dialog.setSize(420, 300);
        dialog.setModal(true);
        dialog.setMovable(true);
        dialog.setPosition((600 - dialog.getWidth()) / 2, 150 + (400 - dialog.getHeight()) / 2);
    }

    private void legalCheck() {
        if(!g.isLegalAddRoute(from, to, nameIdMap.get(spaceShipSB.getSelected()), shipNumSlider.getValue())) {
            errLabel.setVisible(true);
            errLabel.setText(GameStrings.values[g.errno]);
            confirmBtn.setDisabled(true);
        } else {
            errLabel.setVisible(false);
            confirmBtn.setDisabled(false);
        }
    }

    public void show(Stage s) {
        s.addActor(dialog);
    }
}
