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
            new Player("computer 1"),
        };
    }

    public void addRoute(Planet from, Planet to, int shipId, int numShips) {
        Route newRoute = new Route(from, to, shipId, numShips);
        curPlayer().routes.add(newRoute);
        curPlayer().money -= Route.getSetupCost(from, to);

        curPlayer().spaceShips[shipId] -= numShips;
        notifyListeners();
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

    public boolean isLegalRoute(Planet from, Planet to, float numShips) {
        //check if route already exists
        for(Route r : curPlayer().routes) {
            if ((r.from.equals(from) || r.from.equals(to))
             && (r.to.equals(from) || r.to.equals(to))) {
                errno = GameStrings.ERR_ROUTE_EXIST;
                return false;
            }
        }

        //check if there are ships;
        if(numShips <= 0) {
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
        for(Player p : players) {
            for(Route r : p.routes) {
                //get profit and pay maintenance
                p.money += r.genMoney();
                p.money -= r.getMaintenance();
            }
        }

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
