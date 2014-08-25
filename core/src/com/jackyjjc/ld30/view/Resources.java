package com.jackyjjc.ld30.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.jackyjjc.ld30.model.DataSource;

import java.util.HashMap;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class Resources {
    private static Resources r = null;
    private static Resources get() {
        if(r == null) {
            r = new Resources();
        }
        return r;
    }

    public static Skin getSkin() {
        return get().skin;
    }

    public static <T> T get(String key, Class<T> t) {
        if (!get().map.containsKey(key)) {
            return null;
        }

        return (T)get().map.get(key);
    }

    private HashMap<String, Object> map;
    private Skin skin;

    public Resources() {
        i = 0;
        total = DataSource.get().planets.length + 6;
        this.map = new HashMap<>();
    }

    public static float load() {
        return get().loadNext();
    }

    private float total;
    private int i;
    private float loadNext() {
        int numPlanets = DataSource.get().planets.length;

        if(i < numPlanets) {
            this.map.put("planet" + i, new SpriteDrawable(
                    new Sprite(new Texture(Gdx.files.internal("sprites/Planet_" + i + ".png")))));
            this.map.put("planet" + i + "_checked", new SpriteDrawable(
                    new Sprite(new Texture(Gdx.files.internal("sprites/Planet_" + i + "_c.png")))));
            i++;
            return (i / total);
        }


        int off = i - numPlanets;
        switch (off) {
            case 0:
                this.map.put("ship", new Texture("sprites/ship.png"));
                break;
            case 1:
                this.map.put("actionPanel", new Texture("sprites/actionPanel.png"));
                break;
            case 2:
                this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
                break;
            case 3:
                this.map.put("background", new Texture(Gdx.files.internal("sprites/background.jpg")));
                break;
            case 4:
                this.map.put("music", Gdx.audio.newMusic(Gdx.files.internal("music/music.ogg")));
                break;
            case 5:
                this.map.put("title", new Texture(Gdx.files.internal("sprites/title.jpg")));
                break;
        }
        i++;
        return (i / total);
    }
}
