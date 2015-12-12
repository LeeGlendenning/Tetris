package tetris;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Painter extends JComponent implements Runnable, ActionListener {

    public void run() {
        System.out.println("enter");
        Graphics g;
        g = getGraphics();
        paint(g);
        System.out.println("repainted");
    }

    public void actionPerformed(ActionEvent e) {
    }
    /*
     * public void paintComponent(Graphics g) { super.paintComponent(g);
     * System.out.println("enter"); //draw tetris logo----------------------
     * BufferedImage logo = null; try { logo = ImageIO.read(new
     * File("logo.png")); } catch (IOException e) {} g.drawImage(logo, 130, 15,
     * this); //--------------------------------------- g.setColor(new
     * Color(51,153,255)); g.drawRect(104,120,262,520); //draw border for play
     * area
     *
     * if (!dropping){ //piece has just been placed isline1 = true; isline2 =
     * true; isline3 = true; isline4 = true;
     *
     * //check whether line has been made at each y coordinate of piece (there
     * could be 4 different y coords if vertical 1x4 block) for (int l1 = 0; l1
     * < 10; l1 ++){ //check line at yc1 if (coords[l1][yc1].getTaken() !=
     * true){ isline1 = false; } } for (int l2 = 0; l2 < 10; l2 ++){ //check
     * line at yc2 if (coords[l2][yc2].getTaken() != true){ isline2 = false; } }
     * for (int l3 = 0; l3 < 10; l3 ++){ //check line at yc3 if
     * (coords[l3][yc3].getTaken() != true){ isline3 = false; } } for (int l4 =
     * 0; l4 < 10; l4 ++){ //check line at yc4 if (coords[l4][yc4].getTaken() !=
     * true){ isline4 = false; } }
     *
     * //take away line(s) if (isline1){ //is unique line at yc1
     * Stats.incLines(); //increment line count for (int m1 = 0; m1 < 10; m1
     * ++){ coords[m1][yc1].setTaken(false); //delete line } } if (isline2){ if
     * (yc2 != yc1){ //is unique line at yc2 Stats.incLines(); //increment line
     * count for (int m2 = 0; m2 < 10; m2 ++){ coords[m2][yc2].setTaken(false);
     * //delete line } }else{ isline2 = false; } } if (isline3){ if (yc3 != yc1
     * && yc3 != yc2){ //is unique line at yc3 Stats.incLines(); //increment
     * line count for (int m3 = 0; m3 < 10; m3 ++){
     * coords[m3][yc3].setTaken(false); //delete line } }else{ isline3 = false;
     * } } if (isline4){ if (yc4 != yc1 && yc4 != yc2 && yc4 != yc3){ //is
     * unique line at yc4 Stats.incLines(); //increment line count for (int m4 =
     * 0; m4 < 10; m4 ++){ coords[m4][yc4].setTaken(false); //delete line }
     * }else{ isline4 = false; } }
     *
     * //drop pieces above deleted line 1 y coordinate if (isline1){ for (int n1
     * = yc1 - 1; n1 >= 0; n1 --){ for (int o1 = 0; o1 < 10; o1 ++){ if
     * (coords[o1][n1].getTaken() == true){ coords[o1][n1].setTaken(false);
     * coords[o1][n1 + 1].setTaken(true); } } } if (yc2 < yc1) yc2 ++; if (yc3 <
     * yc1) yc3 ++; if (yc4 < yc1) yc4 ++;
     *
     * }
     * if (isline2){ for (int n2 = yc2 - 1; n2 >= 0; n2 --){ for (int o2 = 0; o2
     * < 10; o2 ++){ if (coords[o2][n2].getTaken() == true){
     * coords[o2][n2].setTaken(false); coords[o2][n2 + 1].setTaken(true); } } }
     * if (yc3 < yc1) yc3 ++; if (yc4 < yc1) yc4 ++; } if (isline3){ for (int n3
     * = yc3 - 1; n3 >= 0; n3 --){ for (int o3 = 0; o3 < 10; o3 ++){ if
     * (coords[o3][n3].getTaken() == true){ coords[o3][n3].setTaken(false);
     * coords[o3][n3 + 1].setTaken(true); } } } if (yc4 < yc1) yc4 ++; } if
     * (isline4){ for (int n4 = yc4 - 1; n4 >= 0; n4 --){ for (int o4 = 0; o4 <
     * 10; o4 ++){ if (coords[o4][n4].getTaken() == true){
     * coords[o4][n4].setTaken(false); coords[o4][n4 + 1].setTaken(true); } } }
     * } } //loop to paint pieces already existing in game play area for (int i
     * = 0; i < 10; i ++) //x coords { for (int j = 0; j < 20; j ++) //y coords
     * { if (coords[i][j].getTaken() == true){ Color colour2 =
     * coords[i][j].getColour(); int id2 = coords[i][j].getId();
     * g.setColor(colour2); g.fillRoundRect(coords[i][j].getx(colour2, id2) + 1,
     * coords[i][j].gety(colour2, id2) + 1, width - 2, width - 2, 5, 5); } } }
     *
     *
     * KeyListen k = new Gameplay().new KeyListen(); add(k); k.requestFocus();
     *
     * //generate random number between 1,7 to represent each shape if
     * (!dropping){ id ++; k.requestFocus(); first = true; dropping = true;
     * shape = 1 + (int)(Math.random()*7); }
     *
     * //create new shape if (first){
     *
     * first = false; switch (shape){ case (1): //1x4 block xc1 = 3; xc2	= 4;
     * xc3 = 5; xc4 = 6; yc1 = yc2 = yc3 = yc4 = 0; colour = Color.blue; break;
     * case (2): //2x2 block xc1 = 4; xc2	= 5; xc3 = 4; xc4 = 5; yc1 = 0; yc2	=
     * 0; yc3 = 1; yc4 = 1; colour = Color.yellow; break; case (3): //s block
     * xc1 = 5; xc2	= 6; xc3 = 4; xc4 = 5; yc1 = 0; yc2	= 0; yc3 = 1; yc4 = 1;
     * colour = Color.green; break; case (4): //z block xc1 = 4; xc2	= 5; xc3 =
     * 5; xc4 = 6; yc1 = 0; yc2	= 0; yc3 = 1; yc4 = 1; colour = Color.red;
     * break; case (5): //L block xc1 = 3; xc2	= 4; xc3 = 5; xc4 = 3; yc1 = 0;
     * yc2	= 0; yc3 = 0; yc4 = 1; colour = new Color(255,102,0); //orange break;
     * case (6): //backwards L block xc1 = 3; xc2	= 4; xc3 = 5; xc4 = 5; yc1 =
     * 0; yc2	= 0; yc3 = 0; yc4 = 1; colour = Color.pink; break; case (7): //top
     * hat block xc1 = 3; xc2	= 4; xc3 = 5; xc4 = 4; yc1 = 0; yc2	= 0; yc3 = 0;
     * yc4 = 1; colour = new Color(102,0,205); //purple break; } if
     * (coords[xc1][yc1].getTaken() || coords[xc2][yc2].getTaken()
     * ||coords[xc3][yc3].getTaken() ||coords[xc4][yc4].getTaken()){ gameOver =
     * true; fillCoords(); //reset board //draw game over picture BufferedImage
     * over = null; try { over = ImageIO.read(new File("GameOver.png")); } catch
     * (IOException e) {} g.drawImage(over, -75, 15, this);
     *
     * //somehow delay for a few seconds to see game over picture... add(new
     * JTextField()); //
     *
     * //EditableListPanel elp = new EditableListPanel();
     * //JOptionPane.showMessageDialog(null, elp);
     *
     * Main.panel.getContentPane().removeAll();
     * Main.panel.getContentPane().revalidate(); Main.panel.repaint();
     * Main.panel.add(new HsScreen(), BorderLayout.CENTER); }
     *
     * }
     * x1 = coords[xc1][yc1].getx(colour, id); x2 =
     * coords[xc2][yc2].getx(colour, id); x3 = coords[xc3][yc3].getx(colour,
     * id); x4 = coords[xc4][yc4].getx(colour, id); y1 =
     * coords[xc1][yc1].gety(colour, id); y2 = coords[xc2][yc2].gety(colour,
     * id); y3 = coords[xc3][yc3].gety(colour, id); y4 =
     * coords[xc4][yc4].gety(colour, id);
     *
     *
     * g.setColor(colour); //initialize piece g.fillRoundRect(x1 + 1, y1 + 1,
     * width - 2, width - 2, 5, 5); g.fillRoundRect(x2 + 1, y2 + 1, width - 2,
     * width - 2, 5, 5); g.fillRoundRect(x3 + 1, y3 + 1, width - 2, width - 2,
     * 5, 5); g.fillRoundRect(x4 + 1, y4 + 1, width - 2, width - 2, 5, 5);
     *
     * if (!drop_fast){ if (Integer.parseInt(Stats.lab5.getText())*35 < 450){
     * delay_time = (450 - Integer.parseInt(Stats.lab5.getText())*35)/7; }else{
     * delay_time = 0; } }else{ delay_time = 0; }
     *
     * timer = new Timer(delay_time, this); timer.setCoalesce(true);
     * timer.start();
     *
     * //gravity if (timer_count == 7){ timer_count = 0;
     *
     * int tempid1 = coords[xc1][yc1].getId(); int tempid2 =
     * coords[xc2][yc2].getId(); int tempid3 = coords[xc3][yc3].getId(); int
     * tempid4 = coords[xc4][yc4].getId(); int tempyc1 = yc1; //used for
     * checking whether it is possible to move the piece down any more or if
     * there is another piece in the way int tempyc2 = yc2; int tempyc3 = yc3;
     * int tempyc4 = yc4;
     *
     * while (tempyc1 + 1 < 19 && coords[xc1][tempyc1 + 1].getId() == tempid1){
     * //while loops to check below every square of the block tempyc1 ++; }
     * while (tempyc2 + 1 < 19 && coords[xc2][tempyc2 + 1].getId() == tempid2){
     * tempyc2 ++; } while (tempyc3 + 1 < 19 && coords[xc3][tempyc3 + 1].getId()
     * == tempid3){ tempyc3 ++; } while (tempyc4 + 1 < 19 && coords[xc4][tempyc4
     * + 1].getId() == tempid4){ tempyc4 ++; } if (yc1 < 19 && yc2 < 19 && yc3 <
     * 19 && yc4 < 19 && coords[xc1][tempyc1 + 1].getTaken() == false &&
     * coords[xc2][tempyc2 + 1].getTaken() == false && coords[xc3][tempyc3 +
     * 1].getTaken() == false && coords[xc4][tempyc4 + 1].getTaken() == false){
     * //update piece location if (!delay){ delay = true;
     * g.setColor(Color.black); //remove blocks at previous location
     * g.fillRect(x1 + 1, y1 + 1, width - 2, width - 2); g.fillRect(x2 + 1, y2 +
     * 1, width - 2, width - 2); g.fillRect(x3 + 1, y3 + 1, width - 2, width -
     * 2); g.fillRect(x4 + 1, y4 + 1, width - 2, width - 2); //set the space to
     * empty since the piece is not there anymore
     * coords[xc1][yc1].setTaken(false); coords[xc2][yc2].setTaken(false);
     * coords[xc3][yc3].setTaken(false); coords[xc4][yc4].setTaken(false); yc1
     * ++; yc2 ++; yc3 ++; yc4 ++; y1 = coords[xc1][yc1].gety(colour, id); y2 =
     * coords[xc2][yc2].gety(colour, id); y3 = coords[xc3][yc3].gety(colour,
     * id); y4 = coords[xc4][yc4].gety(colour, id); g.setColor(colour);
     * g.fillRoundRect(x1 + 1, y1 + 1, width - 2, width - 2, 5, 5);
     * g.fillRoundRect(x2 + 1, y2 + 1, width - 2, width - 2, 5, 5);
     * g.fillRoundRect(x3 + 1, y3 + 1, width - 2, width - 2, 5, 5);
     * g.fillRoundRect(x4 + 1, y4 + 1, width - 2, width - 2, 5, 5); } }else{
     * dropping = false; } }
	}
     */
}
