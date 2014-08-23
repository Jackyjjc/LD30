package com.jackyjjc.ld30.controller;

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

    private GameState g;
    private ImageButton selectedPlanet;
    public ScrollPane planetDetail;

    public GameScreenController(GameState g) {
        this.g = g;
    }

    public void selectPlanet(ImageButton planet) {
        if (!inBuildMode || selectedPlanet == null) {
            handleNormalSelect(planet);
            return;
        }

        if (planet == null) {
            return;
        }

        Planet from = DataSource.getPlanet(selectedPlanet.getName());
        Planet to = DataSource.getPlanet(planet.getName());

        if(g.isLegalRoute(from, to)) {
            g.addRoute(from, to);
        }

        deselectButton();
        planet.setChecked(false);
        planetDetail.setVisible(false);
        buildRouteBtn.setChecked(false);
        inBuildMode = false;

        //Label l = (Label)planetDetail.getWidget();
        //l.setText("Construct a routes between " + DataSource.get().planets[Integer.parseInt(selectedPlanet.getName())].name + " and " + DataSource.get().planets[Integer.parseInt(planet.getName())].name + "?");
    }

    public boolean inBuildMode;
    public TextButton buildRouteBtn;

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

    public void build(Planet from, Planet to) {
        //Route route = new Route(selected, to);
    }
}
