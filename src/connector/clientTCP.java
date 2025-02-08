package connector;

import java.io.*;
import java.net.Socket;
public class clientTCP {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private InputStream stream;
    private static clientTCP tcp = new clientTCP();
    public static clientTCP getInstance(){
        return tcp;
    }
    public BufferedReader getBufferedReader() {
        return reader;
    }
    public PrintWriter getPrintWriter() {
        return writer;
    }
    private clientTCP() {
        try {
            socket = new Socket("localhost", 5024);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            stream = socket.getInputStream();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public int readFromStream(byte[] data){
        try {
            int bytesRec = stream.read(data);
            return bytesRec;
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return -1;
    }
    public Socket getSocket(){return socket;}
}
