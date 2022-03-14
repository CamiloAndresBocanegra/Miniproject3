package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main window class
 * @autor Camilo Andres Bocanegra Villa
 * @correo camilo.bocanega@correounivalle.edu.co
 * @codigo 2025804
 */
public class Window extends JFrame {
    //Private:
    Board playerBoard;
    Board enemyBoard;

    /**
     * Constructor of Window class
     */
    public Window() {
        initWindow();

        setSize(1300, 700);
        setResizable(true);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle("Game Window");

//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
    }

    /**
     */
    private void initWindow()
    {
        setLayout(new BorderLayout());
        JOptionPane.showMessageDialog(null, "Controles: R para rotar");

        JPanel leftSide = new JPanel();
        playerBoard = new Board();
        add(leftSide, BorderLayout.WEST);
        leftSide.add(playerBoard);

        JPanel rightSide = new JPanel();
        enemyBoard = new Board();
        rightSide.add(enemyBoard);
        add(rightSide, BorderLayout.EAST);

        Canvas canvas = new Canvas();
        add(canvas);
    }

    /**
     */
    private class LineInputListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
        }
    }

    private class LevelSelectorListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
        }
    }

    private class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Runtime.getRuntime().exit(0);
        }
    }
}
