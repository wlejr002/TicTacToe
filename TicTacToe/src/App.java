import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.lang.*;

// Hi
public class App {
    public static Scanner scan = new Scanner(System.in);
    public static Pattern input_move = Pattern.compile("^[1-3],[1-3]$");
    public static int turn = 0;
    public static PlayerType bot = PlayerType.PLAYER;
    public static PlayerType human = PlayerType.BOT;
    public static int min = Integer.MIN_VALUE;
    public static int max = Integer.MAX_VALUE;
    public static int[] copmuterMove ;

    public static char[][] board = buildBaord();

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

        //char[][] board = buildBaord();
        drawBoard(board);

        int[] rowAndCol = getInput(whoseTurn());

        while (board[rowAndCol[0]][rowAndCol[1]] != '-') {
            System.out.println("already filled put somewhere else");
            rowAndCol = getInput(whoseTurn());
        }

        makeMove(board, rowAndCol, whoseTurn());

        while (!gameOver(board)) {
            System.out.println("it is turn : " + turn);
            drawBoard(board);
            rowAndCol = getInput(whoseTurn());

            while (board[rowAndCol[0]][rowAndCol[1]] != '-') {
                System.out.println("already filled put somewhere else");
                rowAndCol = getInput(whoseTurn());
            }

            makeMove(board, rowAndCol, whoseTurn());


        }


        //System.out.println(test);
        drawBoard(board);

        long endTime = System.currentTimeMillis();
        long timeDiff = (endTime - startTime);
        System.out.println("it took " + timeDiff);

    }

    public static PlayerType whoseTurn() {
        if (turn % 2 == 0) {
            return bot;
        } else {
            return human;
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

    public static void makeMove(char[][] board, int[] index, PlayerType player){
        if (whoseTurn().equals(bot)) {
            board[index[0]][index[1]] = 'X';
        } else {
            board[index[0]][index[1]] = 'O';
        }
        turn +=1;
    }

    public static int[] getInput(PlayerType Player) {
        int[] answer = new int[2];

        System.out.println("Player" + Player + "'s turn, please enter row,column");
        String move = scan.nextLine();

        if (input_move.matcher(move).matches()) {
            int temp_row = Character.getNumericValue(move.charAt(0));
            int temp_col = Character.getNumericValue(move.charAt(2));
            answer[0] = temp_row - 1;
            answer[1] = temp_col - 1;
        } else {
            System.out.println("wrong input, input should be in row,column");
            answer = getInput(Player);
        }
        return answer;

    }

    public static boolean gameOver(char[][] board) {
        return whoWon(board, 'X') || whoWon(board, 'O') || isDraw(board);
    }

    public static boolean whoWon(char[][] board, char player){
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            System.out.println("Player"+ player + " Wins case 1");
            return true;
        } else if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            System.out.println("Player"+ player + " Wins case 2");
            return true;
        }

        for (int i = 0; i < 3; i++) {

            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                System.out.println("Player"+ player + " Wins case 3");
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                System.out.println("Player"+ player + " Wins case 4");
                return true;
            }
        }

        return false;
    }

    public static boolean isDraw(char[][] board){
        boolean checker = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    checker = false;
                    break;
                }
            }
        }
        return checker;
    }

    public static ArrayList<int[]> getEmptySlots(char[][] board){
        ArrayList<int[]> answer = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-'){
                    int[] temp = {i,j};
                    answer.add(temp);
                }
            }
            System.out.println();
        }
        return answer;
    }

    public static int minimax(char[][] board, int depth){
        if (whoWon(board, 'X')){
            return 1;
        } else if (whoWon(board, 'Y')){
            return -1;
        }
        ArrayList<int[]> emptySlots = getEmptySlots(board);

        if (emptySlots.isEmpty()){
            return 0;
        }


        for (int[] pair : emptySlots){


            if (whoseTurn().equals(bot)){
                makeMove(board, pair, bot);
                int score = minimax(board , depth +=1);
                max = Math.max(score, max);

                if (depth == 0){
                    System.out.println("computer position" + pair + score);
                }

                if (score >=0){
                    if (depth ==0){
                        copmuterMove = pair;
                    }
                }  else if (score == 1) {
                    makeMove(board, pair, human);
                }

            } else if (whoseTurn().equals())



        }





    return 1;
    }
}