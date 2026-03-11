module com.ciber.dev_ciberlight {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ciber.dev_ciberlight to javafx.fxml;
    exports com.ciber.dev_ciberlight;
}