package com.company;

public class Score {
    private String name;
    private int points;

    public Score() {
    }

    public Score(String name, int points) {
        this.name = name;
        this.points = points;
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

    @Override
    public String toString() {
        return "Score{" +
                "name='" + name + '\'' +
                ", points=" + points +
                '}';
    }
}
