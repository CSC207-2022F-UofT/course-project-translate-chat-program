package views.screens;

import translate_use_case.MessageTranslateData;
import views.controllers.MessageTranslateController;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TranslateScreen extends JFrame {

    JLabel translatedText;
    MessageTranslateController MTC;

    public TranslateScreen(MessageTranslateData data, MessageTranslateController MTC) throws IOException {
        this.MTC = MTC;
        this.setSize(600,200);
        this.setLocation(
                (int)MouseInfo.getPointerInfo().getLocation().getX() + 10,
                (int)MouseInfo.getPointerInfo().getLocation().getY());
        translatedText = new JLabel(MTC.translate(data).getResult());
        this.add(translatedText);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


    }


}
