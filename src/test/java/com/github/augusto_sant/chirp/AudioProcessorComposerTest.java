package com.github.augusto_sant.chirp;

import junit.framework.TestCase;

import java.util.ArrayList;

public class AudioProcessorComposerTest extends TestCase {

    public void testGetProcessors() {
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        processorComposer.withReverb();
        ArrayList<AudioProcessor> processors = new ArrayList<>();
        processors.add(new ReverbProcessor(0.2f));
        assertEquals(processors.get(0).getClass(),processorComposer.getProcessors().get(0).getClass());
    }

    public void testSetProcessors() {
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        ArrayList<AudioProcessor> processors = new ArrayList<>();
        processors.add(new ReverbProcessor(0.2f));
        processorComposer.setProcessors(processors);
        assertEquals(processors.get(0),processorComposer.getProcessors().get(0));
    }

    public void testWithReverb() {
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        processorComposer.withReverb();
        ArrayList<AudioProcessor> processors = new ArrayList<>();
        processors.add(new ReverbProcessor(0.2f));
        assertEquals(processors.get(0).getClass(),processorComposer.getProcessors().get(0).getClass());
    }

    public void testWithReverb2() {
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        processorComposer.withReverb(0.3f);
        ArrayList<AudioProcessor> processors = new ArrayList<>();
        processors.add(new ReverbProcessor(0.3f));
        assertEquals(processors.get(0).getClass(),processorComposer.getProcessors().get(0).getClass());
    }

    public void testWithPhaser() {
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        processorComposer.withPhaser();
        ArrayList<AudioProcessor> processors = new ArrayList<>();
        processors.add(new PhaserProcessor(0.5f,0.8f));
        assertEquals(processors.get(0).getClass(),processorComposer.getProcessors().get(0).getClass());
    }

    public void testWithPhaser2() {
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        processorComposer.withPhaser(0.3f,0.9f);
        ArrayList<AudioProcessor> processors = new ArrayList<>();
        processors.add(new PhaserProcessor(0.3f,0.9f));
        assertEquals(processors.get(0).getClass(),processorComposer.getProcessors().get(0).getClass());
    }

    public void testWithChorus() {
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        processorComposer.withChorus();
        ArrayList<AudioProcessor> processors = new ArrayList<>();
        processors.add(new ChorusProcessor(0.5f));
        assertEquals(processors.get(0).getClass(),processorComposer.getProcessors().get(0).getClass());
    }

    public void testWithChorus2() {
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        processorComposer.withChorus(0.8f);
        ArrayList<AudioProcessor> processors = new ArrayList<>();
        processors.add(new ChorusProcessor(0.8f));
        assertEquals(processors.get(0).getClass(),processorComposer.getProcessors().get(0).getClass());
    }

    public void testWithTremolo() {
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        processorComposer.withTremolo();
        ArrayList<AudioProcessor> processors = new ArrayList<>();
        processors.add(new TremoloProcessor());
        assertEquals(processors.get(0).getClass(),processorComposer.getProcessors().get(0).getClass());
    }

    public void testClearEffects() {
        AudioProcessorComposer processorComposer = new AudioProcessorComposer();
        processorComposer.withReverb().withTremolo();
        processorComposer.clearEffects();
        assertEquals(0,processorComposer.getProcessors().size());
    }
}