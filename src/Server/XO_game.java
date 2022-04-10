package Server;

import java.security.SecureRandom;
import java.sql.SQLOutput;
import java.util.Base64;
import java.util.Scanner;

public class XO_game {
    public char[][] board = new char[3][3];
    public int sign = 0;
    private String password;

    public void fillBoard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }


    public XO_game(){
        this.fillBoard();
        //gjenero password random
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        password = Base64.getEncoder().encodeToString(bytes).substring(0, 7);
    }

    public XO_game(String manualPassword){
        this.fillBoard();
        this.password = manualPassword;
    }

    public String getPassword(){
        return this.password;
    }

//    public void main(String[] args) {
//          if(checkForWinner()) {
//                 //shfaq mesazhin e fituesit dhe dil nga while loop.
//             sign++;
//             System.out.println("Player " + ((sign - 1) % 2 == 1 ? "x" : "o") + " won!");
//        }
//
//        System.out.println("The End");
//    }

    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    public String getBoard() {
        String _board = "";
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                _board += (board[i][j] + " ");
            }
            _board += "\n";
        }
        return _board;
    }
    public void printBoard(){
        System.out.println(getBoard());
    }

    public boolean checkLegalMove(int x_coord, int y_coord)
    {
        if(x_coord < 0 || y_coord < 0 || x_coord > 2 || y_coord > 2) return false;
        return board[x_coord][y_coord] == '-';
    }
    public boolean isMoveLeft(){
        return sign < 9;
    }

    public void move(int x_coord, int y_coord, char sign) throws Exception{
        if(!checkLegalMove(x_coord, y_coord)) throw new GameException("Move is not legal");
        board[x_coord][y_coord] = sign;
    }
    public void move(int x_coord, int y_coord) throws Exception{
        move(x_coord, y_coord, (sign++) % 2 == 0 ? 'o' : 'x');
    }

    public boolean checkForWinner() {
        if(sign < 5)  {
//early return ne rast se nuk kaluar numrin e levizjeve te nevojshem per te fituar lojen.
            return false;
        }
        for(int i = 0; i < 3; i++){

//shiko nese eshte mbyllur ndonje rresht
            if(board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2])
                return true;


//shiko nese eshte mbyllur ndonje kolone
            if(board[0][i] != '-' && board[0][i] == board[1][i] && board[1][i] == board[2][i])
                return true;
        }

//shiko nese eshte mbyllur matrica e parë
        if(board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return true;

//shiko nese eshte mbyllur matrica e dytë
        if(board[2][0] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0])
            return true;

        return false;
    }

}