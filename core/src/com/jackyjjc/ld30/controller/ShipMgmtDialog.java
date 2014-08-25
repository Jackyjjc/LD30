package com.jackyjjc.ld30.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.jackyjjc.ld30.model.DataSource;
import com.jackyjjc.ld30.model.GameState;
import com.jackyjjc.ld30.model.GameStrings;
import com.jackyjjc.ld30.model.SpaceShip;
import com.jackyjjc.ld30.view.Resources;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class ShipMgmtDialog {

    private Dialog dialog;
    private GameState g;

    public ShipMgmtDialog(final GameState g) {
        this.g = g;
    }

    private Dialog makeDialog() {
        this.dialog = new Dialog("Manage Ships", Resources.getSkin());
        Table rootTable = dialog.getContentTable();

        Array<String> modelList = new Array<>();
        for(SpaceShip s : DataSource.get().spaceShips) {
            modelList.add(s.name);
        }

        Label ownLabel = new Label("Your ships: ", Resources.getSkin());
        rootTable.add(ownLabel).colspan(4).left();
        rootTable.row();

        final SelectBox<String> modelSelBox = new SelectBox<>(Resources.getSkin());
        modelSelBox.setItems(modelList);
        rootTable.add(modelSelBox);

        final TextField text = new TextField("0", Resources.getSkin());
        text.setText("" + g.curPlayer().spaceShips[0]);
        rootTable.add(text);

        TextButton addBtn = new TextButton("+", Resources.getSkin());
        TextButton subBtn = new TextButton("-", Resources.getSkin());

        rootTable.add(addBtn).width(30);
        rootTable.add(subBtn).width(30);
        rootTable.row();

        final Label detailLabel = new Label("", Resources.getSkin());
        updateDetail(detailLabel, 0);
        detailLabel.setWrap(true);
        detailLabel.setAlignment(Align.left | Align.top);
        ScrollPane sp = new ScrollPane(detailLabel, Resources.getSkin());
        rootTable.add(sp).colspan(4).width(400).height(180);
        rootTable.row();

        modelSelBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(modelSelBox.getSelected() == null) {
                    return;
                }
                text.setText("" + g.curPlayer().spaceShips[modelSelBox.getSelectedIndex()]);
                updateDetail(detailLabel, modelSelBox.getSelectedIndex());
            }
        });

        Label label = new Label("Gain/Lost: ", Resources.getSkin());
        rootTable.add(label);
        final Label priceLabel = new Label("0", Resources.getSkin());
        rootTable.add(priceLabel).colspan(3);
        rootTable.row();

        final Label errLabel = new Label("", Resources.getSkin());
        errLabel.setColor(Color.RED);
        errLabel.setVisible(false);
        rootTable.add(errLabel);

        addBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(modelSelBox.getSelected() == null) {
                    return;
                }
                int num;
                try {
                    num = Integer.parseInt(text.getText());
                } catch (NumberFormatException e) {
                    errLabel.setText("Amount is not a number.");
                    errLabel.setVisible(true);
                    return;
                }
                num = num + 1;
                text.setText(num + "");
                updatePrice(g, priceLabel, modelSelBox.getSelectedIndex(), num);
            }
        });

        subBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(modelSelBox.getSelected() == null) {
                    return;
                }
                int num;
                try {
                    num = Integer.parseInt(text.getText());
                } catch (NumberFormatException e) {
                    errLabel.setText("Amount is not a number.");
                    errLabel.setVisible(true);
                    return;
                }
                num = Math.max(num - 1, 0);
                text.setText(num + "");
                updatePrice(g, priceLabel, modelSelBox.getSelectedIndex(), num);
            }
        });

        TextButton confirmBtn = new TextButton("Confirm", Resources.getSkin());
        confirmBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(modelSelBox.getSelected() == null) {
                    return;
                }
                int shipId = modelSelBox.getSelectedIndex();
                int newAmount;
                try {
                    newAmount = Integer.parseInt(text.getText());
                } catch (NumberFormatException e) {
                    errLabel.setText("Amount is not a number.");
                    errLabel.setVisible(true);
                    return;
                }
                if(!g.isLegalShipMgmt(shipId, newAmount)) {
                    errLabel.setText(GameStrings.values[g.errno]);
                    errLabel.setVisible(true);
                } else {
                    errLabel.setVisible(false);
                    g.setShipAmount(shipId, newAmount);
                }
                updatePrice(g, priceLabel, shipId, newAmount);
            }
        });
        dialog.getButtonTable().add(confirmBtn);
        dialog.button("Cancel");

        dialog.setSize(440, 368);
        dialog.setModal(false);
        dialog.setMovable(true);
        dialog.setPosition((600 - dialog.getWidth()) / 2, 150 + (400 - dialog.getHeight()) / 2);

        return dialog;
    }

    public void show(Stage s) {
        s.addActor(makeDialog());
    }

    private void updateDetail(Label detail, int shipId) {
        SpaceShip ship = DataSource.get().spaceShips[shipId];
        detail.setText(ship.name
                + "\n" + ship.description
                + "\n\nCapacity: " + ship.capacity
                + "\nComfortability: " + ship.comfortability
                + "\nPrice: " + ship.price
                + "\nRange: " + ship.range
                + "\nMaintenance Cost: " + ship.maintenance
                + "\nManufacturer: " + ship.manufacturer);
    }

    public void updatePrice(GameState g, Label priceLabel, int shipId, int newAmount) {
        int currentAmount = g.curPlayer().spaceShips[shipId];
        int delta = newAmount - currentAmount;
        SpaceShip ship = DataSource.get().spaceShips[shipId];
        int deltaPrice = - (ship.price * delta);

        priceLabel.setText(deltaPrice + "");
        if(deltaPrice > 0) {
            priceLabel.setText("+" + priceLabel.getText());
            priceLabel.setColor(Color.GREEN);
        } else if (deltaPrice == 0) {
            priceLabel.setColor(priceLabel.getStyle().fontColor);
        } else {
            priceLabel.setColor(Color.RED);
        }
    }
}
