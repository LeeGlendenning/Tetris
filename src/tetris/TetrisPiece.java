package tetris;

import java.awt.Color;
import java.awt.Graphics;

public class TetrisPiece {
    
    private Color colour = null;
    private int xc1=0, xc2=0, xc3=0, xc4=0; //xc = x coordinate
    private int yc1=0, yc2=0, yc3=0, yc4=0; //yc = y coordinate
    final int WIDTH = 26;
    
    private int x1=0, x2=0, x3=0, x4=0;
    private int y1=0, y2=0, y3=0, y4=0;
    
    public TetrisPiece(int type){
        createPiece(type);
    }
    
    private void createPiece(int type){
        switch (type) {
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
            default:
                System.out.println("INVALID PIECE NUMBER");
        }
        
    }
    
    //used for nextPiece
    public void multByWidth(){
        xc1 *= WIDTH;
        xc2 *= WIDTH;
        xc3 *= WIDTH;
        xc4 *= WIDTH;
        yc1 *= WIDTH;
        yc2 *= WIDTH;
        yc3 *= WIDTH;
        yc4 *= WIDTH;
    }
    
    public int[] getXc(){
        return new int[] {xc1, xc2, xc3, xc4};
    }
    
    public int[] getYc(){
        return new int[] {yc1, yc2, yc3, yc4};
    }
    
    public Color getColor(){
        return colour;
    }
    
    public void setXc(int x1, int x2, int x3, int x4){
        xc1 = x1;
        xc2 = x2;
        xc3 = x3;
        xc4 = x4;
    }
    
    public void setYc(int y1, int y2, int y3, int y4){
        yc1 = y1;
        yc2 = y2;
        yc3 = y3;
        yc4 = y4;
    }
    
    public void drawAsNextPiece(Graphics g){
        g.setColor(colour);
        g.fillRoundRect(xc1 + 1, yc1 + 1, WIDTH - 2, WIDTH - 2, 5, 5);
        g.fillRoundRect(xc2 + 1, yc2 + 1, WIDTH - 2, WIDTH - 2, 5, 5);
        g.fillRoundRect(xc3 + 1, yc3 + 1, WIDTH - 2, WIDTH - 2, 5, 5);
        g.fillRoundRect(xc4 + 1, yc4 + 1, WIDTH - 2, WIDTH - 2, 5, 5);
    }
    
    public void drawGameplayPiece(Graphics g, int id){
        x1 = Gameplay.coords[xc1][yc1].getx(colour, id);
        x2 = Gameplay.coords[xc2][yc2].getx(colour, id);
        x3 = Gameplay.coords[xc3][yc3].getx(colour, id);
        x4 = Gameplay.coords[xc4][yc4].getx(colour, id);
        y1 = Gameplay.coords[xc1][yc1].gety(colour, id);
        y2 = Gameplay.coords[xc2][yc2].gety(colour, id);
        y3 = Gameplay.coords[xc3][yc3].gety(colour, id);
        y4 = Gameplay.coords[xc4][yc4].gety(colour, id);


        g.setColor(colour);
        //initialize piece
        g.fillRoundRect(x1 + 1, y1 + 1, WIDTH - 2, WIDTH - 2, 5, 5);
        g.fillRoundRect(x2 + 1, y2 + 1, WIDTH - 2, WIDTH - 2, 5, 5);
        g.fillRoundRect(x3 + 1, y3 + 1, WIDTH - 2, WIDTH - 2, 5, 5);
        g.fillRoundRect(x4 + 1, y4 + 1, WIDTH - 2, WIDTH - 2, 5, 5);
    }
    
    public void drawShadow(Graphics g){
        int tempyc1 = yc1, tempyc2 = yc2, tempyc3 = yc3, tempyc4 = yc4;
        
        while (tempyc1 < 19 && tempyc2 < 19 && tempyc3 < 19 && tempyc4 < 19 && Gameplay.coords[xc1][tempyc1 + 1].getTaken() == false && Gameplay.coords[xc2][tempyc2 + 1].getTaken() == false && Gameplay.coords[xc3][tempyc3 + 1].getTaken() == false && Gameplay.coords[xc4][tempyc4 + 1].getTaken() == false){
            tempyc1 ++;
            tempyc2 ++;
            tempyc3 ++;
            tempyc4 ++;
        }
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRoundRect(xc1 + 1, tempyc1 + 1, WIDTH - 2, WIDTH - 2, 5, 5);
        g.fillRoundRect(xc2 + 1, tempyc2 + 1, WIDTH - 2, WIDTH - 2, 5, 5);
        g.fillRoundRect(xc3 + 1, tempyc3 + 1, WIDTH - 2, WIDTH - 2, 5, 5);
        g.fillRoundRect(xc4 + 1, tempyc4 + 1, WIDTH - 2, WIDTH - 2, 5, 5);
    }
    
    public void incrementXc(){
        xc1 ++;
        xc2 ++;
        xc3 ++;
        xc4 ++;
    }
    
    public void incrementYc(){
        yc1 ++;
        yc2 ++;
        yc3 ++;
        yc4 ++;
    }
    
    public void removePiece(Graphics g){
        g.setColor(Color.black);
        g.fillRect(x1 + 1, y1 + 1, WIDTH - 2, WIDTH - 2);
        g.fillRect(x2 + 1, y2 + 1, WIDTH - 2, WIDTH - 2);
        g.fillRect(x3 + 1, y3 + 1, WIDTH - 2, WIDTH - 2);
        g.fillRect(x4 + 1, y4 + 1, WIDTH - 2, WIDTH - 2);
    }
    
    public void updateY(int id){
        y1 = Gameplay.coords[xc1][yc1].gety(colour, id);
        y2 = Gameplay.coords[xc2][yc2].gety(colour, id);
        y3 = Gameplay.coords[xc3][yc3].gety(colour, id);
        y4 = Gameplay.coords[xc4][yc4].gety(colour, id);
    }
}
