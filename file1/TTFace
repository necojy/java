package ff.ForFace;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

//Sometimes the default string will not come out, then remove the rewrite
//Or just import what you need through the menu below
import java.io.File;
import java.util.Random;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
public class TTFace {
	public static void main(String[] args) {
        // 開啟檔案選擇器
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "png", "bmp");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String imgFile = selectedFile.getAbsolutePath();
            System.out.println("Selected file: " + imgFile);

            // 現有的代碼
            // ...
    		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    		
    		////下兩行，隨機抓圖片
//    		int randomIndex = new Random().nextInt(10,15);
//    		String imgFile = "images/"+Integer.toString(randomIndex)+".jpg";
//    		String imgFile = "images/11.jpg";
    		Mat src = Imgcodecs.imread(imgFile);
    		////
    		
    		//now adds an xml file that tells the app how a face look like
    		//this serves to help app to detect img faces
    		String cmlFile = "xml/lbpcascade_frontalface.xml";
    		CascadeClassifier cc = new CascadeClassifier(cmlFile);
    		
    		
    		MatOfRect faceDetection = new MatOfRect();
    	
    		cc.detectMultiScale(src, faceDetection);
    		System.out.println(String.format("Detected faces: %d", faceDetection.toArray().length));
    		
    		int count = 0;
    		for(Rect rect: faceDetection.toArray()) {
    			Imgproc.rectangle(src, new Point(rect.x,rect.y), new Point (rect.x + rect.width, rect.y +rect.height), new Scalar(0,0,255), 3);
    			// Crop the detected face and save it as a new image
    			Mat cropped = new Mat(src, rect);
    			String outFileName = "images/face_"+ Integer.toString(count) + ".jpg";
    			// 如果文件已存在，則先刪除它
                File file = new File(outFileName);
                //直接開路徑看，確實會出現變化
                if (file.exists()) {
                    file.delete();
                }
                //
    			Imgcodecs.imwrite(outFileName, cropped);
    			System.out.println("Face saved to " + outFileName);
    			count++;
    		}
    		
    		Imgcodecs.imwrite("images/test_out.jpg",src);
    		System.out.println("Image Detection finished!");

        } else {
            System.out.println("No file selected.");
        }
    }

}
