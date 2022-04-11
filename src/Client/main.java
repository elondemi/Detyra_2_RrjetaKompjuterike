package Client;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws SocketException, UnknownHostException {
        DatagramSocket datagramSocket = new DatagramSocket();
        String ipaddress = "127.0.0.1";
        int port = 1234;
        Scanner sc = new Scanner(System.in);
        System.out.print("Shkruani IP (127.0.0.1), shtyp enter per default: ");
        String _ipaddress = sc.nextLine();
        if(_ipaddress.length() > 0) ipaddress = _ipaddress;
        System.out.print("\nShkruani port (1234), shtyp enter per default: ");
        try {
            int _port = Integer.parseInt(sc.nextLine());
            port = _port;
        } catch (Exception ignore) {}

        InetAddress inetAddress = InetAddress.getByName(ipaddress);
        Client client = new Client(datagramSocket, inetAddress);
        System.out.println("Send datagram packets to a server.");
        client.sendThenRecive(port);
    }
}