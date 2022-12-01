package controllers;

import audio_recorder_use_case.AudioRecorderInputBoundary;


public class AudioRecorderController {
    final AudioRecorderInputBoundary audioRecorderInputBoundary;

    public AudioRecorderController(AudioRecorderInputBoundary audioRecorderInputBoundary) {
        this.audioRecorderInputBoundary = audioRecorderInputBoundary;
    }

    public void record() {
        this.audioRecorderInputBoundary.record();
    }
}
