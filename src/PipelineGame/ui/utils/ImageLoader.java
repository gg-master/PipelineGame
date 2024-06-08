package PipelineGame.ui.utils;

import PipelineGame.AppSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    public static BufferedImage loadImage(String imageName) throws IOException {
        URL imageURL = ImageLoader.class.getResource(AppSettings.pathToImages + imageName);
        if (imageURL != null) {
            return ImageIO.read(imageURL);
        }
        throw new IOException("Image not found");
    }

    public static ImageIcon loadAsImageIcon(String imageName) {
        try {
            return new ImageIcon(loadImage(imageName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ImageIcon();
    }
}