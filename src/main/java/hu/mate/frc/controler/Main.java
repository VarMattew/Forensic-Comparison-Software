package hu.mate.frc.controler;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // FXML fájl betöltése
            Parent root = FXMLLoader.load(getClass().getResource("/hu/mate/frc/view/main_window.fxml"));

            // Ablak címének beállítása
            primaryStage.setTitle("Forensic Comparison Software");
            primaryStage.setScene(new Scene(root));

            // Scene létrehozása és hozzáadása a Stage-hez
            primaryStage.setMaximized(true);
            primaryStage.setResizable(false);

            // Az ablak megjelenítése
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
