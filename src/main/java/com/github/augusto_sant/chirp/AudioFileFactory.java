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
     * Loads an audio file from the specified path and creates the {@link AudioFile} class.
     *
     * @param path The path of the audio file.
     * @return An instance of the {@link AudioFile} class representing the loaded audio file.
     */
    public AudioFile loadAudioFile(String path) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        float volume = 0f;
        float speed = 1f;
        String fileName = extractFileName(path);
        String type = extractType(path);
        File file = new File(path);
        return new AudioFile(file, volume, speed, path, fileName,type);
    }


    /**
     * Extracts the file name without the extension from the given path.
     *
     * @param path The path of the audio file.
     * @return The extracted file name without the extension.
     */
    private String extractFileName(String path) {
        int point = path.lastIndexOf(".");
        int lastSlash = path.lastIndexOf("\\");
        if (lastSlash == -1){
            lastSlash=0;
        }
        return path.substring(lastSlash,point);
    }

    /**
     * Extracts the type of the file from the given path.
     *
     * @param path The path of the audio file.
     * @return The extracted type.
     */
    private String extractType(String path) {
        int lastIndex = path.lastIndexOf(".");
        return lastIndex != -1 ? path.substring(lastIndex + 1) : "";
    }
}

