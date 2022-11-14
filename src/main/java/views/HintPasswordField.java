package views;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * A password field that displays a given hint text.
 */
class HintPasswordField extends JPasswordField implements FocusListener {

    private final String hint;
    private boolean showingHint;

    public HintPasswordField(final String hint) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.setEchoChar((char) 0);
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        // Set the character to '*' so password is not visible
        super.setEchoChar('*');
        // If password is empty, stop showing the hint text so that
        // the user can type in their password
        if(this.getPassword().length == 0) {
            super.setText("");
            showingHint = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        // If password is empty, show hint text
        if(this.getPassword().length == 0) {
            super.setText(hint);
            // Make the hint text visible
            super.setEchoChar((char) 0);
            showingHint = true;
        }
    }

    @Override
    public char[] getPassword() {
        char[] empty = new char[0];
        return showingHint ? empty : super.getPassword();
    }
}

