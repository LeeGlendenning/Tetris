package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.UIManager;

public class BlueButton extends JButton{
    private Color foregroundColor;
    private final Color backgroundColor;
    private final Font font;
    private Color border;
    
    public BlueButton(String text){
        this.border = new Color(0x1b356d);
        this.font = new Font("Verdana", Font.BOLD, 20);
        this.backgroundColor = new Color(0x0085ca);
        this.foregroundColor = new JButton().getForeground();
        
        this.setText(text);
        
        UIManager.put("Button.focus", backgroundColor);
        UIManager.put("Button.select", Color.BLUE);
        
        //setPreferredSize(new Dimension(350, 50));
        setBackground(backgroundColor);
        setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, border));
        setFont(font);
        
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent me) {
                setForeground(Color.BLACK);
                setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, border));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setForeground(foregroundColor);
                setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, border));
            }
        });
    }
        
}
