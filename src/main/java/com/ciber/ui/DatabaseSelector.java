package com.ciber.ui;

import com.ciber.config.DatabaseConfig;
import com.ciber.config.DatabaseType;
import javafx.scene.control.ChoiceDialog;

import java.util.Arrays;
import java.util.Optional;

public class DatabaseSelector {

    public static void seleccionar() {

        ChoiceDialog<String> dialog = new ChoiceDialog<>("MySQL",
                Arrays.asList("MySQL", "SQLite"));

        dialog.setTitle("Seleccionar Base de Datos");
        dialog.setHeaderText("¿Con qué base de datos quieres trabajar?");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {

            if (result.get().equals("SQLite")) {
                DatabaseConfig.setDatabase(DatabaseType.SQLITE);
            } else {
                DatabaseConfig.setDatabase(DatabaseType.MYSQL);
            }

        } else {
            System.exit(0);
        }
    }
}