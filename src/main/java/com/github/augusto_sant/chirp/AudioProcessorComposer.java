package com.github.augusto_sant.chirp;

import java.util.ArrayList;

/**
 * A class for composing audio processors to create a chain of effects.
 */
public class AudioProcessorComposer {

    /** The list of audio processors in the composition. */
    private ArrayList<AudioProcessor> processors;

    /**
     * Constructs an AudioProcessorComposer with an empty list of processors.
     */
    public AudioProcessorComposer() {
        this.processors = new ArrayList<AudioProcessor>();
    }

    /**
     * Gets the list of audio processors in the composition.
     *
     * @return The list of audio processors.
     */
    public ArrayList<AudioProcessor> getProcessors() {
        return processors;
    }

    /**
     * Sets the list of audio processors in the composition.
     *
     * @param processors The list of audio processors.
     */
    public void setProcessors(ArrayList<AudioProcessor> processors) {
        this.processors = processors;
    }

    /**
     * Adds a reverb processor with a default delay to the composition.
     *
     * @return The updated AudioProcessorComposer instance.
     */
    public AudioProcessorComposer withReverb() {
        processors.add(new ReverbProcessor(0.2f));
        return this;
    }
    /**
     * Adds a reverb processor with a specified delay to the composition.
     *
     * @param delay The delay time for the reverb effect.
     * @return The updated AudioProcessorComposer instance.
     */
    public AudioProcessorComposer withReverb(float delay) {
        processors.add(new ReverbProcessor(delay));
        return this;
    }
    /**
     * Adds a phaser processor with default modulation parameters to the composition.
     *
     * @return The updated AudioProcessorComposer instance.
     */
    public AudioProcessorComposer withPhaser() {
        processors.add(new PhaserProcessor(0.5f,0.8f));
        return this;
    }
    /**
     * Adds a phaser processor with specified modulation parameters to the composition.
     *
     * @param modulationRate The rate of modulation for the phaser effect.
     * @param modulationDepth The depth of modulation for the phaser effect.
     * @return The updated AudioProcessorComposer instance.
     */
    public AudioProcessorComposer withPhaser(float modulationRate, float modulationDepth) {
        processors.add(new PhaserProcessor(modulationRate, modulationDepth));
        return this;
    }
    /**
     * Adds a chorus processor with a default delay to the composition.
     *
     * @return The updated AudioProcessorComposer instance.
     */
    public AudioProcessorComposer withChorus() {
        processors.add(new ChorusProcessor(0.5f));
        return this;
    }

    /**
     * Adds a chorus processor with a specified delay to the composition.
     *
     * @param delay The delay time for the chorus effect.
     * @return The updated AudioProcessorComposer instance.
     */
    public AudioProcessorComposer withChorus(float delay) {
        processors.add(new ChorusProcessor(delay));
        return this;
    }

    /**
     * Adds a tremolo processor to the composition.
     *
     * @return The updated AudioProcessorComposer instance.
     */
    public AudioProcessorComposer withTremolo() {
        processors.add(new TremoloProcessor());
        return this;
    }

    /**
     * Clears all effects from the composition.
     */
    public void clearEffects(){
        processors.clear();
    }
}
