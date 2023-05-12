import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.JLabel;


public class MouseDrawingTool extends JPanel {

    private Point lastPoint;
    private Color currentColor;
    private int brushSize; // 新增變數
    private Point initialMousePosition;
    private Point initialImagePosition;
    private JLabel imageLabel;
    private Boolean currentDrawMode = false;

    public MouseDrawingTool(JLabel imageLabel) {

        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.RED);
        currentColor = Color.BLACK;
        brushSize = 1; // 預設畫筆粗細為 1
        this.imageLabel = imageLabel;
        
        addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mousePressed(MouseEvent e) 
            {
                if(currentDrawMode) lastPoint = e.getPoint();
                
                else
                {
                    initialMousePosition = e.getPoint();
                    initialImagePosition = imageLabel.getLocation();
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() 
        {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(currentDrawMode)
                {
                    Graphics2D g = (Graphics2D)getGraphics();
                    g.setColor(currentColor);
                    g.setStroke(new BasicStroke(brushSize));
                    g.drawLine(lastPoint.x, lastPoint.y, e.getX(), e.getY());
                    lastPoint = e.getPoint();
                }
                else
                {
                    int deltaX = e.getX() - initialMousePosition.x;
                    int deltaY = e.getY() - initialMousePosition.y;
                    int newX = initialImagePosition.x + deltaX;
                    int newY = initialImagePosition.y + deltaY;
                    imageLabel.setLocation(newX, newY);
                }
                
            }
        });
    }

    
    public void setDrawMode(Boolean drawMode)
    {
        currentDrawMode = drawMode;
    }   

    public void setColor(Color color) {
        currentColor = color;
    }

    public void setBrushSize(int size) { // 新增函數
        brushSize = size;
    }
}