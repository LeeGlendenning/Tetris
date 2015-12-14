package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Main class used to create jframe and run the application in it
 * 
 * @author Lee Glendenning
 * @version 1.0
 */
public class Main extends JPanel {

    public static JFrame panel = new JFrame("Tetris");
    public static boolean firstHs = true;
    
    /*
     * main method used to create a JFrame and put the Home page in it
     * 
     * @param args      used to collect command line arguments. Currently not used
     */
    public static void main(String[] args) throws IOException {
        panel.setSize(600, 700);
        panel.setResizable(false);
        panel.setLocation(375, 25);
        panel.getContentPane().setBackground(Color.BLACK);
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = panel.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new Home(), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(600, 700));
        panel.setLocationRelativeTo(null);
        panel.pack();
        panel.setVisible(true);
    }
}
