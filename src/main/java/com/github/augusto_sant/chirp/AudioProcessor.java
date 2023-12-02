package com.github.augusto_sant.chirp;

import javax.sound.sampled.AudioFormat;

/**
 * An interface representing an audio processor for applying effects to audio data.
 */
public interface AudioProcessor {

    /**
     * Processes the audio data in the provided buffer to apply a effect.
     *
     * @param buffer The audio data buffer to be processed.
     * @param bytesRead The number of bytes to be processed in the buffer.
     * @param format The AudioFormat describing the audio data format.
     */
    void process(byte[] buffer, int bytesRead, AudioFormat format);
}
