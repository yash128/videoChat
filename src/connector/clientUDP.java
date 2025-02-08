package connector;

import java.io.IOException;
import java.net.*;

public class clientUDP {
    private DatagramSocket conn;
    private InetAddress addr;
    private int port = 5073;
    public clientUDP() throws SocketException,UnknownHostException{
        conn = new DatagramSocket();
        addr = InetAddress.getByName("127.0.0.1");
        conn.connect(addr,port);
    }
    public void send(byte[] data) throws IOException{
        DatagramPacket packet = new DatagramPacket(data,data.length,addr,port);
        conn.send(packet);
    }

    public byte[] recv(int length) throws IOException{
        byte[] data = new byte[length];
        DatagramPacket packet = new DatagramPacket(data,length);
        conn.receive(packet);
        return data;
    }
}
