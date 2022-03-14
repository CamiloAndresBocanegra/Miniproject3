package Game;

import java.awt.*;

public class Main
{
    /**
     * Main process of the Java program
     * @param args Object used in order to send input data from command line when
     *             the program is execute by console.
     */
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            Window GameWindow = new Window();
        });
    }
}
