package hu.mate.frc.controler;

import java.io.File;
import java.io.IOException;

import hu.mate.frc.view.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

     private static String startupFile = null;

    @Override
    public void start(Stage primaryStage) {
        try {
            // FXML fájl betöltése
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hu/mate/frc/view/main_window.fxml"));
            Parent root = loader.load();

            // Ablak címének beállítása
            primaryStage.setTitle("Forensic Comparison Software");
            primaryStage.setScene(new Scene(root));

            // Scene létrehozása és hozzáadása a Stage-hez
            primaryStage.setMaximized(true);
            primaryStage.setResizable(false);
            primaryStage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/hu/mate/frc/view/icons/logo.png")));

            // Az ablak megjelenítése
            primaryStage.show();
        if (startupFile != null) {
            File fileToOpen = new File(startupFile);
            if (fileToOpen.exists()) {
                MainWindowController controller = loader.getController();
                controller.addStartupFile(fileToOpen);
            }
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            // Az első argumentum lesz a fájl elérési útja
            startupFile = args[0];
            System.out.println("Indítás a következő fájllal: " + startupFile);
        } else {
            System.out.println("Indítás fájl nélkül.");
        }

        launch(args);
    }
}
