package main;
import com.stuff.video.imageHandler;
import com.stuff.video.videoReceiver;
import javafx.application.Platform;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import connector.*;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    private Stage stage;
    private int imageHeight = 200;
    private int imageWidth = 400;
    private int numOfImg;
    private double HGap;
    private clientUDP udp;
    private clientTCP tcp;
    private clientHandler clients;
    private String myName;
    private int[] ports = {5011,5022,5033,5044};
    private imageHandler imgHandle;
    @FXML
    private HBox groupBar;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView mainImg;
    @FXML
    public void initialize() {
        setImageViews();
        groupBar.setSpacing(20);
        clients = clientHandler.getInstance();
        try {
            udp = new clientUDP();
        }catch (IOException e){
            e.printStackTrace();
            Platform.exit();
        }
        tcp = clientTCP.getInstance();
        imgHandle = new imageHandler(mainImg,udp);
    }
    public void handleThreads (boolean startReceive){/*
        for (Node node : groupBar.getChildren()) {
            if (node instanceof ImageView) {
                videoReceiver receiver = clients.getClients().get(node.getId());
                if (startReceive) {
                    receiver.setRequired(true);
                    receiver.restart();
                } else receiver.setRequired(false);
            }
        }*/
    }
    @FXML
    public void onThreadsDemand(ActionEvent e){
        handleThreads(false);
        if (((Node)e.getSource()).getId().equals("left")){
            scrollPane.setHvalue(scrollPane.getVvalue()+1);
        }else {
            scrollPane.setHvalue(scrollPane.getVvalue()-1);
        }
        //change elements in HBox and enable below code afterwards
//        handleThreads(true);
    }
    private void setImageViews(){
        double width = scrollPane.getPrefWidth();
        numOfImg = (int)width/imageWidth-1;//4
        int totalGap = (int)width-(numOfImg*imageWidth);//200
        HGap = totalGap/(numOfImg-1);//200 / 3 = 0.67
    }
    @FXML
    public void startVid(){
        imgHandle.start();
        System.out.println("video started");
        stage = (Stage)mainImg.getScene().getWindow();
    }
    @FXML
    public void stopVid() {
        imgHandle.stopVid = true;
        System.out.println("video stopped");
    }
    public void setName(String s){
        myName = s;
        String str = String.valueOf(s.charAt(0)+s.charAt(1));
        ImageView view = new ImageView();
        view.setId(str);view.prefHeight(imageHeight);view.prefWidth(imageWidth);
        groupBar.getChildren().add(view);
        try {
            createImg(str,view);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public void loadClients(){
        new Thread(new Task<>() {
            @Override
            protected Object call() throws Exception {
                ObservableMap<String, videoReceiver> map =
                        clients.getClients();
                while (true) {
                    for (int port : ports) {
                        byte[] receivedName = new byte[20];
                        System.out.println("before reading name");
                        tcp.readFromStream(receivedName);
                        String data = new String(receivedName);
                        data = data.trim();
                        ImageView view = new ImageView();
                        view.setId(data);view.prefWidth(imageWidth);view.prefHeight(imageHeight);
                        /*view.setFitWidth(imageWidth);view.setFitHeight(imageHeight);*/
                        createImg(String.valueOf(data.charAt(0))+data.charAt(1),view);
                        videoReceiver receiver = new videoReceiver(port, view);
                        map.put(data, receiver);
                        Platform.runLater(() -> groupBar.getChildren().add(view));
                        System.out.println(data + " joined room");
                    }
                }
            }
        }).start();
    }
    private List<Node> getVisibleItems(ScrollPane pane){
        List<Node> list = new ArrayList<>();
        Bounds paneBounds = pane.localToScene(pane.getBoundsInParent());
        if (pane.getContent() instanceof Parent){
            for (Node n : ((Parent)pane.getContent()).getChildrenUnmodifiable()){
                Bounds nodeBounds = n.localToScene(n.getBoundsInLocal());
                if (paneBounds.intersects(nodeBounds)){
                    list.add(n);
                }
            }
        }
        return list;
    }
    //below func to be increased performance later
    private void createImg(String str,ImageView view) throws IOException{
        Font font = new Font("Arial",Font.BOLD,28);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BufferedImage image = new BufferedImage(imageWidth,imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setFont(font);
        FontMetrics metrics = graphics.getFontMetrics(font);
        graphics.setColor(Color.GREEN);
        graphics.fillRect(0,0,imageWidth,imageHeight);//fill entire image with rect.
        int posX = (imageWidth-metrics.stringWidth(str)) / 2;
        int posY = (imageHeight-metrics.getHeight()) / 2 + metrics.getAscent();
        graphics.setColor(Color.BLACK);
        graphics.drawString(str,posX,posY);
        ImageIO.write(image,"jpg",stream);
        view.setImage(new Image(new ByteArrayInputStream(stream.toByteArray())));
    }
}
