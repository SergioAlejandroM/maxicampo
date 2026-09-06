package com.maxicampo.ui;

import com.maxicampo.model.Usuario;
import com.maxicampo.service.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class LoginView extends StackPane {

    private TextField campoUsuario;
    private PasswordField campoContrasena;
    private Label labelError;
    private Button botonIngresar;

    public LoginView(Consumer<Usuario> alLoginExitoso) {
        this.getStyleClass().add("login-root");

        Label titulo = new Label("MaxiCampo");
        titulo.getStyleClass().add("login-title");

        Label subtitulo = new Label("Inicia sesión para continuar");
        subtitulo.getStyleClass().add("login-subtitle");

        campoUsuario = new TextField();
        campoUsuario.setPromptText("Usuario");
        campoUsuario.getStyleClass().add("login-field");

        campoContrasena = new PasswordField();
        campoContrasena.setPromptText("Contraseña");
        campoContrasena.getStyleClass().add("login-field");

        labelError = new Label();
        labelError.getStyleClass().add("login-error");
        labelError.setVisible(false);

        botonIngresar = new Button("Ingresar");
        botonIngresar.getStyleClass().add("login-button");

        VBox card = new VBox(12, titulo, subtitulo, campoUsuario, campoContrasena, labelError, botonIngresar);
        card.getStyleClass().add("login-card");
        card.setAlignment(Pos.CENTER);
        card.setMaxSize(340, 400);

        this.getChildren().add(card);
        this.setPadding(new Insets(20));

        botonIngresar.setOnAction(actionEvent -> {

            try{
                Usuario usuario = new AuthService().login(campoUsuario.getText(), campoContrasena.getText());
                alLoginExitoso.accept(usuario);
                System.out.println("Login exitoso: " + usuario.getNombre() + "(" + usuario.getRol() + ")");
            } catch (RuntimeException e) {
                labelError.setText(e.getMessage());
                labelError.setVisible(true);
            }

        });
    }
}
