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
        // Basics
        AudioFileFactory audioFileFactory = new AudioFileFactory();
        AudioFile chirp = audioFileFactory.loadAudioFile("src/main/resources/sample.wav");
        System.out.println(chirp);
        chirp.play();
        System.out.println(chirp);
        chirp.setSpeed(0.5f);
        chirp.play();
    }

    public static void example2() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        // Effects on Midi file
        AudioFileFactory audioFileFactory = new AudioFileFactory();
        AudioFile chirp = audioFileFactory.loadAudioFile("src/main/resources/MIDI_sample.mid");
        System.out.println(chirp);
        chirp.play(10); // tempo de duracao
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        processorComposer.withTremolo().withReverb(); // dois efeitos ao mesmo tempo
        chirp.setProcessorComposer(processorComposer);
        System.out.println(chirp);
        chirp.play(10);
    }

    public static void example3() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        // Effects on WAV file
        AudioFileFactory audioFileFactory = new AudioFileFactory();
        AudioFile chirp = audioFileFactory.loadAudioFile("src/main/resources/sample.wav");
        AudioFile chirp2 = audioFileFactory.loadAudioFile("src/main/resources/sample.wav");
        System.out.println(chirp);
        chirp.play();
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        AudioProcessorComposer processorComposer2 = new AudioProcessorComposer();
        processorComposer.withReverb();
        chirp.setProcessorComposer(processorComposer);
        chirp.play();
        chirp.save("src/main/resources/output.wav");
        // 2 audio completamente diferente
        processorComposer2.withReverb().withPhaser();
        chirp2.setProcessorComposer(processorComposer2);
        chirp2.play();
    }

}
