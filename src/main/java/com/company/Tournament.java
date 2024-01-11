package com.company;

import java.util.List;
import java.util.Random;

public class Tournament {
    private String name;
    private List<Team> teams;

    public Tournament(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public List<Team> getTeams() {
        return teams;
    }

    public void addTeam(Team team){
        if(!teams.contains(team)) teams.add(team);
        else System.out.println("This team has already been added");
    }

    public void play() {
        for (Team team1 : teams) {
            for (Team team2 : teams) {
                Random random = new Random();
                int team1Win = random.nextInt(2);
                int team1Score = (team1Win == 1) ? 3 : random.nextInt(3);
                int team2Score = (team1Win == 0) ? 3 : random.nextInt(3);

            }
        }
    }
}
