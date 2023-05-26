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
        JFrame frame = new JFrame("Home page");

        //動態調整JFrame為使用者螢幕大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();


        //設定主頁面
        JPanel homepanel = new JPanel(new GridLayout(3,0));
        homepanel.setBounds((width/2)-500,200,1000,500);
        homepanel.setBackground(Color.GREEN);

        //放置主頁面的背景
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(Color.pink);
        backgroundPanel.setBounds(0,0,width,height);
        //1.人臉辨識
        FaceRecognition FaceRecognitionButton = new FaceRecognition("人臉辨識");
        homepanel.add(FaceRecognitionButton);

        //2.進行圖片編輯
        ImageEditor imageEditorButton = new ImageEditor("圖片編輯");
        homepanel.add(imageEditorButton);
        //當它被按下時會跳關閉目前的JFrame
        //imageEditorButton.addActionListener(e -> frame.dispose());

        //3.自動分享圖片
        AutoSentImage autoSentButton = new AutoSentImage("自動分享圖片");
        homepanel.add(autoSentButton);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(backgroundPanel,Integer.valueOf(0));
        layeredPane.add(homepanel, Integer.valueOf(1));

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


        //設定主頁面
        JPanel homepanel = new JPanel(new GridLayout(3,0));
        homepanel.setBounds((width/2)-500,200,1000,500);
        homepanel.setBackground(Color.GREEN);

        //放置主頁面的背景
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(Color.pink);
        backgroundPanel.setBounds(0,0,width,height);

        //1.人臉辨識
        FaceRecognition FaceRecognitionButton = new FaceRecognition("人臉辨識");
        homepanel.add(FaceRecognitionButton);

        //2.進行圖片編輯
        ImageEditor imageEditorButton = new ImageEditor("圖片編輯");
        homepanel.add(imageEditorButton);
        //當它被按下時會跳關閉目前的JFrame
        //imageEditorButton.addActionListener(ex -> frame.dispose());

        //3.自動分享圖片
        AutoSentImage autoSentButton = new AutoSentImage("自動分享圖片");
        homepanel.add(autoSentButton);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(backgroundPanel,Integer.valueOf(0));
        layeredPane.add(homepanel, Integer.valueOf(1));

        frame.add(layeredPane);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }





}