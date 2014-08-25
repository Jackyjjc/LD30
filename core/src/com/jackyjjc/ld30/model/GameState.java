package com.jackyjjc.ld30.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class GameState {
    private List<GameUpdateListener> listeners;

    public int errno;
    public int turnNum;
    public int curPlayer;
    public Player[] players;

    public GameState() {
        this.listeners = new LinkedList<>();

        this.turnNum = 0;
        this.players = new Player[]{
            new Player("human player"),
        };
    }

    public Route addRoute(Planet from, Planet to, int shipId, int numShips, int price) {
        Route newRoute = new Route(from, to, shipId, numShips, price);
        curPlayer().routes.add(newRoute);
        curPlayer().money -= Route.getSetupCost(from, to);

        curPlayer().spaceShips[shipId] -= numShips;
        notifyListeners();

        return newRoute;
    }

    public void deleteRoute(Route r) {
        curPlayer().routes.remove(r);
    }

    public void editRoute(Route r, int shipId, int newAmount, int price) {
        curPlayer().spaceShips[r.ship.id] += r.numShips;

        r.ship = DataSource.get().spaceShips[shipId];
        r.numShips = newAmount;
        r.price = price;

        curPlayer().spaceShips[shipId] -= newAmount;
    }

    public void setShipAmount(int shipId, int newAmount) {
        int currentAmount = curPlayer().spaceShips[shipId];
        int delta = newAmount - currentAmount;

        SpaceShip ship = DataSource.get().spaceShips[shipId];
        int deltaPrice = ship.price * Math.abs(delta);

        if(delta < 0) {
            curPlayer().money += deltaPrice;
        } else {
            curPlayer().money -= deltaPrice;
        }

        curPlayer().spaceShips[shipId] = newAmount;
        notifyListeners();
    }

    public boolean isLegalShipMgmt(int shipId, int newAmount) {
        int currentAmount = curPlayer().spaceShips[shipId];
        int delta = newAmount - currentAmount;

        if(delta < 0) {
            return true;
        }

        SpaceShip ship = DataSource.get().spaceShips[shipId];
        int deltaPrice = ship.price * Math.abs(delta);

        if(curPlayer().money < deltaPrice) {
            System.out.println(curPlayer().money + " , " + deltaPrice);
            errno = GameStrings.ERR_INSUF_FUND;
            return false;
        }

        return true;
    }

    public boolean isLegalEditRoute(Route r, int shipId, int numShips) {
        if(DataSource.get().spaceShips[shipId].range < r.from.distance[r.to.id]) {
            errno = GameStrings.ERR_SHIP_NOT_SUIT;
            return false;
        }

        //check if there are ships;
        if(numShips <= 0) {
            errno = GameStrings.ERR_NO_SHIP;
            return false;
        }

        return true;
    }

    public boolean isLegalAddRoute(Planet from, Planet to, int shipId, float numShips) {
        //check if route already exists
        for(Route r : curPlayer().routes) {
            if ((r.from.equals(from) || r.from.equals(to))
             && (r.to.equals(from) || r.to.equals(to))) {
                errno = GameStrings.ERR_ROUTE_EXIST;
                return false;
            }
        }

        if(DataSource.get().spaceShips[shipId].range < from.distance[to.id]) {
            errno = GameStrings.ERR_SHIP_NOT_SUIT;
            return false;
        }

        //check if there are ships;
        if(numShips <= 0 || curPlayer().spaceShips[shipId] < numShips) {
            errno = GameStrings.ERR_NO_SHIP;
            return false;
        }

        if(Route.getSetupCost(from, to) > curPlayer().money) {
            errno = GameStrings.ERR_INSUF_FUND;
            return false;
        }

        return true;
    }

    public void endTurn() {
        this.turnNum++;
        //process AI
        for(int i = 0; i < players.length; i++) {
            this.updateCurPlayer();
        }

        //process all the routes
        Player p = players[0];
        p.lastEarn = 0;
        p.lastPaid = 0;

        int totalPass = 0;
        for(Route r : p.routes) {
            r.rand = RNG.randInt(-6, 6);

            //get profit and pay maintenance
            int money = r.genMoney();
            int maintenance = r.getMaintenance();

            p.money += money;
            p.money -= maintenance;
            p.lastEarn += money;
            p.lastPaid += maintenance;

            r.lastProfit = money - maintenance;
            totalPass += r.lastPass;
        }

//        if(p.routes.size() > 100 && totalPass > ) {
//
//        }

        notifyListeners();
    }

    public Player curPlayer() {
        return this.players[this.curPlayer];
    }

    public void updateCurPlayer() {
        this.curPlayer = (curPlayer + 1) % players.length;
    }

    public void addListener(GameUpdateListener l) {
        if(l == null || listeners.contains(l)) {
            return;
        }
        listeners.add(l);
    }

    public void notifyListeners() {
        for(GameUpdateListener l : listeners) {
            l.notifyUpdate(this);
        }
    }
}
