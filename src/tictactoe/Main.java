package tictactoe;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] field = new char[3][3];
        char player = 'X';

        initializeField(field);
        while (true) {
            drawField(field);

            switch(player) {
                case 'X':
                    playerInput(scanner, field, player);
                    player = 'O';
                    break;
                case 'O':
                    playerInput(scanner, field, player);
                    player = 'X';
                    break;
                default:
                    System.out.println("switch - case error");
            }

            if (!gameState(field).equals("Game not finished")) {
                drawField(field);
                System.out.println(gameState(field));
                break;
            }
        }
    }


    public static void initializeField(char[][] field) {
        //initializing 2D array with empty spaces (" ")
        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[0].length; col++) {
                field[row][col] = ' ';
            }
        }
    }

    public static void drawField(char[][] field) {
        //drawing bounds and 2D array
        System.out.println("---------");

        for (int row = 0; row < field.length; row++) {
            System.out.print("| ");
            for (int col = 0; col < field[0].length; col++) {
                System.out.print(field[row][col] + " ");
            }

            System.out.print("|\n");
        }

        System.out.println("---------");
    }

    public static String gameState(char[][] field) {
        int freeSpaces = 0;

        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[0].length; col++) {
                if (field[row][col] == ' ') {
                    freeSpaces++;
                }
            }
        }

        if (doesWin('X', field)) {
            return "X wins";
        }

        if (doesWin('O', field)) {
            return "O wins";
        }

        if (freeSpaces == 0) {
            return "Draw";
        }

        return "Game not finished";
    }

    public static boolean doesWin(char player, char[][] field) {
        int sum = 0;
        int targetSum;

        if (player == 'X') {
            targetSum = 'X' + 'X' + 'X';
        } else {
            targetSum = 'O' + 'O' + 'O';
        }

        //check for player win in every row
        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[0].length; col++) {
                sum += field[row][col];
            }

            if (sum == targetSum) return true;
            sum = 0;
        }

        //check for player win in every column
        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[0].length; col++) {
                sum += field[col][row];
            }

            if (sum == targetSum) return true;
            sum = 0;
        }

        //check for player win in diagonal
        for (int i = 0; i < field.length; i++) {
            sum += field[i][i];
        }

        if (sum == targetSum) return true;
        sum = 0;

        //check for player win in secondary diagonal and return false if player does not win
        for (int i = 0; i < field.length; i++) {
            sum += field[i][field.length - 1 - i];
        }

        return sum == targetSum;
    }

    public static void playerInput(Scanner scanner, char[][] field, char player) {
        while (true) {
            int x, y;

            //we make sure that user input is in the correct format
            while (true) {
                System.out.printf("Player %c's turn. Enter the coordinates: > ", player);
                String input = scanner.nextLine();
                Pattern pattern = Pattern.compile("[1-3]\\s[1-3]");
                Matcher matcher = pattern.matcher(input);
                if (matcher.matches()) {
                    String[] parts = input.split(" ");
                    x = Integer.parseInt(parts[0]);
                    y = Integer.parseInt(parts[1]);
                    break;
                } else {
                    System.out.println("Wrong coordinates! Enter two coordinates [1-3] separated by space. Try again:");
                }
            }

            //use 1-3 valued (X,Y) coordinates for input and convert the coordinates to correct ArrayList indexes
            int arrayRow = 3 - y;
            int arrayCol = x - 1;
            if (field[arrayRow][arrayCol] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                field[arrayRow][arrayCol] = player;
                break;
            }
        }
    }
}
