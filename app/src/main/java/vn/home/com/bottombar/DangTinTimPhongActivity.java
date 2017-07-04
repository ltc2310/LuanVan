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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
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
    private SeekBar sbBanKinhCT;
    private TextView tvBanKinhCT;
    private Double banKinh;

    private Button btnDangTinTimPhong, btnChonDiaChiCanTim;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    String luaChon;
    Double latitude;
    Double longitude;
    String diaChi;

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


        luaChon=getIntent().getStringExtra("diachi");

        if (luaChon !=null){
            if (luaChon.equals("diachidcchon")) {
                Bundle bundle = getIntent().getExtras();
                latitude = bundle.getDouble("latitude",0);
                longitude = bundle.getDouble("longitude",0);
                diaChi=bundle.getString("diaChiTimDuoc","");
            }
            edtDiaChiCanTim.setText(diaChi);
        }



        sbBanKinhCT.setMax(20);
        sbBanKinhCT.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case 0:
                        tvBanKinhCT.setText("Bán kính: 1 km");
                        banKinh = 1D;
                        break;
                    case 1:
                        tvBanKinhCT.setText("Bán kính: 2 km");
                        banKinh = 2D;
                        break;
                    case 2:
                        tvBanKinhCT.setText("Bán kính: 3 km");
                        banKinh = 3D;
                        break;
                    case 3:
                        tvBanKinhCT.setText("Bán kính: 4 km");
                        banKinh = 4D;
                        break;
                    case 4:
                        tvBanKinhCT.setText("Bán kính: 5 km");
                        banKinh = 5D;
                        break;
                    case 5:
                        tvBanKinhCT.setText("Bán kính: 6 km");
                        banKinh = 6D;
                        break;
                    case 6:
                        tvBanKinhCT.setText("Bán kính: 7 km");
                        banKinh = 7D;
                        break;
                    case 7:
                        tvBanKinhCT.setText("Bán kính: 8 km");
                        banKinh = 8D;
                        break;
                    case 8:
                        tvBanKinhCT.setText("Bán kính: 9 km");
                        banKinh = 9D;
                        break;
                    case 9:
                        tvBanKinhCT.setText("Bán kính: 10 km");
                        banKinh = 10D;
                        break;
                    case 10:
                        tvBanKinhCT.setText("Bán kính: 11 km");
                        banKinh = 11D;
                        break;
                    case 11:
                        tvBanKinhCT.setText("Bán kính: 12 km");
                        banKinh = 12D;
                        break;
                    case 12:
                        tvBanKinhCT.setText("Bán kính: 13 km");
                        banKinh = 13D;
                        break;
                    case 13:
                        tvBanKinhCT.setText("Bán kính: 14 km");
                        banKinh = 14D;
                        break;
                    case 14:
                        tvBanKinhCT.setText("Bán kính: 15 km");
                        banKinh = 15D;
                        break;
                    case 15:
                        tvBanKinhCT.setText("Bán kính: 16 km");
                        banKinh = 16D;
                        break;
                    case 16:
                        tvBanKinhCT.setText("Bán kính: 17 km");
                        banKinh = 17D;
                        break;
                    case 17:
                        tvBanKinhCT.setText("Bán kính: 18 km");
                        banKinh = 18D;
                        break;
                    case 18:
                        tvBanKinhCT.setText("Bán kính: 19 km");
                        banKinh = 19D;
                        break;
                    case 19:
                        tvBanKinhCT.setText("Bán kính: 20 km");
                        banKinh = 20D;
                        break;
                    default:
                        tvBanKinhCT.setText("Bán kính: tối đa");
                        banKinh = 20D;
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnChonDiaChiCanTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyLayDiaChi();
            }
        });
    }

    private void xuLyLayDiaChi() {
        Intent intent = new Intent(DangTinTimPhongActivity.this, MapsActivity.class);
        intent.putExtra("luachon", "chontoado");
        startActivity(intent);
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

        databaseReference.child("cantim").child(key).setValue(new PhongTroCanMuon(tenNguoiTim, diaChiCanTim, Double.parseDouble(dienTich),
                Double.parseDouble(giaPhongMin), Double.parseDouble(giaPhongMax), sdt, moTa, currentDate, false, email, key, latitude, longitude, banKinh, false )).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        edtSoDtCanTim = (EditText) findViewById(R.id.edtSoDtCanTim);
        edtHotenCanTim = (EditText) findViewById(R.id.edtHotenCanTim);
        btnDangTinTimPhong = (Button) findViewById(R.id.btnDangTinTimPhong);
        btnChonDiaChiCanTim = (Button) findViewById(R.id.btnChonDiaChiCanTim);
        sbBanKinhCT = (SeekBar) findViewById(R.id.sbBanKinhCT);
        tvBanKinhCT = (TextView) findViewById(R.id.tvBanKinhCT);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

    }
}
