package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    static List<Team> teamList = new ArrayList<>();
    static AnchorPane root;
    static Scene scene;
    static Stage stage = new Stage();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FirebaseController.init();


        root = new AnchorPane();

        Team gsw = new Team("GSW");
        teamList.add(gsw);

        Button createTournament = new Button("Create new tournament");
        createTournament.setFont(Font.font(30));
        createTournament.setLayoutX(400);
        createTournament.setLayoutY(400);

        createTournament.setOnAction(event -> {
            newTournament();
        });

        TextField name = new TextField("");
        name.setPromptText("Name");
        name.setFont(Font.font(20));

        ComboBox<Team> teams = new ComboBox<>();
        teams.getItems().addAll(teamList);



        root.getChildren().add(createTournament);
        scene = new Scene(root, 1200, 1000);
        stage.setScene(scene);
        stage.show();
    }
    public static void newTournament() {
        root = new AnchorPane();
        Tournament tournament = new Tournament();

        TextField name = new TextField("");
        name.setPromptText("Name of the tournament");
        name.setFont(Font.font(20));

        ComboBox<String> teams = new ComboBox<>();
        for (int i = 0; i < teamList.size(); i++) {
            teams.getItems().add(teamList.get(i).getName());
        }
        teams.setPromptText("teams");
        teams.setPrefHeight(47);
        teams.setPrefWidth(200);

        Button addTeam = new Button("Add Team");
        addTeam.setFont(Font.font(20));
        addTeam.setOnAction(event -> {
            for (int i = 0; i < teamList.size(); i++) {
                if (teamList.get(i).getName().equals(teams.getValue())){
                   tournament.addTeam(teamList.get(i));
                   teams.getItems().remove(teamList.get(i).getName());
                }
            }

        });

        Button addTournament = new Button("Add Tournament");
        addTournament.setFont(Font.font(40));
        addTournament.setLayoutX(500);
        addTournament.setLayoutY(800);

        Button editTeam = new Button("Edit Team");
        editTeam.setFont(Font.font(20));


        HBox hBox = new HBox();
        hBox.setSpacing(60);
        hBox.setLayoutX(100);
        hBox.setLayoutY(200);
        hBox.getChildren().addAll(name,teams, addTeam);

        root.getChildren().addAll(hBox, addTournament);
        scene = new Scene(root, 1200, 1000);
        stage.setScene(scene);
        stage.show();
    }
}
