import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

import java.util.ArrayList;
import java.util.List;

public class AutoSentImage extends JButton implements ActionListener {

    public AutoSentImage(String label) {
        super(label);
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            //獲取chrome插件，並開啟chrome瀏覽器
            //System.setProperty("webdriver.chrome.driver", "C:\\Users\\USER\\Desktop\\Demo1\\所需物件\\Chrome\\chromedriver_win32\\chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", "src\\chromedriver.exe");
            ////////自動傳送圖片////////
            Robot robot = new Robot();
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            //用選擇器選擇照片
            JFrame frame = new JFrame();

            ImageFileChooser imageFileChooser = new ImageFileChooser();
            List<Image> userChosenImages = imageFileChooser.openMulitImages(frame);

            if (userChosenImages != null && !userChosenImages.isEmpty()) {

                // 創建一個JOptionPane來請求使用者輸入帳號
                //String userAccount = JOptionPane.showInputDialog(frame, "請輸入帳號:");
                //String userPassword = JOptionPane.showInputDialog(frame, "請輸入密碼:");
                String sendName = null;
                sendName = JOptionPane.showInputDialog(frame, "請輸入傳送對象名稱:");
                
                if(sendName != null)
                {
                	//開啟messenger網站，等待三秒完後登入
                    WebDriver driver = new ChromeDriver();
                    driver.manage().window().maximize();
                    driver.get("https://www.messenger.com/?locale=zh_TW");
                    Thread.sleep(2500);
                    //driver.findElement(By.id("email")).sendKeys(userAccount);
                    //driver.findElement(By.name("pass")).sendKeys(userPassword);
                    driver.findElement(By.id("email")).sendKeys("qwe017953@gmail.com");
                    driver.findElement(By.name("pass")).sendKeys("zxcvbnm,./123");
                    driver.findElement(By.name("login")).click();

                    // 等待三秒後，點選欲選擇的欄位
                    Thread.sleep(2500);
                    //WebElement listItem = driver.findElement(By.xpath("//span[text()='曹宥翔']"));
                   // WebElement listItem = driver.findElement(By.xpath("//span[text()='李小名']"));
                    //WebElement listItem = driver.findElement(By.xpath("//span[text()='Allen Wu']"));
                    WebElement listItem = driver.findElement(By.xpath("//span[text()='" + sendName + "']"));
                    listItem.click();//點擊空白處
                    Thread.sleep(2000);
                    WebElement inputDiv = driver.findElement(By.xpath("//div[@aria-label='訊息']"));
                    inputDiv.click();

                    for (Image userChoosImage : userChosenImages)
                    {
                        ImageIcon icon = new ImageIcon();
                        icon.setImage(userChoosImage);
                        BufferedImage image = (BufferedImage) icon.getImage();
                        TransferableImage transferable = new TransferableImage(image);
                        clipboard.setContents(transferable, null);

                        System.out.println("This spamming will start in 5 seconds");
                        Thread.sleep(4000);
                        inputDiv.click();
                        robot.keyPress(KeyEvent.VK_CONTROL);
                        robot.keyPress(KeyEvent.VK_V);
                        robot.keyRelease(KeyEvent.VK_V);
                        robot.keyRelease(KeyEvent.VK_CONTROL);

                        Thread.sleep(1000);
                        robot.keyPress(KeyEvent.VK_ENTER);
                        robot.keyRelease(KeyEvent.VK_ENTER);
                    }
                    JOptionPane.showMessageDialog(null, "傳輸完畢!");
                    driver.close();
                }
                else
                {
                	JOptionPane.showMessageDialog(null, "請輸入傳送對象名稱");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "讀取圖片失敗!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static class TransferableImage implements Transferable {
        private Image image;

        public TransferableImage(Image image) {
            this.image = image;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (flavor.equals(DataFlavor.imageFlavor) && image != null) {
                return image;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.imageFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(DataFlavor.imageFlavor);
        }
    }
}