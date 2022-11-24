package views.controllers;

import audio_recorder_use_case.AudioRecorderInputBoundary;
import audio_recorder_use_case.AudioRecorderResponse;


public class AudioRecorderController {
    final AudioRecorderInputBoundary audioRecorderInputBoundary;

    public AudioRecorderController(AudioRecorderInputBoundary audioRecorderInputBoundary) {
        this.audioRecorderInputBoundary = audioRecorderInputBoundary;
    }

    public AudioRecorderResponse record() {
        return this.audioRecorderInputBoundary.record();
    }
}
