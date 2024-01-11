package com.company;

import java.util.List;

public class Team {
    private List<Player> players;
    private String name;
    private int points;
    private int wins;
    private int losses;


    public Team(List<Player> players, String name, int points, int wins, int losses) {
        this.players = players;
        this.name = name;
        this.points = 0;
        this.wins = 0;
        this.losses = 0;
    }

    public Team(String name) {
        this.name = name;
    }

    public void addScore(boolean won){
        if(won){
            points+=3;
            wins++;
        }
        else{
            losses++;
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
}
