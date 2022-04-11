package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Client {
    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private byte[] buffer;

    public Client(DatagramSocket datagramSocket, InetAddress inetAddress) {
        this.datagramSocket = datagramSocket;
        this.inetAddress = inetAddress;
    }

    public void sendThenRecive(int port){
        Scanner scanner = new Scanner(System.in);
        while (true){
            try{
                String messageToSend = scanner.nextLine();
                buffer = messageToSend.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                byte[] response = new byte[1024];
                DatagramPacket responsePacket = new DatagramPacket(response, response.length, inetAddress, port);
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(responsePacket);
                String messageFromServer = new String(responsePacket.getData());
                System.out.println("The server says you said: " + messageFromServer);
            } catch (IOException e){
                e.printStackTrace();
                break;
            }
        }
    }
}
