package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/*
 * HowTo class creates HowTo page
 * 
 * @author Lee Glendenning
 * @version 1.0
 */
public class HowTo extends JPanel{
    
    private final JSlider slider;
    private final JButton playButton;
    
    /*
     * HowTo constructor creates HowTo page
     */
    public HowTo(){
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        Font f = new Font("Verdana", Font.BOLD, 22);
        
        ImageIcon image = null;
        try {
            image = new ImageIcon(ImageIO.read(getClass().getResource("/Buttons.png")));
        } catch (IOException ex) {
            Logger.getLogger(Gameover.class.getName()).log(Level.SEVERE, null, ex);
        }
        JLabel picLabel = new JLabel(image);
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        topPanel.add(picLabel);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.BLACK);
        
        slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setFont(new Font("Verdana", Font.PLAIN, 13));
        slider.setBackground(Color.BLACK);
        slider.setForeground(Color.LIGHT_GRAY);
        slider.setMaximumSize(new Dimension(500, 50));
        
        JLabel sliderLabel = new JLabel("Select starting level");
        sliderLabel.setFont(f);
        sliderLabel.setForeground(Color.WHITE);
        sliderLabel.setHorizontalAlignment(JLabel.CENTER);
        bottomPanel.add(sliderLabel, gbc);
        bottomPanel.add(Box.createVerticalStrut(15), gbc);
        bottomPanel.add(Box.createHorizontalStrut(25));
        bottomPanel.add(slider, gbc);
        playButton = new BlueButton("Play");
        playButton.setPreferredSize(new Dimension(250, 50));
        bottomPanel.add(Box.createVerticalStrut(35), gbc);
        bottomPanel.add(playButton, gbc);
        
        add(topPanel);
        add(bottomPanel);
        
        registerListeners();
    }
    
    /*
     * registerListeners method creates callback method for playButton
     */
    private void registerListeners(){
        playButton.addActionListener(new ActionListener() {
            
            /*
             * actionPerformed callback method for when playButton is pressed.
             * Will go to the Gameplay screen
             * 
             * @param e     ActionEvent object holding details for the action. Not currently used
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Stats.level = new Integer(slider.getValue()).toString();
                
                Main.panel.getContentPane().removeAll();
                Main.panel.getContentPane().revalidate();
                Main.panel.repaint();

                Main.panel.add(new Stats(), BorderLayout.WEST);
                Home.curGame = new JPanel(new CardLayout());
                Home.curGame.setBackground(Color.BLACK);
                Home.curGame.add(new Gameplay(), "game");
                Home.curGame.add(new PauseMenu(), "pause");
                
                ((CardLayout)Home.curGame.getLayout()).show(Home.curGame, "game");
                Main.panel.add(Home.curGame, BorderLayout.CENTER);
            }
        });
        
        
    }
    
}
