package AudioUseCase;

import com.google.protobuf.ByteString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AudioBytesFactory {

    //takes an file and converts it to ByteString
    public ByteString createAudioBytes(String File) throws IOException {
        String fileName = File;
        Path path = Paths.get(fileName);
        byte[] data = Files.readAllBytes(path);
        ByteString audioBytes = ByteString.copyFrom(data);

        return audioBytes;
    }

}
