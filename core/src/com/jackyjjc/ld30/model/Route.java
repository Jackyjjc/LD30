package com.jackyjjc.ld30.model;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class Route {
    public Planet from;
    public Planet to;
    public SpaceShip ship;
    public int numShips;
    public int price;
    public double rand;
    public int lastPass;
    public int lastProfit;
    public int createdTime;
    public int currentTurn;

    public static int getSetupCost(Planet from, Planet to) {
        return from.distance[to.id] * 100;
    }

    public static int estimatePrice(Planet from, Planet to) {
        int maxDiff = getMaxDiff(from, to);
        return Math.max(maxDiff - 20, 5);
    }

    public Route(Planet from, Planet to, int shipId, int numShips, int price, int createdTime) {
        this.from = from;
        this.to = to;
        this.ship = DataSource.get().spaceShips[shipId];
        this.numShips = numShips;
        this.price = price;
        this.createdTime = createdTime;
    }

    public int getMaintenance() {
        return (int) (ship.maintenance * numShips * (1 + (currentTurn - createdTime) / 100.0));
    }

    public int getNumPassenger() {
        int relation = DataSource.getRace(from.race).relation[to.race];
        System.out.println(rand);
        double fromPop = from.population * (1.0 + relation / 10.0) * from.business / 100.0 * (1 + rand / 10.0);
        System.out.println(fromPop);
        double toPop = to.population * (1.0 + relation / 10.0) * to.business / 100.0 * (1 + rand / 10.0);
        System.out.println(toPop);
        return (int) (fromPop + toPop);
    }

    public int genMoney() {
        int actualPassengers = getNumPassenger();
        //System.out.println("raw " + actualPassengers);
        actualPassengers = (int) (actualPassengers * (ship.comfortability / 100.0));
        //System.out.println("after comfort " + actualPassengers);

        //time effet
        actualPassengers = (int) (actualPassengers * (0.5 + (createdTime + 5.0) / (currentTurn)));

        int maxDiff = getMaxDiff(from, to);
        double priceEffect = 1 + ((maxDiff - price) / 100.0);
        actualPassengers = (int) (actualPassengers * priceEffect);
        //System.out.println("after price " + actualPassengers);

        int totalShipCap = ship.capacity * numShips;
        actualPassengers = Math.min(actualPassengers, totalShipCap);

        this.lastPass = actualPassengers;

        //System.out.println("actual passengers: " + actualPassengers);

        return actualPassengers * price;
    }

    private static int getMaxDiff(Planet from, Planet to) {
        int travelDiff = Math.abs(from.travel - to.travel);
        int strategicDiff = Math.abs(from.strategic - to.strategic);
        int businessDiff = Math.abs(from.business - to.business);
        int maxDiff = Math.max(travelDiff, Math.max(strategicDiff, businessDiff));
        return maxDiff;
    }
}
