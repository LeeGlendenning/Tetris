package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

public class Gameover extends JPanel {

    public static JTextFieldLimit nameField = nameField = new JTextFieldLimit(10);
    private static Clip clip;
    private static AudioInputStream sound;

    public Gameover() {
        try {
            playMusic();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Gameover.class.getName()).log(Level.SEVERE, null, ex);
        }
        setOpaque(false);
        setBackground(Color.BLACK);

        //draw game over picture
        //ImageIcon image = null;
        
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
    
    private void playMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        if (clip == null){
            //InputStream soundFile = getClass().getClassLoader().getResourceAsStream("GameOver.wav");
            //File soundFile = new File("GameOver.wav");
            sound = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("GameOver.wav")));

            DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(sound);
        }
        clip.start();
    }

    //check for empty or innapropraite names
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

    private int checkForHS() {
        ArrayList<HighScore> h = null;
        try {
            h = HighScore.getScores();
        } catch (URISyntaxException ex) {
            Logger.getLogger(Gameover.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < 10; i++) {
            if (h.size() > i && Stats.lines > h.get(i).lines) {
                return i;
            }
        }
        return -1;
    }

    private void setHS(int hsIndex, String name, int lines) {
        try {
            new HighScore(name, lines).writeScore(hsIndex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Gameover.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showHS() {
        Main.panel.getContentPane().removeAll();
        Main.panel.getContentPane().revalidate();
        Main.panel.add(new HsScreen(), BorderLayout.CENTER);
    }
}
