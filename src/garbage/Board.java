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
    Box[][] buttons;
    Boat[][] boats;

    public Board()
    {

        Boat[] frigates = new Boat[4];
        Boat[] destructors = new Boat[3];
        Boat[] submarines = new Boat[2];
        Boat[] aircraftCarriers = new Boat[1];

        buttons = new Box[10][10];

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
}
