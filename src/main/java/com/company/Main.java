package com.company;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FirebaseController.init();


        AnchorPane root = new AnchorPane();

        TextField name = new TextField("");
        name.setFont(Font.font(20));
        name.setPromptText("Name:");


        TextField score  = new TextField("");
        score.setPromptText("Score:");
        score.setFont(Font.font(20));

        Button add = new Button("Add");
        add.setFont(Font.font(20));


        HBox hBox = new HBox();
        hBox.setSpacing(100);
        hBox.setLayoutX(100);
        hBox.setLayoutY(100);
        hBox.getChildren().addAll(name, score, add);

        root.getChildren().addAll(hBox);
        Scene scene = new Scene(root, 1200, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
