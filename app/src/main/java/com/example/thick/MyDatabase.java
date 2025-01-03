package com.example.thick;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase {
    private DBHelper dbHelper;

    public MyDatabase(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insertPhongBan(PhongBan phongBan) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_MAPB_PB, phongBan.getMaPhongBan());
        values.put(DBHelper.COLUMN_TENPB, phongBan.getTenPhongBan());
        values.put(DBHelper.COLUMN_SOPB, phongBan.getSoPhongBan());
        db.insert(DBHelper.TABLE_PHONGBAN, null, values);
        db.close();
    }

    public void insertNhanVien(NhanVien nhanVien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_MANV, nhanVien.getMaNhanVien());
        values.put(DBHelper.COLUMN_MAPB, nhanVien.getMaPhongBan());
        values.put(DBHelper.COLUMN_TENNV, nhanVien.getTenNhanVien());
        values.put(DBHelper.COLUMN_TUOI, nhanVien.getTuoi());
        db.insert(DBHelper.TABLE_NHANVIEN, null, values);
        db.close();
    }

    public List<NhanVien> getNhanVienByPhongBan(int maPhongBan) {
        List<NhanVien> nhanViens = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NHANVIEN, null, DBHelper.COLUMN_MAPB + " = ?",
                new String[]{String.valueOf(maPhongBan)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int maNV = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MANV));
                String tenNV = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TENNV));
                int tuoi = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TUOI));
                nhanViens.add(new NhanVien(maNV, maPhongBan, tenNV, tuoi));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return nhanViens;
    }
    public List<PhongBan> getPhongBanList() {
        List<PhongBan> phongBanList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_PHONGBAN, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int maPB = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MAPB_PB));
                String tenPB = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TENPB));
                int soPB = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SOPB));
                phongBanList.add(new PhongBan(maPB, tenPB, soPB));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return phongBanList;
    }

    public void deleteNhanVien(int maNhanVien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.delete(DBHelper.TABLE_NHANVIEN, DBHelper.COLUMN_MANV + " = ?",
                new String[]{String.valueOf(maNhanVien)});
        db.close();

        if (rowsAffected == 0) {
            throw new RuntimeException("Không tìm thấy nhân viên với mã: " + maNhanVien);
        }
    }

}

