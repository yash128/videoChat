package main;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class addingView implements Runnable{
    private ImageView view;
    private HBox box;
    public addingView(HBox box){
        this.box = box;
    }
    @Override
    public void run() {
        box.getChildren().add(view);
    }
    public void setView(ImageView view){
        this.view = view;
        Platform.runLater(this);
    }
}
