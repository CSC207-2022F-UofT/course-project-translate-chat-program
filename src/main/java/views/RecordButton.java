package views;

import audio_converter_use_case.AudioConvertData;
import controllers.AudioConvertController;
import controllers.AudioRecorderController;
import controllers.MessageTranslateController;
import translate_use_case.MessageTranslateData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static views.RecordButton.MTC;


public class RecordButton extends JPanel implements ActionListener {
    JButton recordButton;
    JTextField textBox;
    AudioRecorderController audioRecorderController;
    AudioConvertController audioConvertController;
    String lang;

    static MessageTranslateController MTC;
    boolean recording;



    ImageIcon micImage;

    public RecordButton(AudioRecorderController ARC, AudioConvertController ACC, MessageTranslateController MTC, String lang, JTextField textBox){

        this.MTC = MTC; //#TODO remove after
        this.lang = lang;
        this.setLayout(new FlowLayout())    ;
        this.recording = false;
        this.audioRecorderController = ARC;
        this.audioConvertController =  ACC;
        this.textBox = textBox;
        this.recordButton = new JButton("Record");

        this.setSize(600, 200);
        this.recordButton.setPreferredSize(new Dimension(50, 50));
        this.recordButton.setLocation(0, 75);

        recordButton.addActionListener(this);

        this.add(recordButton);
    }
public void display() throws IOException {
    String translation = audioConvertController
            .convert(new AudioConvertData("src/main/Others/RecordedAudio.wav", lang))
            //#TODO this data should be gathered from the chat screen, once everything is put together
            .getResult();
    textBox.setText(translation);
}



    @Override
    public void actionPerformed(ActionEvent e) {
        recording = !recording;
        if (recording) {

            System.out.println("hihihihi");
            Thread counter = new Thread(new Runnable() {
                public void run() {
                    try {
                        for (int i = 1; i <= 15-1; i++) {
                            if (!recording) {
                                break;
                            }
                            Thread.sleep(1000);//The thread sleeps for as long as we record, this determines how long our recording is
                        }
                        System.out.println("yyayayay");
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        display();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    recording = false;
                }


            });
            Thread record = new Thread(new Runnable() {
                public void run() {audioRecorderController.record();

                }


            });
            counter.start();

            record.start();
        } else {
            System.out.println("byebyebyebyebe");
            try {

                audioRecorderController.record();
                display();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
class LabelAdapter extends MouseAdapter {
    TranslateScreen translator;

    public LabelAdapter(String lang) {
        this.lang = lang;
    }

    String lang;

    @Override
    public void mouseClicked(MouseEvent e) {
        String text = ((JTextArea) e.getSource()).getText();
        String textToTrasnlate = text.substring(text.indexOf(":") + 3);

        try {
            translator = new TranslateScreen(new MessageTranslateData(textToTrasnlate, this.lang, "en"), MTC);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}