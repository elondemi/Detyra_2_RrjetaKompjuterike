package Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Server{
    private DatagramSocket datagramSocket;
    private byte[] buffer = new byte[1024];

    public static String available_commands = "/startgame [password] - To start a new game\n" +
            "printboard <gameId> to print board of game with specific gameId\n" +
            "/list - to list all games" +
            "/shfaqfajllat\n" +
            "/shkruajfajllin <emri i fajllit> <permbajtja>\n" +
            "\n";

    private ArrayList<XO_game> ongoing_games = new ArrayList<>();

    public Server(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public void receiveThenSend() {
        while (true) {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                datagramSocket.receive(datagramPacket);
                InetAddress inetAddress = datagramPacket.getAddress();
                int port = datagramPacket.getPort();
                String messageFromClient = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Message from client: " + messageFromClient);
                String response = available_commands;
                String client_commands[] = null;
                if (messageFromClient.split(" ").length > 1) {
                    client_commands = messageFromClient.split(" ");
                } else {
                    client_commands = new String[1];
                    client_commands[0] = messageFromClient;
                }
                try {
                    switch (client_commands[0]) {
                        case "startgame":
                            XO_game game;
                            if (client_commands.length == 1) {
                                game = new XO_game();
                            } else {
                                game = new XO_game(client_commands[1]);
                            }
                            ongoing_games.add(game);
                            response = "You have chosen startgame... Your game ID is: " + (ongoing_games.size() - 1) + "" +
                                    "Your password is " + game.getPassword() + "\n";
                            break;
                        case "move": //move <gameId> <password> <coord_x> <coord_y>
                            int gameId = Integer.parseInt(client_commands[1]);
                            if (!ongoing_games.get(gameId).verifyPassword(client_commands[2])) {
                                response += "Wrong password";
                                throw new Exception("Wrong password");
                            }
                            int coord_x = Integer.parseInt(client_commands[3]);
                            int coord_y = Integer.parseInt(client_commands[4]);
                            ongoing_games.get(gameId).move(coord_x, coord_y);
                            response += ongoing_games.get(gameId).getBoard();
                            if(ongoing_games.get(gameId).checkForWinner())
                            {
                                response += "Loja perfundoi, ka fituar " + (((ongoing_games.get(gameId).sign - 1) % 2 == 0) ? 'x' : 'o');
                            }

                        case "printboard":
                            response = "Printing the board of game\n";
                            response += ongoing_games.get(Integer.parseInt(client_commands[1])).getBoard();
                            break;
                        case "list":
                            response = "Listing all games";
                            for (int i = 0; i < ongoing_games.size(); i++) {
                                response += "\n Game ID: " + (i);
                            }
                            break;
                        case "shfaqfajllat":
                            response = "Duke i shfaqur fajllat" + ListoFajllat();
                            break;
                        case "shkruajfajllin":
                            shkruajFajllin(client_commands[1], client_commands[2]);
                            response = "Fajlli u shkruajt";
                            break;
                    }
//                buffer = response.getBytes();
                } catch (Exception ex) {
                    if (ex instanceof GameException) {
                        response += ex.getMessage();
                    }
                    System.out.println(ex.getMessage());
                }
                buffer = response.getBytes(StandardCharsets.UTF_8);
                datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                System.out.println(datagramPacket);
                datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

     String ListoFajllat() {    // creates a file object
        File file = new File("C:\\Users\\elond\\IdeaProjects\\Detyra_2_RrjetaKompjuterike");

        // returns an array of all files
        String[] fileList = file.list();
        String rezultat ="";//hajde pe rujme qetu :P
      for (String str : fileList) {
//            for(int i = 0; i < fileList.length; i++){
//                String str = fileList[i];
            rezultat += str + "\n";
        }
         return rezultat;
     }

     void shkruajFajllin(String emriFajllit, String permbajtja ) throws IOException {
     FileWriter fw = new FileWriter("C:\\Users\\elond\\IdeaProjects\\Detyra_2_RrjetaKompjuterike\\" + emriFajllit);
     fw.write(permbajtja);
     fw.close();
     }
}
