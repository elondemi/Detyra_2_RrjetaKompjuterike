
package Server;

import java.net.DatagramSocket;
import java.net.SocketException;

public class main {
    public static  void main(String[] args) throws SocketException {
        DatagramSocket datagramSocket = new DatagramSocket(1234);
        Server server = new Server(datagramSocket);
        server.receiveThenSend();
    }
}