package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel {

    public static JFrame panel = new JFrame("Tetris");
    public static boolean firstHs = true;

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