package hu.mate.frc.model;

import java.io.File;
import java.nio.file.Path;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageModel {
    
    private final File sourceFile;
    private String displayName;

    private final Image originalImage;    // A módosítatlan eredeti, a visszaállításhoz
    private WritableImage editableImage;  // Ezen dolgozunk

    private final int width;
    private final int height;

    public ImageModel(File file) {
        this.sourceFile = file;
        this.displayName = Path.of(file.getAbsolutePath()).getFileName().toString();

        this.originalImage = new Image(file.toURI().toString());

        this.width = (int) originalImage.getWidth();
        this.height = (int) originalImage.getHeight();

        
        PixelReader pixelReader = originalImage.getPixelReader();
        this.editableImage = new WritableImage(pixelReader, width, height);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Image getOriginalImage() {
        return originalImage;
    }

    public WritableImage getEditableImage() {
        return editableImage;
    }

    public void setBrightness(double adjustment) {
        PixelReader pixelReader = originalImage.getPixelReader(); // Mindig az eredetiből olvasunk!
        PixelWriter pixelWriter = editableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // 1. Eredeti szín kiolvasása
                Color originalColor = pixelReader.getColor(x, y);

                // 2. Új színkomponensek kiszámítása
                double newRed = originalColor.getRed() + adjustment;
                double newGreen = originalColor.getGreen() + adjustment;
                double newBlue = originalColor.getBlue() + adjustment;

                // 3. "Csíptetés" (Clamping): Biztosítjuk, hogy az értékek 0.0 és 1.0 között maradjanak
                newRed = Math.max(0, Math.min(1, newRed));
                newGreen = Math.max(0, Math.min(1, newGreen));
                newBlue = Math.max(0, Math.min(1, newBlue));

                // 4. Új szín létrehozása és visszaírása a szerkeszthető képre
                Color newColor = new Color(newRed, newGreen, newBlue, originalColor.getOpacity());
                pixelWriter.setColor(x, y, newColor);
            }
        }
        
        System.out.println("Brightness set by: " + adjustment);
    }

    public void setContrast(double adjustment) {
        PixelReader pixelReader = originalImage.getPixelReader(); // Mindig az eredetiből olvasunk!
        PixelWriter pixelWriter = editableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // 1. Eredeti szín kiolvasása
                Color originalColor = pixelReader.getColor(x, y);

                // 2. Új színkomponensek kiszámítása
                double newRed = (originalColor.getRed() - 0.5) * adjustment + 0.5;
                double newGreen = (originalColor.getGreen() - 0.5) * adjustment + 0.5;
                double newBlue = (originalColor.getBlue() - 0.5) * adjustment + 0.5;

                // 3. "Csíptetés" (Clamping): Biztosítjuk, hogy az értékek 0.0 és 1.0 között maradjanak
                newRed = Math.max(0, Math.min(1, newRed));
                newGreen = Math.max(0, Math.min(1, newGreen));
                newBlue = Math.max(0, Math.min(1, newBlue));

                // 4. Új szín létrehozása és visszaírása a szerkeszthető képre
                Color newColor = new Color(newRed, newGreen, newBlue, originalColor.getOpacity());
                pixelWriter.setColor(x, y, newColor);
            }
        }
        System.out.println("Contrast set by: " + adjustment);
    }

    public void setRed(double adjustment) {
        PixelReader pixelReader = originalImage.getPixelReader();
        PixelWriter pixelWriter = editableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color originalColor = pixelReader.getColor(x, y);
                double newRed = originalColor.getRed() + adjustment;
                newRed = Math.max(0, Math.min(1, newRed));
                Color newColor = new Color(newRed, originalColor.getGreen(), originalColor.getBlue(), originalColor.getOpacity());
                pixelWriter.setColor(x, y, newColor);
            }
        }
        System.out.println("Red channel adjusted by: " + adjustment);
    }

    public void setGreen(double adjustment) {
        PixelReader pixelReader = originalImage.getPixelReader();
        PixelWriter pixelWriter = editableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color originalColor = pixelReader.getColor(x, y);
                double newGreen = originalColor.getGreen() + adjustment;
                newGreen = Math.max(0, Math.min(1, newGreen));
                Color newColor = new Color(originalColor.getRed(), newGreen, originalColor.getBlue(), originalColor.getOpacity());
                pixelWriter.setColor(x, y, newColor);
            }
        }
        System.out.println("Green channel adjusted by: " + adjustment);
    }

    public void setBlue(double adjustment) {
        PixelReader pixelReader = originalImage.getPixelReader();
        PixelWriter pixelWriter = editableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color originalColor = pixelReader.getColor(x, y);
                double newBlue = originalColor.getBlue() + adjustment;
                newBlue = Math.max(0, Math.min(1, newBlue));
                Color newColor = new Color(originalColor.getRed(), originalColor.getGreen(), newBlue, originalColor.getOpacity());
                pixelWriter.setColor(x, y, newColor);
            }
        }
        System.out.println("Blue channel adjusted by: " + adjustment);
    }

    public void setSaturation(double adjustment) {
        PixelReader pixelReader = originalImage.getPixelReader();
        PixelWriter pixelWriter = editableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color originalColor = pixelReader.getColor(x, y);
                double luminance = 0.299 * originalColor.getRed() + 0.587 * originalColor.getGreen() + 0.114 * originalColor.getBlue();
                
                double newRed = luminance + (originalColor.getRed() - luminance) * adjustment;
                double newGreen = luminance + (originalColor.getGreen() - luminance) * adjustment;
                double newBlue = luminance + (originalColor.getBlue() - luminance) * adjustment;

                newRed = Math.max(0, Math.min(1, newRed));
                newGreen = Math.max(0, Math.min(1, newGreen));
                newBlue = Math.max(0, Math.min(1, newBlue));

                Color newColor = new Color(newRed, newGreen, newBlue, originalColor.getOpacity());
                pixelWriter.setColor(x, y, newColor);
            }
        }
        System.out.println("Saturation set by: " + adjustment);
    }
}
