package Client;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class main {
    public static void main(String[] args) throws SocketException, UnknownHostException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("localhost");
        Client client = new Client(datagramSocket, inetAddress);
        System.out.println("Send datagram packets to a server.");
        client.sendThenRecive();
    }
}