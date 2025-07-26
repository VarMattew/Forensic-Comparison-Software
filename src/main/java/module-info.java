module hu.mate.frc {
    requires javafx.controls;
    requires javafx.fxml;

    opens hu.mate.frc.controler to javafx.fxml;

    exports hu.mate.frc.controler; 
}