package com.jackyjjc.ld30.model;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class Planet {
    public int id;
    public String name;
    public String description;
    public int race;
    public int population;
    public int travel;
    public int strategic;
    public int business;
    public int[] distance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Planet planet = (Planet) o;

        if (id != planet.id) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
