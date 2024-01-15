package com.company;

import java.util.List;

public class Tournament {
    private List<Team> teams;

    private String name;


    public Tournament() {}

    public Tournament(String name, List<Team> teams){
        this.name = name;
        this.teams = teams;
    }


    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTeam(Team team){
        if(!teams.contains(team)) teams.add(team);
        else System.out.println("This team has already been added");
    }

    public void play(){
        for (int i = 0; i < getTeams().size(); i++) {
            Team team = getTeams().get(i);
            for (int j = i + 1; j < getTeams().size(); j++) {
                if (i != j) {
                    Team enemy = getTeams().get(j);
                    int result = Math.random() > 0.5 ? 1 : 2;
                    if (result == 1) {
                        team.addScore(true);
                        enemy.addScore(false);
                    } else {
                        team.addScore(false);
                        enemy.addScore(true);
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
