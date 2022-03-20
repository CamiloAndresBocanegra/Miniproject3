package Game;

import javax.swing.*;
import java.awt.*;

public class Box extends JButton
{   //private:
    int row;
    int column;
    int beginningY;
    int beginningX;

    boolean isHorizontal = false;
    boolean isOccupied = false;
    boolean isSelected = false;
    boolean isPressed = false;

    boolean hasBeenHit = false;

    int partNumber = 0;
    int boatType = 0;
    int boatIndex = -1;

    int state;

    static int NULL = -1;
    static int WATER = 0;
    static int BOAT = 1;
    static int HIT = 2;
    static int SUNKEN = 3;

    public void setPosition(int x, int y)
    {
        column = x;
        row = y;
    }
}
