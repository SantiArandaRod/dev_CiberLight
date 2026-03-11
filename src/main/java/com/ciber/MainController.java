package com.ciber;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane contentArea;

    private void loadView(String view){

        try{

            AnchorPane pane = FXMLLoader.load(
                    getClass().getResource("/view/" + view)
            );

            contentArea.getChildren().setAll(pane);

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    private void openDashboard(){
        loadView("dashboard.fxml");
    }

    @FXML
    private void openLotes(){
        loadView("lotes.fxml");
    }

    @FXML
    private void openTecnicos(){
        loadView("tecnicos.fxml");
    }

    @FXML
    private void openMateriales(){
        loadView("materiales.fxml");
    }

    @FXML
    private void openAlertas(){
        loadView("alertas.fxml");
    }
}