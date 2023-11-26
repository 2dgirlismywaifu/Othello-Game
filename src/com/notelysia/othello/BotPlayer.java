package com.notelysia.othello;

import java.util.ArrayList;
import java.util.Random;

public class BotPlayer extends Player {

    public BotPlayer(int type) {
        super(type);
    }

    @Override
    public String getNextPosition(int[][] boardArray) {
        int[] bestPos = this.heuristic(boardArray);
        System.out.println("Nước đi: " + (char) (bestPos[1] + 94) + (8 - bestPos[0]));
        return bestPos[0] + " " + bestPos[1];
    }

    //Phương pháp này sử dụng một hàm đánh giá đơn giản để đếm số quân và số nước đi có thể có của mỗi người chơi.
    //Bot sẽ được thiết lập là quân đen hay quân trắng là tuỳ thuộc vào người dùng
    public int[] heuristic(int[][] boardArray) {
        // Tạo danh sách chứa tất cả các vị trị đặt quân cờ tốt nhất
        ArrayList<int[]> bestPosList = new ArrayList<>();
        int bestScore = Integer.MIN_VALUE;

        // Lặp qua tất cả các vị trí trên bàn cờ
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                // Nếu đó là vị trí đặt trống, kiểm tra xem vị trí đó có hợp lệ cho bot không
                if (boardArray[i][j] == -1) {
                    // Tạo bản sao và đặt vào đó là các quân cờ tương ứng với bot hiện tại
                    int[][] newBoardArray = this.copyBoardArray(boardArray);
                    newBoardArray[i][j] = this.getType();
                    // Lật quân cờ theo luật cờ othello
                    this.flip(newBoardArray, i, j, this.getType(), 1 - this.getType());
                    // Tính toán điểm trên bản sao khác của newBoardArray
                    int score = this.evaluate(newBoardArray, this.getType(), 1 - this.getType());
                    // Nếu điểm số tính toán được cao hơn điểm số bestScore, thêm nó vào danh sách
                    if (score > bestScore) {
                        bestPosList.clear();
                        bestPosList.add(new int[]{i, j});
                        bestScore = score;
                        // Trong trường hợp điểm số bằng nhau
                    } else if (score == bestScore) {
                        bestPosList.add(new int[]{i, j});
                    }
                }
            }
        }
        // Trả về một vị trí bất kỳ trong danh sách qua việc lấy ngẫu nhiên vị trí trong danh sách
        return bestPosList.get(new Random().nextInt(bestPosList.size()));
    }

    // Tạo ra một bản sao của BoardArray hiện tại
    public int[][] copyBoardArray(int[][] boardArray) {
        int[][] newBoardArray = new int[boardArray.length][boardArray[0].length];
        for (int i = 0; i < boardArray.length; i++) {
            System.arraycopy(boardArray[i], 0, newBoardArray[i], 0, boardArray[i].length);
        }
        return newBoardArray;
    }

    // Phương thức này sẽ lật các quân cờ khác màu trực theo 8 hướng
    public void flip(int[][] boardArray, int row, int col, int color, int oppColor) {
        // Thiết lập 8 hướng lật quân cờ
        int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        // Loop through each direction
        for (int[] dir : dirs) {
            // Khởi tạo biến theo dõi vị trí quân cờ và đếm số lượng quân cờ bị lật
            int x = row + dir[0];
            int y = col + dir[1];
            int count = 0;
            // Khi vị trí hiện tại nằm trong boardArray và có quân người chơi khác, di chuyển đến vị trí tiếp theo và tăng sốs đếm
            while (x >= 0 && x < boardArray.length && y >= 0 && y < boardArray[x].length && boardArray[x][y] == oppColor) {
                x = x + dir[0];
                y = y + dir[1];
                count++;
            }
            // Nếu vị trí hiện tại nằm trong boardArray và có cùng màu với quân cờ đã đặt, lật các quân cờ theo hướng đó
            if (x >= 0 && x < boardArray.length && y >= 0 && y < boardArray[x].length && boardArray[x][y] == color) {
                // Di chuyển lại về vị trí trước
                x = x - dir[0];
                y = y - dir[1];
                // Khi số đếm là giá trị dương, lật quân cờ và di chuyển về vị trí trước đó
                while (count > 0) {
                    boardArray[x][y] = color;
                    x = x - dir[0];
                    y = y - dir[1];
                    count--;
                }
            }
        }
    }

    // Phương thức này tính toán điểm có thể đạt được sau khi lật cờ và đặt quân cờ
    public int evaluate(int[][] boardArray, int color, int oppColor) {
        // Khởi tạo các biến để đếm số quân cờ và số nước đi có thể có cho mỗi màu quân cờ
        int pieceCount = 0;
        int moveCount = 0;
        int opppieceCount = 0;
        int oppMoveCount = 0;
        // Lặp qua tất cả các vị trí trên boardArray
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                // Nếu vị trí có màu của người chơi hiện tại, tăng số đếm quân cờ
                if (boardArray[i][j] == color) {
                    pieceCount++;
                }
                // Nếu vị trí có màu của người chơi khác, tăng số đếm quân cờ
                if (boardArray[i][j] == oppColor) {
                    opppieceCount++;
                }
                // Nếu ô trống, hãy kiểm tra xem đó có phải là nước đi hợp lệ không
                if (boardArray[i][j] == -1) {
                    // Tạo một bản sao của boardArray và đặt một phần màu quân cờ đã truyền vào qua biến color
                    int[][] newBoardArray = this.copyBoardArray(boardArray);
                    newBoardArray[i][j] = color;
                    // Lật quân cờ của đối thủ theo luật othello
                    this.flip(newBoardArray, i, j, color, oppColor);
                    // Nếu boardArray mới khác với boardArray ban đầu, tăng giá trị cho biến moveCount
                    if (this.isSameboardArray(newBoardArray, boardArray)) {
                        moveCount++;
                    }
                    // Tạo một bản sao khác của boardArray và đặt một mảnh màu của người chơi khác lên các vị trí
                    newBoardArray = this.copyBoardArray(boardArray);
                    newBoardArray[i][j] = oppColor;
                    // Lật các quân cờ theo quy tắc othello
                    this.flip(newBoardArray, i, j, oppColor, color);
                    // Nếu boardArray mới khác với boardArray ban đầu, tăng giá trị cho biến oppMoveCount
                    if (this.isSameboardArray(newBoardArray, boardArray)) {
                        oppMoveCount++;
                    }
                }
            }
        }
        // Trả về sự khác biệt giữa số quân cờ và số nước đi có thể có của người chơi hiện tại và người chơi khác
        return (pieceCount - opppieceCount) + (moveCount - oppMoveCount);
    }

    // Phương thức kiểm tra xem hai bảng Board có giống nhau không
    public boolean isSameboardArray(int[][] boardArray1, int[][] boardArray2) {
        // Loop through all the cells on the boardArrays
        for (int i = 0; i < boardArray1.length; i++) {
            for (int j = 0; j < boardArray1[i].length; j++) {
                // If the cells are different, return false
                if (boardArray1[i][j] != boardArray2[i][j]) {
                    return true;
                }
            }
        }
        // If all the cells are the same, return true
        return false;
    }
}
