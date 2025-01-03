package com.example.thick;

public class PhongBan {
    private int maPhongBan;
    private String tenPhongBan;
    private int soPhongBan;

    public PhongBan(int maPhongBan, String tenPhongBan, int soPhongBan) {
        this.maPhongBan = maPhongBan;
        this.tenPhongBan = tenPhongBan;
        this.soPhongBan = soPhongBan;
    }

    // Getters và Setters
    public int getMaPhongBan() {
        return maPhongBan;
    }

    public void setMaPhongBan(int maPhongBan) {
        this.maPhongBan = maPhongBan;
    }

    public String getTenPhongBan() {
        return tenPhongBan;
    }

    public void setTenPhongBan(String tenPhongBan) {
        this.tenPhongBan = tenPhongBan;
    }

    public int getSoPhongBan() {
        return soPhongBan;
    }

    public void setSoPhongBan(int soPhongBan) {
        this.soPhongBan = soPhongBan;
    }
}

