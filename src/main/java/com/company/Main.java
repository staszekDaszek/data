package com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
        Thread.sleep(3000);
        for (int i = 0; i < tournamentList.size(); i++) {
            Tournament tournament = tournamentList.get(i);
            for (int j = 0; j < tournament.getTeams().size(); j++) {
                if(!teamList.contains(tournament.getTeams().get(j))){
                    teamList.add(tournament.getTeams().get(j));
                }
            }
        }
        menuWindow();

    }
    public static void newTournamentWindow() {
        for (Team team:
             teamList) {
            System.out.println(team.getName());
        }
        root = new AnchorPane();
        List<Team> tempTeam = new ArrayList<>();

        TextField tournamentName = new TextField("");
        tournamentName.setPromptText("Name of the tournament");
        tournamentName.setFont(Font.font(20));

        ObservableList<String> data = FXCollections.observableArrayList();
        for (Team team : teamList) {
            data.add(team.getName());
        }
        ComboBox<String> teams = new ComboBox<>(data);

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
            System.out.println("100");
            if (! tournamentName.getText().isEmpty() && ! tempTeam.isEmpty()){
                System.out.println("200");
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

        Button randomize = new Button("Randomize");
        randomize.setFont(Font.font(20));
        randomize.setOnAction(event -> {
            if(! teamName.getText().isEmpty()){
                boolean isDuplicated = false;
                for (Team team: teamList){
                    if (team.getName().equals(teamName.getText())){
                        isDuplicated = true;
                    }
                }
                if(!isDuplicated){
                Team team = Team.randomize(teamName.getText());
                System.out.println(team);
                teamList.add(team);}
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

        HBox hBox = new HBox(teamName, players, addPlayer, randomize, addTeam);
        hBox.setSpacing(30);
        hBox.setLayoutX(25);
        hBox.setLayoutY(100);

        root.getChildren().addAll(menu, back, hBox);

        scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }
    public static void menuWindow() {
        root = new AnchorPane();

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
        AtomicReference<Tournament> tour = new AtomicReference<>(new Tournament());

        TableView tableView = new TableView();
        root.getChildren().add(tableView);

        TableColumn nameColumn = new TableColumn("Name");
        TableColumn pointsColumn = new TableColumn("Points");
        TableColumn winsColumn = new TableColumn("Wins");
        TableColumn lossesColumn = new TableColumn("Losses");
        tableView.getColumns().addAll(nameColumn, pointsColumn, winsColumn, lossesColumn);


        PropertyValueFactory<Team, String> factory1 = new PropertyValueFactory<>("Name");
        PropertyValueFactory<Team, Integer> factory2 = new PropertyValueFactory<>("Points");
        PropertyValueFactory<Team, Integer> factory3 = new PropertyValueFactory<>("Wins");
        PropertyValueFactory<Team, Integer> factory4 = new PropertyValueFactory<>("Losses");

        nameColumn.setCellValueFactory(factory1);
        pointsColumn.setCellValueFactory(factory2);
        winsColumn.setCellValueFactory(factory3);
        lossesColumn.setCellValueFactory(factory4);
        pointsColumn.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getSortOrder().add(pointsColumn);
        tableView.sort();

        ObservableList<String> data = FXCollections.observableArrayList();
        for (Tournament tournament: tournamentList) {
            data.add(tournament.getName());
        }
        ComboBox<String> tournaments = new ComboBox<>(data);
        tournaments.setPrefWidth(300);
        tournaments.setPrefHeight(50);
        tournaments.setPromptText("Previous tournaments");
        tournaments.setLayoutY(50);
        tournaments.setLayoutX(300);

        tournaments.setOnAction(event -> {
            tour.set(new Tournament());
            String name = tournaments.getValue();
            for (int i = 0; i <tournamentList.size(); i++) {
                if(name.equals(tournamentList.get(i).getName())){
                    tour.set(tournamentList.get(i));
                }
            }
            tableView.getItems().clear();
            for (int i = 0; i < tour.get().getTeams().size(); i++) {
                Team team = tour.get().getTeams().get(i);
                tableView.getItems().add(team);
            }
            //Tournament temp = (Tournament) (tournaments.getSelectionModel().getSelectedItem());
           // List<Animal> tempList = Arrays.asList(temp.getAnimals());
           // ObservableList tempObs =  FXCollections.observableArrayList(tempList);
          //  tableView.setItems(tempObs);
        });



        Button menu = new Button("Menu");
        menu.setFont(Font.font(20));
        menu.setLayoutX(100);
        menu.setLayoutY(550);
        menu.setOnAction(event -> menuWindow());

        Button play = new Button("Play This Tournament");
        play.setFont(Font.font(20));
        play.setLayoutX(300);
        play.setLayoutY(550);
        play.setOnAction(event -> {
            for (int i = 0; i < tour.get().getTeams().size(); i++) {
               Team team = tour.get().getTeams().get(i);
                for (int j = i+1; j < tour.get().getTeams().size(); j++) {
                    if(i!=j){
                        Team enemy = tour.get().getTeams().get(j);
                        int result = Math.random() > 0.5 ? 1 : 2;
                        if(result == 1){
                            enemy.setLosses(enemy.getLosses()+1);
                            enemy.setPoints(enemy.getPoints()+1);
                            team.setWins(team.getWins()+1);
                            team.setPoints(team.getPoints()+3);
                        }
                        else{
                            enemy.setWins(enemy.getWins()+1);
                            enemy.setPoints(enemy.getPoints()+3);
                            team.setLosses(team.getLosses()+1);
                            team.setPoints(team.getPoints()+1);
                        }
                    }

                }
            }
            tableView.getItems().clear();
            for (int i = 0; i < tour.get().getTeams().size(); i++) {
                Team team = tour.get().getTeams().get(i);
                tableView.getItems().add(team);
            }
            tableView.sort();

        });

        Button reset = new Button("Reset");
        reset.setFont(Font.font(20));
        reset.setLayoutX(600);
        reset.setLayoutY(550);
        reset.setOnAction(event -> {
            for (int i = 0; i < tour.get().getTeams().size(); i++) {
                Team team = tour.get().getTeams().get(i);
                team.setWins(0);
                team.setPoints(0);
                team.setLosses(0);
            }
            tableView.getItems().clear();
            for (int i = 0; i < tour.get().getTeams().size(); i++) {
                Team team = tour.get().getTeams().get(i);
                tableView.getItems().add(team);
            }
            tableView.sort();

        });

        root.getChildren().addAll(tournaments, menu, play, reset);
        scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void showSelectedPrevious(Tournament tournament){




        List<Team> teams = tournament.getTeams();
        ObservableList tempObs = FXCollections.observableArrayList(teams);
        //tableView.setItems(tempObs);

    }
}
