package AudioUseCase;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechSettings;

import java.io.FileInputStream;
import java.io.IOException;

public class SpeechClientFactory {

    public SpeechClient createSpeechClient(String keyPath) throws IOException {
        SpeechSettings settings =
                SpeechSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider
                                .create(ServiceAccountCredentials
                                        .fromStream(new FileInputStream(keyPath))))
                        .build();
        SpeechClient speech = SpeechClient.create(settings);
        return speech;
    }

}
