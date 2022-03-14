package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Canvas extends JPanel {
    private Font font;
    private int step;
    private boolean isHot = false;
    private Listener listener;

    public Canvas(){
        listener = new Listener();
        addMouseListener(listener);
        setBackground(Color.LIGHT_GRAY);
        font = new Font(Font.DIALOG,Font.BOLD,27);
        step = 1;
    }

    public void draw(int NewStep){
        step++;
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);
        switch (step)
        {
            case 1: g.setColor(Color.MAGENTA);
                g.drawString("Drawing with Graphics",20,22);
                break;
            case 2: g.setColor(Color.RED);
                g.drawString("Draw Lines",20,22);
                g.drawLine(5,30, 380,30);
                break;
            case 3:g.setColor(Color.CYAN);
                g.drawString("Draw Rect",20,22);
                g.drawRect(5,40,90,55);
                g.fillRect(100,40,90,55);
                g.setColor(Color.ORANGE);
                g.drawRoundRect(290,40,90,55,20,20);
                g.fillRoundRect(195,40,90,55,50,50);
                g.setColor(Color.MAGENTA);
                g.draw3DRect(5,100,90,55,true);
                g.fill3DRect(100,100,90,55,false);
                break;
            case 4:g.setColor(Color.YELLOW);
                g.drawString("Draw Ovals",20,22);
                g.drawOval(195,100,90,55);
                g.fillOval(290,100,90,55);
                break;
            case 5:g.setColor(Color.GREEN);
                g.drawString("Draw Images",20,22);
                ImageIcon image = new ImageIcon(getClass().getResource("/resources/1.jpg"));
                g.drawImage(image.getImage(),7,40,90,90,null);
                break;
            default:g.setColor(Color.BLUE);
                g.drawString("The End!!",50,22);
                step = 0;
                break;
        }
    }

    private class Listener extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            isHot = true;
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            if(isHot)
            {
                draw(1);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            isHot = false;
        }
    }

}
