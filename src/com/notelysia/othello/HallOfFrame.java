package com.notelysia.othello;

import java.io.*;
import java.util.ArrayList;

public class HallOfFrame {
    //Danh sách sẽ lọc theo tỷ lệ chiến thắng
    public ArrayList<Player> topPlayerList;

    public HallOfFrame() {
        this.topPlayerList = new ArrayList<>();
        try {
            File file = new File("topPlayerList.data");
            if (file.exists() && !file.isDirectory()) {
                this.loadFromFile();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Đã xảy ra ngoại lệ: " + e.getMessage());
        }
    }

    //Thêm một người chơi vào danh sách thắng cao nhất
    public void addPlayer(Player player) {
        this.topPlayerList.add(player);
    }

    //Hiển thị bảng xếp hạng theo tỷ lệ chiến thắng
    public void showTopPlayer() {
        System.out.println("Bảng xếp hạng:");
        //Sắp xếp danh sách theo tỷ lệ chiến thắng
        if (this.topPlayerList.isEmpty()) {
            System.out.println("Danh sách trống! Hãy bắt đầu một trò chơi đi");
        } else {
            this.topPlayerList.sort((player1, player2) -> {
                return (int) (player2.getRate() - player1.getRate());
            });
            for (Player player : this.topPlayerList) {
                System.out.println("Người chơi " + player.getName() + " - Tỉ lệ thắng: " + String.format("%.2f",player.getRate()) + "%");
            }
        }
    }

    //Chuỗi hoá (serializable) danh sách người chơi và lưu vào file
    public void saveToFile() {
        //Khởi tạo đối tượng ObjectOutputStream
        ObjectOutputStream objectOutputStream;
        try {
            // Tạo một luồng xuất đối tượng và ghi đè file nếu file đã tồn tại
            objectOutputStream = new ObjectOutputStream(new FileOutputStream("topPlayerList.data"));
            // Ghi đối tượng hiện tại vào file
            for (Player player : this.topPlayerList) {
                objectOutputStream.writeObject(player);
            }
            // Đóng luồng
            objectOutputStream.close();
            System.out.println("Chuỗi hoá thành công: " + "topPlayerList.data");
        } catch (IOException ex) {
            System.out.println("Đã xảy ra ngoại lệ: " + ex.getMessage());
        }
    }

    public void loadFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream;
        // Tạo một luồng nhập đối tượng
        objectInputStream = new ObjectInputStream(new FileInputStream("topPlayerList.data"));
        this.topPlayerList.clear();
        // Đọc đối tượng từ file và ghi từng object lai vào danh sách
        while (true) {
            try {
                Player player = (Player) objectInputStream.readObject();
                this.topPlayerList.add(player);
            } catch (EOFException ex) {
                break;
            }
        }
        // Đóng luồng
        objectInputStream.close();
        System.out.println("Tải danh sách người chơi thắng cao từ topPlayerList.data thành công!");
    }
}
