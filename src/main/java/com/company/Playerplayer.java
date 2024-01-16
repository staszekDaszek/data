package com.company;

public class Playerplayer extends Player {
    private int points;
    public Playerplayer(String tourKey, Player player){
        super(player.getName(), player.getAge(), player.getPosition());
        this.pointsInTour = player.getPointsInTour();
        this.points = this.pointsInTour.get(tourKey);
    }

    public int getPoints() {
        return points;
    }
}
