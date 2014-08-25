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

    public WinDialog(Stage stage) {
        this.stage = stage;
        this.dialog = new Dialog("You win!", Resources.getSkin());
        Table t = dialog.getContentTable();
        Label l = new Label("Congratulation! You won!", Resources.getSkin());
        t.add(l).pad(30);
        t.row();

        dialog.button("Yay!");
        dialog.setModal(true);
        dialog.setMovable(true);
    }

    @Override
    public void notifyWin(GameState g) {
        dialog.show(stage);
    }
}
