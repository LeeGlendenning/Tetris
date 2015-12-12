package tetris;

import java.awt.Color;

public class Point {

    private int x;
    private int y;
    private boolean taken;
    private Color colour;
    private int id;

    public Point(int a, int b, boolean c, Color d, int e) {
        this.x = a;
        this.y = b;
        this.taken = c;
        this.colour = d;
        this.id = e;
    }

    public void setId(int e) {
        this.id = e;
    }

    public int getId() {
        int temp = this.id;
        return temp;
    }

    public void setColour(Color d) {
        this.colour = d;
    }

    public Color getColour() {
        Color temp = this.colour;
        return temp;
    }

    public void setx(int a) {
        this.x = a;
    }

    public void sety(int b) {
        this.y = b;
    }

    public int getx(Color d, int e) //returns x value and sets taken to true and sets colour and sets id
    {
        int temp = this.x;
        this.taken = true;
        this.colour = d;
        this.id = e;
        return temp;
    }
    
    public int getx(){//used for shadow
        return this.x;
    }

    public int gety(Color d, int e) //returns y value and sets taken to true and sets colour and sets id
    {
        int temp = this.y;
        this.taken = true;
        this.colour = d;
        this.id = e;
        return temp;
    }
    
    public int gety(){//used for shadow
        return this.y;
    }

    public boolean getTaken() {
        boolean temp = this.taken;
        return temp;
    }

    public void setTaken(boolean c) {
        this.taken = c;
    }
}
