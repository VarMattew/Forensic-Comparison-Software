module hu.mate.frc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens hu.mate.frc.controler to javafx.fxml;
    opens hu.mate.frc.view to javafx.fxml;
    
    exports hu.mate.frc.controler; 
    exports hu.mate.frc.view;
}