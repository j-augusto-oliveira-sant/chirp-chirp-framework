package com.github.augusto_sant.chirp;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Teste {

    public static void main(String[] args) {
        // Replace with the path to your input audio file
        File audioFile = new File("src/main/java/com/github/augusto_sant/chirp/sample.wav");

        // Play the audio with adjustable speed
        playAdjustableSpeedAudio(audioFile, 0.5); // Adjust the speed factor as needed
    }

    // Function to play audio with adjustable speed
    private static void playAdjustableSpeedAudio(File audioFile, double speedFactor) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);

            // Adjust the playback speed by changing the sample rate
            AudioFormat format = audioInputStream.getFormat();
            AudioFormat newFormat;

            newFormat = new AudioFormat((float) ((float) format.getSampleRate() * speedFactor),
                    format.getSampleSizeInBits(), format.getChannels(), true, format.isBigEndian());

            // Open a source data line for playback
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, newFormat);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(newFormat);
            line.start();

            // Read and write the audio data
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            // Block until all data has been played
            line.drain();
            line.close();
            audioInputStream.close();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
