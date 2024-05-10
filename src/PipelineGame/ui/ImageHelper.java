package PipelineGame.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageHelper {
    public static ImageIcon getScaledIcon(ImageIcon icon, Dimension targetSize) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(targetSize.width, targetSize.height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static BufferedImage imageIconToBufferedImage(ImageIcon icon) {
        BufferedImage bufferedImage = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics g = bufferedImage.getGraphics();
        // Paint the ImageIcon onto the BufferedImage
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        return bufferedImage;
    }

    public static ImageIcon flipHorizontal(ImageIcon icon) {
        // Convert ImageIcon to BufferedImage
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        icon.paintIcon(null, g2d, 0, 0);
        g2d.dispose();

        // Create transformation for horizontal reflection
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);

        // Apply transformation
        BufferedImage mirroredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = mirroredImage.createGraphics();
        g.drawImage(image, tx, null);
        g.dispose();

        // Convert BufferedImage back to ImageIcon
        return new ImageIcon(mirroredImage);
    }

    public static ImageIcon flipVertical(ImageIcon icon) {
        // Convert ImageIcon to BufferedImage
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        icon.paintIcon(null, g2d, 0, 0);
        g2d.dispose();

        // Create transformation for vertical reflection
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -image.getHeight(null));

        // Apply transformation
        BufferedImage mirroredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = mirroredImage.createGraphics();
        g.drawImage(image, tx, null);
        g.dispose();

        // Convert BufferedImage back to ImageIcon
        return new ImageIcon(mirroredImage);
    }

    public static ImageIcon rotateIcon(ImageIcon icon) {
        Image image = icon.getImage();

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setColor(new Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        AffineTransform transform = new AffineTransform();
        transform.translate(bufferedImage.getWidth() / 2.0, bufferedImage.getHeight() / 2.0);
        transform.rotate(Math.PI / 2); // 90 degrees
        transform.translate(-bufferedImage.getHeight() / 2.0, -bufferedImage.getWidth() / 2.0);
        g2d.drawImage(image, transform, null);

        g2d.dispose();

        return new ImageIcon(bufferedImage);
    }
}
