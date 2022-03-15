package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
    boolean placingBoats = true;
    boolean canSetBoat = false;

    JButton[] typeButtons;
    int[] maxIndexes;

    Box[][] boats;

    Box[][] playerBoxes;
    Box[][] enemyBoxes;

    SelectBoatListener selectBoatListener;
    EnemyBoxListener enemyBoxListener;

    ArrayList<Integer> availableShots;

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
        JPanel[] playerRows = new JPanel[10];

        playerBoxes = new Box[10][10];

        for(int row = 0; row < playerBoxes.length; row++)
        {
            playerRows[row] = new JPanel();
            playerRows[row].setLayout(new GridLayout(1, 10));
            for(int column = 0; column < playerBoxes[row].length; column++)
            {
                playerBoxes[row][column] = new Box();
                playerBoxes[row][column].setPosition(column, row);
                playerBoxes[row][column].addMouseListener(playerBoxListener);
                playerRows[row].add(playerBoxes[row][column]);
            }
            playerBoard.add(playerRows[row]);
        }
        ImageIcon image = new ImageIcon(getClass().getResource("/resources/1.png"));
        playerBoxes[1][2].setIcon(image);


        add(leftSide, BorderLayout.WEST);
        leftSide.add(playerBoard);
        //---------------------------------------------------------------------------------
        typeButtons = new JButton[4];
        JPanel middleButtons = new JPanel();
        middleButtons.setLayout(new BoxLayout(middleButtons, BoxLayout.Y_AXIS));
        selectBoatListener = new SelectBoatListener();
        for(int i = 0; i < 4; i++)
        {
            JButton boatButton = new JButton(Integer.toString(i));
            typeButtons[i] = boatButton;
            boatButton.addActionListener(selectBoatListener);
            middleButtons.add(boatButton);
        }
        JButton orientationButton = new JButton("Rotate boat");
        SwitchOrientationListener orientationListener = new SwitchOrientationListener();
        orientationButton.addActionListener(orientationListener);
        middleButtons.add(orientationButton);
        add(middleButtons);

        boats = new Box[4][];
        maxIndexes = new int[4];
        for(int i = 0; i < 4; i++)
        {
            boats[i] = new Box[4-i];
            maxIndexes[i] = 3-i;
        }
        // ENEMY BOARD ----------------------------------------------------------------------------------
        enemyBoxListener = new EnemyBoxListener();

        JPanel rightSide = new JPanel();

        JPanel enemyBoard = new JPanel();
        enemyBoard.setPreferredSize(new Dimension(500, 500));
        enemyBoard.setLayout(new GridLayout(10, 1));
        JPanel[] enemyRows = new JPanel[10];

        enemyBoxes = new Box[10][10];
        for(int row = 0; row < enemyBoxes.length; row++)
        {
            enemyRows[row] = new JPanel();
            enemyRows[row].setLayout(new GridLayout(1, 10));
            for(int column = 0; column < enemyBoxes[row].length; column++)
            {
                enemyBoxes[row][column] = new Box();
                enemyBoxes[row][column].setPosition(column, row);
                enemyBoxes[row][column].addActionListener(enemyBoxListener);
                enemyRows[row].add(enemyBoxes[row][column]);
            }
            enemyBoard.add(enemyRows[row]);
        }
        rightSide.add(enemyBoard);
        add(rightSide, BorderLayout.EAST);
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
                int part = 0;
                for(int row = 0; row < 10; row++)
                {
                    for(int column = 0; column < 10; column++)
                    {
                        if(playerBoxes[row][column].isSelected)
                        {
                            boats[typeSelected][maxIndexes[typeSelected]] = playerBoxes[row][column];
                            playerBoxes[row][column].setOccupied(true);
                            playerBoxes[row][column].setBoat(maxIndexes[typeSelected]);
                            playerBoxes[row][column].setType(typeSelected);
                            playerBoxes[row][column].setPart(part);
                            part++;
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
                if(allBoatsSet)
                {
                    placingBoats = false;
                    for(int y=0; y<playerBoxes.length; y++)
                    {
                        for(int x=0; x<playerBoxes[y].length; x++)
                        {
                            playerBoxes[y][x].setEnabled(false);
                        }
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
            if (placingBoats)
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

    private class EnemyBoxListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Box boxSelected = (Box)e.getSource();
            int boxX = boxSelected.getColumn();
            int boxY = boxSelected.getRow();
            boxSelected.removeActionListener(enemyBoxListener);
            if(boxSelected.isOccupied) {
                int type = boxSelected.boatType;
                int boatIndex = boxSelected.boatIndex;
                boats[type][boatIndex].setHasBeenHit(true);

                boolean allPartsHit = true;
                for (int i = 0; i < boats[type].length; i++) {
                    if (!boats[type][i].hasBeenHit) {
                        allPartsHit = false;
                    }
                }
                if (allPartsHit) {
                    for (int i = 0; i < boats[type].length; i++) {
                        boats[type][i].setState(3);
                    }
                } else {
                    boats[type][boatIndex].setState(2);
                }
            }else {
                boxSelected.setState(0);
            }
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
