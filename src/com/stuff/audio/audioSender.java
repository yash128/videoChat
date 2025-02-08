package com.stuff.audio;

import javafx.concurrent.Task;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class audioSender extends Task {
    private TargetDataLine line;
    private byte[] data;
    public audioSender(AudioFormat format) throws LineUnavailableException {
        DataLine.Info dataLine = new DataLine.Info(TargetDataLine.class,format);
        if (!AudioSystem.isLineSupported(dataLine)) {
            System.out.println("not supported");
            return;
        }
        line = (TargetDataLine) AudioSystem.getLine(dataLine);
        line.open();
        data = new byte[line.getBufferSize()/5];
    }

    public byte[] getBytes() {
        return data;
    }

    @Override
    protected Object call() throws Exception {
        return null;
    }
    public int captureAud(){
        line.start();//This code is not at all correct, line should not start every Tine
        int numBytesRead = line.read(data,0, data.length);
        return numBytesRead;
    }
    public void printMixers(){
        Mixer.Info[] info = AudioSystem.getMixerInfo();
        for (Mixer.Info i : AudioSystem.getMixerInfo()) System.out.println(i.getName() + "----" + i.getDescription());
//        mixer = AudioSystem.getMixer(info[0]);
    }
    public void stopCapture(){line.stop();}
    public void closeTargetLine(){
        line.close();
    }
}