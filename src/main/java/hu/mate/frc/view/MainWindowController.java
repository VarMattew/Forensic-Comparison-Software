package hu.mate.frc.view;

import java.io.File;
import java.util.HashMap;

import hu.mate.frc.model.ImageModel;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainWindowController {

    HashMap<String, ImageModel> imageMap = new HashMap<>();

    @FXML
    private Canvas canvas;
    @FXML
    private GraphicsContext gc;
    @FXML
    private ComboBox<String> imageSelector;

    @FXML
    private Slider brightnessSlider;
    @FXML
    private Slider contrastSlider;
    @FXML
    private Slider redSlider;
    @FXML
    private Slider greenSlider;
    @FXML
    private Slider blueSlider;
    @FXML
    private Slider saturationSlider;

    private ImageModel currentImage;
    
    @FXML
    private void onLoadImageClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Képfájlok", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );

        // 4. Fájlválasztó megjelenítése
        Stage primaryStage = (Stage) canvas.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        // 5. Eredmény feldolgozása
        if (selectedFile != null) {
            try {
                currentImage = new ImageModel(selectedFile);
                imageMap.put(selectedFile.getName(), currentImage);
                System.out.println("Loaded Succesfully: " + selectedFile.getName());
                
                imageSelector.getItems().add(selectedFile.getName());
                imageSelector.setValue(selectedFile.getName()); // Automatikusan kiválasztjuk az új képet
            } catch (Exception ex) {
                System.err.println("Exception during loading: " + ex.getMessage());
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Exception during loading");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void drawImageOnCanvas(Image image) {
        // Arányos méretezés, hogy a kép beleférjen a Canvas-ba és középre kerüljön
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        double imgWidth = image.getWidth();
        double imgHeight = image.getHeight();

        // Kiszámoljuk a méretezési arányt
        double scale = Math.min(canvasWidth / imgWidth, canvasHeight / imgHeight);

        double newWidth = imgWidth * scale;
        double newHeight = imgHeight * scale;

        // Kiszámoljuk a pozíciót a középre igazításhoz
        double x = (canvasWidth - newWidth) / 2;
        double y = (canvasHeight - newHeight) / 2;

        gc = canvas.getGraphicsContext2D();
        // 1. Letöröljük a vászon korábbi tartalmát
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        // 2. Rárajzoljuk az új képet a kiszámolt méretben és pozícióban
        gc.drawImage(image, x, y, newWidth, newHeight);
    }

    @FXML
    private void onImageSelected() {
        String selectedImageName = imageSelector.getValue();
        if (selectedImageName != null && imageMap.containsKey(selectedImageName)) {
            ImageModel imageModel = imageMap.get(selectedImageName);
            Image image = imageModel.getEditableImage();
            currentImage = imageModel; // Frissítjük a currentImage-t
            drawImageOnCanvas(image);
        } else {
            System.err.println("No image selected or image not found in map.");
        }
    }

    @FXML
    private void onBrightnessChanged() {
        currentImage.setBrightness(brightnessSlider.getValue());
        refreshCanvas();
    }

    @FXML
    private void onContrastChanged() {
        currentImage.setContrast(contrastSlider.getValue());
        refreshCanvas();
    }

    @FXML
    private void onRedChanged() {
        if(currentImage == null) return;

        currentImage.setRed(redSlider.getValue());
        refreshCanvas();
    }

    @FXML
    private void onGreenChanged() {
        if(currentImage == null) return;

        currentImage.setGreen(greenSlider.getValue());
        refreshCanvas();
    }

    @FXML
    private void onBlueChanged() {
        if(currentImage == null) return;

        currentImage.setBlue(blueSlider.getValue());
        refreshCanvas();
    }

    @FXML
    private void onSaturationChanged() {
        if(currentImage == null) return;

        currentImage.setSaturation(saturationSlider.getValue());
        refreshCanvas();
    }

    public void refreshCanvas() {
        if (currentImage != null) {
            Image image = currentImage.getEditableImage();
            drawImageOnCanvas(image);
        } else {
            System.err.println("No current image to refresh.");
        }
    }
}
