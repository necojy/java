import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class ImageEditor extends JButton implements ActionListener {

    int pictureNumber = 0;
    Image userChoosImage;
  
    public ImageEditor(String label) {
        super(label);
        addActionListener(this);   
    }

    public void actionPerformed(ActionEvent e) {

        // 開啟時顯示注意事項
        JOptionPane.showMessageDialog(null, "請選擇符合格式的圖片 EX:PNG , JPG ");

        

        //設置frame視窗
        JFrame frame = new JFrame("Mouse Drawing Tool");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        //讀入圖片選擇器
        ImageFileChooser imageFileChooser = new ImageFileChooser();
        userChoosImage = imageFileChooser.openImage(frame);
        JLabel imageLabel = new JLabel(new ImageIcon(userChoosImage));

//--------------------------------------------------------------//
        //設置panel
        JPanel picturePanel = new JPanel(); 
        JPanel functionPanel = new JPanel(new GridLayout(5,0));
        JPanel drawingPanel = new MouseDrawingTool(null);
        JPanel movePanel = new MouseDrawingTool(imageLabel);
        JPanel newImagePanel = new JPanel();
        JPanel topArea = new JPanel();

        
        //設定panel尺寸
        picturePanel.setBounds(0, 80, width - 200, height);
        functionPanel.setBounds(width-200, 0, 200, height-50);
        drawingPanel.setBounds(0, 0, width - 200, height);
        
        newImagePanel.setBounds(0, 0,width - 200, height);
        movePanel.setBounds(0, 0, width - 200, height);
        newImagePanel.setOpaque(false);
        
        topArea.setBounds(0,0,width,height);
        
        //設定panel背景           
        topArea.setBackground(new Color(250, 250, 240));
        picturePanel.setBackground(new Color(250, 250, 240));
//--------------------------------------------------------------//
        //將圖片跟圖片畫板新增至drawingPanel裡面       
        picturePanel.add(imageLabel);
        picturePanel.add(movePanel);
        movePanel.setOpaque(false);
//--------------------------------------------------------------//

        //初始化繪畫區按鈕
        //1.自定義顏色   
        JButton colorButton = new JButton("自定義顏色");
        colorButton.setBackground(Color.WHITE);
        colorButton.setVisible(false);
        drawingPanel.add(colorButton);
        //2.鉛筆
        JButton pencilButton = new JButton("鉛筆");
        pencilButton.setBackground(Color.WHITE);
        pencilButton.setVisible(false);
        drawingPanel.add(pencilButton);
        //3.畫筆粗細設定
        JButton thicknessButton = new JButton("自定義畫筆粗細");
        thicknessButton.setBackground(Color.WHITE);
        drawingPanel.add(thicknessButton);
        thicknessButton.setVisible(false);  
        //4.放大圖片
        JButton bigButton = new JButton("放大");
        drawingPanel.add(bigButton);
        bigButton.setVisible(false); 
        //5.縮小圖片
        JButton smallButton = new JButton("縮小");
        drawingPanel.add(smallButton);
        smallButton.setVisible(false); 
        //6.將畫下的內容儲存
        JButton savePaint = new JButton("儲存筆跡");
        drawingPanel.add(savePaint);
        savePaint.setVisible(false);

//--------------------------------------------------------------//

        //設定功能區按鈕  
        functionPanel.setLayout(new GridLayout(4,0));
        //functionPanel.setOpaque(false);
        functionPanel.setBackground(new Color(245, 222, 179));
        
        //1.設定開啟繪畫按鈕的位置及大小
        ImageIcon drawIcon = new ImageIcon("src\\pan.png");
        JToggleButton openDrawButton = new JToggleButton("");
        drawingPanel.setOpaque(false);//初始化 drawingPanel(繪畫區域)，設置為不可見且透明
        openDrawButton.setIcon(drawIcon);
        openDrawButton.setBackground(null);
        openDrawButton.setOpaque(false);
        functionPanel.add(openDrawButton);
        //2.儲存圖片
        ImageIcon saveIcon = new ImageIcon("src\\save.png");
        JButton SaveButton = new JButton("");
        SaveButton.setIcon(saveIcon);
        SaveButton.setBackground(null);
        SaveButton.setOpaque(false);
        functionPanel.add(SaveButton);
        //3.下一步，進行圖片調整
        ImageIcon nextIcon = new ImageIcon("src\\next.png");
        ImageNextSetting nexSetButton = new ImageNextSetting("",userChoosImage);
        nexSetButton.setIcon(nextIcon);
        nexSetButton.setBackground(null);
        nexSetButton.setOpaque(false);
        functionPanel.add(nexSetButton);
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
        layeredPane.add(drawingPanel, Integer.valueOf(9));
        layeredPane.add(movePanel, Integer.valueOf(10));
        layeredPane.add(topArea, Integer.valueOf(0));
        //將layeredPane新增到frame
        frame.getContentPane().add(layeredPane);

        
        //讀取圖片成功
        if (userChoosImage != null) 
        {
        	//關閉舊的JFrame
        	JFrame olderFrame = (JFrame) SwingUtilities.getWindowAncestor((JButton) e.getSource());
            olderFrame.dispose();
            
            //繪畫模式按鈕功能
            openDrawButton.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    if (openDrawButton.isSelected()) 
                    {
                        JOptionPane.showMessageDialog(null, "繪圖模式已開啟!");
                        ((MouseDrawingTool) drawingPanel).setDrawMode(true);
                        layeredPane.remove(drawingPanel);
                        layeredPane.add(drawingPanel, Integer.valueOf(11));
                        colorButton.setVisible(true);
                        pencilButton.setVisible(true);
                        thicknessButton.setVisible(true);
                        bigButton.setVisible(true);
                        smallButton.setVisible(true);
                        savePaint.setVisible(true);
                        topArea.setBackground(new Color(227,168,105));
                    }
                    else 
                    {
                        // 如果 JToggleButton 沒有被選取，就隱藏 JPanel
                        JOptionPane.showMessageDialog(null, "繪圖模式已關閉!");
                        ((MouseDrawingTool) drawingPanel).setDrawMode(false);
                        layeredPane.remove(drawingPanel);
                        layeredPane.add(drawingPanel, Integer.valueOf(9));
                        colorButton.setVisible(false);
                        pencilButton.setVisible(false);
                        thicknessButton.setVisible(false);
                        bigButton.setVisible(false);
                        smallButton.setVisible(false);
                        savePaint.setVisible(false);
                        topArea.setBackground(new Color(250, 250, 240));
                    }
                }
            }); 
            
            //繪畫模式內->1.自定義顏色
            colorButton.addActionListener(new ActionListener() 
            {   
                @Override
                public void actionPerformed(ActionEvent e) 
                {     
                    int R =Integer.parseInt(JOptionPane.showInputDialog("請輸入Rvalue值："));
                    int G =Integer.parseInt(JOptionPane.showInputDialog("請輸入Gvalue值："));
                    int B =Integer.parseInt(JOptionPane.showInputDialog("請輸入Bvalue值："));
                        
                    ((MouseDrawingTool) drawingPanel).setColor(new Color(R,G,B));
                }
            });

            //繪畫模式內->2.鉛筆
            pencilButton.addActionListener(new ActionListener() 
            {   
                @Override
                public void actionPerformed(ActionEvent e) 
                {     
                    ((MouseDrawingTool) drawingPanel).setColor(Color.BLACK);
                }
            });

             //繪畫模式內->3.畫筆粗細設定                     
            thicknessButton.addActionListener(new ActionListener() 
            {   
                @Override
                public void actionPerformed(ActionEvent e) 
                {     
                    int num =Integer.parseInt(JOptionPane.showInputDialog("請輸入畫筆大小："));
                    // 設定畫筆粗細
                    ((MouseDrawingTool) drawingPanel).setBrushSize(num);
                }
            });  
            //繪畫模式內->4.放大圖片
            bigButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 假設您已經有了 JLabel imageLabel 和 Image image

                    double scale = 1.2; // 縮放因子，每次放大20%
                    int newWidth = (int) (userChoosImage.getWidth(null) * scale);
                    int newHeight = (int) (userChoosImage.getHeight(null) * scale);

                    userChoosImage = userChoosImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
                    imageLabel.setIcon(new ImageIcon(userChoosImage));
                }
            });
            //繪畫模式內->5.縮小圖片
            smallButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 假設您已經有了 JLabel imageLabel 和 Image image

                    double scale = 0.8; // 縮放因子，每次放大20%
                    int newWidth = (int) (userChoosImage.getWidth(null) * scale);
                    int newHeight = (int) (userChoosImage.getHeight(null) * scale);

                    userChoosImage = userChoosImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
                    imageLabel.setIcon(new ImageIcon(userChoosImage));
                }
            });
            
            //繪畫模式內->6.筆跡储存  
            savePaint.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                	((MouseDrawingTool) drawingPanel).setDrawMode(false);
                	layeredPane.remove(drawingPanel);
                    layeredPane.add(drawingPanel, Integer.valueOf(9));
                    
                    Point location = imageLabel.getLocation();
                    int x = location.x;
                    int y = location.y;
                    int userWidth = userChoosImage.getWidth(null);
                    int userHeight = userChoosImage.getHeight(null);
                    
                	Rectangle rectangle = new Rectangle(x+5, y+110, userWidth, userHeight);
                    
                    // 執行儲存圖片的程式碼
                    try 
                    {
                        BufferedImage image = new Robot().createScreenCapture(rectangle);
                        userChoosImage = (Image) image;
                        imageLabel.setIcon(new ImageIcon(userChoosImage));

                        //重新排序functionPanel的位置
                        functionPanel.removeAll();
                        functionPanel.add(openDrawButton);
                        functionPanel.add(SaveButton);

                        //將更改後的圖片傳到下一個介面；
                        ImageNextSetting nexSetButton = new ImageNextSetting("下一步",userChoosImage);
                        nexSetButton.setIcon(nextIcon);
                        nexSetButton.setBackground(null);
                        nexSetButton.setOpaque(false);
                        functionPanel.add(nexSetButton);
                        functionPanel.add(homeButton);

                        openDrawButton.setSelected(false);
                        colorButton.setVisible(false);
                        pencilButton.setVisible(false);
                        thicknessButton.setVisible(false);
                        bigButton.setVisible(false); 
                        smallButton.setVisible(false); 
                        savePaint.setVisible(false);
                        topArea.setBackground(new Color(250, 250, 240));
                    }
                    catch (AWTException e1) 
                    {
                        e1.printStackTrace(); 
                    }
                }
            });
        
        }
        //讀取圖片失敗
        else
        {
            JOptionPane.showMessageDialog(null, "讀取圖片失敗!");
        }
        

//--------------------------------------------------------------//  
    
   

        //當SaveButton被點擊時，儲存圖片
        SaveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
        	BufferedImage image = null;
        	
        	Point location = imageLabel.getLocation();
        	int x = location.x;
            int y = location.y;
            int userWidth = userChoosImage.getWidth(null);
            int userHeight = userChoosImage.getHeight(null);
            
        	Rectangle rectangle = new Rectangle(x+5, y+110, userWidth, userHeight);
        	
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

                // 執行儲存圖片的程式碼
                try 
                {
                    //BufferedImage image = new Robot().createScreenCapture(rectangle);
                    File outputImage = new File(filePath);
                    ImageIO.write((BufferedImage)image, "png", outputImage);
                    JOptionPane.showMessageDialog(null, "圖片儲存成功！");
                }
                
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