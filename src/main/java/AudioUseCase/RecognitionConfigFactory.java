package AudioUseCase;

import com.google.cloud.speech.v1.RecognitionConfig;

public class RecognitionConfigFactory {

    public RecognitionConfig createRecognitionConfig(int sampleRateHertz, int audioChannelCount, String LanguageCode){
        RecognitionConfig config =
                RecognitionConfig.newBuilder()
                            .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                        .setSampleRateHertz(sampleRateHertz)
                        .setAudioChannelCount(audioChannelCount)
                        .setLanguageCode(LanguageCode)
                        .build();
        return config;
    }

}
