package com.notelysia.othello;

public class Test {

    // A method that returns the next position for the bot player as a string
    // The bot is assumed to be the black player
    // The boardArray is a 2D array of ints, where 1 represents black, 2 represents white, and 0 represents empty
    public String getNextPosition(int[][] boardArray) {
        // Convert the boardArray to a char array for easier manipulation
        // Call the heuristic method to get the best position as an int array
        int[] bestPos = this.heuristic(boardArray);
        return bestPos[0] + " " + bestPos[1];
    }

    // A heuristic method that returns the best position for the bot to place a piece
    // The bot is assumed to be the black player
    // The board is a 2D array of chars, where 'B' represents black, 'W' represents white, and '.' represents empty
    // The method uses a simple evaluation function that counts the number of pieces and the number of possible moves for each player
    public int[] heuristic(int[][] board) {
        // Initialize the best position and score
        int[] bestPos = new int[2];
        int bestScore = Integer.MIN_VALUE;

        // Loop through all the empty cells on the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // If the cell is empty, check if it is a valid move for the bot
                if (board[i][j] == -1) {
                    // Make a copy of the board and place a black piece on the cell
                    int[][] newBoard = this.copyBoard(board);
                    newBoard[i][j] = 1;
                    // Flip the opponent's pieces according to the othello rules
                    this.flip(newBoard, i, j, 1, 0);
                    // Evaluate the score of the new board
                    int score = this.evaluate(newBoard, 1, 0);
                    // If the score is better than the current best score, update the best position and score
                    if (score > bestScore) {
                        bestPos[0] = i;
                        bestPos[1] = j;
                        bestScore = score;
                    }
                }
            }
        }
        // Return the best position
        return bestPos;
    }

    // A method that makes a copy of a board
    public int[][] copyBoard(int[][] board) {
        int[][] newBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board[i].length);
        }
        return newBoard;
    }

    // A method that flips the opponent's pieces according to the othello rules
    // The parameters are the board, the row and column of the placed piece,
    // the color of the placed piece, and the color of the opponent's piece
    public void flip(int[][] board, int row, int col, int color, int oppColor) {
        // Define the directions to check for flipping
        int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        // Loop through each direction
        for (int[] dir : dirs) {
            // Initialize the variables to track the current position and the number of pieces to flip
            int x = row + dir[0];
            int y = col + dir[1];
            int count = 0;
            // While the current position is within the board and has the opponent's piece, move to the next position and increment the count
            while (x >= 0 && x < board.length && y >= 0 && y < board[x].length && board[x][y] == oppColor) {
                x += dir[0];
                y += dir[1];
                count++;
            }
            // If the current position is within the board and has the same color as the placed piece, flip the pieces along the direction
            if (x >= 0 && x < board.length && y >= 0 && y < board[x].length && board[x][y] == color) {
                // Move back to the previous position
                x -= dir[0];
                y -= dir[1];
                // While the count is positive, flip the piece and move to the previous position
                while (count > 0) {
                    board[x][y] = color;
                    x -= dir[0];
                    y -= dir[1];
                    count--;
                }
            }
        }
    }

    // A method that evaluates the score of a board for a given color
    // The score is the difference between the number of pieces and the number of
    // possible moves for the given color and the opponent's color
    public int evaluate(int[][] board, int color, int oppColor) {
        // Initialize the variables to count the number of pieces and the number of possible moves for each color
        int pieceCount = 0;
        int moveCount = 0;
        int opppieceCount = 0;
        int oppMoveCount = 0;
        // Loop through all the cells on the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // If the cell has the given color, increment the piece count
                if (board[i][j] == color) {
                    pieceCount++;
                }
                // If the cell has the opponent's color, increment the opponent's piece count
                if (board[i][j] == oppColor) {
                    opppieceCount++;
                }
                // If the cell is empty, check if it is a valid move for each color
                if (board[i][j] == -1) {
                    // Make a copy of the board and place a piece of the given color on the cell
                    int[][] newBoard = this.copyBoard(board);
                    newBoard[i][j] = color;
                    // Flip the opponent's pieces according to the othello rules
                    this.flip(newBoard, i, j, color, oppColor);
                    // If the new board is different from the original board, increment the move count
                    if (this.isSameBoard(newBoard, board)) {
                        moveCount++;
                    }
                    // Make another copy of the board and place a piece of the opponent's color on the cell
                    newBoard = this.copyBoard(board);
                    newBoard[i][j] = oppColor;
                    // Flip the pieces according to the othello rules
                    this.flip(newBoard, i, j, oppColor, color);
                    // If the new board is different from the original board, increment the opponent's move count
                    if (this.isSameBoard(newBoard, board)) {
                        oppMoveCount++;
                    }
                }
            }
        }
        // Return the difference between the number of pieces and the number of possible moves for the given color and the opponent's color
        return (pieceCount - opppieceCount) + (moveCount - oppMoveCount);
    }

    // A method that checks if two boards are the same
    public boolean isSameBoard(int[][] board1, int[][] board2) {
        // Loop through all the cells on the boards
        for (int i = 0; i < board1.length; i++) {
            for (int j = 0; j < board1[i].length; j++) {
                // If the cells are different, return false
                if (board1[i][j] != board2[i][j]) {
                    return true;
                }
            }
        }
        // If all the cells are the same, return true
        return false;
    }

    public void run() {

        int[][] boardArray = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardArray[i][j] = -1;
            }
        }

        // Set the initial four pieces in the center of the board
        boardArray[3][3] = 0;
        boardArray[3][4] = 1;
        boardArray[4][3] = 1;
        boardArray[4][4] = 0;
        System.out.println(this.getNextPosition(boardArray));
    }
}
