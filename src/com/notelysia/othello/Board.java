package com.notelysia.othello;

import java.util.ArrayList;

public class Board {
    private final int[][] boardArray;
    private int nextPlayer; //1: đen, O: trắng

    public Board(int nextPlayer) {
        // Tạo mảng 2 chiều là bàn cờ và đặt tất cả các vị trí là -1
        this.boardArray = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.boardArray[i][j] = -1;
            }
        }
        // Khởi tạo 4 quân cờ đầu ở giữa bàn cờ
        this.boardArray[3][3] = 0;
        this.boardArray[3][4] = 1;
        this.boardArray[4][3] = 1;
        this.boardArray[4][4] = 0;

        this.nextPlayer = nextPlayer;
    }

    public int[][] getBoardArray() {
        return this.boardArray;
    }

    public int getNextPlayer() {
        return this.nextPlayer;
    }

    public void setNextPlayer(int nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    //Kiểm tra xem đây có là một nước đi hợp lệ
    private boolean isValidPosition(String position) {
        //Tách vị trị quân cờ bằng khoảng trắng và lưu trữ chúng tạm thời trong một mảng
        String[] temp = position.split("\\s");
        int row = Integer.parseInt(temp[0]);
        int col = Integer.parseInt(temp[1]);
        //Kiểm tra vị trí đặt cờ có nằm trong bảng không và vị trí đó có bằng -1 không (vị trí trống)
        if (row >= 0 && row < 8 && col >= 0 && col < 8 && this.boardArray[row][col] == -1) {
            return this.canFlip(this.nextPlayer, row, col, -1, -1)//thẳng từ vị trí quân cờ đi xuống sang trái
                    || this.canFlip(this.nextPlayer, row, col, -1, 0)// thẳng theo hướng lên
                    || this.canFlip(this.nextPlayer, row, col, -1, 1)//thẳng từ vị trí quân cờ đi xuống sang phải
                    || this.canFlip(this.nextPlayer, row, col, 0, -1) //thẳng bên trái
                    || this.canFlip(this.nextPlayer, row, col, 0, 1) //thẳng bên phải
                    || this.canFlip(this.nextPlayer, row, col, 1, -1)//thẳng từ vị trí quân cờ đi sang trái
                    || this.canFlip(this.nextPlayer, row, col, 1, 0)// thẳng theo hướng xuống
                    || this.canFlip(this.nextPlayer, row, col, 1, 1);//thẳng từ vị trí quân cờ đi sang phải
        } else {
            //Vị trí không hợp lệ
            return false;
        }
    }

    //Kiểm tra xem các quân cờ cạnh quân cờ vừa đặt có thể bị lật không
    private boolean canFlip(int temPlayer, int row, int col, int dx, int dy) {
        //Lấy giá trị ngược lại của giá trị nextPlayer
        int opposite = 1 - temPlayer;
        //di chuyển một bước theo cùng một hướng
        row = row + dx;
        col = col + dy;
        //Kiểm tra xem vị trí đó có nằm trong bàn cờ không và có quân cờ khác loại
        if (row >= 0 && row < 8 && col >= 0 && col < 8 && this.boardArray[row][col] == opposite) {
            //di chuyển thêm bước nữa theo cùng một hướng
            row = row + dx;
            col = col + dy;

            //Lặp cho đến khi vị trí đó nằm ngoài bàn cờ hoặc trống
            while (row >= 0 && row < 8 && col >= 0 && col < 8 && this.boardArray[row][col] != -1) {
                //Nếu vị trí đó có quân cờ cùng loại giá trị nextPlayer, trả về true
                if (this.boardArray[row][col] == temPlayer) {
                    return true;
                }
                //di chuyển thêm bước nữa theo cùng một hướng
                row = row + dx;
                col = col + dy;
            }
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////////////////
    //Đặt giá trị nước đi của người chơi trên bàn cờ
    public void setNextPosition(String position) {
        //Kiểm tra xem vị trí quân cờ mà người chơi tiếp theo
        // muốn đặt có hợp lệ không rồi mới đổi lượt người chơi
        if (this.isValidPosition(position)) {
            this.placePiece(position);
            if (!this.hasToPass(1 - this.nextPlayer)) {
                System.out.println("Người chơi tiếp theo không có nước đi hợp lệ");
            } else {
                this.setNextPlayer(1 - this.nextPlayer);
            }
        } else {
            System.out.println("Vị trí không hợp lệ, vui lòng thử lại");
        }
    }

    //Đặt quân cờ của người chơi trên bàn cờ và đồng thời lật cờ của đối thủ
    private void placePiece(String position) {
        //Tách vị trị quân cờ bằng khoảng trắng và lưu trữ chúng tạm thời trong một mảng
        String[] temp = position.split("\\s");
        int row = Integer.parseInt(temp[0]);
        int col = Integer.parseInt(temp[1]);
        //Kiểm tra vị trí đặt cờ có nằm trong bảng không và vị trí đó có bằng -1 không (vị trí trống)
        if (row >= 0 && row < 8 && col >= 0 && col < 8 && this.boardArray[row][col] == -1) {
            //Đặt quân cờ có giá trị bằng loại người chơi
            this.boardArray[row][col] = this.nextPlayer;
            //Lật các quân cờ có thể lật được ở tám hướng xung quanh vị trị đặt quân cờ
            this.flip(row, col, -1, -1); //thẳng từ vị trí quân cờ đi xuống sang trái
            this.flip(row, col, -1, 0); // thẳng theo hướng lên
            this.flip(row, col, -1, 1); //thẳng từ vị trí quân cờ đi xuống sang phải
            this.flip(row, col, 0, -1); //thẳng bên trái
            this.flip(row, col, 0, 1); //thẳng bên phải
            this.flip(row, col, 1, -1); //thẳng từ vị trí quân cờ đi sang trái
            this.flip(row, col, 1, 0); // thẳng theo hướng xuống
            this.flip(row, col, 1, 1); //thẳng từ vị trí quân cờ đi sang phải
        }
    }

    //Kiểm tra xem người chơi còn nước nào hợp lệ để đi không
    private boolean hasToPass(int tempNextPlayer) {
        // Lặp tới tất cả các vị trí trên board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // nếu vị trí trống
                if (this.boardArray[row][col] == -1) {
                    // Lặp qua tất cả các hướng xung quanh ô
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            // Nếu hướng đó không đạt tới giá trị 0
                            if (dx != 0 || dy != 0) {
                                if (this.canFlip(tempNextPlayer, row, col, dx, dy)) {
                                    // Trả về true tức người chơi vẫn còn nước đi
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        // trả về false, tức người chơi không còn nước đi hợp lệ
        return false;
    }

    //Lật quân cờ trên bàn cờ
    private void flip(int row, int col, int dx, int dy) {
        // Lấy giá trị đối ngược nextPlayer
        int opposite = 1 - this.nextPlayer;
        // Khởi tạo đối tượng đếm số quân cờ có thể lật
        int count = 0;
        // Di chuyển thêm một bước theo hướng di chuyển
        row = row + dx;
        col = col + dy;
        while (row >= 0 && row < 8 && col >= 0 && col < 8 && this.boardArray[row][col] == opposite) {
            // Tăng thêm một giá trị
            count++;
            // Di chuyển thêm một bước theo hướng di chuyển
            row = row + dx;
            col = col + dy;
        }
        // Kết thúc vòng lặp là quân cờ cùng màu với người chơi hiện tại
        if (row >= 0 && row < 8 && col >= 0 && col < 8 && this.boardArray[row][col] == this.nextPlayer) {
            //Lặp ngược lại từ điểm kết thúc tới bắt đầu
            for (int i = 0; i < count; i++) {
                // Di chuyển thêm một bước theo hướng di chuyển ngược lại
                row = row - dx;
                col -= dy;
                // Lật các quân cờ
                this.boardArray[row][col] = this.nextPlayer;
            }
        }
    }

    //Trò chơi kết thúc
    public boolean isGameOver() {
        if (!this.hasToPass(1 - this.nextPlayer) && !this.hasToPass(this.nextPlayer)) {
            //trò chơi kết thúc
            return true;
        } else {
            //Khai báo giá trị lưu các vị trí còn trống
            int emptyCount = 0;
            //Vòng lặp tìm các ô còn trống
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.boardArray[i][j] == -1) {
                        emptyCount++;
                    }
                }
            }
            //trò chơi kết thúc
            return emptyCount == 0;
        }
    }

    //Gợi ý cho người chơi nước đi hợp lệ
    public ArrayList<String> suggestNextPosition() {
        ArrayList<String> validPositions = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                // If the cell is empty
                if (this.boardArray[row][col] == -1) {
                    //Kiểm tra các vị trí trống trong bảng xem có đặt quân cờ được không
                    //sử dụng canFlip để kiểm tra xem có thể lật được không
                    if (this.canFlip(this.nextPlayer, row, col, -1, -1)//thẳng từ vị trí quân cờ đi xuống sang trái
                            || this.canFlip(this.nextPlayer, row, col, -1, 0)// thẳng theo hướng lên
                            || this.canFlip(this.nextPlayer, row, col, -1, 1)//thẳng từ vị trí quân cờ đi xuống sang phải
                            || this.canFlip(this.nextPlayer, row, col, 0, -1) //thẳng bên trái
                            || this.canFlip(this.nextPlayer, row, col, 0, 1) //thẳng bên phải
                            || this.canFlip(this.nextPlayer, row, col, 1, -1)//thẳng từ vị trí quân cờ đi sang trái
                            || this.canFlip(this.nextPlayer, row, col, 1, 0)// thẳng theo hướng xuống
                            || this.canFlip(this.nextPlayer, row, col, 1, 1)) {//thẳng từ vị trí quân cờ đi sang phải
                        // Thêm vị trí vào danh sách các vị trí hợp lệ với dạng như d3
                        validPositions.add((char) (col + 97) + "" + (8 - row));
                    }
                }
            }
        }
        return validPositions;
    }

    //In ra bảng bàn cờ
    public void printBoard() {
        int blackCount = 0;
        int whiteCount = 0;
        //In ra phần cột
        System.out.print("  ");
        for (int i = 0; i < 8; i++) {
            char letter = (char) ('a' + i);
            System.out.print(" ");
            System.out.print(letter + " ");
        }
        System.out.println();

        //In ra phần hàng
        for (int i = 0; i < 8; i++) {
            //Đảo chiều thành từ 8 về tới 1
            int number = 8 - i;
            System.out.print(number + " ");
            for (int j = 0; j < 8; j++) {
                // -1: trống, 1: đen, 0: trắng
                if (this.boardArray[i][j] == -1) {
                    System.out.print(" ");
                    System.out.print("* ");
                } else if (this.boardArray[i][j] == 0) {
                    whiteCount++;
                    System.out.print(" ");
                    System.out.print("0 ");
                } else if (this.boardArray[i][j] == 1) {
                    blackCount++;
                    System.out.print(" ");
                    System.out.print("1 ");
                }
            }
            System.out.println();
        }
        System.out.println("Số quân cờ trắng: " + whiteCount);
        System.out.println("Số quân cờ đen: " + blackCount);
    }
}


