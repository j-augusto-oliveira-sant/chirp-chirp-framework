package com.github.augusto_sant.chirp;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * A factory class for loading and creating instances of the {@link AudioFile} class.
 */
public class AudioFileFactory {

    /**
     * Loads an audio file from the specified path and creates an instance of the {@link AudioFile} class.
     *
     * @param path The path of the audio file.
     * @return An instance of the {@link AudioFile} class representing the loaded audio file.
     */
    public AudioFile loadAudioFile(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        float volume = 0f;
        float speed = 1f;
        String fileName = extractFileName(path);
        File file = new File(path);
        return new AudioFile(file, volume, speed, path, fileName);
    }

    /**
     * Extracts the file name from the given path.
     *
     * @param path The path of the audio file.
     * @return The extracted file name.
     */
    private String extractFileName(String path) {
        int lastIndex = path.lastIndexOf("/");
        if (lastIndex == -1) {
            lastIndex = path.lastIndexOf("\\");
        }
        return lastIndex != -1 ? path.substring(lastIndex + 1) : path;
    }
}

