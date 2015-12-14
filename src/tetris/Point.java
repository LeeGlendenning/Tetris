package tetris;

import java.awt.Color;

/*
 * Point class to represent a cell in the Tetris gameplay grid. Not the same as the Java.lang Point class
 * 
 * @author Lee Glendenning
 * @version 1.0
 */
public class Point {

    private int x;
    private int y;
    private boolean isLocTaken;
    private Color colour;
    private int id;
    
    /*
     * Point constructor to set instance variables
     * 
     * @param x             int for the x coordinate of the cell in the Tetris gameplay grid
     * @param y             int for the y coordinate of the cell in the Tetris gameplay grid
     * @param isLocTaken    boolean representing whether the cell in the Tetris gameplay grid is occupied
     * @param colour        Color object for the colour of the particular cell
     * @param id            unique id of the cell to be referenced
     */
    public Point(int x, int y, boolean isLocTaken, Color colour, int id) {
        this.x = x;
        this.y = y;
        this.isLocTaken = isLocTaken;
        this.colour = colour;
        this.id = id;
    }
    
    /*
     * setId method used to set the id instance variable
     * 
     * @param id     int unique id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /*
     * getId method returns the id of the cell
     * 
     * @return id       int unique id for the cell
     */
    public int getId() {
        return this.id;
    }
    
    /*
     * setColour method sets the colour instance variable
     * 
     * @param colour    Color object for the colour of the cell
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }
    
    /*
     * getColour method returns the colour of the cell
     * 
     * @return      the colour of the cell
     */
    public Color getColour() {
        return this.colour;
    }
    
    /*
     * setx method sets the x instance variable
     * 
     * @param x     int x
     */
    public void setx(int x) {
        this.x = x;
    }
    
    /*
     * sety method sets the y instance variable
     * 
     * @paramy      int y
     */
    public void sety(int y) {
        this.y = y;
    }
    
    /*
     * getx method returns x value and sets taken to true and sets colour and sets id
     * 
     * @param colour        colour of the cell
     * @param id            int unique id
     * @return x            int x of cell
     */
    public int getx(Color colour, int id){
        this.isLocTaken = true;
        this.colour = colour;
        this.id = id;
        return x;
    }
    
    /*
     * getx method returns the x value of the cell used for shadow
     * 
     * @return x        int x
     */
    public int getx(){
        return x;
    }
    
    /*
     * gety method returns y value and sets taken to true and sets colour and sets id
     * 
     * @param colour        Color object of the cell
     * @param id            int unique id
     * @return y            int y coordinate of cell
     */
    public int gety(Color colour, int id){
        isLocTaken = true;
        this.colour = colour;
        this.id = id;
        return y;
    }
    
    /*
     * gety method do get y coordinate of cell. used for shadow
     * 
     * @return y        int y coordinate of cell
     */
    public int gety(){
        return y;
    }
    
    /*
     * getTaken method returns whether cell is occupied
     * 
     * @return isLocTaken       boolean representing whether cell is occupied
     */
    public boolean getTaken() {
        return isLocTaken;
    }
    
    /*
     * setTaken method sets the isLocTaken variable
     * 
     * @param isLocTaken        whether the cell is taken
     */
    public void setTaken(boolean isLocTaken) {
        this.isLocTaken = isLocTaken;
    }
}
