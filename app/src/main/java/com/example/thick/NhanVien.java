package com.example.thick;

public class NhanVien {
    private int maNhanVien;
    private int maPhongBan;
    private String tenNhanVien;
    private int tuoi;

    public NhanVien(int maNhanVien, int maPhongBan, String tenNhanVien, int tuoi) {
        this.maNhanVien = maNhanVien;
        this.maPhongBan = maPhongBan;
        this.tenNhanVien = tenNhanVien;
        this.tuoi = tuoi;
    }

    // Getters và Setters
    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public int getMaPhongBan() {
        return maPhongBan;
    }

    public void setMaPhongBan(int maPhongBan) {
        this.maPhongBan = maPhongBan;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }
}

