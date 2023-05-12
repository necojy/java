import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;

public class ImageEditor extends JToggleButton implements ActionListener {


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
        Image userChoosImage = imageFileChooser.openImage(frame);
        JLabel imageLabel = new JLabel(new ImageIcon(userChoosImage));

//--------------------------------------------------------------//
        //設置panel
        JPanel picturePanel = new JPanel(); 
        JPanel functionPanel = new JPanel(new GridLayout(5,0));
        JPanel drawingPanel = new MouseDrawingTool(null);
        JPanel movePanel = new MouseDrawingTool(imageLabel);
        JPanel newImagePanel = new JPanel();

        
        //設定panel尺寸
        picturePanel.setBounds(0, 0, width - 200, height);
        functionPanel.setBounds(width-200, 0, 200, height);
        drawingPanel.setBounds(0, 0, width - 200, height);
        movePanel.setBounds(0, 0, width - 200, height);
        newImagePanel.setBounds(0, 0,width - 200, height);
        newImagePanel.setOpaque(false);

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
//--------------------------------------------------------------//

        //設定功能區按鈕  
        //functionPanel.setLayout(new GridLayout(4,0));
        //1.設定開啟繪畫按鈕的位置及大小
        JToggleButton openDrawButton = new JToggleButton("開啟繪圖模式");
        openDrawButton.setSize(200, 500);
        drawingPanel.setOpaque(false);//初始化 drawingPanel(繪畫區域)，設置為不可見且透明
        functionPanel.add(openDrawButton);
        //2.設定旋轉按鈕
        JButton rotateButton = new JButton("旋轉");
        rotateButton.setSize(200, 500);
        double[] degrees = {0.0}; // 使用陣列包裝以便在內部類別中修改
        functionPanel.add(rotateButton);
        //3.新增圖片
        JButton newImageButton = new JButton("新增圖片");
        newImageButton.setSize(200, 500);
        functionPanel.add(newImageButton);
        //4.設定回主頁面按鈕
        Home homeButton = new Home("回主頁面");
        homeButton.setSize(200, 500);
        functionPanel.add(homeButton);

//--------------------------------------------------------------//
        //用layeredPane統一全部panel，進行分層管理
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(functionPanel, Integer.valueOf(1));
        layeredPane.add(picturePanel, Integer.valueOf(2));
        layeredPane.add(drawingPanel, Integer.valueOf(9));
        layeredPane.add(movePanel, Integer.valueOf(10));
        //layeredPane.add(newImagePanel,Integer.valueOf(12));
        
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
            public void actionPerformed(ActionEvent e) 
            {     
                Image newImage = imageFileChooser.openImage(frame);  
                if(newImage == null) System.out.println("NO");
                JLabel newImageLabel = new JLabel(new ImageIcon(newImage));

                //還須待修改
                // JPanel xxx = new MouseDrawingTool(newImageLabel);
                // xxx.setBounds(0, 0, width - 200, height);
                // xxx.setOpaque(false);
                // newImagePanel.add(xxx);
                // newImagePanel.add(newImageLabel);
                // layeredPane.add(xxx,Integer.valueOf(14));
                // layeredPane.add(newImagePanel,Integer.valueOf(3));
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


    
//--------------------------------------------------------------//

    public static void main(String[] args) {

    }

}