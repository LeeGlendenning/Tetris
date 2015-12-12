package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class About extends JPanel {
    
    JButton backButton = new BlueButton("Home");
    
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
        JLabel aboutLabel1 = new JLabel("I created this game using java.swing and java.awt");
        JLabel aboutLabel2 = new JLabel("- no external libraries. I cannot take credit for the");
        JLabel aboutLabel3 = new JLabel("music/sound effects or the Tetris logo, however, I");
        JLabel aboutLabel4 = new JLabel("created everything else from scratch.");
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
        
        backButton.setPreferredSize(new Dimension(200, 40));
        
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
        add(backButton, gbc);
        
        registerListeners();
    }
    
    private void registerListeners() {

        backButton.addActionListener(new ActionListener() {

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
