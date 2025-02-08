package com.stuff.video;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.*;
public class videoReceiver extends Task {
    private DatagramSocket socket;
    private DatagramPacket packet;
    private int port;
    private InetAddress address;
    private ImageView view;
    private boolean required;
    public int length;
    public videoReceiver(int port, ImageView view) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        this.port = port;
        address = InetAddress.getLocalHost();
        this.view = view;
        required = true;
        length = 45000;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void showImage(int byteLength){
        try {
            while (required){
                byte[] data = new byte[byteLength];
                packet = new DatagramPacket(data,byteLength,address,port);
                socket.receive(packet);
                ByteArrayInputStream stream = new ByteArrayInputStream(data);
                Platform.runLater(() -> view.setImage(new Image(stream)));
            }
        }catch (IOException e){
            System.out.println("error while loading image");
        }
    }

    @Override
    protected Object call() throws Exception {
        showImage(length);
        return null;
    }
}
