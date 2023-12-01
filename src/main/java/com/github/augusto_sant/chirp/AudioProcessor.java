package com.github.augusto_sant.chirp;

import javax.sound.sampled.AudioFormat;

public interface AudioProcessor {
    void process(byte[] buffer, int bytesRead, AudioFormat format);
}
