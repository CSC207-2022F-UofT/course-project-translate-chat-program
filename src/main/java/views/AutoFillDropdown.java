package views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * An editable dropdown menu of Strings, with the added functionality that
 * typing and pressing enter will select the closest choice in the dropdown list.
 *
 * For example, if the choices are "apple", "orange", "banana" and the user types
 * "appjfe" and presses enter, "apple" will automatically be selected. If two choices are
 * equally similar to the entered text, it selects the last one in the list.
 */
public class AutoFillDropdown extends JComboBox<String> implements ActionListener {
    /**
     * List of choices in the dropdown menu.
     */
    private String[] choices;

    public AutoFillDropdown(String[] choices) {
        super(choices);
        this.choices = choices;
        super.setEditable(true);
        super.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String item = (String) super.getSelectedItem();
        // If the item is not one of the given choices
        if (!Arrays.asList(choices).contains(item)) {
            String closest = choices[0];
            int maxSame = 0;

            for (String choice : choices) {
                int smaller = Math.min(item.length(), choice.length());
                int numSame = 0;
                // Calculate the number of positions that are the same
                for (int i = 0; i < smaller; i++) {
                    if (item.charAt(i) == choice.charAt(i)) {
                        numSame++;
                    }
                }
                if (numSame >= maxSame) {
                    closest = choice;
                    maxSame = numSame;
                }
            }
            super.setSelectedItem(closest);
        }

    }
}
