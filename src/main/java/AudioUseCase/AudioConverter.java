package AudioUseCase;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.speech.v1.*;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.protobuf.ByteString;
import org.apache.commons.codec.language.bm.Lang;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


public class AudioConverter {
    private String keyPath; //Path containing the json file with the API key
    private SpeechClient speech;
    private AudioBytesFactory audioBytesFactory;
    private SpeechClientFactory speechClientFactory;
    private RecognitionConfigFactory recognitionConfigFactory;

    //Constructor that creates a speechClient as well
    public AudioConverter(String keyPath) throws IOException {
        this.keyPath  = keyPath;
        this.speech = speechClientFactory.createSpeechClient(keyPath);
    }

    //setKey, which also sets the speechClient according to the key
    public void setKey(String keyPath) throws IOException {
        this.keyPath  = keyPath;
        this.speech = speechClientFactory.createSpeechClient(keyPath);
    }


    public String convertToText(String FilePath, String LanguageCode) throws IOException {

        StringBuilder translatedText = new StringBuilder("");
        ByteString audioBytes = audioBytesFactory.createAudioBytes(FilePath);

        //builds request

        RecognitionConfig config = recognitionConfigFactory
                .createRecognitionConfig(16000, 2, LanguageCode);
        RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();

        //speech recognition
        RecognizeResponse response = speech.recognize(config, audio);
        List<SpeechRecognitionResult> results = response.getResultsList();

        for (SpeechRecognitionResult result : results) {
            List<SpeechRecognitionAlternative> alternatives = result.getAlternativesList();
            for (SpeechRecognitionAlternative alternative : alternatives) {
                translatedText.append(alternative.getTranscript());
            }
        }
//        speech.close(); I am not 100% if you need this or not
        return translatedText.toString();
    }



}
