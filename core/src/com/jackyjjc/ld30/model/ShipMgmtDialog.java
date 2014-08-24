package com.jackyjjc.ld30.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.jackyjjc.ld30.view.Resources;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class ShipMgmtDialog {

    private Dialog dialog;

    public ShipMgmtDialog() {
        this.dialog = new Dialog("Manage Ships", Resources.getSkin());

        
    }

    public void show(Stage s) {
        s.addActor(dialog);
    }
}
