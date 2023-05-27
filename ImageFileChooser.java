import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

import java.awt.event.*;
import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ImageFileChooser {
    public Image openImage(JFrame parent) {

        Image image = null;
        int result;
        //檢查用戶是否按下"確定"按鈕
        while (image == null) {
            JFileChooser fileChooser = new JFileChooser();
            result = fileChooser.showOpenDialog(parent);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    image = ImageIO.read(selectedFile);
                    if (image == null) throw new Exception();
                } catch (Exception ex) {
                	JOptionPane.showMessageDialog(null,"請選擇圖片格式");
                    //System.out.printf("\n Please Choose Image File \n");
                }
            } else if (result == JFileChooser.CANCEL_OPTION) {
                System.out.printf("\n User Cancel Selected \n");
                //parent.dispose(); 
                break;
            }
        }

        return image;
    }

    public List<Image> openMulitImages(JFrame parent) {
        List<Image> images = new ArrayList<>();
        int result;
        // 檢查用戶是否按下"確定"按鈕
        while (true) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            result = fileChooser.showOpenDialog(parent);

            if (result == JFileChooser.APPROVE_OPTION) {
                File[] selectedFiles = fileChooser.getSelectedFiles();
                for (File file : selectedFiles) {
                    try {
                        Image image = ImageIO.read(file);
                        if (image != null) {
                            images.add(image);
                        }
                    } catch (IOException ex) {
                        System.out.println("無法讀取圖片：" + file.getName());
                    }
                }
                break;
            } else if (result == JFileChooser.CANCEL_OPTION) {
                System.out.println("用戶取消選擇");
                break;
            }
        }

        return images;
    }
}