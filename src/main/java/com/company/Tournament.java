package com.company;

import java.util.ArrayList;
import com.google.firebase.database.utilities.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Tournament {
    private String name;
    private List<Team> teams;


    public Tournament() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Tournament(String name) {
        this.name = name;
    }

    public void addTeam(Team team){
        if(!teams.contains(team)) teams.add(team);
        else System.out.println("This team has already been added");
    }

    public void play() {
        HashMap<Pair<Team, Team>, Integer> teamsPlay = new HashMap<>();
        for (Team team1 : teams) {
            for (Team team2 : teams) {
                if(!teamsPlay.containsKey(new Pair<>(team1, team2)) && !teamsPlay.containsKey(new Pair<>(team2, team1))){
                    teamsPlay.put(new Pair<>(team1, team2), 0);
                    Random random = new Random();
                    int team1Win = random.nextInt(2);
                    if(team1Win == 1){
                        team1.addScore(true);
                        team2.addScore(false);
                    }
                    else{
                        team1.addScore(false);
                        team2.addScore(true);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "name='" + name + '\'' +
                ", teams=" + teams +
                '}';
    }
}
