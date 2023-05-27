import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceRecognition extends JButton implements ActionListener {

    private int count = 1;

    public FaceRecognition(String label) {
        super(label);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "png", "bmp");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String imgFile = selectedFile.getAbsolutePath();
            System.out.println("Selected file: " + imgFile);

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat src = Imgcodecs.imread(imgFile);

            String cmlFile = "xml/lbpcascade_frontalface.xml";
            CascadeClassifier cc = new CascadeClassifier(cmlFile);

            MatOfRect faceDetection = new MatOfRect();
            cc.detectMultiScale(src, faceDetection);
            System.out.println(String.format("Detected faces: %d", faceDetection.toArray().length));

            for (Rect rect : faceDetection.toArray()) {
                Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(255, 255, 255), 3);

                Mat cropped = new Mat(src, rect);

                String outputFolder = "C:\\output";
                File folder = new File(outputFolder);
                if (!folder.exists()) {
                    folder.mkdir();
                }

                String outFileName = getUniqueFileName(outputFolder, "test");
                Imgcodecs.imwrite(outFileName, cropped);
                System.out.println("Face saved to " + outFileName);
                JOptionPane.showMessageDialog(null, "辨識完畢，圖片存放在\"C:\\\\output\"");
            }
            System.out.println("Image Detection finished!");

        } else {
            System.out.println("No file selected.");
        }
    }
    private String getUniqueFileName(String folder, String baseName) {
        String extension = ".png";
        String fileName = baseName + count + extension;
        String filePath = folder + File.separator + fileName;

        while (new File(filePath).exists()) {
            count++;
            fileName = baseName + count + extension;
            filePath = folder + File.separator + fileName;
        }
        count++;
        return filePath;
    }
}
