import representation.GuiInterface;

import javax.swing.*;

public class Main {
    /**
     * Main method to run the GUI application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuiInterface(new dal.FileProjectDao("data")));
    }
}