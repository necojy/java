import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDragProgram extends JFrame {
    private BufferedImage image;
    private JLabel imageLabel;
    private Point initialMousePosition;
    private Point initialImagePosition;

    public ImageDragProgram() {
        setTitle("Image Drag Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        imageLabel = new JLabel();
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialMousePosition = e.getPoint();
                initialImagePosition = imageLabel.getLocation();
            }
        });

        imageLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - initialMousePosition.x;
                int deltaY = e.getY() - initialMousePosition.y;
                int newX = initialImagePosition.x + deltaX;
                int newY = initialImagePosition.y + deltaY;
                imageLabel.setLocation(newX, newY);
            }
        });

        add(imageLabel);
        pack();
        setVisible(true);
    }

    public void loadImage(Image image) {
        ImageIcon icon = new ImageIcon(image);
        imageLabel.setIcon(icon);
        imageLabel.setSize(icon.getIconWidth(), icon.getIconHeight());
        setSize(icon.getIconWidth(), icon.getIconHeight());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ImageDragProgram program = new ImageDragProgram();
                //program.loadImage("xx.png");
            }
        });
    }
}
