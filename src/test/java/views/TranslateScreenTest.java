package views;

import audio_converter_use_case.*;
import audio_recorder_use_case.*;
import controllers.AudioRecorderController;
import controllers.MessageTranslateController;
import gateways.AudioConvertGoogleCloud;
import gateways.AudioRecorder;
import gateways.MessageTranslateGoogleCloud;
import translate_use_case.MessageTranslateInteractor;
import translate_use_case.MessageTranslatePresenter;
import controllers.AudioConvertController;

import javax.swing.*;
import java.io.IOException;

public class TranslateScreenTest {
    public static void main(String[] args) throws IOException {
        AudioConvertPresenter ACP = new AudioConvertPresenter();
        AudioConvertGoogleCloud ACGC = new AudioConvertGoogleCloud("speech-key.json");
        AudioConvertInteractor ACI = new AudioConvertInteractor(ACGC, ACP);


        AudioRecorderPresenter ARP = new AudioRecorderPresenter();
        AudioRecorder AR = new AudioRecorder();
        AudioRecorderInteractor ARI = new AudioRecorderInteractor(AR, ARP);

        AudioConvertController ACC = new AudioConvertController(ACI);
        AudioRecorderController ARC = new AudioRecorderController(ARI);

        MessageTranslateGoogleCloud MTGC = new MessageTranslateGoogleCloud("speech-key.json");
        MessageTranslatePresenter MTP = new MessageTranslatePresenter();
        MessageTranslateInteractor MTI = new MessageTranslateInteractor(MTGC, MTP);
        MessageTranslateController MTC = new MessageTranslateController(MTI);


        RecordButton rc = new RecordButton(ARC, ACC, MTC,"fr", new JTextField());


    }
}
