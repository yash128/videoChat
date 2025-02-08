package com.stuff.video;
import connector.clientUDP;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.imageLoader;
import java.io.IOException;

public class imageHandler extends Thread {
    final private ImageView imgView;
    public boolean stopVid;
    private imageLoader loader;
    private clientUDP udp;
    public imageHandler(ImageView imgView,clientUDP udp){
        loader = new imageLoader();
        this.imgView = imgView;
        this.udp = udp;
    }
    public void sendCapture() {
        stopVid = false;
        try {
            while (!stopVid) {
                byte[] data = loader.imageLoaders();
                System.out.println(data.length);
                Image img = SwingFXUtils.toFXImage(imageLoader.reader,null);
                imageLoader.reader.flush();
                udp.send(java.nio.ByteBuffer.allocate(data.length).array());
                udp.send(data);
                Platform.runLater(() -> imgView.setImage(img));
                Thread.sleep(500);
            }
        }catch (IOException | InterruptedException e){
            System.out.println("error occurred " + e.getMessage());
        }
    }

    @Override
    public void run() {
        sendCapture();
    }
}
