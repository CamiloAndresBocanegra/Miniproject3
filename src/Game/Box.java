package Game;

import javax.swing.*;

public class Box extends JButton
{   //private:
    boolean isOccupied = false;
    boolean isPressed = false;

    public void setPressed(boolean value)
    {
        isPressed = value;
    }
}
