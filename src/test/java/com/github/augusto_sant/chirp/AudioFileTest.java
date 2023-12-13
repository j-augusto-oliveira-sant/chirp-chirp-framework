package com.github.augusto_sant.chirp;

import junit.framework.TestCase;
import org.junit.After;

import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;


public class AudioFileTest extends TestCase {
    AudioFileFactory audioFileFactory = new AudioFileFactory();

    @After
    void tearDown(){
        File directory = new File("src/test/temp");
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    public void testPlay() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        AudioFile audioFile = audioFileFactory.loadAudioFile("src/test/resources/sample.wav");
        long startTime = System.currentTimeMillis();
        try {
            audioFile.play();
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        long elapsedTimeSeconds = (endTime - startTime) / 1000;
        assertEquals(5, elapsedTimeSeconds, 1); // permite um segundo de diferença
    }

    public void testSave() {
        AudioFile audioFile = null;
        try {
             audioFile = audioFileFactory.loadAudioFile("src/test/resources/sample.wav");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
        try {
            audioFile.save("src/test/temp/output.wav");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
        File outputFile = new File("src/test/temp/output.wav");
        assertTrue("Output file should exist",outputFile.exists());
    }

    public void testSetVolume() {
        AudioFile audioFile = null;
        try {
            audioFile = audioFileFactory.loadAudioFile("src/test/resources/sample.wav");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
        audioFile.setVolume(3f);
        assertEquals(3f,audioFile.getVolume());
    }

    public void testSetSpeed() {
        AudioFile audioFile = null;
        try {
            audioFile = audioFileFactory.loadAudioFile("src/test/resources/sample.wav");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
        audioFile.setSpeed(1.5f);
        assertEquals(1.5f,audioFile.getSpeed());
    }

    public void testSetProcessorComposer() {
        AudioFile audioFile = null;
        try {
            audioFile = audioFileFactory.loadAudioFile("src/test/resources/sample.wav");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        processorComposer.withReverb();
        audioFile.setProcessorComposer(processorComposer);
        assertEquals(audioFile.getProcessorComposer(),processorComposer);
        assertEquals(audioFile.getProcessorComposer().getProcessors(),processorComposer.getProcessors());
    }

    public void testReset() {
        AudioFile audioFile = null;
        try {
            audioFile = audioFileFactory.loadAudioFile("src/test/resources/sample.wav");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
        audioFile.setSpeed(1.5f);
        audioFile.setVolume(3f);
        audioFile.reset();
        assertEquals(0f,audioFile.getVolume());
        assertEquals(1f,audioFile.getSpeed());
    }
}