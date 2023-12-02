package com.github.augusto_sant.chirp;

import junit.framework.TestCase;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class AudioFileFactoryTest extends TestCase {
    AudioFileFactory audioFileFactory = new AudioFileFactory();

    public void testLoadAudioFileSpeed() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        AudioFile audioFile = audioFileFactory.loadAudioFile("sample.wav");
        assertEquals(1f, audioFile.getSpeed());
    }

    public void testLoadAudioFileVolume() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        AudioFile audioFile = audioFileFactory.loadAudioFile("sample.wav");
        assertEquals(0f, audioFile.getVolume());
    }

    public void testLoadAudioFileFileName() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        AudioFile audioFile = audioFileFactory.loadAudioFile("sample.wav");
        assertEquals("sample", audioFile.getFileName());
    }

    public void testLoadAudioFileFileName2() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        AudioFile audioFile = audioFileFactory.loadAudioFile("MIDI_sample.mid");
        assertEquals("MIDI_sample", audioFile.getFileName());
    }

    public void testLoadAudioFileType() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        AudioFile audioFile = audioFileFactory.loadAudioFile("sample.wav");
        assertEquals("wav", audioFile.getType());
    }

    public void testLoadAudioFileType2() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        AudioFile audioFile = audioFileFactory.loadAudioFile("MIDI_sample.mid");
        assertEquals("mid", audioFile.getType());
    }
}