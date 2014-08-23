package com.jackyjjc.ld30.model;

/**
 * @author Jackyjjc (jacky.jjchen@gmail.com)
 */
public class GameState {
    public int turnNum;
    public int curPlayer;
    public Player[] players;

    public GameState() {
        this.turnNum = 0;
        this.players = new Player[4];
        this.players[0] = new Player("human player");
        this.players[1] = new Player("computer 1");
    }

    public void addRoute(Planet from, Planet to) {
        curPlayer().routes.add(new Route(from, to));
    }

    public boolean isLegalRoute(Planet from, Planet to) {
        boolean legal = true;
        for(Route r : curPlayer().routes) {
            if ((r.from.equals(from) || r.from.equals(to))
             && (r.to.equals(from) || r.to.equals(to))) {
                legal = false;
                break;
            }
        }
        return legal;
    }

    public void endTurn() {
        this.turnNum++;
        this.updateCurPlayer();
        //process AI

        //process all the routes
        for(Player p : players) {
            for(Route r : p.routes) {
                p.money += r.genMoney();
            }
        }
    }

    public Player curPlayer() {
        return this.players[this.curPlayer];
    }

    public void updateCurPlayer() {
        this.curPlayer = (curPlayer + 1) % players.length;
    }
}
