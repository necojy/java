package ff.ForFace;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FaceDetectionGUI {
    private JFrame mainFrame;
    private JPanel mainPanel;

    public FaceDetectionGUI() {
        mainFrame = new JFrame("Face Detection GUI");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());

        JButton detectButton = new JButton("Detect Faces");
        detectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                YYFace.main(new String[0]);
            }
        });

        mainPanel.add(detectButton, BorderLayout.CENTER);
        mainFrame.getContentPane().add(mainPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FaceDetectionGUI();
            }
        });
    }
}
