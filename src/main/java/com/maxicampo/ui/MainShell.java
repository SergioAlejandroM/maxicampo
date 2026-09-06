package com.maxicampo.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class MainShell extends BorderPane {

    private StackPane contenido;

    public MainShell(){
        // -- SideBar --
        Label titulo = new Label("MaxiCampo");
        titulo.getStyleClass().add("sidebar-title");

        Button btnProductos = new Button("\uD83D\uDCE6 Productos");
        btnProductos.getStyleClass().add("sidebar-button");
        btnProductos.setMaxWidth(Double.MAX_VALUE);

        VBox sidebar = new VBox(titulo, btnProductos);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(220);
        sidebar.setAlignment(Pos.TOP_LEFT);

        // --- Área de contenido ---
        contenido = new StackPane();
        contenido.setPadding(new Insets(24));

        this.setLeft(sidebar);
        this.setCenter(contenido);

        btnProductos.setOnAction(e -> {
            contenido.getChildren().setAll(new ProductosView());
            contenido.getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());
        });
    }
}
