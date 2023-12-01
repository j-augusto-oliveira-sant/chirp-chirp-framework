package com.github.augusto_sant.chirp;
import javax.sound.sampled.AudioFormat;

public class TremoloProcessor implements AudioProcessor {
    public TremoloProcessor() {
    }

    @Override
    public void process(byte[] buffer, int bytesRead, AudioFormat format) {
        // Adjust the amplitude of the buffer to create a tremolo effect
        for (int i = 0; i < bytesRead; i += 2) {
            // Assuming 16-bit audio (2 bytes per sample)
            short sample = (short) ((buffer[i + 1] << 8) | (buffer[i] & 0xFF));

            // Apply tremolo modulation (adjust the multiplier as needed)
            double tremoloFactor = 0.5 + 0.5 * Math.sin(2 * Math.PI * i / bytesRead);
            sample = (short) (sample * tremoloFactor);

            // Update the buffer with the modified sample
            buffer[i] = (byte) (sample & 0xFF);
            buffer[i + 1] = (byte) ((sample >> 8) & 0xFF);
        }
    }
}
