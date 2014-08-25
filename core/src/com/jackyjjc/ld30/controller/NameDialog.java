package com.jackyjjc.ld30.controller;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jackyjjc.ld30.model.GameState;
import com.jackyjjc.ld30.view.Resources;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class NameDialog {

    private GameState g;
    public NameDialog(GameState g) {
        this.g = g;
    }

    public Dialog makeDialog(final Stage stage) {
        final Dialog dialog = new Dialog("Enter Your Name", Resources.getSkin());
        Table t = dialog.getContentTable();
        Label l = new Label("Please enter your name: ", Resources.getSkin());
        t.add(l);
        t.row();

        final TextField text = new TextField("", Resources.getSkin());
        t.add(text).width(300);
        t.row();

        TextButton confirmBtn = new TextButton("Confirm", Resources.getSkin());
        confirmBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                g.setPlayerName(text.getText());
                dialog.hide();
                IntroDialog introDialog = new IntroDialog();
                introDialog.show(stage);
            }
        });

        dialog.getButtonTable().add(confirmBtn).width(80).center();
        dialog.setSize(300, 200);
        dialog.setModal(true);
        dialog.setMovable(false);

        return dialog;
    }

    public void show(Stage stage) {
        makeDialog(stage).show(stage);
    }
}
