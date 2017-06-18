package vn.home.com.bottombar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vn.home.com.model.DiaChi;
import vn.home.com.model.PhongTro;
import vn.home.com.model.PhongTroCanMuon;

public class DangTinTimPhongActivity extends AppCompatActivity {

    private EditText edtDiaChiCanTim, edtGiaPhongMin, edtGiaPhongMax, edtDienTichPhongCanTim,
                        edtMoTaCanTim, edtSoDtCanTim, edtHotenCanTim;

    private Button btnDangTinTimPhong;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_tin_tim_phong);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDangTinTimPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDangTinCanTimPhongTro();
            }
        });
    }

    private void xuLyDangTinCanTimPhongTro() {
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getEmail();
        String giaPhongMin = edtGiaPhongMin.getText().toString().trim();
        String giaPhongMax = edtGiaPhongMax.getText().toString().trim();
        String diaChiCanTim = edtDiaChiCanTim.getText().toString();
        String sdt = edtSoDtCanTim.getText().toString().trim();
        String moTa = edtMoTaCanTim.getText().toString();
        String tenNguoiTim = edtHotenCanTim.getText().toString();
        String dienTich = edtDienTichPhongCanTim.getText().toString().trim();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String currentDate = formatter.format(date);

        if (diaChiCanTim.isEmpty() || diaChiCanTim.equals("")) {
            Toast.makeText(this, "Vui lòng nhập vào địa chỉ bạn cần tìm ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (giaPhongMin.isEmpty() || giaPhongMin.equals("")) {
            Toast.makeText(this, "Vui lòng nhập vào giá phòng tối thiểu bạn cần tìm ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (giaPhongMax.isEmpty() || giaPhongMax.equals("")) {
            Toast.makeText(this, "Vui lòng nhập vào giá phòng tối đa cần tìm ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dienTich.isEmpty() || dienTich.equals("")) {
            Toast.makeText(this, "Vui lòng nhập vào tên liên hệ ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (moTa.isEmpty() || moTa.equals("")) {
            Toast.makeText(this, "Vui lòng nhập vào nội dung cần tìm ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sdt.isEmpty() || sdt.equals("")) {
            Toast.makeText(this, "Vui lòng nhập vào số điện thoại liên hệ ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tenNguoiTim.isEmpty() || tenNguoiTim.equals("")) {
            Toast.makeText(this, "Vui lòng nhập vào tên liên hệ ", Toast.LENGTH_SHORT).show();
            return;
        }


        String key = databaseReference.push().getKey();

        databaseReference.child("cantim").child(key).setValue(new PhongTroCanMuon(tenNguoiTim, diaChiCanTim, Integer.parseInt(dienTich),
                Double.parseDouble(giaPhongMin), Double.parseDouble(giaPhongMax), sdt, moTa, currentDate, false, email, key )).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(DangTinTimPhongActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(DangTinTimPhongActivity.this, "Tin của bạn đã được gửi, Vui lòng chờ được duyệt !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addControls() {
        edtDiaChiCanTim = (EditText) findViewById(R.id.edtDiaChiCanTim);
        edtDienTichPhongCanTim = (EditText) findViewById(R.id.edtDienTichPhongCanTim);
        edtGiaPhongMin = (EditText) findViewById(R.id.edtGiaPhongMin);
        edtGiaPhongMax = (EditText) findViewById(R.id.edtGiaPhongMax);
        edtMoTaCanTim = (EditText) findViewById(R.id.edtMoTaCanTim);
        edtSoDtCanTim = (EditText) findViewById(R.id.edtMoTaCanTim);
        edtHotenCanTim = (EditText) findViewById(R.id.edtHotenCanTim);
        btnDangTinTimPhong = (Button) findViewById(R.id.btnDangTinTimPhong);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

    }
}
