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

    public static SpriteDrawable getSpriteDrawable(String key) {
        if (!get().spriteMap.containsKey(key)) {
            return null;
        }
        return get().spriteMap.get(key);
    }

    private HashMap<String, SpriteDrawable> spriteMap;
    private Skin skin;

    public Resources() {
        loadSkin();
        loadSprites();
    }

    private void loadSkin() {
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    }

    private void loadSprites() {
        this.spriteMap = new HashMap<String, SpriteDrawable>();

        for(int i = 0; i < DataSource.get().planets.length; i++) {
            this.spriteMap.put("planet" + i, new SpriteDrawable(
                    new Sprite(new Texture(Gdx.files.internal("sprites/Planet_" + i + ".png")))));
            this.spriteMap.put("planet" + i + "_checked", new SpriteDrawable(
                    new Sprite(new Texture(Gdx.files.internal("sprites/Planet_" + i + "_c.png")))));
        }
    }
}
