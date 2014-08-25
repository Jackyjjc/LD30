package com.jackyjjc.ld30.controller;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jackyjjc.ld30.model.GameState;
import com.jackyjjc.ld30.model.WinListener;
import com.jackyjjc.ld30.view.Resources;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class WinDialog implements WinListener {
    private Dialog dialog;
    private Stage stage;

    private Label summaryL;

    public WinDialog(Stage stage) {
        this.stage = stage;
        this.dialog = new Dialog("You win!", Resources.getSkin());
        Table t = dialog.getContentTable();
        Label l = new Label("Congratulations!", Resources.getSkin());
        t.add(l).padTop(20).padLeft(20).padRight(20).left();
        t.row();
        summaryL = new Label("", Resources.getSkin());
        t.add(summaryL).padBottom(20).left().padLeft(20).padRight(20);
        t.row();

        dialog.button("Yay!");
        dialog.setModal(true);
        dialog.setMovable(true);
    }

    @Override
    public void notifyWin(GameState g) {
        summaryL.setText("You beat the game in " + g.turnNum + " turns!" +
            "\n\nYour Records:" +
            "\b\nMax #Passengers: " + g.curPlayer().maxPass +
            "\nMax #Routes: " +g.curPlayer().maxRoute +
            "\nMax Profit per turn: " + g.curPlayer().maxProfit);
        dialog.show(stage);
    }
}
