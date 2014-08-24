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

        Object o = get().map.get(key);
        if(!o.getClass().equals(t)) {
            return null;
        }

        return (T)o;
    }

    private HashMap<String, Object> map;
    private Skin skin;

    public Resources() {
        loadSkin();
        loadSprites();
    }

    private void loadSkin() {
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    }

    private void loadSprites() {
        this.map = new HashMap<String, Object>();

        for(int i = 0; i < DataSource.get().planets.length; i++) {
            this.map.put("planet" + i, new SpriteDrawable(
                    new Sprite(new Texture(Gdx.files.internal("sprites/Planet_" + i + ".png")))));
            this.map.put("planet" + i + "_checked", new SpriteDrawable(
                    new Sprite(new Texture(Gdx.files.internal("sprites/Planet_" + i + "_c.png")))));
        }

        this.map.put("ship", new Texture("sprites/ship.png"));
        this.map.put("actionPanel", new Texture("sprites/actionPanel.png"));
    }
}
