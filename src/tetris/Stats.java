package tetris;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Stats extends JPanel {

    public static String level = "1";
    public static int lines = 0;
    public static JLabel linesLabel = new JLabel(lines + "");
    public static JLabel levelLabel = new JLabel(level + "");
    public static JPanel nextPiecePanel = new JPanel(new GridLayout(1,1));
    private int pxBelowTitle = 20;

    public Stats() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.PAGE_AXIS));
        statsPanel.setOpaque(true);
        statsPanel.setBackground(Color.BLACK);
        JLabel nextPieceTitleLabel = new JLabel("Next");
        JLabel linesTitleLabel = new JLabel("Lines");
        JLabel levelTitleLabel = new JLabel("Level");
        
        JLabel pauseLabel = new JLabel("[Esc] => Pause");
        pauseLabel.setFont(new Font("Verdana", Font.BOLD, 11));
        pauseLabel.setForeground(Color.WHITE);
        statsPanel.add(pauseLabel);
        statsPanel.add(Box.createVerticalStrut(110));//spacing at top
        statsPanel.add(nextPieceTitleLabel);	//"Next"
        statsPanel.add(Box.createVerticalStrut(pxBelowTitle));//spacing below title
        nextPiecePanel.setBackground(Color.BLACK);
        nextPiecePanel.setPreferredSize(new Dimension(112,1));
        nextPiecePanel.add(new NextPiece());
        statsPanel.add(nextPiecePanel);
        statsPanel.add(Box.createVerticalStrut(20));
        statsPanel.add(linesTitleLabel);	//"Lines"
        statsPanel.add(Box.createVerticalStrut(pxBelowTitle));//spacing below title
        statsPanel.add(linesLabel);	//actual number of lines
        statsPanel.add(Box.createVerticalStrut(75));
        statsPanel.add(levelTitleLabel);	//"Level"
        statsPanel.add(Box.createVerticalStrut(pxBelowTitle));//spacing below title
        statsPanel.add(levelLabel);	//actual level
        statsPanel.add(Box.createVerticalStrut(200));//spacing at bottom

        //set font for headings
        Font font = new Font("Verdana", Font.BOLD, 16);
        nextPieceTitleLabel.setFont(font);
        nextPieceTitleLabel.setForeground(Color.WHITE);
        linesTitleLabel.setFont(font);
        linesTitleLabel.setForeground(Color.WHITE);
        levelTitleLabel.setFont(font);
        levelTitleLabel.setForeground(Color.WHITE);
        //set font for variables
        Font font2 = new Font("Comic Sans MS", Font.PLAIN, 12);
        linesLabel.setFont(font2);
        linesLabel.setForeground(Color.WHITE);
        levelLabel.setFont(font2);
        levelLabel.setForeground(Color.WHITE);

        //center all labels on left side of frame
        nextPieceTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        linesTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        linesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.setLayout(new BorderLayout());
        this.add(statsPanel, BorderLayout.CENTER);
        
        //InputStream soundFile = getClass().getClassLoader().getResourceAsStream("TypeA.wav");
        //File soundFile = new File("TypeA.wav");
        Gameplay.sound = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("TypeA.wav")));

        DataLine.Info info = new DataLine.Info(Clip.class, Gameplay.sound.getFormat());
        Gameplay.clip = (Clip) AudioSystem.getLine(info);
        Gameplay.clip.open(Gameplay.sound);
        
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.add(new SoundIcon(Gameplay.clip));
        
        add(bottomPanel, BorderLayout.SOUTH);
    }

    //increment lines and repaint
    public static void incLines() {
        linesLabel.setText((lines++) + 1 + "");
        if (Integer.parseInt(level) <= lines/10){
            levelLabel.setText((lines / 10) + 1 + "");
        }
    }
}
