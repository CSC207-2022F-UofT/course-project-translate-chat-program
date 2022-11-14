package MessageUseCase;
import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Translate;


public class LanguageIdentifier {

    public String identifyLanguage(Translate translate, String text){
        Detection detection = translate.detect(text);
        String detectedLanguage = detection.getLanguage();
        return detectedLanguage;
    }

}
