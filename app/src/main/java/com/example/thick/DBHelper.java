package com.example.thick;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EmployeeDB";
    private static final int DATABASE_VERSION = 1;

    // Table NhanVien
    public static final String TABLE_NHANVIEN = "NhanVien";
    public static final String COLUMN_MANV = "MaNhanVien";
    public static final String COLUMN_MAPB = "MaPhongBan";
    public static final String COLUMN_TENNV = "TenNhanVien";
    public static final String COLUMN_TUOI = "Tuoi";

    // Table PhongBan
    public static final String TABLE_PHONGBAN = "PhongBan";
    public static final String COLUMN_MAPB_PB = "MaPhongBan";
    public static final String COLUMN_TENPB = "TenPhongBan";
    public static final String COLUMN_SOPB = "SoPhongBan";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table PhongBan
        String createPhongBanTable = "CREATE TABLE " + TABLE_PHONGBAN + " (" +
                COLUMN_MAPB_PB + " INTEGER PRIMARY KEY, " +
                COLUMN_TENPB + " TEXT, " +
                COLUMN_SOPB + " INTEGER)";
        db.execSQL(createPhongBanTable);

        // Create table NhanVien
        String createNhanVienTable = "CREATE TABLE " + TABLE_NHANVIEN + " (" +
                COLUMN_MANV + " INTEGER PRIMARY KEY, " +
                COLUMN_MAPB + " INTEGER, " +
                COLUMN_TENNV + " TEXT, " +
                COLUMN_TUOI + " INTEGER)";
        db.execSQL(createNhanVienTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NHANVIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHONGBAN);
        onCreate(db);
    }
}
