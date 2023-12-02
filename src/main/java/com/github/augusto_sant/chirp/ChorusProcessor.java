package com.github.augusto_sant.chirp;
import javax.sound.sampled.AudioFormat;
import java.util.Arrays;

/**
 * A Java class implementing the AudioProcessor interface for chorus audio processing.
 */
public class ChorusProcessor implements AudioProcessor {
    /** The delay time for the chorus effect. */
    private float delay;

    /**
     * Constructs a ChorusProcessor with the specified delay time.
     *
     * @param delay The delay time for the chorus effect.
     */
    public ChorusProcessor(float delay){
        this.delay = delay;
    }

    @Override
    public void process(byte[] buffer, int bytesRead, AudioFormat format) {
        // Reverb parameters
        int delayInSamples = (int) (format.getSampleRate() * this.delay);  // 50ms delay
        float feedback = 0.5f;

        // Create delay buffer
        float[] delayBuffer = new float[delayInSamples];
        Arrays.fill(delayBuffer, 0.0f);

        // Convert bytes to float samples
        float[] samples = new float[bytesRead / 2];
        for (int i = 0, j = 0; i < bytesRead; i += 2, j++) {
            samples[j] = (float) ((buffer[i + 1] << 8) | (buffer[i] & 0xFF)) / 32768.0f;
        }

        // Apply reverb using a basic feedback delay network (FDN)
        for (int i = 0; i < samples.length; i++) {
            float delayedSample = delayBuffer[i % delayInSamples];
            samples[i] += feedback * delayedSample;
            delayBuffer[i % delayInSamples] = samples[i];
        }

        // Convert back to bytes and write to the SourceDataLine
        for (int i = 0, j = 0; i < bytesRead; i += 2, j++) {
            short sample = (short) (samples[j] * 32768.0f);
            buffer[i] = (byte) sample;
            buffer[i + 1] = (byte) (sample >> 8);

        }
    }
}
