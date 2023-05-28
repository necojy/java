package ff.ForFace;
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
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
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

                // 銳化裁減的圖片
                Mat sharpened = new Mat();
                Imgproc.GaussianBlur(cropped, sharpened, new Size(0, 0), 3);
                Core.addWeighted(cropped, 1.5, sharpened, -0.5, 0, sharpened);

                // 調整圖片尺寸
                Mat resized = new Mat();
                Size newSize = new Size(556, 538);
                Imgproc.resize(sharpened, resized, newSize);

                String outputFolder = "C:\\output";
                File folder = new File(outputFolder);
                if (!folder.exists()) {
                    folder.mkdir();
                }

             // 繪製圓形
                int centerX = resized.cols() / 2;
                int centerY = resized.rows() / 2;
                int radius = 278;
                Scalar circleColor = new Scalar(255, 255, 255); // 白色

                // 創建遮罩圖像
                Mat mask = new Mat(resized.size(), CvType.CV_8UC1, Scalar.all(0));
                Imgproc.circle(mask, new Point(centerX, centerY), radius, new Scalar(255), -1);

                // 在原圖像上使用遮罩圖像
                Mat result1 = new Mat();
                Core.bitwise_and(resized, resized, result1, mask);

                // 將圓外的部分設置為白色
                Mat outsideMask = new Mat(resized.size(), CvType.CV_8UC1, Scalar.all(255));
                Core.subtract(outsideMask, mask, outsideMask);
                Imgproc.cvtColor(outsideMask, outsideMask, Imgproc.COLOR_GRAY2BGR);
                Core.bitwise_or(result1, outsideMask, result1);

                // 將處理後的圖像保存
                String outFileName = getUniqueFileName(outputFolder, "test");
                Imgcodecs.imwrite(outFileName, result1);
                System.out.println("Face saved to " + outFileName);

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
