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
        JPanel newImagePanel = new JPanel();

        
        //設定panel尺寸
        picturePanel.setBounds(0, 30, width - 200, height);
        functionPanel.setBounds(width-200, 0, 200, height);
        drawingPanel.setBounds(0, 0, width - 200, height);
        newImagePanel.setBounds(0, 0,width - 200, height);
        newImagePanel.setOpaque(false);

//--------------------------------------------------------------//
        //將圖片跟圖片畫板新增至drawingPanel裡面       
        picturePanel.add(imageLabel);

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
        
        //4.將畫下的內容儲存
        JButton savePaint = new JButton("儲存筆跡");
        drawingPanel.add(savePaint);
        savePaint.setVisible(false);

//--------------------------------------------------------------//

        //設定功能區按鈕  
        functionPanel.setLayout(new GridLayout(6,0));
        //1.設定開啟繪畫按鈕的位置及大小
        JToggleButton openDrawButton = new JToggleButton("開啟繪圖模式");
        //openDrawButton.setSize(200, 500);
        drawingPanel.setOpaque(false);//初始化 drawingPanel(繪畫區域)，設置為不可見且透明
        functionPanel.add(openDrawButton);
        //2.儲存圖片
        JButton SaveButton = new JButton("匯出圖片");
        functionPanel.add(SaveButton);
        //3.進行圖片調整
        ImageNextSetting nexSetButton = new ImageNextSetting("下一步",userChoosImage);
        functionPanel.add(nexSetButton);
        //4.設定回主頁面按鈕
        Home homeButton = new Home("回主頁面");
        functionPanel.add(homeButton);
        

//--------------------------------------------------------------//
        //用layeredPane統一全部panel，進行分層管理
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(functionPanel, Integer.valueOf(1));
        layeredPane.add(picturePanel, Integer.valueOf(2));
        layeredPane.add(drawingPanel, Integer.valueOf(9));
 
        //將layeredPane新增到frame
        frame.getContentPane().add(layeredPane);

        
        //讀取圖片成功
        if (userChoosImage != null) 
        {
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
                        savePaint.setVisible(true);
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
                        savePaint.setVisible(false);
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
            //繪畫模式內->4.筆跡储存  
            savePaint.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
       
                    Rectangle rectangle = new Rectangle(0, 100, width - 200, height-150);
        
                    // 執行儲存圖片的程式碼
                    try 
                    {
                        BufferedImage image = new Robot().createScreenCapture(rectangle);
                        userChoosImage = (Image) image;
                        imageLabel.setIcon(new ImageIcon(userChoosImage));

                        //重新排序functionPanel的位置
                        //有bug
                        functionPanel.removeAll();
                        functionPanel.add(openDrawButton);
                        functionPanel.add(SaveButton);

                        //將更改後的圖片傳到下一個介面；
                        ImageNextSetting nexSetButton = new ImageNextSetting("下一步",userChoosImage);
                        functionPanel.add(nexSetButton);
                        functionPanel.add(homeButton);

                        openDrawButton.setSelected(false);
                        colorButton.setVisible(false);
                        pencilButton.setVisible(false);
                        thicknessButton.setVisible(false);
                        savePaint.setVisible(false);
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