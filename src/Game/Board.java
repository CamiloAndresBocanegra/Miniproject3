package Game;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    //private:
    JButton[] buttons;

    public Board()
    {
        setPreferredSize(new Dimension(500, 500));
        setLayout(new GridLayout(10, 10));
        buttons = new JButton[100];
        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new JButton(Integer.toString(i));
            add(buttons[i]);
        }
    }
}
