package com.ciber.controller;

import com.ciber.model.RestauracionItem;
import com.ciber.service.RestauracionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RestauracionController {

    @FXML private TableView<RestauracionItem> tablaRestauracion;
    @FXML private TableColumn<RestauracionItem, String> colTipo;
    @FXML private TableColumn<RestauracionItem, Integer> colId;
    @FXML private TableColumn<RestauracionItem, String> colNombre;
    @FXML private TableColumn<RestauracionItem, String> colAccion;
    @FXML private TableColumn<RestauracionItem, String> colFecha;

    private final RestauracionService service = new RestauracionService();
    private final ObservableList<RestauracionItem> items = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tabla"));
        colId.setCellValueFactory(new PropertyValueFactory<>("registroId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colAccion.setCellValueFactory(new PropertyValueFactory<>("accion"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        cargarRestaurables();
    }

    @FXML
    private void restaurarSeleccionado() {
        RestauracionItem item = tablaRestauracion.getSelectionModel().getSelectedItem();

        if (item == null) {
            mostrarError("Selecciona un registro para restaurar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Restaurar registro");
        confirm.setContentText("Deseas restaurar " + item.getTabla() + " #" + item.getRegistroId() + "?");

        confirm.showAndWait().ifPresent(resp -> {
            if (resp == ButtonType.OK) {
                try {
                    service.restaurar(item);
                    cargarRestaurables();
                } catch (Exception e) {
                    mostrarError(e.getMessage());
                }
            }
        });
    }

    @FXML
    private void refrescar() {
        cargarRestaurables();
    }

    private void cargarRestaurables() {
        try {
            items.setAll(service.listarRestaurables());
            tablaRestauracion.setItems(items);
        } catch (Exception e) {
            items.clear();
            tablaRestauracion.setItems(items);
            mostrarError("No se pudo conectar a MongoDB. Verifica la IP, el puerto y que Mongo este iniciado.");
        }
    }

    private void mostrarError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
