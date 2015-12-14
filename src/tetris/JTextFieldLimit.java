package tetris;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.*;

/*
 * JTextFieldLimit class creates a JTextField and enforces a limit of 10 characters inputted to it
 * 
 * @author I do not know who wrote this class. I found this code online.
 * @version 1.0
 */
public class JTextFieldLimit extends JPanel {
    private JTextField textField = new JTextField(10);
    
    public JTextFieldLimit(int limit) {
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new TextLengthDocFilter(limit));
        setBackground(Color.BLACK);
        setLayout(new GridLayout(1,1));
        textField.setBorder(new JButton().getBorder());
        add(textField);
    }
    
    /*
     * getText method returns text in the JTextField as a String
     * 
     * @return text in the JTextField as a String
     */
    public String getText(){
        return textField.getText();
    }
    
    /*
     * TextLengthDocFilter class extends DocumentFilter to enforce a limit of 10 characters on a document
     * 
     * @author Lee Glendenning
     * @version 1.0
     */
    private class TextLengthDocFilter extends DocumentFilter {

        private int maxTextLength;
        
        /*
         * TextLengthDocFilter constructor sets maxTextLength instance variable
         * 
         * @param maxTextLength     integer for the max length of text enforced in document
         */
        public TextLengthDocFilter(int maxTextLength) {
            this.maxTextLength = maxTextLength;
        }
        
        /*
         * verifyText method checks that the text is within the enforced maxTextLimit in length
         * 
         * @param text      String of text that is in the document
         * @returns         boolean representing whether the document text is within the enforced maxTextLimit in length
         */
        private boolean verifyText(String text) {
            return text.length() <= maxTextLength;
        }
        
        /*
         * insertString method inserts the given string into the document
         * 
         * @param fb        FilterBypass object used to get document from
         * @param offset    int offset to insert string at
         * @param string    String object to be inserted into document
         * @param attr      AttributeSet object for the document
         * @throws          BadLocationException thrown if the range in doc.getText is not a valid location in the document
         *                  or if super.insertString attempts to insert string in an invalid location of document
         */
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
        
        /*
         * replace method replaces the string at the given location of document with the given string
         * 
         * @param fb        FilterBypass object used to get document from
         * @param offset    int offset to replace string at
         * @param length    length of string used as upper bound index for the replace
         * @param string    String object to be replaced into document
         * @param attrs     AttributeSet object for the document
         * @throws          BadLocationException thrown if the range in doc.getText is not a valid location in the document
         *                  or if super.replace attempts to replace string in an invalid location of document
         */
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
        
        /*
         * remove method removes the string in the document at the given location
         * 
         * @param fb        FilterBypass object used to get document from
         * @param offset    int offset to remove string at
         * @param length    length of string used as upper bound index for the remove
         * @throws          BadLocationException thrown if the range in doc.getText is not a valid location in the document
         *                  or if super.remove attempts to remove string in an invalid location of document
         */
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