package com.company;

import java.util.HashMap;

public class Player {
    private String name;
    private int age;
    private String position;
    private HashMap<String, Integer> pointsInTour = new HashMap<>();

    public Player(String name, int age, String position) {
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public Player(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    public void addPoints(int points, String key){
        if(pointsInTour.containsKey(key)){
            pointsInTour.put(key, pointsInTour.get(key) + points);
        }
        else{
            pointsInTour.put(key, points);
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", position='" + position + '\'' +
                '}';
    }
}
