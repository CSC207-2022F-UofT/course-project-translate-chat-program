package views;

import audio_converter_use_case.*;
import audio_recorder_use_case.*;
import gateways.AudioConvertGoogleCloud;
import gateways.AudioRecorder;
import gateways.MessageTranslateGoogleCloud;
import translate_use_case.MessageTranslateInteractor;
import translate_use_case.MessageTranslatePresenter;
import views.controllers.AudioConvertController;
import views.controllers.AudioRecorderController;
import views.controllers.MessageTranslateController;
import views.styles.RecordButton;

import java.io.IOException;

public class AudioMessageAndTranslate {
    public static void main(String[] args) throws IOException {
        AudioConvertPresenter ACP = new AudioConvertPresenter();
        AudioConvertGoogleCloud ACGC = new AudioConvertGoogleCloud("speech-key.json");
        AudioConvertInteractor ACI = new AudioConvertInteractor(ACGC, ACP);

        AudioRecorderPresenter ARP = new AudioRecorderPresenter();
        AudioRecorder AR = new AudioRecorder();
        AudioRecorderInteractor ARI = new AudioRecorderInteractor(AR, ARP);

        MessageTranslateGoogleCloud MTGC = new MessageTranslateGoogleCloud("speech-key.json");
        MessageTranslatePresenter MTP = new MessageTranslatePresenter();
        MessageTranslateInteractor MTI = new MessageTranslateInteractor(MTGC, MTP);
        MessageTranslateController MTC = new MessageTranslateController(MTI);

        AudioConvertController ACC = new AudioConvertController(ACI);
        AudioRecorderController ARC = new AudioRecorderController(ARI);

        RecordButton rc = new RecordButton(ARC, ACC, MTC);

        rc.setVisible(true);
//        JFrame frame = new JFrame();
//        frame.add(rc);
//        frame.setVisible(true);
    }
}
