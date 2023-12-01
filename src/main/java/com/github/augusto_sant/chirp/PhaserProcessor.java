package com.github.augusto_sant.chirp;
import javax.sound.sampled.AudioFormat;
import java.util.Arrays;

public class PhaserProcessor implements AudioProcessor {

    private float modulationRate;
    private float modulationDepth;

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
