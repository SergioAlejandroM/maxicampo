package com.maxicampo.ui;

import com.maxicampo.dao.ProductoDAO;
import com.maxicampo.dao.UnidadVentaDAO;
import com.maxicampo.model.Producto;
import com.maxicampo.model.TipoMedida;
import com.maxicampo.model.UnidadVenta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class ProductosView extends BorderPane {

    private ProductoDAO productoDAO = new ProductoDAO();
    private UnidadVentaDAO unidadVentaDAO = new UnidadVentaDAO();
    private TableView<Producto> tabla = new TableView<>();
    private TableView<UnidadVenta> tablaUnidades = new TableView<>();
    private ObservableList<Producto> datos;
    private ObservableList<UnidadVenta> datosUnidades = FXCollections.observableArrayList();

    public ProductosView(){

        Button btnNuevo = new Button("+ Nuevo producto");
        btnNuevo.getStyleClass().add("btn-primary");
        btnNuevo.setOnAction(actionEvent -> {
            Dialog<Producto> dialog = new Dialog<>();
            dialog.setTitle("Nuevo Producto");
            dialog.getDialogPane().getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());

            TextField campoId =  new TextField();
            campoId.setPromptText("ID");

            TextField campoNombre =  new TextField();
            campoNombre.setPromptText("Nombre");

            TextField campoCategoria =  new TextField();
            campoCategoria.setPromptText("Categoria");

            ComboBox<TipoMedida> comboTipoMedida = new ComboBox<>();
            comboTipoMedida.getItems().addAll(TipoMedida.values());
            comboTipoMedida.setPromptText("Tipo de medida");

            TextField campoStock =  new TextField();
            campoStock.setPromptText("Stock inicial");
            campoStock.setTextFormatter(new TextFormatter<>(change -> {
                String textoNuevo = change.getControlNewText();
                if(textoNuevo.matches("\\d*\\.?\\d*")){
                    return change;
                }
                return null;
            }));

            TextField campoCosto =  new TextField();
            campoCosto.setPromptText("Costo");
            campoCosto.setTextFormatter(new TextFormatter<>(change -> {
                String textoNuevo = change.getControlNewText();
                if(textoNuevo.matches("\\d*\\.?\\d*")){
                    return change;
                }
                return null;
            }));

            VBox formulario = new VBox(10, campoId, campoNombre, campoCategoria, comboTipoMedida, campoStock, campoCosto);
            formulario.setPadding(new Insets(20));

            dialog.getDialogPane().setContent(formulario);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.setResultConverter(buttonType -> {
                if(buttonType == ButtonType.OK){
                    return new Producto(
                            campoId.getText(),
                            campoNombre.getText(),
                            campoCategoria.getText(),
                            comboTipoMedida.getValue(),
                            Double.parseDouble(campoStock.getText()),
                            Double.parseDouble(campoCosto.getText())
                    );

                }
                return null;
            });
            dialog.showAndWait().ifPresent(productoCreado -> {
                productoDAO.crearProducto(productoCreado);
                datos.add(productoCreado);
            });

        });

        Button btnEditar = new Button("Editar");
        btnEditar.getStyleClass().add("btn-secondary");
        btnEditar.setOnAction(actionEvent -> {
            Producto seleccionado = tabla.getSelectionModel().getSelectedItem();
            if(seleccionado != null){
                Dialog<Producto> editDialog = new Dialog<>();
                editDialog.setTitle("Editar Producto");
                editDialog.getDialogPane().getStylesheets().add(getClass().getResource("/styles/app.css").toExternalForm());

                TextField campoId =  new TextField();
                campoId.setText(seleccionado.getId());
                campoId.setDisable(true);

                TextField campoNombre =  new TextField();
                campoNombre.setText(seleccionado.getNombre());

                TextField campoCategoria =  new TextField();
                campoCategoria.setText(seleccionado.getCategoria());

                ComboBox<TipoMedida> comboTipoMedida = new ComboBox<>();
                comboTipoMedida.getItems().addAll(TipoMedida.values());
                comboTipoMedida.setValue(seleccionado.getTipoMedida());

                TextField campoStock = new TextField();
                campoStock.setText(String.valueOf(seleccionado.getStock()));
                campoStock.setTextFormatter(new TextFormatter<>(change -> {
                    String textoNuevo = change.getControlNewText();
                    if(textoNuevo.matches("\\d*\\.?\\d*")){
                        return change;
                    }
                    return null;
                }));

                TextField campoCosto =  new TextField();
                campoCosto.setText(String.valueOf(seleccionado.getCosto()));
                campoCosto.setTextFormatter(new TextFormatter<>(change -> {
                    String textoNuevo = change.getControlNewText();
                    if(textoNuevo.matches("\\d*\\.?\\d*")){
                        return change;
                    }
                    return null;
                }));

                VBox formulario = new VBox(10, campoId, campoNombre, campoCategoria, comboTipoMedida, campoStock, campoCosto);
                formulario.setPadding(new Insets(20));

                editDialog.getDialogPane().setContent(formulario);
                editDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                editDialog.setResultConverter(buttonType -> {
                    if( buttonType == ButtonType.OK){
                        seleccionado.setNombre(campoNombre.getText());
                        seleccionado.setCategoria(campoCategoria.getText());
                        seleccionado.setTipoMedida(comboTipoMedida.getValue());
                        seleccionado.setStock(Double.parseDouble(campoStock.getText()));
                        seleccionado.setCosto(Double.parseDouble(campoCosto.getText()));

                        return seleccionado;
                    }
                    return null;
                });
                editDialog.showAndWait().ifPresent(productoEditado -> {
                    productoDAO.actualizarProducto(productoEditado);
                    tabla.refresh();
                });

            }else {
                Alert alerta = new Alert(Alert.AlertType.WARNING,"Selecciona un producto primero");
                alerta.showAndWait();
                return;
            }
        });

        Button btnEliminar = new Button("Eliminar");
        btnEliminar.getStyleClass().add("btn-danger");
        btnEliminar.setOnAction(actionEvent -> {
                    Producto seleccionado = tabla.getSelectionModel().getSelectedItem();
                    if(seleccionado != null){
                        productoDAO.eliminarProducto(seleccionado.getId());
                        datos.remove(seleccionado);
                    }else{
                        Alert alerta = new Alert(Alert.AlertType.WARNING,"Selecciona un producto primero.");
                        alerta.showAndWait();
                        return;
                    }
                }
        );

        HBox toolbar = new HBox(btnNuevo, btnEditar, btnEliminar);
        toolbar.getStyleClass().add("toolbar-container");
        toolbar.setAlignment(Pos.CENTER_LEFT);

        TableColumn<Producto, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Producto, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        TableColumn<Producto, TipoMedida> colTipoMedida = new TableColumn<>("Tipo Medida");
        colTipoMedida.setCellValueFactory(new PropertyValueFactory<>("tipoMedida"));

        TableColumn<Producto, Double> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Producto, Double> colCosto = new TableColumn<>("Costo");
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        tabla.getColumns().addAll(colId,colNombre,colCategoria,colTipoMedida,colStock,colCosto);

        List<Producto> productos = productoDAO.obtenerTodos();
        datos = FXCollections.observableArrayList(productos);
        tabla.setItems(datos);

        TableColumn<UnidadVenta, String> colDescripcion = new TableColumn<>("Descripcion");
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<UnidadVenta, Double> colConversion = new TableColumn<>("Conversion");
        colConversion.setCellValueFactory(new PropertyValueFactory<>("conversion"));

        TableColumn<UnidadVenta, Double> colPrecioVenta = new TableColumn<>("Precio Venta");
        colPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));

        tablaUnidades.getColumns().addAll(colDescripcion,colConversion,colPrecioVenta);

        tabla.getSelectionModel().selectedItemProperty().addListener((observable, productoAnterior, productoNuevo) ->{
            if(productoNuevo != null){
                List<UnidadVenta> unidades = unidadVentaDAO.obtenerPorProducto(productoNuevo.getId());
                datosUnidades.setAll(unidades);
            }else {
                datosUnidades.clear();
            }
        });

        tablaUnidades.setItems(datosUnidades);

        VBox vboxTablas = new VBox(tabla,tablaUnidades);
        vboxTablas.setPadding(new Insets(10));


        this.setTop(toolbar);
        this.setCenter(vboxTablas);

    }

}