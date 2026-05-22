package com.ciber.controller;

import com.ciber.model.EstadoLote;
import com.ciber.model.Lote;
import com.ciber.model.Material;
import com.ciber.model.Tecnico;
import com.ciber.service.LoteService;
import com.ciber.service.MaterialService;
import com.ciber.service.TecnicoService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class DashboardController {

    @FXML private Label lblLotesActivos;
    @FXML private Label lblMaterialesBajos;
    @FXML private Label lblTecnicosActivos;
    @FXML private Label lblLotesFinalizados;
    @FXML private TableView<Material> tablaAlertasMaterial;
    @FXML private TableColumn<Material, String> colMaterial;
    @FXML private TableColumn<Material, Integer> colStock;
    @FXML private TableColumn<Material, Integer> colMinimo;

    private final LoteService loteService = new LoteService();
    private final MaterialService materialService = new MaterialService();
    private final TecnicoService tecnicoService = new TecnicoService();

    @FXML
    public void initialize() {
        colMaterial.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colMinimo.setCellValueFactory(new PropertyValueFactory<>("stockMinimo"));

        cargarMetricas();
    }

    private void cargarMetricas() {
        List<Lote> lotes = loteService.listar();
        List<Material> materiales = materialService.listar();
        List<Tecnico> tecnicos = tecnicoService.listar();

        List<Material> materialesBajos = materiales.stream()
                .filter(materialService::hayAlerta)
                .toList();

        long lotesFinalizados = lotes.stream()
                .filter(lote -> lote.getEstado() == EstadoLote.FINALIZADO)
                .count();

        long tecnicosActivos = tecnicos.stream()
                .filter(Tecnico::isActivo)
                .count();

        lblLotesActivos.setText(String.valueOf(lotes.size()));
        lblMaterialesBajos.setText(String.valueOf(materialesBajos.size()));
        lblTecnicosActivos.setText(String.valueOf(tecnicosActivos));
        lblLotesFinalizados.setText(String.valueOf(lotesFinalizados));
        tablaAlertasMaterial.setItems(FXCollections.observableArrayList(materialesBajos));
    }
}
