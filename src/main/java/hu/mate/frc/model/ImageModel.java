package hu.mate.frc.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
                Color editableColor = editableImage.getPixelReader().getColor(x, y);
                double newRed = originalColor.getRed() + adjustment;
                newRed = Math.max(0, Math.min(1, newRed));
                Color newColor = new Color(newRed, editableColor.getGreen(), editableColor.getBlue(), editableColor.getOpacity());
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
                Color editableColor = editableImage.getPixelReader().getColor(x, y);
                double newGreen = originalColor.getGreen() + adjustment;
                newGreen = Math.max(0, Math.min(1, newGreen));
                Color newColor = new Color(editableColor.getRed(), newGreen, editableColor.getBlue(), editableColor.getOpacity());
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
                Color editableColor = editableImage.getPixelReader().getColor(x, y);
                double newBlue = originalColor.getBlue() + adjustment;
                newBlue = Math.max(0, Math.min(1, newBlue));
                Color newColor = new Color(editableColor.getRed(), editableColor.getGreen(), newBlue, editableColor.getOpacity());
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
    
    public List<int[]> updateHistogram() {
        int[] redCounts = new int[256];
        int[] greenCounts = new int[256];
        int[] blueCounts = new int[256];

        PixelReader pixelReader = editableImage.getPixelReader();
        for (int y = 0; y < editableImage.getHeight(); y++) {
            for (int x = 0; x < editableImage.getWidth(); x++) {
                Color color = pixelReader.getColor(x, y);
                redCounts[(int) (color.getRed() * 255)]++;
                greenCounts[(int) (color.getGreen() * 255)]++;
                blueCounts[(int) (color.getBlue() * 255)]++;
            }
        }
        List<int[]> histogram = new ArrayList<>();
        histogram.add(redCounts);
        histogram.add(greenCounts);
        histogram.add(blueCounts);
        return histogram;
    }

    public void saveToFile(File destinationFile) {
        try {
            BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(editableImage, null);

            javax.imageio.ImageIO.write(bufferedImage, getFileExtension(destinationFile), destinationFile);

            System.out.println("Image saved to: " + destinationFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Failed to save image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        // Ha nincs kiterjesztés, legyen a PNG az alapértelmezett.
        return "png";
    }
}
