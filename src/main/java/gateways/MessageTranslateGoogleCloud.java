package gateways;

import atranslate_use_case.MessageTranslateData;
import atranslate_use_case.MessageTranslateGateway;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MessageTranslateGoogleCloud implements MessageTranslateGateway {
    String keyPath;
    private Translate translate;

    public MessageTranslateGoogleCloud(String keyPath) throws IOException {
        this.keyPath = keyPath;
        this.translate = createTranslate(keyPath);
    }
    @Override
    public String translate(MessageTranslateData data){
        String original = data.getOriginal();
        String targetLanguage = data.getTargetLanguage();
        String sourceLanguage = data.getSourceLanguage();

        Translation translation = translate.translate(
                original,
                TranslateOption.sourceLanguage(sourceLanguage),
                TranslateOption.targetLanguage(targetLanguage));

        return translation.getTranslatedText();
    }
    public Translate createTranslate(String keyPath) throws IOException {
        Translate translate = TranslateOptions.newBuilder().setCredentials(ServiceAccountCredentials
                .fromStream(new FileInputStream(keyPath))).build().getService();

        return translate;
    }
}