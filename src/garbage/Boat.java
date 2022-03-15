package Game;

public class Boat {
    //privates:
    boolean isOnBoard = false;
    boolean horizontalOrientation = false;

    int length;

    int positionX = 0;
    int positionY = 0;

    public void setPosition(int x, int y)
    {
        positionX = x;
        positionY = y;
    }

    public void setLength(int newLength)
    {
        length = newLength;
    }

    public void setOrientation(boolean isHorizontal)
    {
        horizontalOrientation = isHorizontal;
    }

    public void setIsOnBoard(boolean onBoard)
    {
        isOnBoard = onBoard;
    }

}
