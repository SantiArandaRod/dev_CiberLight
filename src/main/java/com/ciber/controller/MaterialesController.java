package com.ciber.controller;

import com.ciber.model.Material;
import com.ciber.service.MaterialService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MaterialesController {

    @FXML
    private TableView<Material> tablaMateriales;

    @FXML
    private TableColumn<Material, Integer> colId;

    @FXML
    private TableColumn<Material, String> colNombre;

    @FXML
    private TableColumn<Material, Integer> colStock;

    @FXML
    private TableColumn<Material, Integer> colStockMinimo;

    private ObservableList<Material> materiales = FXCollections.observableArrayList();

    private MaterialService service = new MaterialService();

    @FXML
    public void initialize() {

        // conectar columnas con el modelo
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colStockMinimo.setCellValueFactory(new PropertyValueFactory<>("stockMinimo"));

        // 🔥 alerta visual de stock bajo
        colStock.setCellFactory(column -> new TableCell<Material, Integer>() {
            @Override
            protected void updateItem(Integer stock, boolean empty) {
                super.updateItem(stock, empty);

                if (empty) {
                    setText(null);
                    setStyle("");
                    return;
                }

                Material m = getTableView().getItems().get(getIndex());
                setText(String.valueOf(stock));

                if (m.getStock() < m.getStockMinimo()) {
                    setStyle("-fx-background-color: #ffcccc;");
                } else {
                    setStyle("");
                }
            }
        });

        cargarMateriales();
    }

    // =========================
    // CARGA DE DATOS
    // =========================

    private void cargarMateriales() {
        materiales.clear();
        materiales.addAll(service.listar());
        tablaMateriales.setItems(materiales);
    }

    // =========================
    // ENTRADA DE STOCK
    // =========================

    @FXML
    private void agregarStock() {

        Material m = tablaMateriales.getSelectionModel().getSelectedItem();

        if (m == null) {
            mostrarError("Selecciona un material");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Entrada de stock");
        dialog.setHeaderText("Cantidad a ingresar");

        dialog.showAndWait().ifPresent(valor -> {
            try {
                int cantidad = Integer.parseInt(valor);

                service.entradaStock(m.getId(), cantidad);

                cargarMateriales();

            } catch (Exception e) {
                mostrarError(e.getMessage());
            }
        });
    }

    // =========================
    // SALIDA DE STOCK
    // =========================

    @FXML
    private void eliminarStock() {

        Material m = tablaMateriales.getSelectionModel().getSelectedItem();

        if (m == null) {
            mostrarError("Selecciona un material");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Salida de stock");
        dialog.setHeaderText("Cantidad a retirar");

        dialog.showAndWait().ifPresent(valor -> {
            try {
                int cantidad = Integer.parseInt(valor);

                service.salidaStock(m.getId(), cantidad);

                cargarMateriales();

            } catch (Exception e) {
                mostrarError(e.getMessage());
            }
        });
    }

    // =========================
    // CREAR MATERIAL
    // =========================

    @FXML
    private void agregarMaterial() {

        TextInputDialog dialogNombre = new TextInputDialog();
        dialogNombre.setTitle("Nuevo Material");
        dialogNombre.setHeaderText("Registrar material");
        dialogNombre.setContentText("Nombre:");

        String nombre = dialogNombre.showAndWait().orElse(null);

        if (nombre == null || nombre.isEmpty()) {
            return;
        }

        try {
            TextInputDialog dialogStock = new TextInputDialog("0");
            dialogStock.setHeaderText("Stock inicial");
            int stock = Integer.parseInt(dialogStock.showAndWait().orElse("0"));

            TextInputDialog dialogMin = new TextInputDialog("0");
            dialogMin.setHeaderText("Stock mínimo");
            int stockMin = Integer.parseInt(dialogMin.showAndWait().orElse("0"));

            Material nuevo = new Material(0, nombre, stock, stockMin);

            service.crear(nuevo);

            cargarMateriales();

        } catch (Exception e) {
            mostrarError("Datos inválidos");
        }
    }

    // =========================
    // ELIMINAR MATERIAL
    // =========================

    @FXML
    private void eliminarMaterial() {

        Material m = tablaMateriales.getSelectionModel().getSelectedItem();

        if (m == null) {
            mostrarError("Selecciona un material");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Eliminar material");
        confirm.setContentText("¿Seguro que deseas eliminar este material?");

        confirm.showAndWait().ifPresent(resp -> {
            if (resp == ButtonType.OK) {
                service.eliminar(m.getId());
                cargarMateriales();
            }
        });
    }

    // =========================
    // MENSAJES DE ERROR
    // =========================

    private void mostrarError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}