package com.github.augusto_sant.chirp.examples;

import com.github.augusto_sant.chirp.AudioFile;
import com.github.augusto_sant.chirp.AudioFileFactory;
import com.github.augusto_sant.chirp.AudioProcessorComposer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Hello world!
 */
public class Example
{
    public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        example1();
//        example2();
//        example3();
    }

    public static void example1() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
        // Basics
        AudioFileFactory audioFileFactory = new AudioFileFactory();
        AudioFile file = audioFileFactory.loadAudioFile("src/main/resources/sample.wav");
        System.out.println(file);
        file.play();
        System.out.println(file);
        file.setSpeed(0.5f);
        file.play();
    }

    public static void example2() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        // Effects on Midi file
        AudioFileFactory audioFileFactory = new AudioFileFactory();
        AudioFile file = audioFileFactory.loadAudioFile("src/main/resources/MIDI_sample.mid");
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        file.setVolume(3f);
        file.setSpeed(0.5f);
        processorComposer.withTremolo().withReverb(); // dois efeitos ao mesmo tempo
        file.setProcessorComposer(processorComposer);
        System.out.println(file);
        file.play(10);
    }

    public static void example3() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        // Effects on WAV file
        AudioFileFactory audioFileFactory = new AudioFileFactory();
        AudioFile file = audioFileFactory.loadAudioFile("src/main/resources/sample.wav");
        AudioFile file1 = audioFileFactory.loadAudioFile("src/main/resources/sample.wav");
        System.out.println(file);
        file.play();
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        AudioProcessorComposer processorComposer2 = new AudioProcessorComposer();
        processorComposer.withReverb();
        file.setProcessorComposer(processorComposer);
        file.play();
        file.save("src/main/resources/output.wav");
        // 2 audio completamente diferente
        processorComposer2.withReverb().withPhaser();
        file1.setProcessorComposer(processorComposer2);
        file1.play();
    }

}
