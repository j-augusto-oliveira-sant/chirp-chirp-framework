package com.github.augusto_sant.chirp;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Represents an audio file and provides methods for controlling its playback.
 * This class follows the Singleton pattern to ensure a single instance is used globally.
 */
public class AudioFile {
    private File file;
    private float volume;
    private float speed;
    private String path;
    private String fileName;
    private AudioProcessorComposer processorComposer;

    public AudioFile(File file,float volume,float speed, String path, String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.file = file;
        this.volume = volume;
        this.speed = speed;
        this.path = path;
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AudioFile{");
        sb.append("file=").append(file);
        sb.append(", volume=").append(volume);
        sb.append(", path='").append(path).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * Plays the audio file.
     */
    public void play(){

    }

    private void playAudioFile() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        System.out.println("Playing audio file: " + file.getName());
        System.out.println(this.getVolumeInfo());
        System.out.println(this.getSpeedInfo());

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);

        AudioFormat fileFormat = audioInputStream.getFormat();
        AudioFormat format = new AudioFormat(
                (float) (fileFormat.getSampleRate() * this.speed),
                fileFormat.getSampleSizeInBits(),
                fileFormat.getChannels(),
                true,
                fileFormat.isBigEndian()
        );

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

        try {
            line.open(format);
            line.start();
            // Adjust volume using FloatControl
            FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(this.volume);
            // Read and play the audio
            int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
            byte[] buffer = new byte[bufferSize];
            int bytesRead;
            while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
                // Run processes
                this.applyEffects(buffer, bytesRead, format);
                line.write(buffer, 0, bytesRead);
            }

            line.drain();
        } finally {
            if (line != null) {
                line.close();
            }
            audioInputStream.close();
        }
    }

    /**
     * Sets the volume level of the audio file.
     *
     * @param volume The new volume level.
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }
    public String getVolumeInfo() {
        float percentage = 100.0f * (float) Math.pow(10.0, volume / 20.0);
        return "Volume: " + percentage + "% | " + this.volume + " db";
    }

    public String getSpeedInfo() {
        return "Speed: " + (this.speed * 100) + "%";
    }

    public AudioProcessorComposer getProcessorComposer() {
        return processorComposer;
    }

    public void setProcessorComposer(AudioProcessorComposer processorComposer) {
        this.processorComposer = processorComposer;
    }

    private void applyEffects(byte[] buffer, int bytesRead, AudioFormat format) {
        if (this.processorComposer != null) {
            for (AudioProcessor processor : this.processorComposer.getProcessors()){
                processor.process(buffer, bytesRead, format);
            }
        }
    }

    public void reset(){
        this.volume = 0f;
        this.speed = 1f;
    }
}
