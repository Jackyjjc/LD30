package com.jackyjjc.ld30.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class DataSource {

    private static DataSource d = null;
    public static DataSource get() {
        if(d == null) {
            d = DataSource.newInstance();
        }
        return d;
    }

    public Race[] races;
    public Planet[] planets;
    public SpaceShip[] spaceShips;

    public static Planet getPlanet(String id) {
        return get().planets[Integer.parseInt(id)];
    }
    public static Race getRace(int id) {return get().races[id];};

    private DataSource() {}

    public static DataSource newInstance() {
        Json json = new Json();
        DataSource ds = json.fromJson(DataSource.class, Gdx.files.internal("data/data.json"));

        return ds;
    }
}
