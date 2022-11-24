package views.styles;

import audio_converter_use_case.AudioConvertData;
import translate_use_case.MessageTranslateData;
import views.screens.TranslateScreen;
import views.controllers.AudioConvertController;
import views.controllers.AudioRecorderController;
import views.controllers.MessageTranslateController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import static views.styles.RecordButton.MTC;


public class RecordButton extends JFrame implements ActionListener {
    JButton recordButton;
    JPanel panel;
    JTextField textBox;
    JLabel timeLabel;
    AudioRecorderController audioRecorderController;
    AudioConvertController audioConvertController;

    static MessageTranslateController MTC;
    boolean recording;



    ImageIcon micImage;

    public RecordButton(AudioRecorderController ARC, AudioConvertController ACC, MessageTranslateController MTC){

        this.MTC = MTC; //#TODO remove after

        this.setLayout(new FlowLayout())    ;
        this.recording = false;
        this.audioRecorderController = ARC;
        this.audioConvertController =  ACC;
        this.panel = new JPanel();
        this.textBox = new JTextField();
        this.recordButton = new JButton();
        this.timeLabel = new JLabel("test");

        this.setSize(600, 200);
        this.textBox.setPreferredSize(new Dimension(500, 50));
        this.textBox.addMouseListener(new LabelAdapter());
        this.recordButton.setPreferredSize(new Dimension(50, 50));
        this.recordButton.setLocation(0, 75);

        this.timeLabel.setPreferredSize(new Dimension(100, 50));
        this.timeLabel.setLocation(100, 50);

        Border Black = BorderFactory.createLineBorder(Color.BLACK);
        this.timeLabel.setBorder(Black);
        recordButton.addActionListener(this);

        this.add(textBox);
        this.add(recordButton);
        this.add(timeLabel);



        this.setDefaultCloseOperation(3);
        this.setVisible(true);


    }
public void display() throws IOException {
    String translation = audioConvertController
            .convert(new AudioConvertData("src/main/Others/RecordedAudio.wav", "en"))
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
                            timeLabel.setText(""+i);
                            Thread.sleep(1000);//The thread sleeps for as long as we record, this determines how long our recording is
                        }
                        System.out.println("yyayayay");
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    timeLabel.setText("");
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
    @Override
    public void mouseClicked(MouseEvent e) {
        String text = ((JTextField) e.getSource()).getText();

        try {
            translator = new TranslateScreen(new MessageTranslateData(text, "fr", "en"), MTC);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        translator.setDefaultCloseOperation(3);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(translator != null){translator.dispose();}

    }
}