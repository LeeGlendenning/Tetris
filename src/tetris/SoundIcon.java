package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/*
 * SoundIcon class used to create an interactive sound icon
 * 
 * @author Lee Glendenning
 * @version 1.0
 */
public class SoundIcon extends JPanel {
    
    private BufferedImage soundIcon = null;
    int iconWidth = 90/2;
    int iconLength = 65/2;
    Clip clip;
    
    /*
     * SoundIcon constructor creates the sound icon
     */
    public SoundIcon(Clip clip){
        this.clip = clip;
        try {
            soundIcon = ImageIO.read(getClass().getResource("/sound2.png"));
        } catch (IOException ex) {
            Logger.getLogger(SoundIcon.class.getName()).log(Level.SEVERE, null, ex);
        }
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createLineBorder(new Color(0x1b356d), 2));
        setPreferredSize(new Dimension(iconWidth, iconLength));
        setMaximumSize(new Dimension(iconWidth, iconLength));
        registerListeners();
    }
    
    /* 
     * paintComponent method draws the soundIcon to the screen
     * 
     * @param g     Graphics object used to draw to the screen
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(soundIcon, 0, 0, iconWidth, iconLength, null); // see javadoc for more info on the parameters
        
        if (!Home.playSound){
            g.setColor(Color.red);
            g.drawLine(2, iconLength-2, iconWidth-2, 2);
        }
    }
    
    /*
     * registerListeners method used to add mouse listener callback method for mouse click on the icon
     */
    private void registerListeners(){
        addMouseListener(new MouseAdapter() {
            /*
             * mousePressed callback method called when mouse is pressed on the sound icon
             */
            @Override
            public void mousePressed(MouseEvent e) {
                Home.playSound = !Home.playSound;
                if (!Home.playSound){
                    System.out.println("Stopping sound: " + Home.playSound);
                    clip.stop();
                    clip.flush();
                    clip.setFramePosition(0);
                }else{
                    System.out.println("Starting sound");
                    clip.start();
                }
                repaint();
            }
        });
    }
}
