package com.example.thick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private Spinner spinnerPhongBan;
    private ListView lvDanhSach;
    private EditText edtMaNV, edtTenNV, edtTuoi;
    private Button btnThem, btnXoa, btnOpenAct;


    private MyDatabase myDatabase;
    private List<PhongBan> phongBanList;
    private List<NhanVien> nhanVienList;
    private ArrayAdapter<String> phongBanAdapter;
    private ArrayAdapter<String> nhanVienAdapter;

    private int selectedPhongBanId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Ánh xạ view
        initUI();

        // Khởi tạo database
        myDatabase = new MyDatabase(this);

        // Thêm dữ liệu mẫu phòng ban
        insertSamplePhongBan();

        // Hiển thị danh sách phòng ban lên spinner
        loadPhongBanData();

        // Xử lý sự kiện chọn phòng ban
        spinnerPhongBan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPhongBanId = phongBanList.get(position).getMaPhongBan();
                loadNhanVienData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Xử lý sự kiện thêm nhân viên
        btnThem.setOnClickListener(view -> addNhanVien());

        // Xử lý sự kiện xóa nhân viên
        btnXoa.setOnClickListener(view -> deleteNhanVien());

        // Xử lý sự kiện chọn nhân viên từ danh sách
        lvDanhSach.setOnItemClickListener((parent, view, position, id) -> {
            NhanVien nhanVien = nhanVienList.get(position);
            edtMaNV.setText(String.valueOf(nhanVien.getMaNhanVien()));
            edtTenNV.setText(nhanVien.getTenNhanVien());
            edtTuoi.setText(String.valueOf(nhanVien.getTuoi()));
        });

        btnOpenAct.setOnClickListener(view -> {
            if (selectedPhongBanId != -1) {
                // Lấy tên phòng ban
                String tenPhongBan = phongBanList.get(selectedPhongBanId - 1).getTenPhongBan();

                // Lấy số lượng môn học (số nhân viên thuộc phòng ban này)
                int soLuongMonHoc = myDatabase.getNhanVienByPhongBan(selectedPhongBanId).size();

                // Tạo Intent để mở Activity_2
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("TEN_PHONG_BAN", tenPhongBan);
                intent.putExtra("SO_LUONG_MON_HOC", soLuongMonHoc);

                // Bắt đầu Activity_2
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Chưa chọn phòng ban!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertSamplePhongBan() {
        phongBanList = new ArrayList<>();
        phongBanList.add(new PhongBan(1, "Phòng Nhân Sự", 101));
        phongBanList.add(new PhongBan(2, "Phòng Kỹ Thuật", 102));
        phongBanList.add(new PhongBan(3, "Phòng Kinh Doanh", 103));

        for (PhongBan phongBan : phongBanList) {
            myDatabase.insertPhongBan(phongBan);
        }
    }

    private void loadPhongBanData() {
        phongBanList = new ArrayList<>();
        phongBanList.addAll(myDatabase.getPhongBanList());

        List<String> tenPhongBanList = new ArrayList<>();
        for (PhongBan phongBan : phongBanList) {
            tenPhongBanList.add(phongBan.getTenPhongBan());
        }

        phongBanAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tenPhongBanList);
        phongBanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPhongBan.setAdapter(phongBanAdapter);
    }

    private void loadNhanVienData() {
        nhanVienList = new ArrayList<>();
        nhanVienList.addAll(myDatabase.getNhanVienByPhongBan(selectedPhongBanId));

        List<String> nhanVienDisplayList = new ArrayList<>();
        for (NhanVien nhanVien : nhanVienList) {
            String display = phongBanList.get(selectedPhongBanId - 1).getTenPhongBan() + "\nMa nhan vien: " +
                    nhanVien.getMaNhanVien() + "\nTen nhan vien: " + nhanVien.getTenNhanVien() + "\nTuoi: " + nhanVien.getTuoi();
            nhanVienDisplayList.add(display);
        }

        nhanVienAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nhanVienDisplayList);
        lvDanhSach.setAdapter(nhanVienAdapter);
    }

    private void addNhanVien() {
        try {
            int maNV = Integer.parseInt(edtMaNV.getText().toString());
            String tenNV = edtTenNV.getText().toString();
            int tuoi = Integer.parseInt(edtTuoi.getText().toString());

            if (tenNV.isEmpty() || tuoi <= 0) {
                Toast.makeText(this, "Dữ liệu không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            NhanVien nhanVien = new NhanVien(maNV, selectedPhongBanId, tenNV, tuoi);
            myDatabase.insertNhanVien(nhanVien);
            Toast.makeText(this, "Thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();
            loadNhanVienData();

        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteNhanVien() {
        try {
            int maNV = Integer.parseInt(edtMaNV.getText().toString());
            myDatabase.deleteNhanVien(maNV);
            Toast.makeText(this, "Xóa nhân viên thành công!", Toast.LENGTH_SHORT).show();
            loadNhanVienData();
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initUI(){
        spinnerPhongBan = findViewById(R.id.spinnerPhongBan);
        lvDanhSach = findViewById(R.id.lvDanhSach);
        edtMaNV = findViewById(R.id.edtMaNV);
        edtTenNV = findViewById(R.id.edtTenNV);
        edtTuoi = findViewById(R.id.edtTuoi);
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnOpenAct = findViewById(R.id.btnOpenAct);
    }


}