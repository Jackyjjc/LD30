package com.jackyjjc.ld30.view;

import com.badlogic.gdx.Game;

public class LDGame extends Game {

	@Override
	public void create () {
        this.setScreen(new LoadScreen(this));
	}

    @Override
	public void render () {
        super.render();
	}
}
