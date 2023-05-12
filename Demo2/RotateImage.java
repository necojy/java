import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImage;

import java.awt.geom.AffineTransform;

public class RotateImage {
    public static BufferedImage rotate(BufferedImage image, double degrees) {

        // 計算旋轉後的圖片尺寸
        int width = image.getWidth();
        int height = image.getHeight();
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int rotatedWidth = (int) Math.round(width * cos + height * sin);
        int rotatedHeight = (int) Math.round(height * cos + width * sin);

        // 建立旋轉後的圖片物件
        BufferedImage rotatedImage = new BufferedImage(rotatedWidth, rotatedHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        // 執行圖片旋轉
        AffineTransform transform = new AffineTransform();
        transform.translate((rotatedWidth - width) / 2, (rotatedHeight - height) / 2);
        transform.rotate(radians, width / 2, height / 2);
        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }
}