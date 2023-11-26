package com.notelysia.othello;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public Scanner scanner = new Scanner(System.in);

    public HumanPlayer(int type) {
        super(type);
    }

    @Override
    public String getNextPosition(int[][] boardArray) {
        int row;
        int col;
        do {
            System.out.print("Nhập nước đi (VD: d6): ");
            String input = this.scanner.nextLine();
            if (input.length() == 2) { // The input should have two characters
                // Chuyển ký tự đầu về in hoa theo bảng mã ASCII
                int letter = Character.toUpperCase(input.charAt(0));
                // Chuyển ký tự thứ hai về dạng số riêng
                int number = Character.digit(input.charAt(1), 10);
                // Kiểm tra xem ký tự đầu có nằm trong khoảng từ A đến H
                //và ký tự số có nằm trong khoảng từ 1 đến 8
                if (letter >= 65 && letter <= 72 && number >= 1 && number <= 8) {
                    row = 8 - number;
                    col = letter - 65;
                    // Kiểm tra xem vị trí này có trống không
                    if (boardArray[row][col] == -1) {
                        // trả về vị trí theo thứ tự hàng cột
                        return row + " " + col;
                    } else {
                        System.out.println("Vị trí này không trống, vui lòng thử lại!");
                    }
                } else {
                    System.out.println("Vị trí này nằm ngoài bảng, vui lòng thử lại!");
                }
            } else {
                System.out.println("Vị trí này không hợp lệ, vui lòng thử lại!");
            }
        } while (true);
    }
}