package tetris;

import java.awt.Graphics;
import javax.swing.JComponent;

public class NextPiece extends JComponent {
    
    public NextPiece(){
        repaint();
    }
    
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
        
        //System.out.println(nextPiece.getXc()[0] + ", " + nextPiece.getXc()[1] + ", " + nextPiece.getXc()[2] + ", " + nextPiece.getXc()[3] + ", ");
        nextPiece.drawAsNextPiece(g);
    }
    
}
