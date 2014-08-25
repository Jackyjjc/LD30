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

import java.util.List;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class EditRouteDialog {

    private final GameState g;
    private SpaceShipSim sim;
    private Route curRoute;

    private Label errLabel;
    private SelectBox<String> spaceShipSB;
    private Slider shipNumSlider;
    private TextButton confirmBtn;
    private TextButton deleteBtn;

    public EditRouteDialog(final GameState g, final SpaceShipSim sim) {
        this.g = g;
        this.sim = sim;
    }

    private Dialog makeDialog() {
        Dialog dialog = new Dialog("Edit Route", Resources.getSkin());

        Table t = dialog.getContentTable();
        //t.debugAll();

        Label l = new Label("Routes: ", Resources.getSkin());
        t.add(l).colspan(4).left();
        t.row();

        Label lastPassLabel = new Label("#Passengers last turn: ", Resources.getSkin());
        final Label lastPassNum = new Label("0", Resources.getSkin());

        Label lastProfitLabel = new Label("Profit last turn: ", Resources.getSkin());
        final Label lastProfitNum = new Label("0", Resources.getSkin());

        final List<Route> routes = g.curPlayer().routes;
        final Array<String> routeNames = new Array<>();
        for(Route r : routes) {
            routeNames.add(r.from.name + "  <==>  " + r.to.name);
        }
        curRoute = routes.size() > 0 ? routes.get(0) : null;

        final SelectBox<String> routeSelBox = new SelectBox<>(Resources.getSkin());
        routeSelBox.setItems(routeNames);
        t.add(routeSelBox).colspan(3).left();
        t.row();

        //ship selector
        Array<String> shipNames = new Array<>();
        for(SpaceShip s : DataSource.get().spaceShips) {
            shipNames.add(s.name);
        }
        spaceShipSB = new SelectBox<>(Resources.getSkin());
        spaceShipSB.setItems(shipNames);

        shipNumSlider = new Slider(0, 0, 1, false, Resources.getSkin());
        if(curRoute != null) {
            shipNumSlider.setRange(0, g.curPlayer().spaceShips[curRoute.ship.id] + curRoute.numShips);
        }

        final Label numShipLabel = new Label("0", Resources.getSkin());
        final Label mLabel = new Label("0", Resources.getSkin());
        if(curRoute != null) {
            spaceShipSB.setSelectedIndex(curRoute.ship.id);
            numShipLabel.setText(curRoute.numShips + "");
            mLabel.setText(curRoute.getMaintenance() + "");
            lastPassNum.setText(curRoute.lastPass + "");
            lastProfitNum.setText(curRoute.lastProfit + "");
        }

        shipNumSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                numShipLabel.setText(String.valueOf((int)shipNumSlider.getValue()));

                if(spaceShipSB.getSelected() == null) {
                    return;
                }

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
                if(spaceShipSB.getSelected() == null || curRoute == null) {
                    return;
                }

                if (curRoute.ship.id == spaceShipSB.getSelectedIndex()) {
                    shipNumSlider.setValue(curRoute.numShips);
                    shipNumSlider.setRange(0, g.curPlayer().spaceShips[curRoute.ship.id] + curRoute.numShips);
                } else {
                    shipNumSlider.setValue(0);
                    shipNumSlider.setRange(0, g.curPlayer().spaceShips[spaceShipSB.getSelectedIndex()]);
                }
                legalCheck();
            }
        });

        routeSelBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(routeSelBox.getSelected() == null) {
                    return;
                }

                curRoute = routes.get(routeSelBox.getSelectedIndex());
                spaceShipSB.setSelectedIndex(curRoute.ship.id);
                shipNumSlider.setValue(curRoute.numShips);
                lastPassNum.setText(curRoute.lastPass + "");
                lastProfitNum.setText(curRoute.lastProfit + "");
                legalCheck();
            }
        });

        l = new Label("Ship: ", Resources.getSkin());
        t.add(l).colspan(4).left();
        t.row();
        t.add(spaceShipSB);
        t.add(shipNumSlider).width(100).left();
        t.add(numShipLabel).left();
        t.row();

        l = new Label("Maintenance: ", Resources.getSkin());
        t.add(l);
        t.add(mLabel).colspan(3);
        t.row();

        l = new Label("Price: ", Resources.getSkin());
        t.add(l);
        final TextField text = new TextField("0", Resources.getSkin());
        if(curRoute != null) {
            text.setText(curRoute.price + "");
        }
        text.setDisabled(true);
        t.add(text).width(50);
        TextButton addBtn = new TextButton("+", Resources.getSkin());
        TextButton subBtn = new TextButton("-", Resources.getSkin());

        addBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                int num;
                try {
                    num = Integer.parseInt(text.getText());
                } catch (NumberFormatException e) {
                    errLabel.setText("Price is not a number.");
                    errLabel.setVisible(true);
                    return;
                }
                text.setText((num + 1) + "");
            }
        });

        subBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                int num;
                try {
                    num = Integer.parseInt(text.getText());
                } catch (NumberFormatException e) {
                    errLabel.setText("Price is not a number.");
                    errLabel.setVisible(true);
                    return;
                }
                num = Math.max(0, num - 1);
                text.setText(num + "");
            }
        });

        t.add(addBtn).width(30);
        t.add(subBtn).width(30);
        t.row();

        t.add(lastPassLabel);
        t.add(lastPassNum);
        t.row();

        t.add(lastProfitLabel);
        t.add(lastProfitNum);
        t.row();

        errLabel = new Label("", Resources.getSkin());
        errLabel.setColor(Color.RED);
        errLabel.setVisible(false);
        t.add(errLabel).colspan(4).center();
        t.row();

        deleteBtn = new TextButton("Delete", Resources.getSkin());
        deleteBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(curRoute == null) {
                    return;
                }
                g.deleteRoute(curRoute);
                sim.deleteRoute(curRoute);
            }
        });

        confirmBtn = new TextButton("Change", Resources.getSkin());
        confirmBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(curRoute == null) {
                    return;
                }
                int price;
                try {
                    price = Integer.parseInt(text.getText());
                } catch (NumberFormatException e) {
                    errLabel.setText("Price is not a number.");
                    errLabel.setVisible(true);
                    return;
                }
                int newAmount = (int) shipNumSlider.getValue();
                g.editRoute(curRoute, spaceShipSB.getSelectedIndex(), newAmount, price);
                sim.editRoute(curRoute, newAmount);
            }
        });

        dialog.button(confirmBtn);
        dialog.button(deleteBtn);
        dialog.button("Cancel");

        legalCheck();

        dialog.setSize(420, 370);
        dialog.setModal(true);
        dialog.setKeepWithinStage(false);
        dialog.setMovable(true);
        dialog.setPosition((600 - dialog.getWidth()) / 2, 150 + (400 - dialog.getHeight()) / 2);

        return dialog;
    }

    private void legalCheck() {
        if(curRoute == null || spaceShipSB.getSelected() == null) {
            confirmBtn.setDisabled(true);
            deleteBtn.setDisabled(true);
            return;
        }
        if(!g.isLegalEditRoute(curRoute, spaceShipSB.getSelectedIndex(), (int) shipNumSlider.getValue())) {
            errLabel.setVisible(true);
            errLabel.setText(GameStrings.values[g.errno]);
            confirmBtn.setDisabled(true);
        } else {
            errLabel.setVisible(false);
            confirmBtn.setDisabled(false);
        }
    }

    public void show(Stage s) {
        s.addActor(makeDialog());
    }
}
