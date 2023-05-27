import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import javax.imageio.*;
import javax.imageio.ImageIO;

public class Test  {

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


        ImageIcon icon = new ImageIcon("src\\Home.png");
        JLabel label = new JLabel();
        label.setBounds(0, 0, 10000, 10000);
        label.setIcon(icon);
        
        JLayeredPane layeredPane = new JLayeredPane();
        //layeredPane.add(backgroundPanel,Integer.valueOf(0));
        layeredPane.add(homepanel, Integer.valueOf(1));
        
        JPanel imagepanel = new JPanel();
        imagepanel.setBounds((width/2)-500,200,width,height);
        imagepanel.setBackground(Color.red);
        imagepanel.add(label, Integer.valueOf(2));
        layeredPane.add(imagepanel,Integer.valueOf(3));


        frame.add(layeredPane);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }




}