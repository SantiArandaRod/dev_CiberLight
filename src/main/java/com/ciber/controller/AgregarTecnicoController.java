package com.ciber.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AgregarTecnicoController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtEspecialidad;

    private TecnicosController tecnicosController;

    public void setTecnicosController(TecnicosController controller){
        this.tecnicosController = controller;
    }

    @FXML
    private void guardarTecnico(){

        String nombre = txtNombre.getText();
        String especialidad = txtEspecialidad.getText();

        if(nombre.isEmpty() || especialidad.isEmpty()){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Complete todos los campos.");
            alert.showAndWait();
            return;
        }

        tecnicosController.agregarTecnico(nombre,especialidad);

        Stage stage =
                (Stage) txtNombre.getScene().getWindow();

        stage.close();

    }

}