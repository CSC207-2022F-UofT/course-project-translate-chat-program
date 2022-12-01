package controllers;

import audio_converter_use_case.AudioConvertData;
import audio_converter_use_case.AudioConvertInputBoundary;
import audio_converter_use_case.AudioConvertResponse;


import java.io.IOException;

public class AudioConvertController {
    final AudioConvertInputBoundary audioConvertInputBoundary;

    public AudioConvertController(AudioConvertInputBoundary audioConvertInputBoundary) {
        this.audioConvertInputBoundary = audioConvertInputBoundary;
    }

    public AudioConvertResponse convert(AudioConvertData data) throws IOException {
        return this.audioConvertInputBoundary.convert(data);
    }
}
