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

import java.util.List;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class EditRouteDialog {

    private final GameState g;
    private Route curRoute;

    private Label errLabel;
    private SelectBox<String> routeSelBox;
    private Slider shipNumSlider;
    private TextButton confirmBtn;

    private Dialog dialog;

    public EditRouteDialog(final GameState g) {
        this.g = g;
        this.dialog = new Dialog("Edit Route", Resources.getSkin());

        Table t = dialog.getContentTable();

        final List<Route> routes = g.curPlayer().routes;
        Array<String> routeNames = new Array<>();
        for(Route r : routes) {
            routeNames.add(r.from.name + "  <==>  " + r.to.name);
        }
        curRoute = routes.size() > 0 ? routes.get(0) : null;

        routeSelBox = new SelectBox<>(Resources.getSkin());
        routeSelBox.setItems(routeNames);
        routeSelBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                curRoute = routes.get(routeSelBox.getSelectedIndex());
            }
        });
        t.add(routeSelBox);
        t.row();

        //ship selector
        Array<String> shipNames = new Array<>();
        for(SpaceShip s : DataSource.get().spaceShips) {
            shipNames.add(s.name);
        }
        final SelectBox<String> spaceShipSB = new SelectBox<>(Resources.getSkin());
        spaceShipSB.setItems(shipNames);

        shipNumSlider = new Slider(0, 0, 1, false, Resources.getSkin());
        if(shipNames.size > 0) {
            shipNumSlider.setRange(0, g.curPlayer().spaceShips[curRoute.ship.id] + curRoute.numShips);
        }

        final Label numShipLabel = new Label("0", Resources.getSkin());
        final Label mLabel = new Label("0", Resources.getSkin());
        if(shipNames.size > 0) {
            mLabel.setText(curRoute.getMaintenance() + "");
        }

        shipNumSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                numShipLabel.setText(String.valueOf((int)shipNumSlider.getValue()));

                SpaceShip s = DataSource.get().spaceShips[spaceShipSB.getSelectedIndex()];
                double num = shipNumSlider.getValue();
                int cost = (int) (s.maintenance * num);

                mLabel.setText(Integer.toString(cost) + " / turn");
                legalCheck();
            }
        });

        spaceShipSB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (curRoute.ship.id == spaceShipSB.getSelectedIndex()) {
                    shipNumSlider.setValue(curRoute.numShips);
                    shipNumSlider.setRange(0, g.curPlayer().spaceShips[curRoute.ship.id] + curRoute.numShips);
                } else {
                    shipNumSlider.setValue(0);
                    shipNumSlider.setRange(0, g.curPlayer().spaceShips[spaceShipSB.getSelectedIndex()]);
                }
            }
        });

        Label l = new Label("Ship: ", Resources.getSkin());
        t.add(l);
        t.row();
        t.add(spaceShipSB);
        t.add(shipNumSlider).width(100).left();
        t.add(numShipLabel).left();
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

        TextButton deleteBtn = new TextButton("Delete", Resources.getSkin());
        deleteBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                g.deleteRoute(curRoute);
            }
        });

        confirmBtn = new TextButton("Change", Resources.getSkin());
        confirmBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                g.editRoute(curRoute, spaceShipSB.getSelectedIndex(), (int) shipNumSlider.getValue());
            }
        });

        dialog.button(confirmBtn);
        dialog.button(deleteBtn);
        dialog.button("Cancel");

        legalCheck();

        dialog.setSize(420, 300);
        dialog.setModal(true);
        dialog.setMovable(true);
        dialog.setPosition((600 - dialog.getWidth()) / 2, 150 + (400 - dialog.getHeight()) / 2);
    }

    private void legalCheck() {
        if(!g.isLegalEditRoute(curRoute, routeSelBox.getSelectedIndex(), (int) shipNumSlider.getValue())) {
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
