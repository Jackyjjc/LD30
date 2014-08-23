package com.jackyjjc.ld30.view;

import com.badlogic.gdx.Game;
import com.jackyjjc.ld30.view.GameScreen;

public class LDGame extends Game {

	@Override
	public void create () {
        this.setScreen(new GameScreen());
	}

    @Override
	public void render () {
        super.render();
	}
}
