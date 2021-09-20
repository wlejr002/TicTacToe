import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.lang.*;
import java.util.HashMap;

// Hi
public class App {
    public static Scanner scan = new Scanner(System.in);
    public static Pattern input_move = Pattern.compile("^[1-3],[1-3]$");
    public static int turn = 0;
    public static PlayerType bot = PlayerType.BOT;
    public static PlayerType player = PlayerType.PLAYER;
    public static int min = Integer.MIN_VALUE;
    public static int max = Integer.MAX_VALUE;
    public static int[] copmuterMove;
    public static boolean Playerfirst;
    public static HashMap<PlayerType, Character> playerMark = new HashMap<PlayerType, Character>();

    public static char[][] board = buildBaord();

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        drawBoard(board);
        /*
        System.out.println("enter your name");
        String p1 = scan.nextLine();
        System.out.println("hi" + p1);
        System.out.println("enter your name");
        String p2 = scan.nextLine();
        System.out.println("hi" + p2);
        */
        System.out.println("Do you want to start first ? \"yes\" to start first ");
        String p1 = scan.nextLine();
        if (p1.equalsIgnoreCase("yes")) {
            System.out.println("Starting with player");
            Playerfirst = true;
            playerMark.put(player, 'X');
            playerMark.put(bot, 'O');
        } else {
            System.out.println("Starting with BOT");
            Playerfirst = false;
            playerMark.put(bot, 'X');
            playerMark.put(player, 'O');
        }

        System.out.println("Player with mark " + playerMark.get(player) + ", AI with mark " + playerMark.get(bot));

        do {
            if (whoseTurn() == bot) {

                System.out.println("IT IS TRUN , " + turn);
                int score = min;
                int[] index = new int[]{-1, -1};

                ArrayList<int[]> emptySlots = getEmptySlots(board);
                for (int[] pair : emptySlots) {
                    makeMove(board, pair, bot);
                    int tempscore = minimax(board, 0, player);
                    board[pair[0]][pair[1]] = '-';
                    if (tempscore > score) {
                        score = tempscore;
                        index[0] = pair[0];
                        index[1] = pair[1];
                    }
                }
                makeMove(board, index, bot);
                drawBoard(board);
                turn += 1;


            } else {
                System.out.println("IT IS TRUN , " + turn);
                int[] rowAndCol = getInput(whoseTurn());


                while (board[rowAndCol[0]][rowAndCol[1]] != '-') {
                    System.out.println("already filled put somewhere else");
                    rowAndCol = getInput(whoseTurn());
                }

                makeMove(board, rowAndCol, whoseTurn());
                turn += 1;
                drawBoard(board);
            }

        } while (!gameOver(board));


        //System.out.println(test);
        drawBoard(board);

        long endTime = System.currentTimeMillis();
        long timeDiff = (endTime - startTime);
        System.out.println("it took " + timeDiff);

    }

    public static PlayerType whoseTurn() {
        if (Playerfirst) {
            if (turn == 0) {

                return player;
            } else if (turn % 2 == 0) {

                return player;
            } else {

                return bot;
            }
        } else {

            if (turn == 0) {

                return bot;
            } else if (turn % 2 == 0) {

                return bot;
            } else {

                return player;
            }
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

    public static void makeMove(char[][] board, int[] index, PlayerType player) {
        //System.out.println("player is " + player + " mark is " + playerMark.get(player));
        board[index[0]][index[1]] = playerMark.get(player);
    }

    public static int[] getInput(PlayerType Player) {
        int[] answer = new int[2];
        System.out.println("It is " + Player + "'s turn with mark " + playerMark.get(Player) + " type in \"row,column\" ");
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
        return whoWon(board, player) || whoWon(board, bot) || isDraw(board);
    }

    public static boolean whoWon(char[][] board, PlayerType ptype) {

        char player = playerMark.get(ptype);

        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            //System.out.println("Player "+ ptype + " Wins case 1");
            return true;
        } else if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            //System.out.println("Player "+ ptype + " Wins case 2");
            return true;
        }

        for (int i = 0; i < 3; i++) {

            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                //System.out.println("Player "+ player + " Wins case 3");
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                //System.out.println("Player "+ player + " Wins case 4");
                return true;
            }
        }

        return false;
    }

    public static boolean isDraw(char[][] board) {
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

    public static ArrayList<int[]> getEmptySlots(char[][] board) {
        ArrayList<int[]> answer = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    int[] temp = {i, j};
                    answer.add(temp);
                }
            }

        }
        return answer;
    }

    public static int minimax(char[][] board, int depth, PlayerType currentTurn) {
        if (whoWon(board, bot)) {
            //System.out.println("AI WON-------------");
            return 1;
        } else if (whoWon(board, player)) {
            //System.out.println("HUMAN WON-------------");
            return -1;
        } else if (isDraw(board)) {
            //System.out.println("ITS DRAW-------------");
            return 0;
        }

        ArrayList<int[]> emptySlots = getEmptySlots(board);
        int tempmin = Integer.MAX_VALUE;
        int tempmax = Integer.MIN_VALUE;

        if (currentTurn == bot) {
            for (int[] pair : emptySlots) {
                makeMove(board, pair, bot);
                int score = minimax(board, depth + 1, player);

                tempmax = Math.max(score, tempmax);
                board[pair[0]][pair[1]] = '-';
            }
            return tempmax;
        } else {
            for (int[] pair : emptySlots) {
                makeMove(board, pair, player);
                int score = minimax(board, depth + 1, bot);
                tempmin = Math.min(score, tempmin);
                board[pair[0]][pair[1]] = '-';


            }
            return tempmin;
        }

    }


}