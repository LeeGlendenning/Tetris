package tetris;

import java.awt.Graphics;
import javax.swing.JComponent;

/*
 * NextPiece class used to create a box to display the next piece that will be generated for the player will get
 * 
 * @author Lee Glendenning
 * @version 1.0
 */
public class NextPiece extends JComponent {
    
    /*
     * NextPiece constructor calls the paintComponent method with the repaint call
     */
    public NextPiece(){
        repaint();
    }
    
    /*
     * paintComponent method used to draw the next piece for the player
     * 
     * @param g     Graphics object used to draw to the screen
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        TetrisPiece nextPiece = new TetrisPiece(Gameplay.nextPiece);
        
        nextPiece.multByWidth();
        
        //move piece to (0,0) to be drawn in next piece space
        while (nextPiece.getXc()[0] >= nextPiece.WIDTH && nextPiece.getXc()[1] >= nextPiece.WIDTH && nextPiece.getXc()[2] >= nextPiece.WIDTH && nextPiece.getXc()[3] >= nextPiece.WIDTH){
            nextPiece.setXc(nextPiece.getXc()[0] - nextPiece.WIDTH, nextPiece.getXc()[1] - nextPiece.WIDTH, nextPiece.getXc()[2] - nextPiece.WIDTH, nextPiece.getXc()[3] - nextPiece.WIDTH);
        }
        while (nextPiece.getYc()[0] >= nextPiece.WIDTH && nextPiece.getYc()[1] >= nextPiece.WIDTH && nextPiece.getYc()[2] >= nextPiece.WIDTH && nextPiece.getYc()[3] >= nextPiece.WIDTH){
            nextPiece.setYc(nextPiece.getYc()[0] - nextPiece.WIDTH, nextPiece.getYc()[1] - nextPiece.WIDTH, nextPiece.getYc()[2] - nextPiece.WIDTH, nextPiece.getYc()[3] - nextPiece.WIDTH);
        }
        
        nextPiece.drawAsNextPiece(g);
    }
    
}
