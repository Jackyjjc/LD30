package com.jackyjjc.ld30.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jackyjjc.ld30.LDGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "LD30";
        config.width  = 800;
        config.height = 600;

		new LwjglApplication(new LDGame(), config);
	}
}
