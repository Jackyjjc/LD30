package com.jackyjjc.ld30.controller;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.jackyjjc.ld30.view.Resources;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class IntroDialog {
    public Dialog makeDialog() {
        Dialog dialog = new Dialog("Introdution", Resources.getSkin());

        Table t = dialog.getContentTable();

        Array<String> chapters = new Array<>();
        chapters.add("Overview");
        chapters.add("Goal");
        chapters.add("Routes");
        chapters.add("Planets");
        chapters.add("Races");
        chapters.add("SpaceShips");

        final SelectBox<String> selectBox = new SelectBox<String>(Resources.getSkin());
        selectBox.setItems(chapters);
        t.add(selectBox).width(400);
        t.row();

        final Label textLabel = new Label("", Resources.getSkin());
        showChapter(textLabel, 0);
        textLabel.setWrap(true);

        ScrollPane sp = new ScrollPane(textLabel, Resources.getSkin());
        t.add(sp).width(460).height(280);
        t.row();

        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showChapter(textLabel, selectBox.getSelectedIndex());
            }
        });

        final TextButton nextBtn = new TextButton("Next", Resources.getSkin());
        nextBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(selectBox.getSelected() == null) {
                    return;
                }

                int nextIndex = selectBox.getSelectedIndex() + 1;
                if(nextIndex > 5) {
                    return;
                }
                selectBox.setSelectedIndex(nextIndex);
                showChapter(textLabel, nextIndex);
                if(nextIndex == 5) {
                    nextBtn.setVisible(false);
                }
            }
        });

        dialog.getButtonTable().add(nextBtn);
        dialog.button("Close");
        dialog.setModal(false);
        dialog.setMovable(true);
        dialog.setPosition((600 - dialog.getWidth()) / 2, 150 + (400 - dialog.getHeight()) / 2);
        return dialog;
    }

    public void showChapter(Label label, int index) {

        String text = "";
        switch (index) {
            case 0:
                text = "Not only humans like to travel to other planets" +
                        ", other races in the galaxies like travelling too!" +
                        "\n\nTherefore, Your job is to run a space shuttle company and connect the galaxy!";
                break;
            case 1:
                text = "You need to achieve these goals to win:" +
                        "\n\n\t - own 10 or more routes." +
                        "\n\n\t - carry 2000 or more passengers in one turn." +
                        "\n\n\t - own 100000 or more money.";
                break;
            case 2:
                text = "You can set up as many routes as possible, but: " +
                        "\n\n\t - You can only build 1 route between 2 planets." +
                        "\n\n\t - Routes have an initial setup cost " +
                              "which increases as the distance increase" +
                        "\n\n\t - Each route need to have at least one spaceship operating on it." +
                        "\n\n\t - You can edit or delete your routes in the 'Edit Routes' menu.";
                break;
            case 3:
                text = "Each planet has its unique values: " +
                        "\n\n\t - Travel value" +
                        "\n\n\t - Strategic value" +
                        "\n\n\t - Business value" +
                        "\n\nThe key here is to connect planets that has low values with the ones has high values. " +
                        "Also try to avoid connecting races that hates each other.";
                break;
            case 4:
                text = "There are five races in this game. You can learn more in the 'Races' menu";
                break;
            case 5:
                text = "Choosing your space ship is important and there are a few things you might want to consider:" +
                        "\n\n\t - Price, of course." +
                        "\n\n\t - Capacity. It affects the amount of passengers." +
                        "\n\n\t - How comfortable the seats are. No one want to sit on a rock for a few hours." +
                        "\n\n\t - Maintenance. It determines how much it would cost you each turn." +
                        "\n\n\t - Range. Determine if you can use it to connect two planets." +
                        "\n\n\"However, the most important thing is to choose the manufacturer\" - says the sale representative from UNS Ltd. \"Remember, UNS makes the best spaceship in the galaxy(TM)\"" +
                        "\n\n'Above information is provided by UNS Spaceship Ltd.'";
                break;
            case 6:
                text = "Have fun!";
                break;
        }

        label.setText(text);
    }

    public void show(Stage stage) {
        makeDialog().show(stage);
    }
}
