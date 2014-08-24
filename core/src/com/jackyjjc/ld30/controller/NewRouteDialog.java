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

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class NewRouteDialog {

    private final GameState g;
    private final Planet from;
    private final Planet to;

    private Label errLabel;
    private Slider shipNumSlider;
    private TextButton confirmBtn;

    private Dialog dialog;

    public NewRouteDialog(final GameState g, final Planet from, final Planet to) {
        this.g = g;
        this.from = from;
        this.to = to;

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

        Array<String> shipNames = new Array<>();
        for(Tuple<SpaceShip, Integer> ship : g.curPlayer().spaceShips) {
            shipNames.add(ship._1.name);
        }

        final MaintainLabel mLabel = new MaintainLabel(g);
        final Label numShipLabel = new Label("0", Resources.getSkin());

        shipNumSlider = new Slider(0, 0, 1, false, Resources.getSkin());
        if(shipNames.size > 0) {
            shipNumSlider.setRange(0, g.curPlayer().spaceShips.get(0)._2);
        }

        shipNumSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                numShipLabel.setText(Integer.toString((int) shipNumSlider.getValue()));
                mLabel.update();
                legalCheck();
            }
        });

        final SelectBox<String> spaceShipSB = new SelectBox<>(Resources.getSkin());
        spaceShipSB.setItems(shipNames);
        spaceShipSB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                shipNumSlider.setValue(0);
                shipNumSlider.setRange(0, g.curPlayer().spaceShips.get(spaceShipSB.getSelectedIndex())._2);
            }
        });

        mLabel.setSlider(shipNumSlider);
        mLabel.setSelectBox(spaceShipSB);

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
                    g.addRoute(from, to, spaceShipSB.getSelectedIndex(), (int) shipNumSlider.getValue());
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
        if(!g.isLegalRoute(from, to, shipNumSlider.getValue())) {
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

    private static class MaintainLabel extends Label {
        private GameState g;
        private SelectBox<String> sb;
        private Slider slider;

        public MaintainLabel(GameState g) {
            super("0", Resources.getSkin());
            this.g = g;
        }

        public void setSelectBox(SelectBox<String> sb) {
            this.sb = sb;
        }

        public void setSlider(Slider slider) {
            this.slider = slider;
        }

        public void update() {
            SpaceShip s = g.curPlayer().spaceShips.get(sb.getSelectedIndex())._1;
            double num = slider.getValue();
            int cost = (int) (s.maintenance * num);

            this.setText(Integer.toString(cost) + " / turn");
        }
    }
}
