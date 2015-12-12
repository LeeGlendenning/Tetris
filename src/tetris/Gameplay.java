package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * Gameplay class used to make Gameplay object. Object is used to play Tetris.
 * 
 * @author Lee Glendenning
 * @version 1.0
 */
public class Gameplay extends JComponent implements ActionListener {

    boolean gameOver = false;
    int timer_count = 0;
    boolean isline1 = false;
    boolean isline2 = false;
    boolean isline3 = false;
    boolean isline4 = false;
    int id = 0;
    public static Color colour;
    boolean dropping = false;
    int curPiece;
    boolean first = true;
    public static int x1, x2, x3, x4;
    public static int y1, y2, y3, y4;
    int xc1, xc2, xc3, xc4; //xc = x coordinate
    int yc1, yc2, yc3, yc4; //yc = y coordinate
    static int interval = 800;
    Timer timer;
    static boolean delay = true;
    int delay_time;
    int initTimerVal = 80;
    boolean drop_fast = false;
    public static Point coords[][] = new Point[10][20];
    public static int width = 26; //length/width of cell
    public static int nextPiece = -1;
    public static Clip clip;
    public static AudioInputStream sound;
    private static int pieceRepeat = 0;
    private static int lastPiece = -1;
    public KeyListen k = new KeyListen();
    private boolean lockDropping = false;
    
    
    /* 
     * drawLogo method draws the Tetris logo to the top of the play area (130,15)
     * 
     * @param g     Graphics object used to draw to the screen
     */
    private void drawLogo(Graphics g){
        BufferedImage logo = null;
        try {
            logo = ImageIO.read(getClass().getResource("/logo.png"));
        } catch (IOException e) {
        }
        g.drawImage(logo, 130, 15, this);
    }
    
    /* 
     * paintComponent method calls all of the drawing methods to draw all graphics for game
     * 
     * @param g     Graphics object used to draw to the screen
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        k.requestFocus(); // request focus for the key listener (allows user to use keyboard to place pieces)
        
        drawLogo(g); // draw tetris logo
        
        g.setColor(new Color(51, 153, 255));
        g.drawRect(104, 120, 262, 520); //draw border for play area

        if (!dropping) { //piece has just been placed
            isline1 = true;
            isline2 = true;
            isline3 = true;
            isline4 = true;

            //check whether line has been made at each y coordinate of piece (there could be 4 different y coords if vertical 1x4 block)
            for (int l1 = 0; l1 < 10; l1++) { //check line at yc1
                if (coords[l1][yc1].getTaken() != true) {
                    isline1 = false;
                }
            }
            for (int l2 = 0; l2 < 10; l2++) { //check line at yc2
                if (coords[l2][yc2].getTaken() != true) {
                    isline2 = false;
                }
            }
            for (int l3 = 0; l3 < 10; l3++) { //check line at yc3
                if (coords[l3][yc3].getTaken() != true) {
                    isline3 = false;
                }
            }
            for (int l4 = 0; l4 < 10; l4++) { //check line at yc4
                if (coords[l4][yc4].getTaken() != true) {
                    isline4 = false;
                }
            }
            
            int lineCount = 0;
            //take away line(s)
            if (isline1) { //is unique line at yc1
                lineCount ++;
                Stats.incLines(); //increment line count
                for (int m1 = 0; m1 < 10; m1++) {
                    coords[m1][yc1].setTaken(false); //delete line
                }
            }
            if (isline2) {
                if (yc2 != yc1) { //is unique line at yc2
                    lineCount ++;
                    Stats.incLines(); //increment line count
                    for (int m2 = 0; m2 < 10; m2++) {
                        coords[m2][yc2].setTaken(false); //delete line
                    }
                } else {
                    isline2 = false;
                }
            }
            if (isline3) {
                if (yc3 != yc1 && yc3 != yc2) { //is unique line at yc3
                    lineCount ++;
                    Stats.incLines(); //increment line count
                    for (int m3 = 0; m3 < 10; m3++) {
                        coords[m3][yc3].setTaken(false); //delete line
                    }
                } else {
                    isline3 = false;
                }
            }
            if (isline4) {
                if (yc4 != yc1 && yc4 != yc2 && yc4 != yc3) { //is unique line at yc4
                    lineCount ++;
                    Stats.incLines(); //increment line count
                    for (int m4 = 0; m4 < 10; m4++) {
                        coords[m4][yc4].setTaken(false); //delete line
                    }
                } else {
                    isline4 = false;
                }
            }
            if (lineCount == 4){
                playSound("Tetris.wav");
            }

            //drop pieces above deleted line 1 y coordinate
            if (isline1) {
                for (int n1 = yc1 - 1; n1 >= 0; n1--) {
                    for (int o1 = 0; o1 < 10; o1++) {
                        if (coords[o1][n1].getTaken() == true) {
                            coords[o1][n1].setTaken(false);
                            coords[o1][n1 + 1].setTaken(true);
                        }
                    }
                }
                if (yc2 < yc1) {
                    yc2++;
                }
                if (yc3 < yc1) {
                    yc3++;
                }
                if (yc4 < yc1) {
                    yc4++;
                }

            }
            if (isline2) {
                for (int n2 = yc2 - 1; n2 >= 0; n2--) {
                    for (int o2 = 0; o2 < 10; o2++) {
                        if (coords[o2][n2].getTaken() == true) {
                            coords[o2][n2].setTaken(false);
                            coords[o2][n2 + 1].setTaken(true);
                        }
                    }
                }
                if (yc3 < yc1) {
                    yc3++;
                }
                if (yc4 < yc1) {
                    yc4++;
                }
            }
            if (isline3) {
                for (int n3 = yc3 - 1; n3 >= 0; n3--) {
                    for (int o3 = 0; o3 < 10; o3++) {
                        if (coords[o3][n3].getTaken() == true) {
                            coords[o3][n3].setTaken(false);
                            coords[o3][n3 + 1].setTaken(true);
                        }
                    }
                }
                if (yc4 < yc1) {
                    yc4++;
                }
            }
            if (isline4) {
                for (int n4 = yc4 - 1; n4 >= 0; n4--) {
                    for (int o4 = 0; o4 < 10; o4++) {
                        if (coords[o4][n4].getTaken() == true) {
                            coords[o4][n4].setTaken(false);
                            coords[o4][n4 + 1].setTaken(true);
                        }
                    }
                }
            }
            if (isline1 || isline2 || isline3 || isline4){
                playSound("LineClear.wav");
            }
        }

        //loop to paint pieces already existing in game play area
        for (int i = 0; i < 10; i++) //x coords
        {
            for (int j = 0; j < 20; j++) //y coords
            {
                if (coords[i][j].getTaken() == true) {
                    Color colour2 = coords[i][j].getColour();
                    int id2 = coords[i][j].getId();
                    g.setColor(colour2);
                    g.fillRoundRect(coords[i][j].getx(colour2, id2) + 1, coords[i][j].gety(colour2, id2) + 1, width - 2, width - 2, 5, 5);
                }
            }
        }

        //generate random number between 1,7 to represent each curPiece
        if (!dropping) {
            id++;
            k.requestFocus();
            first = true;
            if (nextPiece == -1){
                do{
                    lastPiece = curPiece;
                    curPiece = 1 + (int) (Math.random() * 7);
                    pieceRepeat ++;
                    if (lastPiece == -1){
                        lastPiece = curPiece;
                    }else if (lastPiece != curPiece){
                        pieceRepeat = 1;
                    }
                    
                }while (curPiece == lastPiece && pieceRepeat > 2);//prevent getting same curPiece 3 times in a row
            }else{
                curPiece = nextPiece;
            }
            nextPiece = 1 + (int) (Math.random() * 7);
        }

        //create new curPiece
        if (first) {

            first = false;
            switch (curPiece) {
                case (1): //1x4 block
                    xc1 = 3;
                    xc2 = 4;
                    xc3 = 5;
                    xc4 = 6;
                    yc1 = yc2 = yc3 = yc4 = 0;
                    colour = Color.blue;
                    break;
                case (2): //2x2 block
                    xc1 = 4;
                    xc2 = 5;
                    xc3 = 4;
                    xc4 = 5;
                    yc1 = 0;
                    yc2 = 0;
                    yc3 = 1;
                    yc4 = 1;
                    colour = Color.yellow;
                    break;
                case (3): //s block
                    xc1 = 5;
                    xc2 = 6;
                    xc3 = 4;
                    xc4 = 5;
                    yc1 = 0;
                    yc2 = 0;
                    yc3 = 1;
                    yc4 = 1;
                    colour = Color.green;
                    break;
                case (4): //z block
                    xc1 = 4;
                    xc2 = 5;
                    xc3 = 5;
                    xc4 = 6;
                    yc1 = 0;
                    yc2 = 0;
                    yc3 = 1;
                    yc4 = 1;
                    colour = Color.red;
                    break;
                case (5): //L block
                    xc1 = 3;
                    xc2 = 4;
                    xc3 = 5;
                    xc4 = 3;
                    yc1 = 0;
                    yc2 = 0;
                    yc3 = 0;
                    yc4 = 1;
                    colour = new Color(255, 102, 0); //orange
                    break;
                case (6): //backwards L block
                    xc1 = 3;
                    xc2 = 4;
                    xc3 = 5;
                    xc4 = 5;
                    yc1 = 0;
                    yc2 = 0;
                    yc3 = 0;
                    yc4 = 1;
                    colour = Color.pink;
                    break;
                case (7): //top hat block
                    xc1 = 3;
                    xc2 = 4;
                    xc3 = 5;
                    xc4 = 4;
                    yc1 = 0;
                    yc2 = 0;
                    yc3 = 0;
                    yc4 = 1;
                    colour = new Color(102, 0, 205); //purple
                    break;
            }
            //if new piece overlaps a piece in top row gameover
            if (coords[xc1][yc1].getTaken() || coords[xc2][yc2].getTaken() || coords[xc3][yc3].getTaken() || coords[xc4][yc4].getTaken()) {
                gameOver = true;
                if (Home.playSound){
                    clip.stop();
                    clip.flush();
                    clip.setFramePosition(0);
                }
                fillCoords(); //reset board
                Main.panel.getContentPane().removeAll();
                Main.panel.getContentPane().revalidate();
                Main.panel.add(new Gameover(), BorderLayout.CENTER);
                return;
            }
        }
        
        //---------draw shadow----------------
        int tempyc1 = yc1, tempyc2 = yc2, tempyc3 = yc3, tempyc4 = yc4;
        //temporarily set the current place to be untaken
        coords[xc1][yc1].setTaken(false);
        coords[xc2][yc2].setTaken(false);
        coords[xc3][yc3].setTaken(false);
        coords[xc4][yc4].setTaken(false);

        while (tempyc1 < 19 && tempyc2 < 19 && tempyc3 < 19 && tempyc4 < 19 && coords[xc1][tempyc1 + 1].getTaken() == false && coords[xc2][tempyc2 + 1].getTaken() == false && coords[xc3][tempyc3 + 1].getTaken() == false && coords[xc4][tempyc4 + 1].getTaken() == false){
            tempyc1 ++;
            tempyc2 ++;
            tempyc3 ++;
            tempyc4 ++;
        }
        
        x1 = Gameplay.coords[xc1][tempyc1].getx();
        x2 = Gameplay.coords[xc2][tempyc2].getx();
        x3 = Gameplay.coords[xc3][tempyc3].getx();
        x4 = Gameplay.coords[xc4][tempyc4].getx();
        y1 = Gameplay.coords[xc1][tempyc1].gety();
        y2 = Gameplay.coords[xc2][tempyc2].gety();
        y3 = Gameplay.coords[xc3][tempyc3].gety();
        y4 = Gameplay.coords[xc4][tempyc4].gety();
        
        Graphics2D g2d = (Graphics2D)g;//used to draw transparent colour
        g2d.setColor(new Color(217,217,217,75));
        g2d.fillRoundRect(x1 + 1, y1 + 1, width - 2, width - 2, 5, 5);
        g2d.fillRoundRect(x2 + 1, y2 + 1, width - 2, width - 2, 5, 5);
        g2d.fillRoundRect(x3 + 1, y3 + 1, width - 2, width - 2, 5, 5);
        g2d.fillRoundRect(x4 + 1, y4 + 1, width - 2, width - 2, 5, 5);
        //set the current place back to taken
        coords[xc1][yc1].setTaken(true);
        coords[xc2][yc2].setTaken(true);
        coords[xc3][yc3].setTaken(true);
        coords[xc4][yc4].setTaken(true);
        //------------------------------------
        
        
        //draw game piece
        x1 = coords[xc1][yc1].getx(colour, id);
        x2 = coords[xc2][yc2].getx(colour, id);
        x3 = coords[xc3][yc3].getx(colour, id);
        x4 = coords[xc4][yc4].getx(colour, id);
        y1 = coords[xc1][yc1].gety(colour, id);
        y2 = coords[xc2][yc2].gety(colour, id);
        y3 = coords[xc3][yc3].gety(colour, id);
        y4 = coords[xc4][yc4].gety(colour, id);
        
        g.setColor(colour);
        //initialize piece
        g.fillRoundRect(x1 + 1, y1 + 1, width - 2, width - 2, 5, 5);
        g.fillRoundRect(x2 + 1, y2 + 1, width - 2, width - 2, 5, 5);
        g.fillRoundRect(x3 + 1, y3 + 1, width - 2, width - 2, 5, 5);
        g.fillRoundRect(x4 + 1, y4 + 1, width - 2, width - 2, 5, 5);
        
        
        
        if (!dropping){//means a new piece has just been generated so draw the next piece
            dropping = true;
            Stats.nextPiecePanel.removeAll();
            Stats.nextPiecePanel.revalidate();
            Stats.nextPiecePanel.add(new NextPiece());
        }

        if (!drop_fast) {
            if (delay_time >= 0){
                if (Integer.parseInt(Stats.levelLabel.getText()) < 8){
                    delay_time = initTimerVal - Integer.parseInt(Stats.levelLabel.getText()) * 10;
                }else if (Integer.parseInt(Stats.levelLabel.getText()) == 8){
                    delay_time = 6;
                }else if (Integer.parseInt(Stats.levelLabel.getText()) == 9){
                    delay_time = 3;
                }else {
                    delay_time = 1;
                }
            } else {//if negative, just make it 1
                delay_time = 1;
            }
        } else {
            delay_time = 0;
        }
        
        timer = new Timer(delay_time, this);
        
        timer.setCoalesce(true);
        timer.start();

        //gravity
        
        if (timer_count == 7) {
            timer_count = 0;

            int tempid1 = coords[xc1][yc1].getId();
            int tempid2 = coords[xc2][yc2].getId();
            int tempid3 = coords[xc3][yc3].getId();
            int tempid4 = coords[xc4][yc4].getId();
            //int tempyc1 = yc1, tempyc2 = yc2, tempyc3 = yc3, tempyc4 = yc4;
            tempyc1 = yc1; //used for checking whether it is possible to move the piece down any more or if there is another piece in the way
            tempyc2 = yc2;
            tempyc3 = yc3;
            tempyc4 = yc4;

            while (tempyc1 + 1 < 19 && coords[xc1][tempyc1 + 1].getId() == tempid1) {	//while loops to check below every square of the block
                tempyc1++;
            }
            while (tempyc2 + 1 < 19 && coords[xc2][tempyc2 + 1].getId() == tempid2) {
                tempyc2++;
            }
            while (tempyc3 + 1 < 19 && coords[xc3][tempyc3 + 1].getId() == tempid3) {
                tempyc3++;
            }
            while (tempyc4 + 1 < 19 && coords[xc4][tempyc4 + 1].getId() == tempid4) {
                tempyc4++;
            }
            if (yc1 < 19 && yc2 < 19 && yc3 < 19 && yc4 < 19 && coords[xc1][tempyc1 + 1].getTaken() == false && coords[xc2][tempyc2 + 1].getTaken() == false && coords[xc3][tempyc3 + 1].getTaken() == false && coords[xc4][tempyc4 + 1].getTaken() == false) {
                //update piece location
                if (!delay) {
                    delay = true;
                    g.setColor(Color.black);
                    //remove blocks at previous location
                    g.fillRect(x1 + 1, y1 + 1, width - 2, width - 2);
                    g.fillRect(x2 + 1, y2 + 1, width - 2, width - 2);
                    g.fillRect(x3 + 1, y3 + 1, width - 2, width - 2);
                    g.fillRect(x4 + 1, y4 + 1, width - 2, width - 2);
                    //set the space to empty since the piece is not there anymore
                    coords[xc1][yc1].setTaken(false);
                    coords[xc2][yc2].setTaken(false);
                    coords[xc3][yc3].setTaken(false);
                    coords[xc4][yc4].setTaken(false);
                    yc1++;
                    yc2++;
                    yc3++;
                    yc4++;
                    y1 = coords[xc1][yc1].gety(colour, id);
                    y2 = coords[xc2][yc2].gety(colour, id);
                    y3 = coords[xc3][yc3].gety(colour, id);
                    y4 = coords[xc4][yc4].gety(colour, id);
                    g.setColor(colour);
                    g.fillRoundRect(x1 + 1, y1 + 1, width - 2, width - 2, 5, 5);
                    g.fillRoundRect(x2 + 1, y2 + 1, width - 2, width - 2, 5, 5);
                    g.fillRoundRect(x3 + 1, y3 + 1, width - 2, width - 2, 5, 5);
                    g.fillRoundRect(x4 + 1, y4 + 1, width - 2, width - 2, 5, 5);
                }
            } else if (!lockDropping){
                dropping = false;
            }
        }
    }
    
    /*
     * Gameplay constructor used for creating a Gameplay object.
     * Initializes the game board.
     */
    public Gameplay() {
        init();
        fillCoords();
        add(k);
        k.requestFocus();
    }
    
    /* 
     * init method is called by the Gameplay constructor to start playing music for the main screen,
     * set initial lines to 0 and level to 1
     */
    private void init(){
        playMusic();
        Stats.lines = 0;
        Stats.linesLabel.setText(Stats.lines+"");
        Stats.levelLabel.setText(Stats.level);
    }
    
    /*
     * playMusic method used to start music in continuous loop
     */
    private void playMusic(){
        if (Home.playSound){
            if (clip == null){
                InputStream soundFile = getClass().getClassLoader().getResourceAsStream("TypeA.wav");
                try {
                    //File soundFile = new File("TypeA.wav");
                    sound = AudioSystem.getAudioInputStream(soundFile);
                

                DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(sound);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(Gameover.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Gameplay.clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }
    
    /*
     * fillCoords method called by init method. Used to fill coords array with new emptpy Points
     * i.e. no pieces in coords yet
     */
    private void fillCoords() {
        int x = 105; //x index of coords
        for (int i = 0; i < 10; i++) //x coords
        {
            int y = 120; //y index pf coords
            for (int j = 0; j < 20; j++) //y coords
            {
                coords[i][j] = new Point(x, y, false, null, 0); //fill x coord with left most pixel of cell, fill y coord with top most pixel of cell
                y += width;
            }
            x += width;
        }
    }
    
    /*
     * playSound method to play sound effect on action.
     * e.g. hard drop, line clear, etc.
     * 
     * @param sound     String holding file name of sound to be played
     */
    private void playSound(String sound){
        if (Home.playSound){
            AudioInputStream ais = null;
            try {
                //play music
                Clip clip2 = AudioSystem.getClip();
                ais = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream(sound)));
                clip2.open(ais);
                if (sound.equals("HardDrop.wav")){ // increase volume for hard drop sound because it is hard to hear otherwise
                    ((FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN)).setValue(6);
                }
                clip2.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                Logger.getLogger(Gameplay.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    ais.close();
                } catch (IOException ex) {
                    Logger.getLogger(Gameplay.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /*
     * actionPerformed callback method for the timer governing piece dropping.
     * Method resets timer and repaints the screen.
     * 
     * @param e     ActionEvent object containing details for why this callback method was called. Not actually used
     */
    @Override
    public void actionPerformed(ActionEvent e) { // deal with timer
        if (!gameOver) {
            delay = false;
            timer_count++;
            timer.stop();
            repaint();
        }
    }
    
    /*
     * KeyListen class object to be embedded into Gameplay.
     * Class holds method for acting on key pressed and released.
     * 
     * @author Lee Glendenning
     * @version 1.0
     */
    public class KeyListen extends JPanel {
        //arrow key presses
        /*
         * KeyListen constructor adds key listener
         */
        public KeyListen() {
            setFocusable(true);
            addKeyListener(new KeyAdapter() {
                
                /*
                 * keyPressed method callback method called when any key is pressed down
                 * 
                 * @param e     KeyEvent object holding details for which key was pressed
                 */
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_SPACE://auto drop piece
                            playSound("HardDrop.wav");
                            //set old location to empty
                            coords[xc1][yc1].setTaken(false);
                            coords[xc2][yc2].setTaken(false);
                            coords[xc3][yc3].setTaken(false);
                            coords[xc4][yc4].setTaken(false);
                            
                            //update location (down by a row each iteration)
                            while (yc1 < 19 && yc2 < 19 && yc3 < 19 && yc4 < 19 && coords[xc1][yc1 + 1].getTaken() == false && coords[xc2][yc2 + 1].getTaken() == false && coords[xc3][yc3 + 1].getTaken() == false && coords[xc4][yc4 + 1].getTaken() == false){
                                yc1 ++;
                                yc2 ++;
                                yc3 ++;
                                yc4 ++;
                            }
                            
                            //set new location to taken
                            x1 = coords[xc1][yc1].getx(colour, id);
                            x2 = coords[xc2][yc2].getx(colour, id);
                            x3 = coords[xc3][yc3].getx(colour, id);
                            x4 = coords[xc4][yc4].getx(colour, id);
                            dropping = false;
                            timer_count = 0;
                            //repaint();
                            break;
                        case KeyEvent.VK_ESCAPE://pause
                            timer.stop();
                            ((CardLayout)Home.curGame.getLayout()).show(Home.curGame, "pause");
                            break;
                        case KeyEvent.VK_UP: //rotate piece
                            if (checkRotate() == true) {
                                coords[xc1][yc1].setTaken(false);
                                coords[xc2][yc2].setTaken(false);
                                coords[xc3][yc3].setTaken(false);
                                coords[xc4][yc4].setTaken(false);
                                if (curPiece == 1) { //rotate 1x4 block on inner right square

                                    if (yc1 == yc2) { //1x4 block must be horizontal
                                        xc1 = xc3;
                                        xc2 = xc3;
                                        xc4 = xc3;
                                        yc1 += 2;
                                        yc2++;
                                        yc4--;
                                    } else { //1x4 block must be vertical
                                        yc1 = yc3;
                                        yc2 = yc3;
                                        yc4 = yc3;
                                        xc1 -= 2;
                                        xc2--;
                                        xc4++;
                                    }

                                }//no 2x2 block because when rotated its curPiece stays consistent
                                else if (curPiece == 3) { //s block
                                    if (yc1 == yc2) { //horizontal state
                                        xc1--;
                                        xc2 -= 2;
                                        xc3 += 1;
                                        yc2 -= 1;
                                        yc4 -= 1;
                                    } else { //vertical state
                                        xc1++;
                                        xc2 += 2;
                                        xc3 -= 1;
                                        yc2 += 1;
                                        yc4 += 1;
                                    }
                                } else if (curPiece == 4) { //z block
                                    if (yc1 == yc2) { //horizontal state
                                        xc1++;
                                        xc3--;
                                        xc4 -= 2;
                                        yc2++;
                                        yc4++;
                                    } else { //vertical state
                                        xc1--;
                                        xc3++;
                                        xc4 += 2;
                                        yc2--;
                                        yc4--;
                                    }
                                } else if (curPiece == 5) { //L block
                                    if (yc1 != yc4) { //1 of horizontal states
                                        if (yc1 < yc4) { //first horizontal case. rotate to vertical state 1
                                            xc1++;
                                            xc3--;
                                            yc1--;
                                            yc3++;
                                            yc4 -= 2;
                                        } else { //second horizontal case. rotate to vertical state 2
                                            xc1 -= 2;
                                            xc2--;
                                            xc4--;
                                            yc1++;
                                            yc3--;
                                            yc4 += 2;
                                        }
                                    } else { //1 of vertical states
                                        if (xc1 > xc4) { //first vertical state. rotate to horizontal state 2
                                            xc1++;
                                            xc3--;
                                            xc4 += 2;
                                            yc1++;
                                            yc3--;
                                        } else { //second vertical state. rotate to horizontal state 1
                                            xc2++;
                                            xc3 += 2;
                                            xc4--;
                                            yc1--;
                                            yc3++;
                                        }
                                    }
                                } else if (curPiece == 6) { //backwards L block
                                    if (yc3 != yc4) { //1 of horizontal states
                                        if (yc3 < yc4) { //first horizontal case. rotate to vertical state 1
                                            xc1++;
                                            xc3--;
                                            xc4 -= 2;
                                            yc1--;
                                            yc3++;
                                        } else { //second horizontal case. rotate to vertical state 2
                                            xc1 -= 2;
                                            xc2--;
                                            xc4++;
                                            yc1++;
                                            yc3--;
                                        }
                                    } else { //1 of vertical states
                                        if (xc3 > xc4) { //first vertical state. rotate to horizontal state 2
                                            xc1++;
                                            xc3--;
                                            yc1++;
                                            yc3--;
                                            yc4 -= 2;
                                        } else { //second vertical state. rotate to horizontal state 1
                                            xc2++;
                                            xc3 += 2;
                                            xc4++;
                                            yc1--;
                                            yc3++;
                                            yc4 += 2;
                                        }
                                    }
                                } else if (curPiece == 7) { //top hat block
                                    if (yc2 != yc4) { //1 of horizontal states
                                        if (yc2 < yc4) { //first horizontal case. rotate to vertical state 1
                                            xc1++;
                                            xc3--;
                                            xc4--;
                                            yc1--;
                                            yc3++;
                                            yc4--;
                                        } else { //second horizontal case. rotate to vertical state 2
                                            xc1--;
                                            xc3++;
                                            xc4++;
                                            yc1++;
                                            yc3--;
                                            yc4++;
                                        }
                                    } else { //1 of vertical states
                                        if (xc2 > xc4) { //first vertical state. rotate to horizontal state 2
                                            xc1++;
                                            xc3--;
                                            xc4++;
                                            yc1++;
                                            yc3--;
                                            yc4--;
                                        } else { //second vertical state. rotate to horizontal state 1
                                            xc1--;
                                            xc3++;
                                            xc4--;
                                            yc1--;
                                            yc3++;
                                            yc4++;
                                        }
                                    }
                                }
                                //restrict rotation to not go through walls of play area
                                checkBounds();
                                //repaint();
                            }
                            break;
                        case KeyEvent.VK_DOWN: //drop piece quickly
                            drop_fast = true;
                            break;
                        case KeyEvent.VK_LEFT: //slide piece left
                            if (dropping){
                                if (xc1 > 0 && xc2 > 0 && xc3 > 0 && xc4 > 0) {// && coords[xc1 - 1][yc1].getTaken() == false && coords[xc2 - 1][yc2].getTaken() == false && coords[xc3 - 1][yc3].getTaken() == false && coords[xc4 - 1][yc4].getTaken() == false){
                                    if (checkLeft() == true) {
                                        lockDropping = true;
                                        //set old location to empty
                                        coords[xc1][yc1].setTaken(false);
                                        coords[xc2][yc2].setTaken(false);
                                        coords[xc3][yc3].setTaken(false);
                                        coords[xc4][yc4].setTaken(false);
                                        //update location
                                        xc1--;
                                        xc2--;
                                        xc3--;
                                        xc4--;
                                        //set new location to taken
                                        x1 = coords[xc1][yc1].getx(colour, id);
                                        x2 = coords[xc2][yc2].getx(colour, id);
                                        x3 = coords[xc3][yc3].getx(colour, id);
                                        x4 = coords[xc4][yc4].getx(colour, id);

                                        //repaint();
                                        lockDropping = false;
                                        if (timer_count == 5){
                                            timer_count = 6;
                                        }
                                    }
                                }
                            }
                            break;

                        case KeyEvent.VK_RIGHT: //slide piece right
                            if (dropping){
                                if (xc1 < 9 && xc2 < 9 && xc3 < 9 && xc4 < 9) {// && coords[xc1 + 1][yc1].getTaken() == false && coords[xc2 + 1][yc2].getTaken() == false && coords[xc3 + 1][yc3].getTaken() == false && coords[xc4 + 1][yc4].getTaken() == false){
                                    if (checkRight() == true) {
                                        lockDropping = true;
                                        //set old location to empty
                                        coords[xc1][yc1].setTaken(false);
                                        coords[xc2][yc2].setTaken(false);
                                        coords[xc3][yc3].setTaken(false);
                                        coords[xc4][yc4].setTaken(false);
                                        //update location
                                        xc1++;
                                        xc2++;
                                        xc3++;
                                        xc4++;
                                        //set new location to taken
                                        x1 = coords[xc1][yc1].getx(colour, id);
                                        x2 = coords[xc2][yc2].getx(colour, id);
                                        x3 = coords[xc3][yc3].getx(colour, id);
                                        x4 = coords[xc4][yc4].getx(colour, id);

                                        //repaint();
                                        lockDropping = false;
                                        if (timer_count == 5){
                                            timer_count = 6;
                                        }
                                    }
                                }
                            }
                            break;
                    }
                }
                
                /*
                 * keyReleased callback method called when a previously pressed down key is released.
                 * Used to detect whether space bar has been released in which case fast drop is disabled.
                 * 
                 * @param e     KeyEvent object holding details for which key has been released
                 */
                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) { //check that down arrow key still held. if so, keep dropping quickly
                        drop_fast = false;					 //if released, drop at regular speed relative to level
                    }
                }
            });

        }
    }

    //ensure rotation doesn't pass through boundaries
    /*
     * checkBounds method used to enforce pieces staying within boundaries of gameplay area.
     * Method is called after rotating a piece.
     */
    private void checkBounds() {
        // if piece has been moved past the left wall of the play area, move it back in bounds
        while (xc1 < 0 || xc2 < 0 || xc3 < 0 || xc4 < 0) {
            xc1++;
            xc2++;
            xc3++;
            xc4++;
        }
        // if piece has been moved past the right wall of the play area, move it back in bounds
        while (xc1 > 9 || xc2 > 9 || xc3 > 9 || xc4 > 9) {
            xc1--;
            xc2--;
            xc3--;
            xc4--;
        }
        // if piece has been moved below the bottom wall of the play area, move it back in bounds
        while (yc1 > 19 || yc2 > 19 || yc3 > 19 || yc4 > 19) {
            yc1--;
            yc2--;
            yc3--;
            yc4--;
        }
        // // if piece has been moved above the top wall of the play area, move it back in bounds
        while (yc1 < 0 || yc2 < 0 || yc3 < 0 || yc4 < 0) {
            yc1++;
            yc2++;
            yc3++;
            yc4++;
        }
    }
    
    /*
     * checkRotate method used to verify whether an attempted rotation is valid.
     * i.e. valid rotation if piece will not overlap existing piece
     * 
     * @return validrotation    boolean representing whether the attempted rotation is in fact a valid one
     */
    private boolean checkRotate() {
        boolean validRotation = true;

        if (curPiece == 1) { //rotate 1x4 block on inner right square
            if (yc1 == yc2) { //1x4 block must be horizontal
                if (yc3 - 1 < 0 || yc3 + 2 > 19 || coords[xc3][yc3 - 1].getTaken() == true || coords[xc3][yc3 + 1].getTaken() == true || coords[xc3][yc3 + 2].getTaken() == true) {
                    validRotation = false;
                }
            } else { //1x4 block must be vertical
                if (xc1 - 2 < 0 || xc4 + 1 > 9 || coords[xc3 - 2][yc3].getTaken() == true || coords[xc3 - 1][yc3].getTaken() == true || coords[xc3 + 1][yc3].getTaken() == true) {
                    validRotation = false;
                }
            }
        }//no 2x2 block because when rotated its curPiece stays consistent
        else if (curPiece == 3) { //s block
            if (yc1 == yc2) { //horizontal state
                if (yc1 - 1 < 0 || coords[xc1][yc1 - 1].getTaken() == true || coords[xc2][yc2 + 1].getTaken() == true) {
                    validRotation = false;
                }
            } else { //vertical state
                if (xc4 + 1 > 9 || coords[xc1][yc1 + 1].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                    validRotation = false;
                }
            }
        } else if (curPiece == 4) { //z block
            if (yc1 == yc2) { //horizontal state
                if (yc4 + 1 > 19 || coords[xc3 - 1][yc3].getTaken() == true || coords[xc4 - 2][yc4 + 1].getTaken() == true) {
                    validRotation = false;
                }
            } else { //vertical state
                if (xc2 + 1 > 9 || coords[xc1 - 1][yc1].getTaken() == true || coords[xc2 + 1][yc2].getTaken() == true) {
                    validRotation = false;
                }
            }
        } else if (curPiece == 5) { //L block
            if (yc1 != yc4) { //1 of horizontal states
                if (yc1 < yc4) { //first horizontal case. rotate to vertical state 1
                    if (yc1 - 1 < 0 || coords[xc1][yc1 - 1].getTaken() == true || coords[xc2][yc2 - 1].getTaken() == true || coords[xc2][yc2 + 1].getTaken() == true) {
                        validRotation = false;
                    }
                } else { //second horizontal case. rotate to vertical state 2
                    if (yc3 + 1 > 19 || yc2 + 1 > 19 || coords[xc3][yc3 - 1].getTaken() == true || coords[xc3][yc3 + 1].getTaken() == true || coords[xc2][yc2 + 1].getTaken() == true) {
                        validRotation = false;
                    }
                }
            } else { //1 of vertical states
                if (xc1 > xc4) { //first vertical state. rotate to horizontal state 2
                    if (xc2 + 1 > 9 || xc1 + 1 > 9 || coords[xc1 + 1][yc1].getTaken() == true || coords[xc2 + 1][yc2].getTaken() == true || coords[xc2 - 1][yc2].getTaken() == true) {
                        validRotation = false;
                    }
                } else { //second vertical state. rotate to horizontal state 1
                    if (xc2 + 2 > 9 || coords[xc2 + 1][yc2].getTaken() == true || coords[xc2 + 2][yc2].getTaken() == true) {
                        validRotation = false;
                    }
                }
            }
        } else if (curPiece == 6) { //backwards L block
            if (yc3 != yc4) { //1 of horizontal states
                if (yc3 < yc4) { //first horizontal case. rotate to vertical state 1
                    if (yc2 - 1 < 0 || coords[xc1][yc1 + 1].getTaken() == true || coords[xc2][yc2 - 1].getTaken() == true) {
                        validRotation = false;
                    }
                } else { //second horizontal case. rotate to vertical state 2
                    if (yc3 + 1 > 19 || coords[xc3][yc3 + 1].getTaken() == true || coords[xc2][yc2 - 1].getTaken() == true) {
                        validRotation = false;
                    }
                }
            } else { //1 of vertical states
                if (xc3 > xc4) { //first vertical state. rotate to horizontal state 2
                    if (xc2 + 1 > 9 || coords[xc1 - 1][yc1].getTaken() == true || coords[xc2 - 1][yc2].getTaken() == true || coords[xc2 + 1][yc2].getTaken() == true) {
                        validRotation = false;
                    }
                } else { //second vertical state. rotate to horizontal state 1
                    if (xc2 + 2 > 9 || coords[xc2 + 1][yc2].getTaken() == true || coords[xc2 + 2][yc2].getTaken() == true || coords[xc1 + 2][yc1].getTaken() == true) {
                        validRotation = false;
                    }
                }
            }
        } else if (curPiece == 7) { //top hat block
            if (yc2 != yc4) { //1 of horizontal states
                if (yc2 < yc4) { //first horizontal case. rotate to vertical state 1
                    if (yc2 - 1 < 0 || coords[xc2][yc2 - 1].getTaken() == true) {
                        validRotation = false;
                    }
                } else { //second horizontal case. rotate to vertical state 2
                    if (yc3 + 1 > 19 || coords[xc2][yc2 + 1].getTaken() == true) {
                        validRotation = false;
                    }
                }
            } else { //1 of vertical states
                if (xc2 > xc4) { //first vertical state. rotate to horizontal state 2
                    if (xc2 + 1 > 9 || coords[xc2 + 1][yc2].getTaken() == true) {
                        validRotation = false;
                    }
                } else { //second vertical state. rotate to horizontal state 1
                    if (xc2 - 1 < 0 || coords[xc2 - 1][yc2].getTaken() == true) {
                        validRotation = false;
                    }
                }
            }
        }

        return validRotation;
    }

    /*
     * checkLeft method used to make sure that if a piece moves left, it does not overlap an existing piece
     * 
     * @return validMove    boolean representing whether if curPiece were to be moved left, it would not overlap existing pieces
     */
    private boolean checkLeft() {
        boolean validMove = true;

        if (curPiece == 1) {
            if (yc1 == yc2) { //1x4 block must be horizontal
                if (coords[xc1 - 1][yc1].getTaken() == true) {
                    validMove = false;
                }
            } else { //1x4 block must be vertical
                if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc2 - 1][yc2].getTaken() == true || coords[xc3 - 1][yc3].getTaken() == true || coords[xc4 - 1][yc4].getTaken() == true) {
                    validMove = false;
                }
            }
        } else if (curPiece == 2) { //2x2 block
            if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc3 - 1][yc3].getTaken() == true) {
                validMove = false;
            }
        } else if (curPiece == 3) { //s block
            if (yc1 == yc2) { //horizontal state
                if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc3 - 1][yc3].getTaken() == true) {
                    validMove = false;
                }
            } else { //vertical state
                if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc2 - 1][yc2].getTaken() == true || coords[xc3 - 1][yc3].getTaken() == true) {
                    validMove = false;
                }
            }
        } else if (curPiece == 4) { //z block
            if (yc1 == yc2) { //horizontal state
                if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc3 - 1][yc3].getTaken() == true) {
                    validMove = false;
                }
            } else { //vertical state
                if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc3 - 1][yc3].getTaken() == true || coords[xc4 - 1][yc4].getTaken() == true) {
                    validMove = false;
                }
            }
        } else if (curPiece == 5) { //L block
            if (yc1 != yc4) { //1 of horizontal states
                if (yc1 < yc4) { //first horizontal case
                    if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc4 - 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                } else { //second horizontal case
                    if (coords[xc3 - 1][yc3].getTaken() == true || coords[xc4 - 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                }
            } else { //1 of vertical states
                if (xc1 > xc4) { //first vertical state
                    if (coords[xc4 - 1][yc4].getTaken() == true || coords[xc2 - 1][yc2].getTaken() == true || coords[xc3 - 1][yc3].getTaken() == true) {
                        validMove = false;
                    }
                } else { //second vertical state
                    if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc2 - 1][yc2].getTaken() == true || coords[xc3 - 1][yc3].getTaken() == true) {
                        validMove = false;
                    }
                }
            }
        } else if (curPiece == 6) { //backwards L block
            if (yc3 != yc4) { //1 of horizontal states
                if (yc3 < yc4) { //first horizontal case
                    if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc4 - 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                } else { //second horizontal case
                    if (coords[xc3 - 1][yc3].getTaken() == true || coords[xc4 - 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                }
            } else { //1 of vertical states
                if (xc3 > xc4) { //first vertical state
                    if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc2 - 1][yc2].getTaken() == true || coords[xc4 - 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                } else { //second vertical state
                    if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc2 - 1][yc2].getTaken() == true || coords[xc3 - 1][yc3].getTaken() == true) {
                        validMove = false;
                    }
                }
            }
        } else if (curPiece == 7) { //top hat block
            if (yc2 != yc4) { //1 of horizontal states
                if (yc2 < yc4) { //first horizontal case
                    if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc4 - 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                } else { //second horizontal case
                    if (coords[xc3 - 1][yc3].getTaken() == true || coords[xc4 - 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                }
            } else { //1 of vertical states
                if (xc2 > xc4) { //first vertical state
                    if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc3 - 1][yc3].getTaken() == true || coords[xc4 - 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                } else { //second vertical state
                    if (coords[xc1 - 1][yc1].getTaken() == true || coords[xc2 - 1][yc2].getTaken() == true || coords[xc3 - 1][yc3].getTaken() == true) {
                        validMove = false;
                    }
                }
            }
        }
        return validMove;
    }

    /*
     * checkLeft method used to make sure that if a piece moves right, it does not overlap an existing piece
     * 
     * @return validMove    boolean representing whether if curPiece were to be moved right, it would not overlap existing pieces
     */
    private boolean checkRight() {
        boolean validMove = true;


        if (curPiece == 1) { //rotate 1x4 block on inner right square

            if (yc1 == yc2) { //1x4 block must be horizontal
                if (coords[xc4 + 1][yc4].getTaken() == true) {
                    validMove = false;
                }
            } else { //1x4 block must be vertical
                if (coords[xc1 + 1][yc1].getTaken() == true || coords[xc2 + 1][yc2].getTaken() == true || coords[xc3 + 1][yc3].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                    validMove = false;
                }
            }

        } else if (curPiece == 2) { //2x2 block
            if (coords[xc2 + 1][yc2].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                validMove = false;
            }
        } else if (curPiece == 3) { //s block
            if (yc1 == yc2) { //horizontal state
                if (coords[xc2 + 1][yc2].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                    validMove = false;
                }
            } else { //vertical state
                if (coords[xc2 + 1][yc2].getTaken() == true || coords[xc3 + 1][yc3].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                    validMove = false;
                }
            }
        } else if (curPiece == 4) { //z block
            if (yc1 == yc2) { //horizontal state
                if (coords[xc2 + 1][yc2].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                    validMove = false;
                }
            } else { //vertical state
                if (coords[xc1 + 1][yc1].getTaken() == true || coords[xc2 + 1][yc2].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                    validMove = false;
                }
            }
        } else if (curPiece == 5) { //L block
            if (yc1 != yc4) { //1 of horizontal states
                if (yc1 < yc4) { //first horizontal case
                    if (coords[xc3 + 1][yc3].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                } else { //second horizontal case
                    if (coords[xc1 + 1][yc1].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                }
            } else { //1 of vertical states
                if (xc1 > xc4) { //first vertical state
                    if (coords[xc3 + 1][yc3].getTaken() == true || coords[xc2 + 1][yc2].getTaken() == true || coords[xc1 + 1][yc1].getTaken() == true) {
                        validMove = false;
                    }
                } else { //second vertical state
                    if (coords[xc4 + 1][yc4].getTaken() == true || coords[xc2 + 1][yc2].getTaken() == true || coords[xc3 + 1][yc3].getTaken() == true) {
                        validMove = false;
                    }
                }
            }
        } else if (curPiece == 6) { //backwards L block
            if (yc3 != yc4) { //1 of horizontal states
                if (yc3 < yc4) { //first horizontal case
                    if (coords[xc3 + 1][yc3].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                } else { //second horizontal case
                    if (coords[xc1 + 1][yc1].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                }
            } else { //1 of vertical states
                if (xc3 > xc4) { //first vertical state
                    if (coords[xc1 + 1][yc1].getTaken() == true || coords[xc2 + 1][yc2].getTaken() == true || coords[xc3 + 1][yc3].getTaken() == true) {
                        validMove = false;
                    }
                } else { //second vertical state
                    if (coords[xc1 + 1][yc1].getTaken() == true || coords[xc2 + 1][yc2].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                }
            }
        } else if (curPiece == 7) { //top hat block
            if (yc2 != yc4) { //1 of horizontal states
                if (yc2 < yc4) { //first horizontal case
                    if (coords[xc3 + 1][yc3].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                } else { //second horizontal case
                    if (coords[xc1 + 1][yc1].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                }
            } else { //1 of vertical states
                if (xc2 > xc4) { //first vertical state
                    if (coords[xc1 + 1][yc1].getTaken() == true || coords[xc2 + 1][yc2].getTaken() == true || coords[xc3 + 1][yc3].getTaken() == true) {
                        validMove = false;
                    }
                } else { //second vertical state
                    if (coords[xc1 + 1][yc1].getTaken() == true || coords[xc3 + 1][yc3].getTaken() == true || coords[xc4 + 1][yc4].getTaken() == true) {
                        validMove = false;
                    }
                }
            }
        }
        return validMove;
    }

}
