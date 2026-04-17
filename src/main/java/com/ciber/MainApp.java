package com.ciber;

import com.ciber.config.DatabaseConfig;
import com.ciber.config.DatabaseType;
import com.ciber.ui.DatabaseSelector;
import com.ciber.util.DBInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // 🔥 seleccionar DB antes de todo
        DatabaseSelector.seleccionar();

        if (DatabaseConfig.getDatabase() == DatabaseType.SQLITE) {
            DBInitializer.initSQLite();
        }

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/main.fxml")
        );

        Parent root = loader.load();

        Scene scene = new Scene(root, 720, 400);

        stage.setTitle("CiberLightCol - Sistema de Producción");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}