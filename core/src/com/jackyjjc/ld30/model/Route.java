package com.jackyjjc.ld30.model;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class Route {
    public Planet from;
    public Planet to;

    public Route(Planet from, Planet to) {
        this.from = from;
        this.to = to;
    }

    public int genMoney() {
        int numPeople = (int) (from.population * (from.business / 100.0) + to.population * (to.business / 100));

        int travelDiff = Math.abs(from.travel - to.travel);
        int strategicDiff = Math.abs(from.strategic - to.strategic);
        int businessDiff = Math.abs(from.business - to.business);

        int maxDiff = Math.max(travelDiff, Math.max(strategicDiff, businessDiff));
        return numPeople * maxDiff;
    }
}
