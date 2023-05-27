import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import javax.imageio.ImageIO;

public class Home extends JToggleButton implements ActionListener {

    public Home(String label) {
        super(label);
        addActionListener(this);
    }


    public void actionPerformed(ActionEvent e) {
    	
    	//關閉舊的JFrame
    	//JFrame olderFrame = (JFrame) SwingUtilities.getWindowAncestor((JButton) e.getSource());
        //olderFrame.dispose();
    	
    	JFrame frame = new JFrame("Home page");

        //動態調整JFrame為使用者螢幕大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        //放置主頁面的背景的圖片
        ImageIcon icon = new ImageIcon("src\\Home.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel label = new JLabel(scaledIcon);
        
        JPanel backgroundImagePanel = new JPanel();
        backgroundImagePanel.setBounds(0,0,width,height);
        backgroundImagePanel.setOpaque(false);
        backgroundImagePanel.add(label);
     //////////////////////////////////////////////
        
        //設定主頁面
        JPanel homepanel = new JPanel(new GridLayout(3,0));
        homepanel.setBounds(25,height/2-100,width/2,height);
        //1.人臉辨識
        JPanel facePanel = new JPanel();
        facePanel.setBounds(25,height/2-150,800,150);
        facePanel.setOpaque(false);
        facePanel.setBackground(Color.getHSBColor(0, 0, 0));
        
        ImageIcon FaceIcon = new ImageIcon("src\\AI_Icon.png");
        FaceRecognition FaceRecognitionButton = new FaceRecognition("");
        FaceRecognitionButton.setIcon(FaceIcon);
        FaceRecognitionButton.setBackground(null);
        FaceRecognitionButton.setOpaque(false);
        facePanel.add(FaceRecognitionButton);
        
        //2.進行圖片編輯
        
        JPanel ImageEditorPanel = new JPanel();
        ImageEditorPanel.setBounds(25,height/2-150+150,800,150);
        ImageEditorPanel.setOpaque(false);
        ImageEditorPanel.setBackground(Color.getHSBColor(0, 0, 0));
        
        ImageIcon ImageIcon = new ImageIcon("src\\ImageEditor.png");
        ImageEditor imageEditorButton = new ImageEditor("");
        imageEditorButton.setIcon(ImageIcon);
        imageEditorButton.setBackground(null);
        imageEditorButton.setOpaque(false);
        ImageEditorPanel.add(imageEditorButton);


        // //3.自動分享圖片
        
        JPanel AutoSendPanel = new JPanel();
        AutoSendPanel.setBounds(25,height/2-150+300,800,150);
        AutoSendPanel.setOpaque(false);
        AutoSendPanel.setBackground(Color.getHSBColor(0, 0, 0));
        
        ImageIcon fbIcon = new ImageIcon("src\\fbShare.png");
        AutoSentImage autoSentButton = new AutoSentImage("");
        autoSentButton.setIcon(fbIcon);
        autoSentButton.setBackground(null);
        autoSentButton.setOpaque(false);
        AutoSendPanel.add(autoSentButton);
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(backgroundImagePanel,Integer.valueOf(1));
        layeredPane.add(facePanel,Integer.valueOf(2));
        layeredPane.add(ImageEditorPanel,Integer.valueOf(3));
        layeredPane.add(AutoSendPanel,Integer.valueOf(4));

        frame.add(layeredPane);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Home page");

        //動態調整JFrame為使用者螢幕大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        //放置主頁面的背景的圖片
        ImageIcon icon = new ImageIcon("src\\Home.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel label = new JLabel(scaledIcon);
        
        JPanel backgroundImagePanel = new JPanel();
        backgroundImagePanel.setBounds(0,0,width,height);
        backgroundImagePanel.setOpaque(false);
        backgroundImagePanel.add(label);
     //////////////////////////////////////////////
        
        //設定主頁面
        JPanel homepanel = new JPanel(new GridLayout(3,0));
        homepanel.setBounds(25,height/2-100,width/2,height);
        //1.人臉辨識
        JPanel facePanel = new JPanel();
        facePanel.setBounds(25,height/2-150,800,150);
        facePanel.setOpaque(false);
        facePanel.setBackground(Color.getHSBColor(0, 0, 0));
        
        ImageIcon FaceIcon = new ImageIcon("src\\AI_Icon.png");
        FaceRecognition FaceRecognitionButton = new FaceRecognition("");
        FaceRecognitionButton.setIcon(FaceIcon);
        FaceRecognitionButton.setBackground(null);
        FaceRecognitionButton.setOpaque(false);
        facePanel.add(FaceRecognitionButton);
        
        //2.進行圖片編輯
        
        JPanel ImageEditorPanel = new JPanel();
        ImageEditorPanel.setBounds(25,height/2-150+150,800,150);
        ImageEditorPanel.setOpaque(false);
        ImageEditorPanel.setBackground(Color.getHSBColor(0, 0, 0));
        
        ImageIcon ImageIcon = new ImageIcon("src\\ImageEditor.png");
        ImageEditor imageEditorButton = new ImageEditor("");
        imageEditorButton.setIcon(ImageIcon);
        imageEditorButton.setBackground(null);
        imageEditorButton.setOpaque(false);
        ImageEditorPanel.add(imageEditorButton);


        // //3.自動分享圖片
        
        JPanel AutoSendPanel = new JPanel();
        AutoSendPanel.setBounds(25,height/2-150+300,800,150);
        AutoSendPanel.setOpaque(false);
        AutoSendPanel.setBackground(Color.getHSBColor(0, 0, 0));
        
        ImageIcon fbIcon = new ImageIcon("src\\fbShare.png");
        AutoSentImage autoSentButton = new AutoSentImage("");
        autoSentButton.setIcon(fbIcon);
        autoSentButton.setBackground(null);
        autoSentButton.setOpaque(false);
        AutoSendPanel.add(autoSentButton);
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(backgroundImagePanel,Integer.valueOf(1));
        layeredPane.add(facePanel,Integer.valueOf(2));
        layeredPane.add(ImageEditorPanel,Integer.valueOf(3));
        layeredPane.add(AutoSendPanel,Integer.valueOf(4));

        frame.add(layeredPane);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }





}