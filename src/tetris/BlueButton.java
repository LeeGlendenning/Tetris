package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.UIManager;

/*
 * BlueButton class used to create BlueButton object.
 * This is the only button used in the project.
 * 
 * @author Lee Glendenning
 * @version 1.0
 */
public class BlueButton extends JButton{
    private Color foregroundColor;
    private final Color backgroundColor;
    private final Font font;
    private Color border;
    
    /*
     * BlueButton constructor creates the button
     * 
     * @param text      The text to be displayed on the button
     */
    public BlueButton(String text){
        this.border = new Color(0x1b356d);
        this.font = new Font("Verdana", Font.BOLD, 20);
        this.backgroundColor = new Color(0x0085ca);
        this.foregroundColor = new JButton().getForeground();
        
        this.setText(text);
        
        UIManager.put("Button.focus", backgroundColor);
        UIManager.put("Button.select", Color.BLUE);
        
        setBackground(backgroundColor);
        setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, border));
        setFont(font);
        
        addMouseListener(new MouseAdapter() {
            
            /*
             * mouseEntered callback method called when user hovers over the button.
             * Method draws a darker border around the button and darkens the text on the button.
             * 
             * @param me        MouseEvent object to hold coordinates of the mouse. Not currently used
             */
            @Override
            public void mouseEntered(MouseEvent me) {
                setForeground(Color.BLACK);
                setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, border));
            }
            
            /*
             * mouseExited callback method called when user stops hovering over the button.
             * Method draws a lighter border around the button and lightens the text on the button.
             * 
             * @param me        MouseEvent object to hold coordinates of the mouse. Not currently used
             */
            @Override
            public void mouseExited(MouseEvent me) {
                setForeground(foregroundColor);
                setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, border));
            }
        });
    }
        
}
