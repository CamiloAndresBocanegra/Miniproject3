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
public class Window extends JFrame
{
    //Private:
    int typeSelected = 0;
    boolean horizontalOrientation = true;
    boolean isPlacingBoats = true;
    boolean canSetBoat = false;

    JButton[] typeButtons;
    int[] maxIndexes;

    Box[][] boats;

    Box[][] playerBoxes;

    SelectBoatListener selectBoatListener;

    /**
     * Constructor of Window class
     */
    public Window()
    {
        initWindow();

        setSize(1300, 700);
        setResizable(true);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle("Game Window");
    }

    /**
     */
    private void initWindow()
    {
        PlayerBoxListener playerBoxListener = new PlayerBoxListener();
        setLayout(new BorderLayout());
//        JOptionPane.showMessageDialog(null, "Controles: R para rotar");

        JPanel leftSide = new JPanel();
        JPanel playerBoard = new JPanel();
        playerBoard.setPreferredSize(new Dimension(500, 500));
        playerBoard.setLayout(new GridLayout(10, 1));
        JPanel[] rows = new JPanel[10];

        playerBoxes = new Box[10][10];

        for(int row = 0; row < playerBoxes.length; row++)
        {
            rows[row] = new JPanel();
            rows[row].setLayout(new GridLayout(1, 10));
            for(int column = 0; column < playerBoxes[row].length; column++)
            {
                playerBoxes[row][column] = new Box();
                playerBoxes[row][column].setPosition(column, row);
                playerBoxes[row][column].addMouseListener(playerBoxListener);
                rows[row].add(playerBoxes[row][column]);
            }
            playerBoard.add(rows[row]);
        }
        ImageIcon image = new ImageIcon(getClass().getResource("/resources/1.png"));
        playerBoxes[1][2].setIcon(image);


        add(leftSide, BorderLayout.WEST);
        leftSide.add(playerBoard);


        //---------------------------------------------------------------------------------
        typeButtons = new JButton[4];
        JPanel boatButtons = new JPanel();
        selectBoatListener = new SelectBoatListener();
        for(int i = 0; i < 4; i++)
        {
            JButton boatButton = new JButton(Integer.toString(i));
            typeButtons[i] = boatButton;
            boatButton.addActionListener(selectBoatListener);
            boatButtons.add(boatButton);
        }
        JButton orientationButton = new JButton("Rotate boat");
        SwitchOrientationListener orientationListener = new SwitchOrientationListener();
        orientationButton.addActionListener(orientationListener);
        boatButtons.add(orientationButton);
        leftSide.add(boatButtons);

        JPanel rightSide = new JPanel();
//        enemyBoard = new Board();
//        rightSide.add(enemyBoard);
        add(rightSide, BorderLayout.EAST);

        boats = new Box[4][];
        maxIndexes = new int[4];
        for(int i = 0; i < 4; i++)
        {
            boats[i] = new Box[4-i];
            maxIndexes[i] = 3-i;
        }
    }

    private class PlayerBoxListener extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            ((Box)e.getSource()).setPressed(true);
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            if(((Box)e.getSource()).isPressed && canSetBoat && (maxIndexes[typeSelected] > -1))
            {

                for(int row = 0; row < 10; row++)
                {
                    for(int column = 0; column < 10; column++)
                    {
                        if(playerBoxes[row][column].isSelected)
                        {
                            boats[typeSelected][maxIndexes[typeSelected]] = playerBoxes[row][column];
                            playerBoxes[row][column].setOccupied(true);
                            playerBoxes[row][column].setBoat(maxIndexes[typeSelected]);
                        }
                    }
                }
                maxIndexes[typeSelected] -= 1;
                if(maxIndexes[typeSelected] == -1)
                {
                    typeButtons[typeSelected].setEnabled(false);
                }
                boolean allBoatsSet = true;
                for(int i=0; i < maxIndexes.length; i++)
                {
                    if (maxIndexes[i] > -1)
                    {
                        allBoatsSet = false;
                    }
                }//TODO: activate a button when all boats are set.
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
            if (isPlacingBoats)
            {
                int boxX = ((Box)e.getSource()).getColumn();
                int boxY = ((Box)e.getSource()).getRow();
                int boatLength = typeSelected + 1;

                int boxesSelected = 0;
                if (horizontalOrientation) {
                    for(int column = boxX; column-boxX < boatLength; column++)
                    {
                        if (column < 10 && !playerBoxes[boxY][column].isOccupied)
                        {
                            playerBoxes[boxY][column].setText("1");
                            boxesSelected++;
                            playerBoxes[boxY][column].select(true);
                        }
                    }
                }else{
                    for(int row = boxY; row-boxY < boatLength; row++)
                    {
                        if(row < 10 && !playerBoxes[row][boxX].isOccupied)
                        {
                            playerBoxes[row][boxX].setText("1");
                            boxesSelected++;
                            playerBoxes[row][boxX].select(true);
                        }
                    }
                }
                canSetBoat = (boxesSelected == boatLength);
            }
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            ((Box)e.getSource()).setPressed(false);
            for(int row = 0; row < 10; row++)
            {
                for(int column = 0; column < 10; column++)
                {
                    playerBoxes[row][column].select(false);
                    if(!playerBoxes[row][column].isOccupied)
                    {
                        playerBoxes[row][column].setText("");
                    }
                }
            }
        }
    }

    private class SwitchOrientationListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            horizontalOrientation = !horizontalOrientation;
        }
    }

    private class SelectBoatListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            typeSelected = Integer.parseInt(((JButton)e.getSource()).getText());
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
