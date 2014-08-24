package com.jackyjjc.ld30.view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jackyjjc.ld30.controller.GameScreenController;
import com.jackyjjc.ld30.model.GameState;
import com.jackyjjc.ld30.model.GameUpdateListener;
import com.jackyjjc.ld30.model.ShipMgmtDialog;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class ActionPanel implements GameUpdateListener {

    private Table table;
    private Label moneyLabel;

    public ActionPanel(final GameState model, final GameScreenController controller, final Stage stage) {

        //creating rhs panel
        table = new Table();
        table.setSize(200, 600);
        table.setPosition(600, 0);
        //table.setDebug(true);

        Label l = new Label("Coperation: \n      " + model.curPlayer().name, Resources.getSkin());
        table.add(l);
        table.row();

        moneyLabel = new Label("Money: " + model.curPlayer().money, Resources.getSkin());
        table.add(moneyLabel);
        table.row();

        final TextButton buildRouteBtn = new TextButton("Build Route", Resources.getSkin());
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(buildRouteBtn.getStyle());
        style.checked = style.down;
        buildRouteBtn.setStyle(style);
        buildRouteBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.inBuildMode = !controller.inBuildMode;
                buildRouteBtn.setChecked(controller.inBuildMode);
            }
        });
        controller.buildRouteBtn = buildRouteBtn;
        table.add(buildRouteBtn);
        table.row();

        TextButton shipBtn = new TextButton("Spaceships", Resources.getSkin());
        shipBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ShipMgmtDialog dialog = new ShipMgmtDialog(model);
                dialog.show(stage);
            }
        });
        table.add(shipBtn);
        table.row();

        TextButton endTurnBtn = new TextButton("End Turn", Resources.getSkin());
        endTurnBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                model.endTurn();
            }
        });
        table.add(endTurnBtn);

        model.addListener(this);
    }

    public Table getRootTable() {
        return table;
    }

    @Override
    public void notifyUpdate(GameState g) {
        moneyLabel.setText("Money: " + g.curPlayer().money);
    }
}
