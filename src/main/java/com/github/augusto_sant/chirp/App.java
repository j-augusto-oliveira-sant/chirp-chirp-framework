package com.github.augusto_sant.chirp;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Hello world!
 */
public class App 
{
    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
//        example1();
        example2();
//        example3();
    }

    public static void example1() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        // Higher speed and volume
        AudioFileFactory audioFileFactory = new AudioFileFactory();
        AudioFile chirp = audioFileFactory.loadAudioFile("src/main/java/com/github/augusto_sant/chirp/sample.wav");
        System.out.println(chirp);
        chirp.play();
        chirp.setSpeed(1.5f);
        chirp.setVolume(6f);
        System.out.println(chirp);
        chirp.play();
    }
    public static void example2() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        // Effects on Midi file
        AudioFileFactory audioFileFactory = new AudioFileFactory();
        AudioFile chirp = audioFileFactory.loadAudioFile("src/main/java/com/github/augusto_sant/chirp/MIDI_sample.mid");
        System.out.println(chirp);
        chirp.play();
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        processorComposer.withTremolo().withReverb();
        chirp.setProcessorComposer(processorComposer);
        System.out.println(chirp);
        chirp.play();
    }
    public static void example3() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        // Effects on WAV file
        AudioFileFactory audioFileFactory = new AudioFileFactory();
        AudioFile chirp = audioFileFactory.loadAudioFile("src/main/java/com/github/augusto_sant/chirp/sample.wav");
        AudioFile chirp2 = audioFileFactory.loadAudioFile("src/main/java/com/github/augusto_sant/chirp/sample.wav");
        System.out.println(chirp);
        chirp.play();
        chirp.setSpeed(1.5f);
        chirp.setVolume(6f);
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        AudioProcessorComposer processorComposer2 = new AudioProcessorComposer();
        processorComposer.withReverb();
        chirp.setProcessorComposer(processorComposer);
        chirp.play();
        // 2
        processorComposer2.withReverb().withPhaser();
        chirp2.setProcessorComposer(processorComposer2);
        chirp2.play();
    }

}
