package start;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class tr {
    public static void main(String[] args) throws Exception{
        Scanner scan1 = new Scanner(System.in);
        Socket socket1 = new Socket(scan1.nextLine(),1121);
        DatagramSocket socket = new DatagramSocket();
        while (true) {
            Scanner scan = new Scanner(System.in);
            String str = scan.nextLine();
            System.out.println(str);
            DatagramPacket packet = new DatagramPacket(str.getBytes(),str.length(),InetAddress.getByName("192.168.43.55"),5012);
            socket.send(packet);
        }
    }
}
