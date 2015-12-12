package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

public class Home extends JPanel {

    private JButton playButton = new BlueButton("Game");
    private JButton hsButton = new BlueButton("High Scores");
    private JButton aboutButton = new BlueButton("About");
    public Clip clip;
    private AudioInputStream sound;
    public static boolean playSound = true;
    public static JPanel curGame;

    public Home() throws IOException {
        
        registerListeners();

        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);

        JPanel titlePanel = new JPanel(new GridLayout(1, 1));
        titlePanel.setBackground(Color.BLACK);
        ImageIcon logo = new ImageIcon(ImageIO.read(getClass().getResource("/home_logo.png")));
        //ImageIcon logo = new ImageIcon("home_logo.png");
        JLabel logoLabel = new JLabel("", logo, JLabel.CENTER);
        titlePanel.add(logoLabel);
        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(new JLabel(" "), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
        buttonPanel.setBackground(Color.BLACK);

        JPanel tempPanel = new JPanel();
        tempPanel.setBackground(Color.BLACK);
        playButton.setPreferredSize(new Dimension(350, 50));
        
        tempPanel.add(playButton);
        buttonPanel.add(tempPanel);
        JPanel temp2Panel = new JPanel();
        temp2Panel.setBackground(Color.BLACK);
        hsButton.setPreferredSize(new Dimension(350, 50));
        temp2Panel.add(hsButton);
        buttonPanel.add(temp2Panel);
        JPanel temp3Panel = new JPanel();
        temp3Panel.setBackground(Color.BLACK);
        aboutButton.setPreferredSize(new Dimension(350, 50));
        temp3Panel.add(aboutButton);
        buttonPanel.add(temp3Panel);
        
        try {//play music to initialize clip which is being passed to SoundIcon
            playMusic();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(buttonPanel);
        bottomPanel.add(new SoundIcon(clip));
        
        topPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.CENTER);
        
    }
    
    private void playMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        if (playSound){
            if (clip == null){
                
                //File soundFile = new File("TitleScreen.wav");
                //InputStream soundFile = getClass().getClassLoader().getResourceAsStream("TitleScreen.wav");
                sound = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("TitleScreen.wav")));

                DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(sound);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            clip.start();
        }
    }

    private void registerListeners() {
        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (playSound){
                    clip.stop();
                    clip.flush();
                    clip.setFramePosition(0);
                }
                Main.panel.getContentPane().removeAll();
                Main.panel.getContentPane().revalidate();
                Main.panel.repaint();

                Main.panel.add(new HowTo(), BorderLayout.CENTER);
            }
        });

        hsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (clip != null){
                    clip.stop();
                }
                Main.firstHs = true;
                Main.panel.getContentPane().removeAll();
                Main.panel.getContentPane().revalidate();
                Main.panel.repaint();
                Main.panel.add(new HsScreen(), BorderLayout.CENTER);
            }
        });
        
        aboutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (playSound){
                    clip.stop();
                    clip.flush();
                    clip.setFramePosition(0);
                }
                Main.panel.getContentPane().removeAll();
                Main.panel.getContentPane().revalidate();
                Main.panel.repaint();
                Main.panel.add(new About(), BorderLayout.CENTER);
            }
        });
    }
}
