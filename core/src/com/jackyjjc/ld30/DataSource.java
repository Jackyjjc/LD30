package com.jackyjjc.ld30;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class DataSource {

    private Race[] races;
    private Planet[] planets;

    private DataSource() {}

    public static DataSource newInstance() {
        Json json = new Json();
        DataSource ds = json.fromJson(DataSource.class, Gdx.files.internal("data/data.json"));

        return ds;
    }
}
