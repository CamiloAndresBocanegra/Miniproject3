package Game;

import javax.swing.*;
import java.awt.*;

public class Box extends JButton
{   //private:
    int row;
    int column;
    boolean isHorizontal = false;
    boolean isOccupied = false;
    boolean isSelected = false;
    boolean isPressed = false;

    boolean hasBeenHit = false;

    int partNumber = 0;
    int boatType = 0;
    int boatIndex = -1;

    int WATER = 0;
    int BOAT = 1;
    int HIT = 2;
    int SUNKEN = 3;

    int state;

    public void setState(int value)
    {
        state = value;
        setText(Integer.toString(value));
    }

    public void setHasBeenHit(boolean value)
    {
        hasBeenHit = value;
    }

    public void setHorizontal(boolean value)
    {
        isHorizontal = value;
    }

    public void setPart(int part)
    {
        partNumber = part;
    }

    public void setType(int type)
    {
        boatType = type;
    }

    public void setBoat(int value)
    {
        boatIndex = value;
    }

    public void setPosition(int x, int y)
    {
        column = x;
        row = y;
    }
    public int getColumn()
    {
        return column;
    }
    public int getRow()
    {
        return row;
    }

    public void setPressed(boolean value)
    {
        isPressed = value;
    }

    public void setOccupied(boolean value){isOccupied = value;}

    public void select(boolean value)
    {
        isSelected = value;
    }

}
