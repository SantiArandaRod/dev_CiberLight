package com.ciber.controller;

import com.ciber.model.Material;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
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

    @FXML
    public void initialize() {

        // conectar columnas con el modelo
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colStockMinimo.setCellValueFactory(new PropertyValueFactory<>("stockMinimo"));

        tablaMateriales.setItems(materiales);
    }

    @FXML
    private void agregarStock() {

        Material m = tablaMateriales.getSelectionModel().getSelectedItem();

        if (m != null) {
            m.sumarStock(1);
            tablaMateriales.refresh();
        }
    }

    @FXML
    private void eliminarStock() {

        Material m = tablaMateriales.getSelectionModel().getSelectedItem();

        if (m != null) {
            m.restarStock(1);
            tablaMateriales.refresh();
        }
    }

    @FXML
    private void agregarMaterial() {

        TextInputDialog dialogNombre = new TextInputDialog();
        dialogNombre.setTitle("Nuevo Material");
        dialogNombre.setHeaderText("Registrar material");
        dialogNombre.setContentText("Nombre del material:");

        String nombre = dialogNombre.showAndWait().orElse(null);

        if(nombre == null || nombre.isEmpty()){
            return;
        }

        TextInputDialog dialogStock = new TextInputDialog();
        dialogStock.setTitle("Stock inicial");
        dialogStock.setHeaderText(null);
        dialogStock.setContentText("Stock inicial:");

        int stock = Integer.parseInt(dialogStock.showAndWait().orElse("0"));

        TextInputDialog dialogMin = new TextInputDialog();
        dialogMin.setTitle("Stock mínimo");
        dialogMin.setHeaderText(null);
        dialogMin.setContentText("Stock mínimo:");

        int stockMin = Integer.parseInt(dialogMin.showAndWait().orElse("0"));

        int id = materiales.size() + 1;

        Material nuevo = new Material(id, nombre, stock, stockMin);

        materiales.add(nuevo);
    }

    @FXML
    private void eliminarMaterial() {

        Material m = tablaMateriales.getSelectionModel().getSelectedItem();

        if (m != null) {
            materiales.remove(m);
        }
    }

}