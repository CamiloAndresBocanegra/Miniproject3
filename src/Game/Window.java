package Game;
/*TODO:
    - if i hit a boat, i can shoot again
    - put images
    - put timer between shots
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

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
    int[] boatsToPlace;

    Box[][] playerBoxes;
    Box[][] enemyBoxes;

    SelectBoatListener selectBoatListener;
    EnemyBoxListener enemyBoxListener;

    ArrayList<Integer> boxesToShoot;

    Random RNG;

    /**
    *   Auxiliar Function that turns a bool value into a 1 if true, and a 0 if false
     */
    public int boolToInt(boolean value)
    {
        if(value)
        {
            return 1;
        }else{
            return 0;
        }
    }
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
        if(false)
        {
            ImageIcon image = new ImageIcon(getClass().getResource("/resources/1.png"));
            playerBoxes[1][2].setIcon(image);
        }


        add(leftSide, BorderLayout.WEST);
        leftSide.add(playerBoard);

        boatsToPlace = new int[4];
        for(int type = 0; type < 4; type++)
        {
            boatsToPlace[type] = 4-type;
        }
        // MIDDLE BUTTONS ---------------------------------------------------------------------------------
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

        JButton showHideButton = new JButton("Show / Hide");
        ShowHideListener showHideListener = new ShowHideListener();
        showHideButton.addActionListener(showHideListener);
        middleButtons.add(showHideButton);
        add(middleButtons);

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

        boxesToShoot = new ArrayList<Integer>();
        for(int i=0; i < 100; i++)
        {
            boxesToShoot.add(i);
        }

        //RANDOMLY PLACING ENEMY BOATS ______________________________________________________________
        RNG = new Random();
        for(int boatType=3; boatType >= 0; boatType--)
        {
            int boatsOfThisType = 4-boatType;
            while(boatsOfThisType > 0)
            {
                boatsOfThisType--;
                boolean placeHorizontally = RNG.nextInt(2)==0;
                //SET POSIBLE PLACES TO PLACE THE BOAT
                ArrayList<Integer> availablePlaces = new ArrayList<Integer>();
                int boatLength = boatType + 1;
                for (int row = 0; row < enemyBoxes.length; row++) {
                    for (int column = 0; column < enemyBoxes[row].length; column++) {
                        boolean canBeAvailable = true;
                        for (int currentPart = 0; currentPart < boatLength; currentPart++) {
                            if (placeHorizontally) {
                                if (column + currentPart >= 10 || enemyBoxes[row][column + currentPart].isOccupied) {
                                    canBeAvailable = false;
                                }
                            } else {
                                if (row + currentPart >= 10 || enemyBoxes[row + currentPart][column].isOccupied) {
                                    canBeAvailable = false;
                                }
                            }
                        }
                        if (canBeAvailable) {
                            availablePlaces.add(row * 10 + column);
                        }
                    }
                }
                //PICK A RANDOM AVAILABLE PLACE AND SET A BOAT THERE__________________________________________________
                int chosenIndex = RNG.nextInt(availablePlaces.size());
                int row = availablePlaces.get(chosenIndex) / 10;
                int column = availablePlaces.get(chosenIndex) % 10;
                for (int currentPart = 0; currentPart < boatLength; currentPart++) {
                    Box thisBox;
                    if (placeHorizontally) {
                        thisBox = enemyBoxes[row][column + currentPart];
                    } else {
                        thisBox = enemyBoxes[row + currentPart][column];
                    }
                    thisBox.isOccupied = true;
                    thisBox.isHorizontal = placeHorizontally;
                    thisBox.setState(Box.BOAT);
                    thisBox.setType(boatType);
                    thisBox.setBoatIndex(boatsOfThisType);
                    thisBox.setPart(currentPart);
                    thisBox.beginningX = column;
                    thisBox.beginningY = row;
                }
            }
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
            Box boxClicked = (Box)e.getSource();
            if(boxClicked.isPressed && canSetBoat && (boatsToPlace[typeSelected] > 0))
            {
                int beginningX = boxClicked.column;
                int beginningY = boxClicked.row;
                int boatLength = typeSelected+1;

                for(int i=0; i<boatLength; i++)
                {
                    int column = beginningX + i*boolToInt(horizontalOrientation);
                    int row = beginningY + i*boolToInt(!horizontalOrientation);
                    Box currentBox = playerBoxes[row][column];
                    currentBox.isOccupied = true;
                    currentBox.boatIndex = boatsToPlace[typeSelected] - 1;
                    currentBox.boatType = typeSelected;
                    currentBox.isHorizontal = horizontalOrientation;
                    currentBox.setState(Box.BOAT);
                    currentBox.setPart(i);
                    currentBox.beginningX = beginningX;
                    currentBox.beginningY = beginningY;
                }
                boatsToPlace[typeSelected] -= 1;
                if(boatsToPlace[typeSelected] == 0)
                {
                    typeButtons[typeSelected].setEnabled(false);
                }
                boolean allBoatsSet = true;
                for(int i=0; i < boatsToPlace.length; i++)
                {
                    if (boatsToPlace[i] > 0)
                    {
                        allBoatsSet = false;
                    }
                }//TODO: activate a button when all boats are set.
                if(allBoatsSet)
                {
                    //TODO: start second phase when all boats are set
                    placingBoats = false;
                    for(int y=0; y<playerBoxes.length; y++)
                    {
                        for(int x=0; x<playerBoxes[y].length; x++)
                        {
                            playerBoxes[y][x].setFocusable(false);
                        }
                    }
                }

                canSetBoat = false;
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
                for(int i = 0; i < boatLength; i++)
                {
                    int row = boxY + (i * boolToInt(!horizontalOrientation));
                    int column = boxX + (i * boolToInt(horizontalOrientation));
                    if((row < 10) && (column < 10) && !playerBoxes[row][column].isOccupied)
                    {
                        playerBoxes[row][column].setText("1");
                        boxesSelected++;
                        playerBoxes[row][column].select(true);
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
                        playerBoxes[row][column].setText("");//TODO: change to whatever image needs to be
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
            if(!placingBoats)
            {
                Box boxSelected = (Box) e.getSource();
                boxSelected.removeActionListener(enemyBoxListener);

                shootBox(boxSelected, enemyBoxes);

                boolean GameOver = true;
                for(int row = 0; row < enemyBoxes.length; row++)
                {
                    for(int column=0; column < enemyBoxes[row].length; column++)
                    {
                        if(enemyBoxes[row][column].state == Box.BOAT)
                        {
                            GameOver = false;
                            break;
                        }
                    }
                    if(!GameOver){break;}
                }

                if(GameOver)
                {
                    //TODO: end game
                    JOptionPane.showMessageDialog(null, "you win yeah");
                }else{
                    int chosenIndex = RNG.nextInt(boxesToShoot.size());
                    int row = boxesToShoot.get(chosenIndex) / 10;
                    int column = boxesToShoot.get(chosenIndex) % 10;
                    boxesToShoot.remove(chosenIndex);
                    shootBox(playerBoxes[row][column], playerBoxes);
                }

            }
        }
    }

    public void shootBox(Box target, Box[][]board)
    {
        if (target.state == Box.BOAT)
        {
            target.hasBeenHit = true;
            int type = target.boatType;

            int beginningX = target.beginningX;
            int beginningY = target.beginningY;

            boolean allPartsHit = true;
            for (int i = 0; i < type+1; i++)
            {
                int column = beginningX + (i*boolToInt(target.isHorizontal));
                int row = beginningY + (i*boolToInt(!target.isHorizontal));
                if(!board[row][column].hasBeenHit)
                {
                    allPartsHit = false;
                }
            }
            if (allPartsHit)
            {
                for (int i = 0; i < type+1; i++)
                {
                    int column = beginningX + (i*boolToInt(target.isHorizontal));
                    int row = beginningY + (i*boolToInt(!target.isHorizontal));
                    board[row][column].setState(Box.SUNKEN);
                    board[row][column].setText("3");//TODO: put images instead of numbers
                }
            } else {
                target.setState(Box.HIT);
                target.setText("2");
            }
        } else {
            target.setState(Box.WATER);
            target.setText("0");
        }
    }

    private class ShowHideListener implements ActionListener
    {
        boolean isShowing = false;
        @Override
        public void actionPerformed(ActionEvent e)
        {
            isShowing = !isShowing;
            for(int row=0; row < enemyBoxes.length; row++)
            {
                for(int column=0; column < enemyBoxes[row].length; column++)
                {
                    if(enemyBoxes[row][column].state == Box.BOAT)
                    {
                        if(isShowing)
                        {
                            enemyBoxes[row][column].setText("1");
                        }else{
                            enemyBoxes[row][column].setText("");
                        }
                    }
                }
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
