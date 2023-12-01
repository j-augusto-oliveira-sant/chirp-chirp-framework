package com.github.augusto_sant.chirp;

import java.util.ArrayList;

public class AudioProcessorComposer {

    private ArrayList<AudioProcessor> processors;

    public AudioProcessorComposer() {
        this.processors = new ArrayList<AudioProcessor>();
    }

    public ArrayList<AudioProcessor> getProcessors() {
        return processors;
    }

    public void setProcessors(ArrayList<AudioProcessor> processors) {
        this.processors = processors;
    }

    public AudioProcessorComposer withReverb() {
        processors.add(new ReverbProcessor(0.2f));
        return this;
    }
    public AudioProcessorComposer withReverb(float delay) {
        processors.add(new ReverbProcessor(delay));
        return this;
    }

    public AudioProcessorComposer withPhaser() {
        processors.add(new PhaserProcessor(0.5f,0.8f));
        return this;
    }
    public AudioProcessorComposer withPhaser(float modulationRate, float modulationDepth) {
        processors.add(new PhaserProcessor(modulationRate, modulationDepth));
        return this;
    }

    public AudioProcessorComposer withChorus() {
        processors.add(new ChorusProcessor(0.5f));
        return this;
    }
    public AudioProcessorComposer withChorus(float delay) {
        processors.add(new ChorusProcessor(delay));
        return this;
    }
    public AudioProcessorComposer withTremolo() {
        processors.add(new TremoloProcessor());
        return this;
    }
    public void clearEffects(){
        processors.clear();
    }
}
