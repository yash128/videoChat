package main;

import com.stuff.video.videoReceiver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class clientHandler {
    private ObservableMap<String, videoReceiver> clients;
    private static clientHandler handler = new clientHandler();
    private clientHandler() {
        this.clients = FXCollections.observableHashMap();
    }
    public ObservableMap<String, videoReceiver> getClients() {
        return clients;
    }
    public static clientHandler getInstance(){
        return handler;
    }
}
