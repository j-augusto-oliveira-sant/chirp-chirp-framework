package com.github.augusto_sant.chirp;
import javax.sound.sampled.AudioFormat;
import java.util.Arrays;

/**
 * A Java class implementing the AudioProcessor interface for phaser audio processing.
 */
public class PhaserProcessor implements AudioProcessor {
    /** The modulation rate for the phaser effect. */
    private float modulationRate;
    /** The modulation depth for the phaser effect. */
    private float modulationDepth;

    /**
     * Constructs a PhaserProcessor with the specified modulation rate and depth.
     *
     * @param modulationRate The rate of modulation for the phaser effect.
     * @param modulationDepth The depth of modulation for the phaser effect.
     */
    public PhaserProcessor(float modulationRate, float modulationDepth) {
        this.modulationRate = modulationRate;
        this.modulationDepth = modulationDepth;
    }

    @Override
    public void process(byte[] buffer, int bytesRead, AudioFormat format) {
        for (int i = 0; i < bytesRead; i += format.getFrameSize()) {
            float modulation = (float) Math.sin(2 * Math.PI * modulationRate * i / format.getSampleRate());
            float scaleFactor = 1 + modulationDepth * modulation;
            for (int j = 0; j < format.getFrameSize(); j++) {
                buffer[i + j] *= scaleFactor;
            }
        }
    }
}
