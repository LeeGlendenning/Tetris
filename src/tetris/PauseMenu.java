package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class PauseMenu extends JPanel {
    
    private final JButton resumeButton = new BlueButton("Resume");
    private final JButton quitButton = new BlueButton("Quit");
    
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
    
    private void registerListeners(){
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout)Home.curGame.getLayout()).show(Home.curGame, "game");
            }
        });
        
        quitButton.addActionListener(new ActionListener() {
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
                try {
                    Main.panel.add(new Home(), BorderLayout.CENTER);
                } catch (IOException ex) {
                    Logger.getLogger(PauseMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
