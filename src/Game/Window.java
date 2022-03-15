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
    PlayerBoard playerBoard;
    Board enemyBoard;

    SelectBoatListener selectBoatListener;

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
//        JOptionPane.showMessageDialog(null, "Controles: R para rotar");

        JPanel leftSide = new JPanel();
        playerBoard = new PlayerBoard();
        add(leftSide, BorderLayout.WEST);
        leftSide.add(playerBoard);

        JPanel boatButtons = new JPanel();
        selectBoatListener = new SelectBoatListener();
        for(int i = 0; i < 4; i++)
        {
            JButton boatButton = new JButton(Integer.toString(i));
            boatButton.addActionListener(selectBoatListener);
            boatButtons.add(boatButton);
        }
        leftSide.add(boatButtons);

        JPanel rightSide = new JPanel();
        enemyBoard = new Board();
        rightSide.add(enemyBoard);
        add(rightSide, BorderLayout.EAST);

    }

    /**
     */
    private class SelectBoatListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            playerBoard.setTypeSelected(Integer.parseInt(((JButton)e.getSource()).getText()));
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
