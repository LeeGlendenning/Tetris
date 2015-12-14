package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/*
 * PauseMenu class used to create the pause menu
 * 
 * @author Lee Glendenning 
 * @version 1.0
 */
public class PauseMenu extends JPanel {
    
    private final JButton resumeButton = new BlueButton("Resume");
    private final JButton quitButton = new BlueButton("Quit");
    
    /*
     * PauseMenu constructor used to create the pause menu over the Gameplay
     */
    public PauseMenu(){
        
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        
        JPanel pausePanel = new JPanel(new GridBagLayout());
        pausePanel.setBackground(Color.BLACK);
        pausePanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.WHITE));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel pauseLabel = new JLabel(" PAUSED");
        pauseLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        pauseLabel.setForeground(Color.WHITE);
        pausePanel.add(pauseLabel, gbc);
        pausePanel.add(Box.createVerticalStrut(100), gbc);
        resumeButton.setPreferredSize(new Dimension(150, 50));
        quitButton.setPreferredSize(new Dimension(150, 50));
        pausePanel.add(resumeButton, gbc);
        pausePanel.add(Box.createVerticalStrut(10), gbc);
        pausePanel.add(quitButton, gbc);
        
        add(pausePanel, BorderLayout.CENTER);
        
        registerListeners();
    }
    
    /*
     * registerListeners method used to create callback methods for resume and quit buttons
     * 
     * @param e     ActionEvent object holds details for the event. Not currently used
     */
    private void registerListeners(){
        resumeButton.addActionListener(new ActionListener() {
            /*
             * actionPerformed method used to switch card layout to Gameplay
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout)Home.curGame.getLayout()).show(Home.curGame, "game");
            }
        });
        
        quitButton.addActionListener(new ActionListener() {
            /*
             * actionPerformed method used to switch back to Home page
             * 
             * @param e     ActionEvent object holds details for the event. Not currently used
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Home.playSound){
                    Gameplay.clip.stop();
                    Gameplay.clip.flush();
                    Gameplay.clip.setFramePosition(0);
                }
                Main.panel.getContentPane().removeAll();
                Main.panel.getContentPane().revalidate();
                Main.panel.repaint();
                Main.panel.add(new Home(), BorderLayout.CENTER);
            }
        });
    }
}
