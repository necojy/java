import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class ImageNextSetting extends JButton implements ActionListener {    

    private Image userChoosImage;
    private Image newImage = null;
    public int x,y,width,heigth;

    public ImageNextSetting(String label,Image userChoosImage) {
        super(label);
        this.userChoosImage = userChoosImage;
        addActionListener(this);
    }

    public void setUserChooseImage(Image userChoosImage)
    {
        this.userChoosImage = userChoosImage;
    }

   

    public void actionPerformed(ActionEvent e) {
    	
    	//關閉舊的JFrame
    	JFrame olderFrame = (JFrame) SwingUtilities.getWindowAncestor((JButton) e.getSource());
        olderFrame.dispose();

        //設置frame視窗
        JFrame frame = new JFrame("設定圖片樣式");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        //讀入圖片選擇器
        //ImageFileChooser imageFileChooser = new ImageFileChooser();
        //Image userChoosImage = imageFileChooser.openImage(frame);
        JLabel imageLabel = new JLabel(new ImageIcon(userChoosImage));

        //--------------------------------------------------------------//
        //設置panel
        JPanel picturePanel = new JPanel(); 
        JPanel functionPanel = new JPanel(new GridLayout(4,0));
        JPanel movePanel = new MouseDrawingTool(imageLabel);
        JPanel newImagePanel = new JPanel();

        //設定panel尺寸
        picturePanel.setBounds(0, 0, width - 200, height);
        functionPanel.setBounds(width-200, 0, 200, height-50);
        movePanel.setBounds(0, 0, width - 200, height);
        newImagePanel.setBounds(0, 0,width - 200, height);
        newImagePanel.setOpaque(false);

        //--------------------------------------------------------------//
        
        //      
        picturePanel.add(imageLabel);
        picturePanel.add(movePanel);
        movePanel.setOpaque(false);

        //--------------------------------------------------------------//

        //設定功能區按鈕  
        functionPanel.setBackground(new Color(245, 222, 179));

        //1.設定旋轉按鈕
        ImageIcon rotateIcon = new ImageIcon("src\\rotate.png");
        JButton rotateButton = new JButton("");     
        rotateButton.setIcon(rotateIcon);
        rotateButton.setBackground(null);
        rotateButton.setOpaque(false);
        rotateButton.setSize(200, 500);
        double[] degrees = {0.0}; // 使用陣列包裝以便在內部類別中修改
        functionPanel.add(rotateButton);
        
        //2.新增圖片
        ImageIcon addImageIcon = new ImageIcon("src\\addImage.png");
        JButton newImageButton = new JButton("");
        newImageButton.setIcon(addImageIcon);
        newImageButton.setBackground(null);
        newImageButton.setOpaque(false);
        newImageButton.setSize(200, 500);
        functionPanel.add(newImageButton);

        //3.儲存圖片
        ImageIcon saveIcon = new ImageIcon("src\\save.png");
        JButton SaveButton = new JButton("");
        SaveButton.setIcon(saveIcon);
        SaveButton.setBackground(null);
        SaveButton.setOpaque(false);
        functionPanel.add(SaveButton);

        //4.設定回主頁面按鈕
        ImageIcon HomeIcon = new ImageIcon("src\\BackHome.png");
        Home homeButton = new Home("");
        homeButton.setIcon(HomeIcon);
        homeButton.setBackground(null);
        homeButton.setOpaque(false);
        functionPanel.add(homeButton);

        //--------------------------------------------------------------//
        //用layeredPane統一全部panel，進行分層管理
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(functionPanel, Integer.valueOf(1));
        layeredPane.add(picturePanel, Integer.valueOf(2));
        layeredPane.add(movePanel, Integer.valueOf(10));
        


        //將layeredPane新增到frame
        frame.getContentPane().add(layeredPane);

        //讀取圖片成功
        if (userChoosImage != null) 
        {
            //實做旋轉按鈕功能
            rotateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                // 設定旋轉的角度
                degrees[0] = (degrees[0] + 15.0) % 360;
                ImageIcon icon = new ImageIcon();
                icon.setImage(userChoosImage);
                BufferedImage image = (BufferedImage) icon.getImage();
                BufferedImage rotatedImage = RotateImage.rotate(image, degrees[0]); // 呼叫 rotateImage 方法進行圖片旋轉
                imageLabel.setIcon(new ImageIcon(rotatedImage)); // 更新圖片Icon
                userChoosImage = (Image)rotatedImage;
                movePanel.removeAll(); // 移除舊的元件
                MouseDrawingTool newMovePanel = new MouseDrawingTool(imageLabel);
                newMovePanel.setOpaque(false);
                movePanel.add(newMovePanel); // 添加新的 MouseDrawingTool 到 movePanel
                movePanel.setOpaque(false);

                picturePanel.removeAll();
                picturePanel.setOpaque(false);
                picturePanel.add(imageLabel);
                picturePanel.add(movePanel);

                layeredPane.remove(picturePanel);
                layeredPane.remove(movePanel);
                layeredPane.add(movePanel, Integer.valueOf(10));
                layeredPane.add(picturePanel, Integer.valueOf(2));
                }
            });

        }
        //讀取圖片失敗
        else
        {
            JOptionPane.showMessageDialog(null, "讀取圖片失敗!");
        }

        //--------------------------------------------------------------//  

        //當newImageButton被點擊時，新增圖片
        newImageButton.addActionListener(new ActionListener() 
        {   
            @Override
            public void actionPerformed(ActionEvent e) {
                // 建立檔案選擇器對話框
                if(newImage == null)
                {
                    ImageFileChooser imageFileChooser = new ImageFileChooser();
                    newImage = imageFileChooser.openImage(frame);  
                    JLabel newImageLabel = new JLabel(new ImageIcon(newImage));
                    newImagePanel.add(newImageLabel);
                    newImagePanel.setOpaque(false);
                    newImagePanel.setBackground(null);

                    JPanel movePanel2 = new MouseDrawingTool(newImageLabel);
                    movePanel2.setOpaque(false);
                    newImagePanel.add(movePanel2);
                    layeredPane.add(newImagePanel,Integer.valueOf(100));
              	
              
                }
                else 
                {
                    JOptionPane.showMessageDialog(null,"目前只能新增一張圖片");
                }
            }
        });

        //當SaveButton被點擊時，儲存圖片
        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
            	 BufferedImage image = null;
            	 Rectangle rectangle = new Rectangle(0, 100, width - 200, height-150);
            	 try 
                 {
                     image = new Robot().createScreenCapture(rectangle);
                 }
            	 catch (AWTException e1) 
                 {
                     e1.printStackTrace(); 
                 }
            	 
            	// 建立檔案選擇器對話框
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("儲存圖片"); // 設定對話框標題
                
    
                // 顯示對話框，讓使用者選擇儲存的檔案路徑和檔名
                int userSelection = fileChooser.showSaveDialog(frame);
    
                if (userSelection == JFileChooser.APPROVE_OPTION) 
                {
                    File fileToSave = fileChooser.getSelectedFile();
                    String filePath = fileToSave.getAbsolutePath();
    
                	Rectangle rectangle1 = new Rectangle(x, y, width-200, height-5);

                    // 執行儲存圖片的程式碼
                    try 
                    {
                    	
                        File outputImage = new File(filePath);
                        ImageIO.write((BufferedImage)image, "png", outputImage);
                        //ImageIO.write(image, "png", outputImage);
                        JOptionPane.showMessageDialog(null, "圖片儲存成功！");
                    }
//                    catch (AWTException e1) 
//                    {
//                        e1.printStackTrace(); 
//                    }
                    catch (IOException ex) 
                    {
                        JOptionPane.showMessageDialog(null, "儲存圖片失敗：" + ex.getMessage());
                    }
                }
            }
        });

         //當homeButton被點擊時，關閉目前frame
         homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });



        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}