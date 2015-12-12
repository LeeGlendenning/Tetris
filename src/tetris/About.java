package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * About class used to create About page
 * 
 * @author Lee Glendenning
 * @version 1.0
 */
public class About extends JPanel {
    
    JButton homeButton = new BlueButton("Home");
    
    /*
     * About constructor to create About page
     */
    public About(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        Font f = new Font("Verdana", Font.PLAIN, 18);
        
        JLabel aboutTitle = new JLabel("                             About");
        aboutTitle.setFont(new Font("Helvetica", Font.BOLD, 26));
        aboutTitle.setForeground(Color.WHITE);
        JLabel aboutLabel1 = new JLabel("I created this game using java.swing and java.awt - no");
        JLabel aboutLabel2 = new JLabel("external libraries. I cannot take credit for the music/");
        JLabel aboutLabel3 = new JLabel("sound effects or the Tetris logo, however, everything");
        JLabel aboutLabel4 = new JLabel("else was created from scratch.");
        JLabel aboutLabel5 = new JLabel("-Lee Glendenning");
        aboutLabel1.setFont(f);
        aboutLabel2.setFont(f);
        aboutLabel3.setFont(f);
        aboutLabel4.setFont(f);
        aboutLabel5.setFont(f);
        aboutLabel1.setForeground(Color.LIGHT_GRAY);
        aboutLabel2.setForeground(Color.LIGHT_GRAY);
        aboutLabel3.setForeground(Color.LIGHT_GRAY);
        aboutLabel4.setForeground(Color.LIGHT_GRAY);
        aboutLabel5.setForeground(Color.LIGHT_GRAY);
        
        homeButton.setPreferredSize(new Dimension(200, 40));
        
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(aboutTitle, gbc);
        add(Box.createVerticalStrut(75), gbc);
        add(aboutLabel1, gbc);
        add(aboutLabel2, gbc);
        add(aboutLabel3, gbc);
        add(aboutLabel4, gbc);
        add(Box.createVerticalStrut(30), gbc);
        add(aboutLabel5, gbc);
        add(Box.createVerticalStrut(150), gbc);
        add(Box.createHorizontalStrut(140));
        add(homeButton, gbc);
        
        registerListeners();
    }
    
    /*
     * registerListeners method used to add action performed callback method for pressing of the Home button
     */
    private void registerListeners() {

        homeButton.addActionListener(new ActionListener() {
            
            /*
             * actionPerformed callback method called when Home button is pressed.
             * Loads the home page.
             * 
             * @param e     ActionEvent object used to hold details for the actionPerformed. Not currently used
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.panel.getContentPane().removeAll();
                Main.panel.getContentPane().revalidate();
                Main.panel.repaint();
                try {
                    Main.panel.add(new Home(), BorderLayout.CENTER);
                } catch (IOException ex) {
                    Logger.getLogger(About.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
}
