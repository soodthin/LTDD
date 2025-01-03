package com.example.thick;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private TextView tvTenPhongBan, tvSoLuongMonHoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        // Ánh xạ view
        tvTenPhongBan = findViewById(R.id.tvTenPhongBan);
        tvSoLuongMonHoc = findViewById(R.id.tvSoLuongMonHoc);

        // Nhận dữ liệu từ Intent
        String tenPhongBan = getIntent().getStringExtra("TEN_PHONG_BAN");
        int soLuongMonHoc = getIntent().getIntExtra("SO_LUONG_MON_HOC", 0);

        // Hiển thị dữ liệu lên TextView
        tvTenPhongBan.setText("Tên phòng ban: " + tenPhongBan);
        tvSoLuongMonHoc.setText("Số lượng môn học: " + soLuongMonHoc);
    }
}