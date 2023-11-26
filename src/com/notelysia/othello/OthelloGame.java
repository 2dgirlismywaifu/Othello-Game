package com.notelysia.othello;

import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

public class OthelloGame {
    public Board board;
    public HallOfFrame hallOfFrame;
    public Player player1;
    public Player player2;

    public OthelloGame() {
        this.hallOfFrame = new HallOfFrame();
    }

    public static void main(String[] args) {
        OthelloGame othelloGame = new OthelloGame();
        othelloGame.firstMenu();
    }

    public void loadMode(int choice) {
        Scanner scanner = new Scanner(System.in);
        String namePlayer1, namePlayer2;
        int typePlayer1, typePlayer2;
        // Check the user's choice and create the corresponding player objects
        switch (choice) {
            case 1: // Human vs Human
                System.out.print("Nhập tên người chơi thứ nhất: ");
                namePlayer1 = scanner.nextLine();
                System.out.print("Quân trắng (0)/Quân đen (1): ");
                typePlayer1 = scanner.nextInt();
                scanner.nextLine();
                this.player1 = new HumanPlayer(typePlayer1);
                this.player1.setName(namePlayer1);
                this.player1.setWinMatch(0);
                this.player1.setTotalMatch(0);
                this.player1.setRate(0);
                System.out.print("Nhập tên người chơi thứ hai: ");
                namePlayer2 = scanner.nextLine();
                if (this.player1.getType() == 1) {
                    typePlayer2 = 0;
                    this.player2 = new HumanPlayer(typePlayer2);
                    this.player2.setName(namePlayer2);
                    this.player2.setWinMatch(0);
                    this.player2.setTotalMatch(0);
                    this.player2.setRate(0);
                    this.run(this.player2, this.player1);
                } else {
                    typePlayer2 = 1;
                    this.player2 = new HumanPlayer(typePlayer2);
                    this.player2.setName(namePlayer2);
                    this.player2.setWinMatch(0);
                    this.player2.setTotalMatch(0);
                    this.player2.setRate(0);
                    this.run(this.player1, this.player2);
                }
                break;
            case 2: // Human vs Bot
                System.out.print("Nhập tên người chơi: ");
                namePlayer1 = scanner.nextLine();
                System.out.print("Quân trắng (0)/Quân đen (1): ");
                typePlayer1 = scanner.nextInt();
                scanner.nextLine();
                this.player1 = new HumanPlayer(typePlayer1);
                this.player1.setName(namePlayer1);
                this.player1.setWinMatch(0);
                this.player1.setTotalMatch(0);
                this.player1.setRate(0);
                System.out.print("Nhập tên bot: ");
                namePlayer2 = scanner.nextLine();
                if (this.player1.getType() == 1) {
                    typePlayer2 = 0;
                    this.player2 = new LongNTBotPlayer(typePlayer2);
                    this.player2.setName(namePlayer2);
                    this.player2.setWinMatch(0);
                    this.player2.setTotalMatch(0);
                    this.player2.setRate(0);
                    this.run(this.player2, this.player1);
                } else {
                    typePlayer2 = 1;
                    this.player2 = new LongNTBotPlayer(typePlayer2);
                    this.player2.setName(namePlayer2);
                    this.player2.setWinMatch(0);
                    this.player2.setTotalMatch(0);
                    this.player2.setRate(0);
                    this.run(this.player1, this.player2);
                }
                break;
            case 3: // Bot với Bot
                System.out.print("Nhập tên bot thứ nhất: ");
                namePlayer1 = scanner.nextLine();
                System.out.print("Quân trắng (0)/Quân đen (1): ");
                typePlayer1 = scanner.nextInt();
                scanner.nextLine();
                this.player1 = new LongNTBotPlayer(typePlayer1);
                this.player1.setName(namePlayer1);
                this.player1.setWinMatch(0);
                this.player1.setTotalMatch(0);
                this.player1.setRate(0);
                System.out.print("Nhập tên bot thứ hai: ");
                namePlayer2 = scanner.nextLine();
                if (this.player1.getType() == 1) {
                    typePlayer2 = 0;
                    this.player2 = new LongNTBotPlayer(typePlayer2);
                    this.player2.setName(namePlayer2);
                    this.player2.setWinMatch(0);
                    this.player2.setTotalMatch(0);
                    this.player2.setRate(0);
                    this.run(this.player2, this.player1);
                } else {
                    typePlayer2 = 1;
                    this.player2 = new LongNTBotPlayer(typePlayer2);
                    this.player2.setName(namePlayer2);
                    this.player2.setWinMatch(0);
                    this.player2.setTotalMatch(0);
                    this.player2.setRate(0);
                    this.run(this.player1, this.player2);
                }
                break;
            default: // Lựa chọn không hợp lệ
                System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại!");
                this.loadMode(1);
        }
    }

    private void firstMenu() {
        String menu = "Trò chơi Othello\n" +
                "1. Bắt đầu trò chơi mới\n" +
                "2. Bảng xếp hạng\n" +
                "3. Thoát trò chơi\n" +
                "Nhập lựa chọn: ";
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.print(menu);
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice < 1 || choice > 3) {
                System.out.println("Lựa chọn không hợp lệ! Vui lòng thử lại.");
            }
        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                // Lựa chọn chế độ chơi
                String modes = "Chế độ chơi\n" +
                        "1. Người với người\n" +
                        "2. Người với Bot\n" +
                        "3. Bot với Bot (thử nghiệm)\n" +
                        "Nhập lựa chọn: ";
                System.out.print(modes);
                int choiceMode = scanner.nextInt();
                while (choiceMode < 1 || choiceMode > 3) {
                    System.out.print("Lựa chọn không hợp lệ! Vui lòng thử lại.");
                    choiceMode = scanner.nextInt();
                }
                this.loadMode(choiceMode);
                break;
            case 2:
                this.hallOfFrame.showTopPlayer();
                this.firstMenu();
                break;
            case 3:
                this.hallOfFrame.saveToFile();
                System.exit(0);
                break;
        }
    }

    //In ra các nước đi gợi ý cho HumanPlayer
    private void printSuggestPosition() {
        System.out.print("Vị trí hợp lệ: ");
        for (String string : this.board.suggestNextPosition()) {
            System.out.print(string + " ");
        }
        System.out.println();
    }

    //Lưu thông tin người chơi
    private void savePlayer(Player white, Player black, int blackCount, int whiteCount) {
        Iterator<Player> playerList = this.hallOfFrame.topPlayerList.iterator();
        boolean found = false;
        while (playerList.hasNext()) {
            Player playerFounded = playerList.next();
            if (Objects.equals(playerFounded.getName(), black.getName())) {
                if (blackCount > whiteCount) {
                    playerFounded.setWinMatch(playerFounded.getWinMatch() + 1);
                    playerFounded.setTotalMatch(playerFounded.getTotalMatch() + 1);
                    playerFounded.setRate(this.calculateWinRate(playerFounded.getWinMatch(), playerFounded.getTotalMatch()));
                } else {
                    playerFounded.setTotalMatch(playerFounded.getTotalMatch() + 1);
                    playerFounded.setRate(this.calculateWinRate(playerFounded.getWinMatch(), playerFounded.getTotalMatch()));
                }
                found = true;
                //In ra tỷ lệ chiến thắng của người chơi
                System.out.println("Tỉ lệ chiến thắng của " + black.getName() + ": " + String.format("%.2f",playerFounded.getRate()) + "%");
            }
            if (Objects.equals(playerFounded.getName(), white.getName())) {
                if (whiteCount > blackCount) {
                    playerFounded.setWinMatch(playerFounded.getWinMatch() + 1);
                    playerFounded.setTotalMatch(playerFounded.getTotalMatch() + 1);
                    playerFounded.setRate(this.calculateWinRate(playerFounded.getWinMatch(), playerFounded.getTotalMatch()));
                } else {
                    playerFounded.setTotalMatch(playerFounded.getTotalMatch() + 1);
                    playerFounded.setRate(this.calculateWinRate(playerFounded.getWinMatch(), playerFounded.getTotalMatch()));
                }
                found = true;
                //In ra tỷ lệ chiến thắng của người chơi
                System.out.println("Tỉ lệ chiến thắng của " + white.getName() + ": " + String.format("%.2f",playerFounded.getRate()) + "%");
            }
        }
        if (!found) {
            if (blackCount > whiteCount) {
                black.setWinMatch(black.getWinMatch() + 1);
                black.setTotalMatch(black.getTotalMatch() + 1);
                black.setRate(this.calculateWinRate(black.getWinMatch(), black.getTotalMatch()));

                white.setTotalMatch(white.getTotalMatch() + 1);
                white.setRate(this.calculateWinRate(white.getWinMatch(), white.getTotalMatch()));
            } else if (whiteCount > blackCount) {
                white.setWinMatch(white.getWinMatch() + 1);
                white.setTotalMatch(white.getTotalMatch() + 1);
                white.setRate(this.calculateWinRate(white.getWinMatch(), white.getTotalMatch()));

                black.setTotalMatch(black.getTotalMatch() + 1);
                black.setRate(this.calculateWinRate(black.getWinMatch(), black.getTotalMatch()));
            } else {
                black.setTotalMatch(black.getTotalMatch() + 1);
                black.setRate(this.calculateWinRate(black.getWinMatch(), black.getTotalMatch()));

                white.setTotalMatch(white.getTotalMatch() + 1);
                white.setRate(this.calculateWinRate(white.getWinMatch(), white.getTotalMatch()));
            }
            this.hallOfFrame.addPlayer(black);
            System.out.println("Tỉ lệ chiến thắng của " + black.getName() + ": " + String.format("%.2f",black.getRate()) + "%");
            this.hallOfFrame.addPlayer(white);
            System.out.println("Tỉ lệ chiến thắng của " + white.getName() + ": " + String.format("%.2f",white.getRate()) + "%");
        }
    }

    //Tính tỷ lệ chiến thắng
    private float calculateWinRate(float winMatch, float totalMatch) {
        return  (winMatch / totalMatch) * 100;
    }

    private void run(Player white, Player black) {
        Scanner scanner = new Scanner(System.in);
        String position;
        this.board = new Board(black.getType());
        boolean gameOver = false;
        // Dùng vòng lặp để duy trì lượt chơi
        while (!gameOver) {
            this.board.printBoard();
            int nextPlayer = this.board.getNextPlayer();
            if (nextPlayer == black.getType()) {
                if (black instanceof HumanPlayer) { //Nếu player là HumanPlayer
                    this.printSuggestPosition();
                }
                System.out.println("Lượt quân đen (" + black.getType() + "): " + black.getName());
                position = black.getNextPosition(this.board.getBoardArray());
            } else {
                if (white instanceof HumanPlayer) { //Nếu player là HumanPlayer
                    this.printSuggestPosition();
                }
                System.out.println("Lượt quân trắng (" + white.getType() + "): " + white.getName());
                position = white.getNextPosition(this.board.getBoardArray());
            }
            this.board.setNextPosition(position);
            // Kiểm tra nếu trò chơi kết thúc
            if (this.board.isGameOver()) {
                //đặt trạng thái gameOver là true để kết thúc vòng lặp
                gameOver = true;
                System.out.println("Trò chơi kết thúc!");
                // Đếm số lượng quân trắng và quân đen trên bàn cờ
                int blackCount = 0;
                int whiteCount = 0;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (this.board.getBoardArray()[i][j] == 0) {
                            whiteCount++;
                        } else if (this.board.getBoardArray()[i][j] == 1) {
                            blackCount++;
                        }
                    }
                }
                if (blackCount > whiteCount) {
                    System.out.println("Người chơi " + black.getName() + " chiến thắng!");
                } else if (whiteCount > blackCount) {
                    System.out.println("Người chơi " + white.getName() + " chiến thắng!");
                } else {
                    System.out.println("Người chơi " + black.getName() + " hoà " + white.getName());
                }
                this.savePlayer(white, black, blackCount, whiteCount);
            }
        }
        //Hỏi người chơi có muốn bắt đầu ván khác không
        System.out.println("Bạn có muốn chơi tiếp ván tiếp theo không?");
        System.out.println("1. Có"); //Tạo ván mới
        System.out.println("2. Không");//Quay về menu chính
        System.out.print("Lựa chọn: ");
        int decision = scanner.nextInt();
        while (decision < 1 || decision > 2) {
            System.out.println("Lựa chọn không hợp lệ! Vui lòng thử lại.");
            decision = scanner.nextInt();
        }
        switch (decision) {
            case 1:
                this.run(this.player1, this.player2);
                break;
            case 2:
                this.firstMenu();
                break;
        }
    }
}
