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
    static List<Player> playerList = new ArrayList<>();
    static List<Tournament> tournamentList = new ArrayList<>();
    static AnchorPane root;
    static Scene scene;
    static Stage stage = new Stage();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FirebaseController.init();
        FirebaseController.getTournaments();

        menuWindow();

    }
    public static void newTournamentWindow() {
        root = new AnchorPane();
        List<Team> tempTeam = new ArrayList<>();

        TextField tournamentName = new TextField("");
        tournamentName.setPromptText("Name of the tournament");
        tournamentName.setFont(Font.font(20));

        ComboBox<String> teams = new ComboBox<>();
        for (Team team : teamList) {
            teams.getItems().add(team.getName());
        }
        teams.setPromptText("teams");
        teams.setPrefHeight(43);
        teams.setPrefWidth(200);

        Button addTeam = new Button("Add Team");
        addTeam.setFont(Font.font(20));
        addTeam.setOnAction(event -> {
            for (Team team : teamList) {
                if (team.getName().equals(teams.getValue())) {
                    tempTeam.add(team);
                    teams.getItems().remove(team.getName());
                }
            }

        });

        Button addTournament = new Button("Add Tournament");
        addTournament.setFont(Font.font(20));
        addTournament.setLayoutX(300);
        addTournament.setLayoutY(400);
        addTournament.setOnAction(event -> {
            if (! tournamentName.getText().isEmpty() && ! tempTeam.isEmpty()){
                Tournament tournament = new Tournament();
                tournament.setName(tournamentName.getText());
                tournament.setTeams(tempTeam);
                tournamentList.add(tournament);
                FirebaseController.addTournament(tournament);
            }
        });

        Button newTeam = new Button("Create Team");
        newTeam.setFont(Font.font(20));
        newTeam.setOnAction(event -> newTeamWindow());

        Button menu = new Button("Menu");
        menu.setFont(Font.font(20));
        menu.setLayoutX(600);
        menu.setLayoutY(400);
        menu.setOnAction(event -> menuWindow());

        HBox hBox = new HBox();
        hBox.setSpacing(40);
        hBox.setLayoutX(100);
        hBox.setLayoutY(100);
        hBox.getChildren().addAll(tournamentName, teams, addTeam, newTeam);

        root.getChildren().addAll(hBox, addTournament, menu);
        scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void newTeamWindow() {
        root = new AnchorPane();
        List<Player> tempPlayers = new ArrayList<>();

        Button menu = new Button("Menu");
        menu.setFont(Font.font(20));
        menu.setLayoutX(600);
        menu.setLayoutY(400);
        menu.setOnAction(event -> menuWindow());

        Button back = new Button("Back");
        back.setFont(Font.font(20));
        back.setLayoutX(300);
        back.setLayoutY(400);
        back.setOnAction(event -> newTournamentWindow());

        TextField teamName = new TextField("");
        teamName.setPromptText("Name");
        teamName.setFont(Font.font(20));

        ComboBox<String> players = new ComboBox<>();
        players.setPromptText("Players");
        for (Player player: playerList){
            players.getItems().add(player.getName());
        }
        players.setPrefHeight(43);
        players.setPrefWidth(200);

        Button addPlayer = new Button("Add player");
        addPlayer.setFont(Font.font(20));
        addPlayer.setOnAction(event -> {
            for (Player player: playerList){
                if (player.getName().equals(players.getValue()) && ! tempPlayers.contains(player)){
                    tempPlayers.add(player);
                }
            }
        });

        Button addTeam = new Button("Create team");
        addTeam.setFont(Font.font(20));
        addTeam.setOnAction(event -> {
            boolean isDuplicated = false;
            if (! teamName.getText().isEmpty() && ! tempPlayers.isEmpty()){
                for (Team team: teamList){
                    if (team.getName().equals(teamName.getText())){
                        isDuplicated = true;
                    }
                }
                if (!isDuplicated){
                    Team team = new Team(teamName.getText());
                    team.setPlayers(tempPlayers);
                    teamList.add(team);
                }
            }
        });

        HBox hBox = new HBox(teamName, players, addPlayer,  addTeam);
        hBox.setSpacing(40);
        hBox.setLayoutX(100);
        hBox.setLayoutY(100);

        root.getChildren().addAll(menu, back, hBox);

        scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }
    public static void menuWindow() {
        root = new AnchorPane();

        Team gsw = new Team("GSW");
        teamList.add(gsw);
        Player player = new Player("Kobe Bryant", 23, "shooting guard");
        Player player1 = new Player("Micheal Jordan", 33, "shooting guard");
        Player player2 = new Player("Steph Curry", 31, "point guard");
        Player player3 = new Player("Tracy McGrady", 29, "small forward");
        playerList.add(player3);
        playerList.add(player);
        playerList.add(player2);
        playerList.add(player1);

        Button createTournament = new Button("Create new tournament");
        createTournament.setFont(Font.font(30));
        createTournament.setLayoutX(340);
        createTournament.setLayoutY(400);

        createTournament.setOnAction(event -> newTournamentWindow());

        Button previousTournaments = new Button("Previous Tournaments");
        previousTournaments.setFont(Font.font(30));
        previousTournaments.setLayoutX(340);
        previousTournaments.setLayoutY(200);

        previousTournaments.setOnAction(event -> previousTournamentsWindow());

        root.getChildren().addAll(createTournament, previousTournaments);
        scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void previousTournamentsWindow() {
        root = new AnchorPane();
        FirebaseController.getTournaments();
        ComboBox<String> tournaments = new ComboBox<>();
        for (Tournament tournament: tournamentList){
            tournaments.getItems().add(tournament.getName());
        }
        tournaments.setPrefWidth(300);
        tournaments.setPrefHeight(50);
        tournaments.setPromptText("Previous tournaments");
        tournaments.setLayoutY(100);
        tournaments.setLayoutX(300);

        Button menu = new Button("Menu");
        menu.setFont(Font.font(20));
        menu.setLayoutX(600);
        menu.setLayoutY(400);
        menu.setOnAction(event -> menuWindow());

        root.getChildren().addAll(tournaments, menu);
        scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }
}
