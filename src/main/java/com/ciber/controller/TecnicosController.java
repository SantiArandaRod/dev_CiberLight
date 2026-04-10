package com.ciber.controller;

import com.ciber.model.Tecnico;
import com.ciber.service.TecnicoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TecnicosController {

    @FXML
    private TableView<Tecnico> tablaTecnicos;

    @FXML
    private TableColumn<Tecnico,Integer> colId;

    @FXML
    private TableColumn<Tecnico,String> colNombre;

    @FXML
    private TableColumn<Tecnico,String> colEspecialidad;

    @FXML
    private TableColumn<Tecnico,Integer> colLotes;

    @FXML
    private TableColumn<Tecnico,Boolean> colEstado;

    private ObservableList<Tecnico> listaTecnicos =
            FXCollections.observableArrayList();

    // 🔥 NUEVO: service
    private TecnicoService service = new TecnicoService();

    @FXML
    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        colLotes.setCellValueFactory(new PropertyValueFactory<>("lotesActivos"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("activo"));

        // Mostrar Activo/Inactivo
        colEstado.setCellFactory(col -> new TableCell<Tecnico, Boolean>() {
            @Override
            protected void updateItem(Boolean activo, boolean empty) {
                super.updateItem(activo, empty);

                if(empty){
                    setText(null);
                }else{
                    setText(activo ? "Activo" : "Inactivo");
                }
            }
        });

        tablaTecnicos.setItems(listaTecnicos);

        // Filas en gris si están inactivos
        tablaTecnicos.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Tecnico tecnico, boolean empty) {
                super.updateItem(tecnico, empty);

                if(tecnico == null || empty){
                    setStyle("");
                }
                else if(!tecnico.isActivo()){
                    setStyle("-fx-background-color: #f2f2f2;");
                }
                else{
                    setStyle("");
                }
            }
        });

        // 🔥 Cargar desde BD
        cargarDatos();
    }

    // =========================
    // 🔄 CARGAR DATOS
    // =========================
    private void cargarDatos(){

        listaTecnicos.clear();
        listaTecnicos.addAll(service.listar());

    }

    // =========================
    // ➕ AGREGAR
    // =========================
    public void agregarTecnico(String nombre,String especialidad){

        service.crear(nombre, especialidad);
        cargarDatos();

    }

    // =========================
    // 🪟 ABRIR FORM
    // =========================
    @FXML
    private void abrirAgregarTecnico(){

        try{

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/agregar_tecnico.fxml")
            );

            Parent root = loader.load();

            AgregarTecnicoController controller = loader.getController();
            controller.setTecnicosController(this);

            Stage stage = new Stage();
            stage.setTitle("Agregar Técnico");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    // =========================
    // ❌ INACTIVAR
    // =========================
    @FXML
    private void inactivarTecnico(){

        Tecnico tecnico =
                tablaTecnicos.getSelectionModel().getSelectedItem();

        if(tecnico == null){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Seleccione un técnico.");
            alert.showAndWait();
            return;
        }

        if(!tecnico.isActivo()){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("El técnico ya está inactivo.");
            alert.showAndWait();
            return;
        }

        // 🔥 ahora se hace en BD
        service.inactivar(tecnico.getId());

        cargarDatos();

    }

}