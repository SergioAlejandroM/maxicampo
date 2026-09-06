package com.maxicampo;

import atlantafx.base.theme.PrimerLight;
import com.maxicampo.dao.ConexionDB;
import com.maxicampo.dao.InicializadorDB;
import com.maxicampo.dao.UsuarioDAO;
import com.maxicampo.model.Rol;
import com.maxicampo.model.Usuario;
import com.maxicampo.ui.LoginView;
import com.maxicampo.ui.MainShell;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

public class MainApp extends Application {

    @Override
    public void start(Stage stage){

        Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());

        LoginView loginView = new LoginView(usuario -> {
            Scene scenePrincipal = new Scene(new MainShell(), 900, 600);
            scenePrincipal.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
            stage.setScene(scenePrincipal);
        });

        Scene sceneLogin = new Scene(loginView, 900, 600);
        sceneLogin.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());

        stage.setTitle("MaxiCampo v2");
        stage.setScene(sceneLogin);
        stage.show();

    }

    public static void main(String[] args) {

        new InicializadorDB().crearTablas();

        launch(args);
    }
}
