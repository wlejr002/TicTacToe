import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static Scanner scan = new Scanner(System.in);
    public static Pattern input_move = Pattern.compile("^[0-2],[0-2]$");
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
        String p1 = "you";
        String p2 = "P2";

        char[][] board = new char[3][3];

        for(int i = 0 ; i < 3 ; i++){
            for(int j = 0 ; j < 3; j ++){
                board[i][j] = '-';
            }
        }
        drawBoard(board);

        String test = getinput("me");
        int temp_row = Character.getNumericValue(test.charAt(0)); 
        int temp_col = Character.getNumericValue(test.charAt(2)); 
        board[temp_row][temp_col] = 'O';
        drawBoard(board);

        //System.out.println(test);

        long endTime = System.currentTimeMillis();

        long timeDiff = (endTime - startTime);


        System.out.println("it took " + timeDiff);

    }

    public static void drawBoard(char[][] board) {
        for(int i = 0 ; i < 3 ; i++){
            for(int j = 0 ; j < 3; j ++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public static String getinput(String Player){
        
        System.out.println("Player"+ Player + "'s turn, please enter row,column");
        String move = scan.nextLine();
        
        String val;
        if (input_move.matcher(move).matches()){
            val = move;
        } else {
            System.out.println("wrong input, input should be in row,column");
            val = getinput(Player);
        }
        return val;

    }


/*
    public static void wincheck(char[][] board){
        for(int i =0; i <3; i ++){
            if(board[i][0] == board[i][1] && board[i][0] == board[i][2]){
               
            }
        }
    }
    */
    
}