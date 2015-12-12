package tetris;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class HighScore implements Serializable {

    String name;
    int lines;

    public HighScore(String n, int l) {
        name = n;
        lines = l;
    }

    public void writeScore(int index) throws URISyntaxException{
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        //ArrayList<HighScore> h = new ArrayList();
        try {
            ArrayList<HighScore> h = null;
            ObjectInputStream ois = null;
            FileInputStream fin = null;
            try {
                fin = new FileInputStream(new File(getClass().getClassLoader().getResource("/highscore.dat").toURI()));
                //fin = new FileInputStream("highscore.dat");
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
            fout = new FileOutputStream(new File(getClass().getClassLoader().getResource("/highscore.dat").toURI()));
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
    public static ArrayList<HighScore> getScores() throws URISyntaxException{
        ArrayList<HighScore> h = null;
        ObjectInputStream ois = null;

        try {
            //FileInputStream fin = new FileInputStream(new File(tetris.HighScore.class.getResource("/highscore.dat").toURI()));
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
