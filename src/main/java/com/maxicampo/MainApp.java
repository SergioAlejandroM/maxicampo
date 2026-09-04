package com.maxicampo;

import com.maxicampo.dao.ConexionDB;
import com.maxicampo.dao.InicializadorDB;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage){

        StackPane root = new StackPane(new Label("MaxiCampo - En construccion"));
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("MaxiCampo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        new InicializadorDB().crearTablas();
        launch(args);
    }
}
