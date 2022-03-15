package Game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerBoard extends Board
{
    int typeSelected = 0;

    public PlayerBoard()
    {
        super();
        Listener listener = new Listener();
        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i].addMouseListener(listener);
        }
    }

    public void setTypeSelected(int newType)
    {
        typeSelected = newType;
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
        {
            ((Box)e.getSource()).setPressed(false);
            ((Box)e.getSource()).setText("");
        }

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
            ((Box)e.getSource()).setText(Integer.toString(typeSelected));
        }
    }
}
