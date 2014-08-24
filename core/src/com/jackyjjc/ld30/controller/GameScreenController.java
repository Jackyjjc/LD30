package com.jackyjjc.ld30.controller;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.jackyjjc.ld30.model.DataSource;
import com.jackyjjc.ld30.model.GameState;
import com.jackyjjc.ld30.model.Planet;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class GameScreenController {

    private Stage stage;
    private GameState g;
    private ImageButton selectedPlanet;
    public ScrollPane planetDetail;

    public GameScreenController(GameState g, Stage stage) {
        this.g = g;
        this.stage = stage;
    }

    public void selectPlanet(ImageButton planet) {
        if (!inBuildMode || selectedPlanet == null) {
            handleNormalSelect(planet);
            return;
        } else {
            handleBuildSelect(planet);
        }
    }

    public boolean inBuildMode;
    public TextButton buildRouteBtn;

    public void handleBuildSelect(ImageButton planet) {
        if (planet == null || selectedPlanet == planet) {
            deselectButton();
            exitBuildMode();
            return;
        }

        Planet from = DataSource.getPlanet(selectedPlanet.getName());
        Planet to = DataSource.getPlanet(planet.getName());

        NewRouteDialog dialog = new NewRouteDialog(g, from, to);
        dialog.show(stage);

        deselectButton();
        planet.setChecked(false);
        planetDetail.setVisible(false);
        exitBuildMode();
    }

    public void handleNormalSelect(ImageButton planet) {
        if(selectedPlanet != null) {
            selectedPlanet.setChecked(false);
        }

        if (planet == null || selectedPlanet == planet) {
            deselectButton();
            planetDetail.setVisible(false);
        } else {
            selectedPlanet = planet;
            planetDetail.setVisible(true);

            Label l = (Label) planetDetail.getWidget();

            Planet p = DataSource.getPlanet(planet.getName());
            String race = p.race == -1 ? "none" : DataSource.getRace(p.race).name;

            l.setText(p.name
                    + "\nfraction: " + race + "   population: " + p.population
                    + "\ntravel: " + p.travel + "   strategic: " + p.strategic + "   business: " + p.business
                    + "\n\n" + p.description);
        }
    }



    private void deselectButton() {
        this.selectedPlanet.setChecked(false);
        this.selectedPlanet = null;
    }

    private void exitBuildMode() {
        buildRouteBtn.setChecked(false);
        inBuildMode = false;
    }

    public void build(Planet from, Planet to) {
        //Route route = new Route(selected, to);
    }
}
