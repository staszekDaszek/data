package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Team {
    private List<Player> players;
    private String name;
    private int points;
    private int wins;
    private int losses;
    private static final int NAMES_NUMBER = 2943;
    private static final String namesTxt = "names.txt";
    private static final int MAX_AGE = 150;
    private static final String positionsTxt = "positions.txt";
    private static final int POSITIONS_NUMBER = 73380;


    public Team(List<Player> players, String name) {
        this.players = players;
        this.name = name;
        this.points = 0;
        this.wins = 0;
        this.losses = 0;
    }

    public Team(String name) {
        this.name = name;
    }

    public Team() {}

    public void addScore(boolean won) {
        if (won) {
            points += 3;
            wins++;
        } else {
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

    public static Team randomize(String name) {
        Random radom = new Random();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int nameIndex = radom.nextInt(NAMES_NUMBER + 1);
            Scanner scanner = null;
            try {
                scanner = new Scanner(new FileInputStream(namesTxt), "UTF-8");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            int j = 0;
            while(scanner.hasNextLine()){
                String s = scanner.nextLine();
                if(j == nameIndex - 1){
                    int age = radom.nextInt(MAX_AGE);
                    nameIndex = radom.nextInt(POSITIONS_NUMBER + 1);
                    Scanner scanner1 = null;
                    try {
                        scanner1 = new Scanner(new FileInputStream(positionsTxt), "UTF-8");
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    int k = 0;
                    while(scanner1.hasNextLine()){
                        String ss = scanner1.nextLine();
                        if(k == nameIndex - 1){
                            Player player = new Player(s, age, ss);
                            players.add(player);
                        }
                        k++;
                    }
                }
                j++;
            }
        }
        return new Team(players, name);
    }

    @Override
    public String toString() {
        return "Team{" +
                "players=" + players +
                ", name='" + name + '\'' +
                '}';
    }
}
