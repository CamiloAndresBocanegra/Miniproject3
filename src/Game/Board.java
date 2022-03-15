package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;

public class Board extends JPanel {
//    int FRIGATES = 0;
//    int DESTRUCTORS = 1;
//    int SUBMARINES = 2;
//    int AIRCRAFTCARRIERS = 3;
    //private:
    Box[] buttons;
    Boat[][] boats;

    public Board()
    {
        setPreferredSize(new Dimension(500, 500));
        setLayout(new GridLayout(10, 10));
        buttons = new Box[100];
        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new Box();
            add(buttons[i]);
        }

        Boat[] frigates = new Boat[4];
        Boat[] destructors = new Boat[3];
        Boat[] submarines = new Boat[2];
        Boat[] aircraftCarriers = new Boat[1];

        boats = new Boat[4][];
        boats[0] = frigates;
        boats[1] = destructors;
        boats[2] = submarines;
        boats[3] = aircraftCarriers;
        for(int i = 0; i < boats.length; i++)
        {
            for(int j = 0; j < boats[i].length; j++)
            {
                boats[i][j] = new Boat();
                boats[i][j].setLength(5-boats[i].length);
            }
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
        { ((Box)e.getSource()).setPressed(true); }

        @Override
        public void mouseExited(MouseEvent e)
        { ((Box)e.getSource()).setPressed(false); }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            if(((Box)e.getSource()).isPressed)
            {

            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {

        }
    }
}
