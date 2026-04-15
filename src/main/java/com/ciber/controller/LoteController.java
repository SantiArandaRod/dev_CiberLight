package com.ciber.controller;

import com.ciber.model.*;
import com.ciber.service.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.time.LocalDate;

public class LoteController {

    @FXML private TableView<Lote> tablaLotes;

    @FXML private TableColumn<Lote, String> colNombre;
    @FXML private TableColumn<Lote, String> colCliente;
    @FXML private TableColumn<Lote, String> colTecnico;
    @FXML private TableColumn<Lote, String> colEstado;

    @FXML private ComboBox<Tecnico> comboTecnicos;

    private ObservableList<Lote> lista = FXCollections.observableArrayList();

    private LoteService service = new LoteService();
    private TecnicoService tecnicoService = new TecnicoService();

    @FXML
    public void initialize() {

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        colTecnico.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getTecnico().getNombre())
        );

        colEstado.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEstado().name())
        );

        cargarTecnicos();
        cargarLotes();
    }

    private void cargarTecnicos() {

        comboTecnicos.setItems(
                FXCollections.observableArrayList(tecnicoService.listar())
        );

        comboTecnicos.setConverter(new StringConverter<>() {
            @Override
            public String toString(Tecnico t) {
                return t != null ? t.getNombre() : "";
            }

            @Override
            public Tecnico fromString(String s) {
                return null;
            }
        });
    }

    private void cargarLotes() {
        lista.clear();
        lista.addAll(service.listar());
        tablaLotes.setItems(lista);
    }

    @FXML
    private void crearLote() {

        Tecnico t = comboTecnicos.getValue();

        if (t == null) {
            mostrarError("Selecciona técnico");
            return;
        }

        TextInputDialog dialogNombre = new TextInputDialog();
        dialogNombre.setHeaderText("Nombre del lote");
        String nombre = dialogNombre.showAndWait().orElse("");

        if (nombre.isEmpty()) return;

        TextInputDialog dialogCliente = new TextInputDialog();
        dialogCliente.setHeaderText("Cliente");
        String cliente = dialogCliente.showAndWait().orElse("");

        if (cliente.isEmpty()) return;

        Lote l = new Lote(
                0,
                nombre,
                cliente,
                LocalDate.now(),
                null,
                EstadoLote.EN_PROCESO,
                t,
                true
        );

        service.crear(l);
        cargarLotes();
    }

    @FXML
    private void finalizarLote() {

        Lote l = tablaLotes.getSelectionModel().getSelectedItem();

        if (l == null) return;

        service.finalizar(l.getId());

        cargarLotes();
    }

    @FXML
    private void eliminarLote() {

        Lote l = tablaLotes.getSelectionModel().getSelectedItem();

        if (l == null) return;

        service.eliminar(l.getId());

        cargarLotes();
    }

    private void mostrarError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}