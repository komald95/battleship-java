package org.scrum.psd.battleship.controller.dto;

import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private static final int BOARD_SIZE = 8;
    private static char[][] userBoard = new char[BOARD_SIZE][BOARD_SIZE];
    private static char[][] enemyBoard = new char[BOARD_SIZE][BOARD_SIZE];

    public static void showBoard(List<Ship> fleets) {
        initializeBoard(userBoard);
        initializeBoard(enemyBoard);

        // Assuming 'myFleet' is populated and available
        for (Ship ship : fleets) {
            placeShipOnBoard(userBoard, ship);
        }

        // Similarly, you would populate enemyBoard with the enemy's ships

//        System.out.println("User Board:");
//        printBoard(userBoard);
//
//        System.out.println("\nEnemy Board:");
//        printBoard(enemyBoard);
    }

    // Initialize the board with empty spaces
    private static void initializeBoard(char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Place ship on the board
    private static void placeShipOnBoard(char[][] board, Ship ship) {
        for (Position position : ship.getPositions()) {
            int row = position.toString().charAt(1) - '1';
            int col = position.toString().charAt(0) - 'A';
            board[row][col] = 'S';
        }
    }

    public static void printBoard(char[][] board, List<Ship> fleet) {
        // Initialize the board with empty water
        for (int i = 0; i < BOARD_SIZE; i++) {
            Arrays.fill(board[i], '~'); // Use '~' to represent water
        }

        // Mark ship positions on the board
        for (Ship ship : fleet) {
            for (Position pos : ship.getPositions()) {
                int row = pos.getRow() - 1; // Convert 1-based row to 0-based index
                int col = pos.getColumn().ordinal(); // Convert column letter to index

                // Check for valid indices
                if (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) {
                    board[row][col] = ship.getName().charAt(0); // Use the first letter of the ship name
                } else {
                    System.out.printf("Invalid position: %s, row: %d, col: %d%n", pos, row, col);
                }
            }
        }

        // Print the board
        System.out.print("  ");
        for (char c = 'A'; c < 'A' + BOARD_SIZE; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}