package tetris;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * HighScore class used to create a HighScore object that can read from highscores file and write itself into the file
 */
public class HighScore implements Serializable {

    String name;
    int lines;
    
    /*
     * Highscore constructor initializes the HighScore object with a the name of the
     * player who achieved it as well as the number of lines they achieved
     */
    public HighScore(String n, int l) {
        name = n;
        lines = l;
    }
    
    /*
     * writeScore method writes the HighScore object details into the highscore file
     * 
     * @param index     index for highscore to be written to. e.g. player achieved 3rd highest score so write to index 2
     */
    public void writeScore(int index){
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        try {
            ArrayList<HighScore> h = null;
            ObjectInputStream ois = null;
            FileInputStream fin = null;
            try {
                try {
                    fin = new FileInputStream(new File(getClass().getClassLoader().getResource("/highscore.dat").toURI()));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
                }
                ois = new ObjectInputStream(fin);
                h = (ArrayList<HighScore>) ois.readObject();
            } catch (    ClassNotFoundException | IOException ex) {
                Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fin.close();
                } catch (IOException ex) {
                    Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ex) {
                    Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            h.add(index,this);
            try {
                fout = new FileOutputStream(new File(getClass().getClassLoader().getResource("/highscore.dat").toURI()));
            } catch (URISyntaxException ex) {
                Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
            }
            oos = new ObjectOutputStream(fout);
            oos.writeObject(h);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //returns list of scores or null if empty
    /*
     * getScores method gathers the current highscores from the highscores file
     * 
     * @return h        ArrayList of HighScores gathered from the highscores file
     */
    public static ArrayList<HighScore> getScores(){
        ArrayList<HighScore> h = null;
        ObjectInputStream ois = null;

        try {
            InputStream fin = tetris.HighScore.class.getResourceAsStream("/highscore.dat");
            ois = new ObjectInputStream(fin);
            try {
                h = (ArrayList<HighScore>) ois.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ois != null) {
            try {
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(HighScore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return h;
    }
}
