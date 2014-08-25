package com.jackyjjc.ld30.controller;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jackyjjc.ld30.model.GameState;
import com.jackyjjc.ld30.model.GameUpdateListener;
import com.jackyjjc.ld30.view.Resources;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class ReportDialog implements GameUpdateListener {
    private int lastTurn = 0;
    private Dialog dialog;

    private Label earnLabel;
    private Label lostLabel;

    private Stage stage;

    public ReportDialog(Stage stage) {
        this.stage = stage;

        dialog = new Dialog("Turn Report", Resources.getSkin());
        Table t = dialog.getContentTable();

        Label l = new Label("In last turn: ", Resources.getSkin());
        t.add(l).left().colspan(2);
        t.row();

        l = new Label("You earned: ", Resources.getSkin());
        t.add(l).width(100);
        earnLabel = new Label("0", Resources.getSkin());
        t.add(earnLabel).width(100);
        t.row();

        l = new Label("You paid: ", Resources.getSkin());
        t.add(l).width(100);
        lostLabel = new Label("0", Resources.getSkin());
        t.add(lostLabel).width(100);
        t.row();

        dialog.button("OK");
        dialog.setSize(600, 400);
        dialog.setModal(true);
        dialog.setMovable(true);
    }

    @Override
    public void notifyUpdate(GameState g) {
        if(g.turnNum == lastTurn) {
            return;
        }

        if(g.turnNum < lastTurn) {
            //disaster.wat?
            lastTurn = g.turnNum;
            return;
        }

        earnLabel.setText(g.curPlayer().lastEarn + "");
        lostLabel.setText(g.curPlayer().lastPaid + "");

        dialog.show(stage);
    }
}
