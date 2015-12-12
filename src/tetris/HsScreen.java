package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

@SuppressWarnings("serial")
public class HsScreen extends JPanel {

    private JButton backButton = new BlueButton("Home");
    public static boolean addName = true;

    public HsScreen() {
        if (addName && Main.firstHs) {
            Main.firstHs = false;
            //JPanel namePanel = new JPanel(new BorderLayout());
            //namePanel.add(new JLabel("Enter name:"), BorderLayout.CENTER);
            //JOptionPane.showInputDialog(null, namePanel, "High Score!", JOptionPane.NO_OPTION);
        }
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                setLayout(new BorderLayout());
                setBackground(Color.BLACK);

                JPanel hsPanel = new JPanel();
                hsPanel.setLayout(new BoxLayout(hsPanel, BoxLayout.Y_AXIS));
                hsPanel.setBackground(Color.DARK_GRAY);
                hsPanel.setPreferredSize(new Dimension(400, 600));
                hsPanel.setMaximumSize(new Dimension(400, 600));
                hsPanel.setBorder(BorderFactory.createMatteBorder(20, 40, 0, 40, Color.BLACK));
                JLabel titleLabel = new JLabel("High Scores");
                titleLabel.setFont(new Font("Helvetica", Font.BOLD, 28));
                titleLabel.setForeground(Color.WHITE);
                titleLabel.setHorizontalAlignment(JLabel.CENTER);
                titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
                add(titleLabel, BorderLayout.NORTH);

                ArrayList<HighScore> h = null;
                h = HighScore.getScores();

                //String hsNames = "";
                //String hsLines = "";
                int i = 0;
                JLabel temp;
                JLabel temp2;
                JPanel tempPanel;

                if (h != null) {
                    for (i = 0; i < 10; i++) {

                        if (i < h.size()) {
                            tempPanel = new JPanel(new BorderLayout());
                            tempPanel.setBackground(Color.DARK_GRAY);
                            tempPanel.setBorder(BorderFactory.createEmptyBorder(0, 95, 0, 100));
                            temp = new JLabel(i + 1 + ". " + h.get(i).name);
                            temp2 = new JLabel(" " + h.get(i).lines);
                            temp.setFont(new Font("Verdana", Font.PLAIN, 22));
                            temp2.setFont(new Font("Verdana", Font.PLAIN, 22));
                            temp.setForeground(Color.WHITE);
                            temp2.setForeground(Color.WHITE);
                            temp.setHorizontalAlignment(JLabel.CENTER);
                            temp2.setHorizontalAlignment(JLabel.CENTER);
                            tempPanel.add(temp, BorderLayout.WEST);
                            tempPanel.add(temp2, BorderLayout.EAST);
                            hsPanel.add(tempPanel);
                        } else {
                            break;
                        }
                    }
                }
                while (i < 10) {
                    tempPanel = new JPanel(new BorderLayout());
                    tempPanel.setBackground(Color.DARK_GRAY);
                    tempPanel.setBorder(BorderFactory.createEmptyBorder(0, 95, 0, 100));
                    temp = new JLabel(i + 1 + ".");
                    temp2 = new JLabel(" " + 0);
                    temp.setFont(new Font("Verdana", Font.PLAIN, 22));
                    temp2.setFont(new Font("Verdana", Font.PLAIN, 22));
                    temp.setForeground(Color.WHITE);
                    temp2.setForeground(Color.WHITE);
                    temp.setHorizontalAlignment(JLabel.CENTER);
                    temp2.setHorizontalAlignment(JLabel.CENTER);
                    tempPanel.add(temp, BorderLayout.WEST);
                    tempPanel.add(temp2, BorderLayout.EAST);
                    hsPanel.add(tempPanel);
                    i++;

                }

                add(hsPanel, BorderLayout.CENTER);

                JPanel backPanel = new JPanel();
                backPanel.setBackground(Color.BLACK);
                backPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                backButton.setPreferredSize(new Dimension(200, 40));
                backPanel.add(backButton);
                add(backPanel, BorderLayout.SOUTH);
                Main.panel.pack();
                Main.panel.setVisible(true);

                registerListeners();
            }
        });
    }

    private void registerListeners() {

        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Main.panel.getContentPane().removeAll();
                Main.panel.getContentPane().revalidate();
                Main.panel.repaint();
                try {
                    Main.panel.add(new Home(), BorderLayout.CENTER);
                } catch (IOException ex) {
                    Logger.getLogger(HsScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
}
