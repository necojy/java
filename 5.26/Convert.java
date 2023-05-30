import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Convert {
    public static BufferedImage convertImage = null;

    // public static void main(String[] args) {
    //     try {
    //     File file = new File("man.png");
    //     InputStream is;
    //     is = new FileInputStream(file);
    //     BufferedImage bi = ImageIO.read(is);
    //     transferAlpha(bi);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     } 
    // }

    public static BufferedImage transferAlpha(BufferedImage bi) {
        //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //File file = new File("man.png");
        //InputStream is;
        try {
            //is = new FileInputStream(file);
           // BufferedImage bi = ImageIO.read(is);
            Image image = (Image) bi;   
            ImageIcon imageIcon = new ImageIcon(image);
            BufferedImage bufferedImage = new BufferedImage(
                imageIcon.getIconWidth(), 
                imageIcon.getIconHeight(),
                BufferedImage.TYPE_4BYTE_ABGR
            );
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
            int alpha = 0;
            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage.getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage.getWidth(); j2++) {
                    int rgb = bufferedImage.getRGB(j2, j1);

                    int R = (rgb & 0xff0000) >> 16;
                    int G = (rgb & 0xff00) >> 8;
                    int B = (rgb & 0xff);
                    if (R == 255 && G == 255 && B == 255) { 
                        rgb = (alpha << 24) | (0x00ffffff & ~rgb);
                    }

                    bufferedImage.setRGB(j2, j1, rgb);
                }
            }

            g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
            //ImageIO.write(bufferedImage, "png", new File("man.png"));
            convertImage = bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return convertImage;
        //return byteArrayOutputStream.toByteArray();
    }
}
