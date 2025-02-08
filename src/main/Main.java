package main;
import connector.clientTCP;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Controller controller;
    private Parent root;
    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Font font = new Font(20);
        GridPane pane = new GridPane();
        Label l1 = new Label("Name : ");l1.setFont(font);
        Label l2 = new Label("ID  : ");l2.setFont(font);
        final TextField name = new TextField();
        final TextField id = new TextField();
        pane.setHgap(20);pane.setVgap(20);pane.setAlignment(Pos.CENTER);
        pane.add(l1,0,0);
        pane.add(name,1,0);
        pane.add(l2,0,1);
        pane.add(id,1,1);
        ChoiceBox<String> isHost = new ChoiceBox<>();
        isHost.setValue("create new meeting");
        isHost.getItems().add("create new meeting");
        isHost.getItems().add("join existing meeting");
        isHost.prefHeight(85);
        pane.add(isHost,0,2);
        Button send = new Button("Connect");
        send.setOnAction(e -> {
            StringBuilder name1 = new StringBuilder(name.getText());
            name1.append(" ".repeat(Math.max(0, 20 - name.getText().length())));
            clientTCP tcp = clientTCP.getInstance();
            System.out.println(name1.length());
            //below line to be removed afterwards
            imageLoader.folderName = name1.charAt(0);
            tcp.getPrintWriter().println(name1 + ":" + id.getText());
            try {
                byte[] inputData = new byte[1];
                tcp.readFromStream(inputData);
                if (new String(inputData).equals("q")) throw new IOException();
            } catch (IOException g) {
                System.out.println(g.getMessage());
                Platform.exit();
            }
            try {
                root = loader.load();
                controller = loader.getController();
                controller.loadClients();
                Scene main = new Scene(root, 1400, 800);
                controller.setName(name.getText());
                primaryStage.setScene(main);
                primaryStage.setTitle("Fully encrypted");
            }catch (IOException r){
                System.out.println(r.getMessage());
            }
        });
        send.setAlignment(Pos.CENTER);
        send.setBackground(new Background(new BackgroundFill(Color.RED,new CornerRadii(50),new Insets(0))));
        send.setPadding(new Insets(10));send.setFont(font);
        pane.add(send,0,3,3,1);
        GridPane.setMargin(send,new Insets(10,0,0,40));
        Scene start = new Scene(pane,600,400);
        primaryStage.setScene(start);
        primaryStage.setTitle("Welcome to ");
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
