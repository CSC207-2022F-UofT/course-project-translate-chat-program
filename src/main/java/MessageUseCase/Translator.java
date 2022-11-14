package MessageUseCase;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.Translation;
public class Translator {
    private String keyPath;
    private TranslateClientFactory translateClientFactory;
    private Translate translate;
    private LanguageIdentifier languageIdentifier;

    public Translator(String keyPath){
        this.keyPath = keyPath;
        this.translate = translateClientFactory.createTranslate(keyPath);
    }

    public void setKeyPath(String keyPath){
        this.keyPath = keyPath;
        this.translate = translateClientFactory.createTranslate(keyPath);
    }

    public String Translate(String text, String targetLanguage, String sourceLanguage){
        Translation translation = translate.translate(
                text,
                TranslateOption.sourceLanguage(sourceLanguage),
                TranslateOption.targetLanguage(targetLanguage));

        return translation.getTranslatedText();
    }

    public String Translate(String text, String targetLanguage){
        Translation translation = translate.translate(
                text,
                TranslateOption.targetLanguage(targetLanguage));

        return translation.getTranslatedText();
    }


}
