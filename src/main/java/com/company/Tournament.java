package com.company;

import java.util.List;

public class Tournament {
    private List<Team> teams;

    private String name;
    private static final int MAX_POINTS_NUMBER = 200;


    public Tournament() {
    }

    public Tournament(String name, List<Team> teams) {
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

    public void addTeam(Team team) {
        if (!teams.contains(team)) teams.add(team);
        else System.out.println("This team has already been added");
    }

    public void play() {
        for (int i = 0; i < getTeams().size(); i++) {
            Team team = getTeams().get(i);
            for (int j = i + 1; j < getTeams().size(); j++) {
                if (i != j) {
                    Team enemy = getTeams().get(j);
                    int teamScore = 0, enemyScore = 0;
                    while(teamScore == enemyScore){
                        teamScore = (int) (Math.round(Math.random() * MAX_POINTS_NUMBER));
                        enemyScore = (int) (Math.round(Math.random() * MAX_POINTS_NUMBER));
                    }
                    team.addScore((teamScore > enemyScore), Main.tourKeyMap.get(this), teamScore);
                    enemy.addScore((teamScore < enemyScore), Main.tourKeyMap.get(this), enemyScore);
                    for (Player player : team.getPlayers()) {
                        if(teamScore > 0){
                            int playerScore = (int)(Math.round(Math.random() * teamScore));
                            player.addPoints(playerScore, Main.tourKeyMap.get(this));
                            System.out.println("trtetet " + player.getPointsInTour().toString());
                            teamScore -= playerScore;
                        }
                        else break;
                    }
                    for (Player player : enemy.getPlayers()) {
                        if(enemyScore > 0){
                            int playerScore = (int)(Math.round(Math.random() * enemyScore));
                            player.addPoints(playerScore, Main.tourKeyMap.get(this));
                            enemyScore -= playerScore;
                        }
                        else break;
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
