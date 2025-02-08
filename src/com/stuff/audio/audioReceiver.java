package com.stuff.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class audioReceiver {
    private SourceDataLine line;
    public audioReceiver(AudioFormat format){
        DataLine.Info info = new DataLine.Info(SourceDataLine.class,format);

    }


//    private DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//    public void sendAud(byte[] data) throws IOException {
//        out.writeInt(data.length);
//        out.write(data);
//        out.flush();
//    }
//    public byte[] recvAud() throws IOException {
//        DataInputStream in = new DataInputStream(socket.getInputStream());
//        int length = in.readInt();
//        byte[] data = new byte[length];
//        in.readFully(data);
//        return data;
//    }

}
