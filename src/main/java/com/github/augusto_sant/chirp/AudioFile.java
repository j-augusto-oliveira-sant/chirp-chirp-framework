package com.github.augusto_sant.chirp;

import javax.sound.sampled.*;
import java.io.*;

/**
 * Represents an audio file and provides methods for controlling its playback.
 *
 */
public class AudioFile {
    private File file;
    private float volume;
    private float speed;
    private String path;
    private String fileName;
    private String type;
    private AudioProcessorComposer processorComposer;

    public AudioFile(File file,float volume,float speed, String path, String fileName,String type) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.file = file;
        this.volume = volume;
        this.speed = speed;
        this.path = path;
        this.fileName = fileName;
        this.type = type;
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
     * Gets the path of the audio file.
     *
     * @return The path of the audio file.
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the name of the audio file.
     *
     * @return The name of the audio file.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Gets the type of the audio file.
     *
     * @return The type of the audio file. Accepted types are those defined in the javax.sound package.
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/javax/sound/sampled/AudioFileFormat.Type.html">javax.sound.sampled.AudioFileFormat.Type</a>
     */
    public String getType() {
        return type;
    }

    /**
     * Plays the audio file.
     */
    public void play() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playAudioFile(0,false);
    }

    /**
     * Plays audio for the specified duration in seconds.
     *
     * @param durationInSeconds The duration for which the audio should be played, specified in seconds.
     */
    public void play(int durationInSeconds) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        playAudioFile(durationInSeconds,true);
    }
    private void playAudioFile(int durationInSeconds,boolean incomplete) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
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
        long framesToPlay = (long) (format.getSampleRate() * durationInSeconds);
        byte[] buffer;
        try {
            line.open(format);
            line.start();
            // Adjust volume using FloatControl
            FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(this.volume);
            // Read and play the audio
            int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
            buffer = new byte[bufferSize];
            int bytesRead;
            long totalFramesPlayed = 0;
            while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
                // Run processes
                this.applyEffects(buffer, bytesRead, format);
                line.write(buffer, 0, bytesRead);
                totalFramesPlayed += bytesRead / format.getFrameSize();
                if (incomplete && totalFramesPlayed > framesToPlay){
                    break;
                }
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
     * Saves audio for the specified duration to the given output file.
     *
     * @param outputFilePath    The path where the audio should be saved.
     * @param durationInSeconds The duration for which the audio should be saved, specified in seconds.
     */
    public void save(String outputFilePath, int durationInSeconds)
            throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        saveAudioFile(outputFilePath,durationInSeconds, true);
    }

    /**
     * Saves audio to the given output file.
     *
     * @param outputFilePath    The path where the audio should be saved.
     */
    public void save(String outputFilePath)
            throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        saveAudioFile(outputFilePath,0, false);
    }

    /**
     * Resets volume and speed.
     *
     */
    public void reset(){
        this.volume = 0f;
        this.speed = 1f;
    }

    private void saveAudioFile(String outputFilePath, int durationInSeconds, boolean incomplete)throws IOException, LineUnavailableException, UnsupportedAudioFileException{
        System.out.println("Saving audio file: " + outputFilePath);
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
        long framesToPlay = (long) (format.getSampleRate() * durationInSeconds);
        byte[] buffer;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
            buffer = new byte[bufferSize];
            int bytesRead;
            long totalFramesPlayed = 0;
            while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
                // Run processes
                this.applyEffects(buffer, bytesRead, format);
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                totalFramesPlayed += bytesRead / format.getFrameSize();
                if (incomplete && totalFramesPlayed > framesToPlay){
                    break;
                }
            }
        } finally {
            audioInputStream.close();
        }
        File outputFile = new File(outputFilePath);
        byte[] modifiedAudioData = byteArrayOutputStream.toByteArray();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(modifiedAudioData);
        AudioSystem.write(new AudioInputStream(byteArrayInputStream, format, modifiedAudioData.length), AudioFileFormat.Type.WAVE, outputFile);
    }

    /**
     * Sets the volume level of the audio file.
     *
     * @param volume The new volume level, where 6f is the maximum, 0f is the initial volume.
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }

    /**
     * Gets the volume level of the audio file.
     *
     */
    public float getVolume() {
        return this.volume;
    }

    /**
     * Sets the playback speed of the audio.
     *
     * @param speed The speed at which the audio should be played, where 1.0 represents normal speed and only positive values allowed
     */
    public void setSpeed(float speed){
        this.speed = speed;
    }

    /**
     * Gets the playback speed of the audio.
     *
     */
    public float getSpeed(){
        return this.speed;
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

    /**
     * Sets the audio processor composer for customized audio processing.
     *
     * @param processorComposer The AudioProcessorComposer instance responsible for composing audio processing operations.
     */
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


}
