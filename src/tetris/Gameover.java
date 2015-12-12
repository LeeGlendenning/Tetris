package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

/*
 * Gameover class used to create Gameover page
 * 
 * @author Lee Glendenning
 * @version 1.0
 */
public class Gameover extends JPanel {

    public static JTextFieldLimit nameField = nameField = new JTextFieldLimit(10);
    private static Clip clip;
    private static AudioInputStream sound;
    
    /*
     * Gameover constructor creates Gameover page
     */
    public Gameover() {
        
        playMusic();    
        setOpaque(false);
        setBackground(Color.BLACK);

        //draw game over picture
        ImageIcon image = null;
        try {
            image = new ImageIcon(ImageIO.read(getClass().getResource("/GameOver.png")));
        } catch (IOException ex) {
            Logger.getLogger(Gameover.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JLabel picLabel = new JLabel(image);
        add(picLabel);
        
        JLabel linesLabel = new JLabel("You got " + Stats.lines + " lines!");
        linesLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        linesLabel.setForeground(Color.LIGHT_GRAY);
        add(linesLabel);
        
        final int hsIndex = checkForHS();

        if (hsIndex != -1) {//player achieved a hs
            JPanel wrapperPanel = new JPanel(new BorderLayout());
            wrapperPanel.setBackground(Color.BLACK);

            JLabel titleLabel = new JLabel("High Score!");
            titleLabel.setFont(new Font("Verdana", Font.BOLD, 22));
            titleLabel.setForeground(Color.LIGHT_GRAY);
            JPanel titlePanel = new JPanel();
            titlePanel.setBackground(Color.BLACK);
            titlePanel.add(titleLabel);

            JLabel hsLabel = new JLabel("Enter name: ");
            hsLabel.setFont(new Font("Verdana", Font.BOLD, 16));
            hsLabel.setForeground(Color.LIGHT_GRAY);

            JButton hsButton = new BlueButton("Submit");
            JPanel hsPanel = new JPanel(new GridLayout(1, 3));
            hsPanel.setBackground(Color.BLACK);
            hsPanel.add(hsLabel);
            hsPanel.add(nameField);
            hsPanel.add(hsButton);

            wrapperPanel.add(titlePanel, BorderLayout.NORTH);
            wrapperPanel.add(new JLabel("  "), BorderLayout.CENTER);//create a bit of space
            wrapperPanel.add(hsPanel, BorderLayout.SOUTH);

            add(wrapperPanel);
            
            hsButton.addActionListener(new ActionListener() {//when user has entered their name and pressed the Submit button

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isNameValid()){//check for empty/invalid name entries
                        setHS(hsIndex, nameField.getText(), Stats.lines);
                        showHS();
                    }else{
                        nameField.setBorder(BorderFactory.createLineBorder(Color.RED));
                    }
                }
            });
        } else {//no highscore so show gameover screen for 2 seconds before showing highscore screen
            Timer timer = new Timer(2000, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    showHS();
                }
            });
            timer.setRepeats(false); // Only execute once
            timer.start();
        }
    }
    
    /*
     * playMusic method used to start music in continuous loop
     */
    private void playMusic(){
        if (clip == null){
            try {
                //InputStream soundFile = getClass().getClassLoader().getResourceAsStream("GameOver.wav");
                //File soundFile = new File("GameOver.wav");
                sound = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("GameOver.wav")));

                DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(sound);
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                Logger.getLogger(Gameover.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        clip.start();
    }

    /*
     * isNameValid method checks for empty or innapropraite names
     * 
     * @return isNameValid      whether the name entered for highscores contains profanity or is empty
     */
    private static boolean isNameValid() {
        String name = nameField.getText();
        String invalidNames[] = {"slut", "$lut", "gay", "g@y", "homo", "h0mo", "hom0", "h0m0", "hooker", "h0oker", "ho0ker", "h00ker", "prostitute", "pr0stitute", "dick", "d!ck", "d1ck", "di(k", "d!(k", "d1(k", "b!tch", "b!t(h", "b1t(h", "bit(h", "bitch", "b1tch", "fuck", "fu(k", "shit", "sh1t", "sh!t", "$hit", "$h1t", "$h!t", "penis", "pen1s", "pen!s", "peni$", "pen1$", "pen!$", "vagina", "v@gina", "vagin@", "v@gin@", "pussy", "pu$sy", "pus$y", "pu$$y", "cunt", "(unt", "sperm", "$perm", "cock", "(ock", "c0ck", "(0ck"};
        
        if (name.equals("")){
            return false;
        }
        
        for (int i = 0; i < invalidNames.length; i++) {
            if (name.contains(invalidNames[i]) || name.contains(invalidNames[i].toUpperCase())){
                return false;
            }
        }
        
        return true;
    }
    
    /*
     * checkForHS method checks whether the current player has achieved a highscore. If they have,
     * the place is returned. e.g. current player just got the 3rd highscore so 2 is returned (0 is highest score)
     * 
     * @returns i       high score place where 0 is the place for the best score and 9 is the last high score
     */
    private int checkForHS() {
        ArrayList<HighScore> h = HighScore.getScores(); // get list of high scores

        // check up to 10 high scores
        for (int i = 0; i < 10; i++) {
            // if there are at least i high scores && the current player achieved more lines than the i'th highscore,
            // return that current player has the i'th highscore
            if (h.size() > i && Stats.lines > h.get(i).lines) {
                return i;
            }
        }
        return -1;
    }
    
    /*
     * setHS method writes a new highscore for name of lines at hsIndex
     * 
     * @param hsIndex       the place for the highscore. e.g. 3rd highest score will be 2
     * @param name          the name of the current player who has achieved a highscore
     * @param lines         the number of lines the current player has achieved
     */
    private void setHS(int hsIndex, String name, int lines) {
        new HighScore(name, lines).writeScore(hsIndex);
    }
    
    /*
     * showHS method draws the highscore screen
     */
    private void showHS() {
        Main.panel.getContentPane().removeAll();
        Main.panel.getContentPane().revalidate();
        Main.panel.add(new HsScreen(), BorderLayout.CENTER);
    }
}
