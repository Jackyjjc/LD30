package com.jackyjjc.ld30.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.jackyjjc.ld30.model.DataSource;
import com.jackyjjc.ld30.model.GameState;
import com.jackyjjc.ld30.model.Race;
import com.jackyjjc.ld30.view.Resources;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class RaceDialog {

    private GameState g;
    public RaceDialog(GameState g) {
        this.g = g;
    }

    public Dialog makeDialog() {
        Dialog dialog = new Dialog("Races", Resources.getSkin());

        Table t = dialog.getContentTable();

        Array<String> raceNames = new Array<>();
        for(Race r : DataSource.get().races) {
            raceNames.add(r.name);
        }

        final SelectBox<String> raceSelBox = new SelectBox<String>(Resources.getSkin());
        raceSelBox.setItems(raceNames);
        t.add(raceSelBox).width(400);
        t.row();

        final Label textLabel = new Label("", Resources.getSkin());
        showRace(textLabel, 0);
        textLabel.setWrap(true);

        ScrollPane sp = new ScrollPane(textLabel, Resources.getSkin());
        t.add(sp).width(400).height(180);
        t.row();

        raceSelBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(raceSelBox.getSelected() == null) {
                    return;
                }
                showRace(textLabel, raceSelBox.getSelectedIndex());
            }
        });

        dialog.button("OK");
        dialog.setSize(420, 368);
        dialog.setModal(false);
        dialog.setMovable(true);
        dialog.setPosition((600 - dialog.getWidth()) / 2, 150 + (400 - dialog.getHeight()) / 2);
        return dialog;
    }

    public void showRace(Label label, int index) {
        Race r = DataSource.getRace(index);
        label.setText(r.name +
                "\n\n" + r.description);
    }

    public void show(Stage stage) {
        makeDialog().show(stage);
    }
}
