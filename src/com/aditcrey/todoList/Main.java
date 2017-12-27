package com.aditcrey.todoList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
        primaryStage.setTitle("Todo list");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
        primaryStage.getIcons().add(new Image(""));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
