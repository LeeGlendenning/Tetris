package tetris;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.*;

public class JTextFieldLimit extends JPanel {
    private JTextField textField = new JTextField(10);
    
    public JTextFieldLimit(int limit) {
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new TextLengthDocFilter(limit));
        setBackground(Color.BLACK);
        setLayout(new GridLayout(1,1));
        textField.setBorder(new JButton().getBorder());
        //textField.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        add(textField);


    }
    
    public String getText(){
        return textField.getText();
    }

    private class TextLengthDocFilter extends DocumentFilter {

        private int maxTextLength;

        public TextLengthDocFilter(int maxTextLength) {
            this.maxTextLength = maxTextLength;
        }

        private boolean verifyText(String text) {
            return text.length() <= maxTextLength;
        }

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string,
                AttributeSet attr) throws BadLocationException {

            Document doc = fb.getDocument();
            String oldText = doc.getText(0, doc.getLength());
            StringBuilder sb = new StringBuilder(oldText);
            sb.insert(offset, string);

            if (verifyText(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            }

        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            Document doc = fb.getDocument();
            String oldText = doc.getText(0, doc.getLength());
            StringBuilder sb = new StringBuilder(oldText);

            sb.replace(offset, offset + length, text);
            if (verifyText(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
            Document doc = fb.getDocument();
            String oldText = doc.getText(0, doc.getLength());
            StringBuilder sb = new StringBuilder(oldText);

            sb.replace(offset, offset + length, "");

            if (verifyText(sb.toString())) {
                super.remove(fb, offset, length);
            }
        }
    }
}