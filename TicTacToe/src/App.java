import jdk.swing.interop.SwingInterOpUtils;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.regex.Pattern;

public class App {
    public static Scanner scan = new Scanner(System.in);
    public static Pattern input_move = Pattern.compile("^[1-3],[1-3]$");
    public static int turn = 0;
    public static String p1 = "P1";
    public static String p2 = "P2";



    public static void main(String[] args) {
        /*
        System.out.println("enter your name");
        String p1 = scan.nextLine();
        System.out.println("hi" + p1);
        System.out.println("enter your name");
        String p2 = scan.nextLine();
        System.out.println("hi" + p2);
        */

        long startTime = System.currentTimeMillis();



        char[][] board = buildBaord();
        drawBoard(board);



        int[] rowAndCol = getInput(whosturn());

        while (board[rowAndCol[0]][rowAndCol[1]] != '-'){
            System.out.println("already filled put somewhere else");
            rowAndCol = getInput(whosturn());
        }

        if (whosturn() == p1) {
            board[rowAndCol[0]][rowAndCol[1]] = 'X';
        } else {
            board[rowAndCol[0]][rowAndCol[1]] = 'O';
        }
        turn +=1;

        while (!gameOver(board)){
            System.out.println("it is turn : " + turn);
            drawBoard(board);
            rowAndCol = getInput(whosturn());
            while (board[rowAndCol[0]][rowAndCol[1]] != '-'){
                System.out.println("already filled put somewhere else");
                rowAndCol = getInput(whosturn());
            }
            if (whosturn() == p1) {
                board[rowAndCol[0]][rowAndCol[1]] = 'X';
            } else {
                board[rowAndCol[0]][rowAndCol[1]] = 'O';
            }
            turn +=1;
        }




        //System.out.println(test);
        drawBoard(board);
        long endTime = System.currentTimeMillis();
        long timeDiff = (endTime - startTime);
        System.out.println("it took " + timeDiff);

    }
    public static String whosturn(){
        if (turn % 2 == 0){
            return p1;
        } else {
            return p2;
        }
    }
    public static char[][] buildBaord() {
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
        return board;
    }

    public static void drawBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public static int[] getInput(String Player) {
        int[] answer = new int[2];

        System.out.println("Player" + Player + "'s turn, please enter row,column");
        String move = scan.nextLine();

        if (input_move.matcher(move).matches()) {
            int temp_row = Character.getNumericValue(move.charAt(0));
            int temp_col = Character.getNumericValue(move.charAt(2));
            answer[0] = temp_row-1;
            answer[1] = temp_col-1;
        } else {
            System.out.println("wrong input, input should be in row,column");
            answer = getInput(Player);
        }
        return answer;

    }


    public static boolean gameOver(char[][] board) {
        if (board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] =='X') {

            System.out.println("Playwer 1 Wins case 1");
            return true;
        } else if (board[0][0] == 'O' && board[1][1] == 'O' && board[2][2] =='O'){

            System.out.println("Playwer 2 Wins case 2");
            return true;

        } else if (board[0][2] == 'X' && board[1][1] == 'X' && board[2][0] =='X') {
            System.out.println("Player 1 Wins case 3");
            return true;
        } else if (board[0][2] == 'O' && board[1][1] == 'O' && board[2][0] =='O'){
            System.out.println("Player 2 Wins case 4");
            return true;
        }
        for (int i = 0; i < 3; i++) {
            if (board[i][0]=='X' && board[i][1] == 'X' && board[i][2] == 'X'){
                System.out.println("Player 1 Wins case 5");
                return true;
            } else if (board[i][0]=='O' && board[i][1] == 'O' && board[i][2] == 'O') {
                System.out.println("Player 2 Wins case 6");
                return true;
            }
            if (board[0][i]=='X' && board[1][i] == 'X' && board[2][i] == 'X'){
                System.out.println("Player 1 Wins case 7");
                return true;
            } else if (board[0][i]=='O' && board[1][i] == 'O' && board[2][i] == 'O') {
                System.out.println("Player 2 Wins case 8");
                return true;
            }
        }
        boolean isFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-'){
                    isFull = false;
                }
            }
        }

        if (isFull == true){
            System.out.println("Draw");
            return true;
        }


        return false;
    }


    
}