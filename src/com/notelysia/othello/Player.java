package com.notelysia.othello;

import java.io.Serializable;

public abstract class Player implements Serializable {
    //Người chơi quân đen: 1
    //Người chơi quân trắng: 0
    private String name;
    private int type;
    private float winMatch; //Số trận chiến thắng của player
    private float totalMatch; //Tổng số trận mà player đã chơi
    private double rate; //tỷ lệ chiến thắng

    public Player(int type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getWinMatch() {
        return this.winMatch;
    }

    public void setWinMatch(float winMatch) {
        this.winMatch = winMatch;
    }

    public float getTotalMatch() {
        return this.totalMatch;
    }

    public void setTotalMatch(float totalMatch) {
        this.totalMatch = totalMatch;
    }

    public double getRate() {
        return this.rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public abstract String getNextPosition(int[][] boardArray);

}
