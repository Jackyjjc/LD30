package com.jackyjjc.ld30.model;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class Route {
    public Planet from;
    public Planet to;
    public SpaceShip ship;
    public int numShips;

    public static int getSetupCost(Planet from, Planet to) {
        return from.distance[to.id] * 100;
    }

    public Route(Planet from, Planet to, int shipId, int numShips) {
        this.from = from;
        this.to = to;
        this.ship = DataSource.get().spaceShips[shipId];
        this.numShips = numShips;
    }

    public int getMaintenance() {
        return ship.maintenance * numShips;
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
