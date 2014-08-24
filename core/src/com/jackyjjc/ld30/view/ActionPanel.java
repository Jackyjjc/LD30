package com.jackyjjc.ld30.view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jackyjjc.ld30.controller.EditRouteDialog;
import com.jackyjjc.ld30.controller.GameScreenController;
import com.jackyjjc.ld30.model.GameState;
import com.jackyjjc.ld30.model.GameUpdateListener;
import com.jackyjjc.ld30.controller.ShipMgmtDialog;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class ActionPanel implements GameUpdateListener {

    private Table table;
    private Label moneyLabel;

    public ActionPanel(final GameState model, final GameScreenController controller, final Stage stage) {
        //creating rhs panel
        table = new Table();
        table.top();
        table.setSize(200, 600);
        table.setPosition(600, 0);
        //table.setDebug(true);

        Label l = new Label(model.curPlayer().name, Resources.getSkin());
        table.add(l).top().left().width(200).height(100).colspan(2);
        table.row();

        l = new Label("Money: ", Resources.getSkin());
        table.add(l);
        moneyLabel = new Label(model.curPlayer().money + "", Resources.getSkin());
        table.add(moneyLabel);
        table.row();

        l = new Label("Actions: ", Resources.getSkin());
        table.add(l).colspan(2).left().padTop(20);
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
        table.add(buildRouteBtn).colspan(2).center().width(100).padTop(15);
        table.row();

        TextButton editRouteBtn = new TextButton("Edit Route", Resources.getSkin());
        editRouteBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                EditRouteDialog dialog = new EditRouteDialog(model);
                dialog.show(stage);
            }
        });
        table.add(editRouteBtn).colspan(2).center().width(100).padTop(15);
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
        table.add(shipBtn).colspan(2).center().padTop(15).width(100);
        table.row();

        TextButton endTurnBtn = new TextButton("End Turn", Resources.getSkin());
        endTurnBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                model.endTurn();
            }
        });
        table.add(endTurnBtn).colspan(2).center().padTop(15).width(100);

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
