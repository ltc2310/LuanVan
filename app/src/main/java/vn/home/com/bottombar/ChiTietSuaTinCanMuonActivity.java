package vn.home.com.bottombar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import vn.home.com.model.PhongTroCanMuon;

public class ChiTietSuaTinCanMuonActivity extends AppCompatActivity {
    private EditText edtDiaChiCanTim, edtGiaPhongMin, edtGiaPhongMax, edtDienTichPhongCanTim,
            edtMoTaCanTim, edtSoDtCanTim, edtHotenCanTim;
    private SeekBar sbBanKinhSuaTinCT;
    private TextView tvBanKinhSuaTinCT;
    private Double banKinhSuaTinCT;
    private Button btnSuaTinCanTim, btnSuaDCCT;
    private PhongTroCanMuon phongTroCanMuon;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    String luaChonSuaTin;
    Double latitudeSuaTin;
    Double longitudeSuaTin;
    String diaChiSuaTin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sua_tin_can_muon);
        addControls();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("PHONGTRO_CANMUON_BUNDLE");
        phongTroCanMuon = (PhongTroCanMuon) bundle.getSerializable("PHONGTROCANMUON");
        edtGiaPhongMin.setText(phongTroCanMuon.giaPhongMin.toString());
        edtGiaPhongMax.setText(phongTroCanMuon.giaPhongMax.toString());
        edtDienTichPhongCanTim.setText(phongTroCanMuon.dienTich.toString());
        edtMoTaCanTim.setText(phongTroCanMuon.moTa);
        edtSoDtCanTim.setText(phongTroCanMuon.sdt);
        edtHotenCanTim.setText(phongTroCanMuon.tenNguoiDung);
        edtDiaChiCanTim.setText(phongTroCanMuon.diaChi);

        addEvents();

    }

    private void addEvents() {
        btnSuaTinCanTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDangTinCanTimPhongTro();
            }
        });

        btnSuaDCCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietSuaTinCanMuonActivity.this, MapsActivity.class);
                intent.putExtra("luachon", "chontoado");
                startActivity(intent);
            }
        });

        luaChonSuaTin = getIntent().getStringExtra("diachi");

        if (luaChonSuaTin !=null){
            if (luaChonSuaTin.equals("diachidcchon")) {
                Bundle bundle = getIntent().getExtras();
                latitudeSuaTin = bundle.getDouble("latitude",0);
                longitudeSuaTin = bundle.getDouble("longitude",0);
                diaChiSuaTin=bundle.getString("diaChiTimDuoc","");
            }
            edtDiaChiCanTim.setText(diaChiSuaTin);
        }

        sbBanKinhSuaTinCT.setMax(20);
        sbBanKinhSuaTinCT.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case 0:
                        tvBanKinhSuaTinCT.setText("Bán kính: 1 km");
                        banKinhSuaTinCT = 1D;
                        break;
                    case 1:
                        tvBanKinhSuaTinCT.setText("Bán kính: 2 km");
                        banKinhSuaTinCT = 2D;
                        break;
                    case 2:
                        tvBanKinhSuaTinCT.setText("Bán kính: 3 km");
                        banKinhSuaTinCT = 3D;
                        break;
                    case 3:
                        tvBanKinhSuaTinCT.setText("Bán kính: 4 km");
                        banKinhSuaTinCT = 4D;
                        break;
                    case 4:
                        tvBanKinhSuaTinCT.setText("Bán kính: 5 km");
                        banKinhSuaTinCT = 5D;
                        break;
                    case 5:
                        tvBanKinhSuaTinCT.setText("Bán kính: 6 km");
                        banKinhSuaTinCT = 6D;
                        break;
                    case 6:
                        tvBanKinhSuaTinCT.setText("Bán kính: 7 km");
                        banKinhSuaTinCT = 7D;
                        break;
                    case 7:
                        tvBanKinhSuaTinCT.setText("Bán kính: 8 km");
                        banKinhSuaTinCT = 8D;
                        break;
                    case 8:
                        tvBanKinhSuaTinCT.setText("Bán kính: 9 km");
                        banKinhSuaTinCT = 9D;
                        break;
                    case 9:
                        tvBanKinhSuaTinCT.setText("Bán kính: 10 km");
                        banKinhSuaTinCT = 10D;
                        break;
                    case 10:
                        tvBanKinhSuaTinCT.setText("Bán kính: 11 km");
                        banKinhSuaTinCT = 11D;
                        break;
                    case 11:
                        tvBanKinhSuaTinCT.setText("Bán kính: 12 km");
                        banKinhSuaTinCT = 12D;
                        break;
                    case 12:
                        tvBanKinhSuaTinCT.setText("Bán kính: 13 km");
                        banKinhSuaTinCT = 13D;
                        break;
                    case 13:
                        tvBanKinhSuaTinCT.setText("Bán kính: 14 km");
                        banKinhSuaTinCT = 14D;
                        break;
                    case 14:
                        tvBanKinhSuaTinCT.setText("Bán kính: 15 km");
                        banKinhSuaTinCT = 15D;
                        break;
                    case 15:
                        tvBanKinhSuaTinCT.setText("Bán kính: 16 km");
                        banKinhSuaTinCT = 16D;
                        break;
                    case 16:
                        tvBanKinhSuaTinCT.setText("Bán kính: 17 km");
                        banKinhSuaTinCT = 17D;
                        break;
                    case 17:
                        tvBanKinhSuaTinCT.setText("Bán kính: 18 km");
                        banKinhSuaTinCT = 18D;
                        break;
                    case 18:
                        tvBanKinhSuaTinCT.setText("Bán kính: 19 km");
                        banKinhSuaTinCT = 19D;
                        break;
                    case 19:
                        tvBanKinhSuaTinCT.setText("Bán kính: 20 km");
                        banKinhSuaTinCT = 20D;
                        break;
                    default:
                        tvBanKinhSuaTinCT.setText("Bán kính: tối đa");
                        banKinhSuaTinCT = 20D;
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

        String key = phongTroCanMuon.idPhongTroCM;

        if (latitudeSuaTin != null){
            DatabaseReference phongtroRef = databaseReference.child("cantim");
            Map<String, Object> phongtroUpdate = new HashMap<String, Object>();
            phongtroUpdate.put( key, new PhongTroCanMuon(tenNguoiTim, diaChiCanTim, Double.parseDouble(dienTich),
                    Double.parseDouble(giaPhongMin), Double.parseDouble(giaPhongMax), sdt, moTa, currentDate, false, email,
                    key, latitudeSuaTin, longitudeSuaTin, banKinhSuaTinCT, false  ));
            phongtroRef.getRef().updateChildren(phongtroUpdate);

            Toast.makeText(ChiTietSuaTinCanMuonActivity.this, "Cập nhật tin thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ChiTietSuaTinCanMuonActivity.this,QuanLyActivity.class));
        }
        else {
            DatabaseReference phongtroRef = databaseReference.child("cantim");
            Map<String, Object> phongtroUpdate = new HashMap<String, Object>();
            phongtroUpdate.put( key, new PhongTroCanMuon(tenNguoiTim, diaChiCanTim, Double.parseDouble(dienTich),
                    Double.parseDouble(giaPhongMin), Double.parseDouble(giaPhongMax), sdt, moTa, currentDate, false, email,
                    key, phongTroCanMuon.latitude, phongTroCanMuon.longitude, banKinhSuaTinCT, false  ));
            phongtroRef.getRef().updateChildren(phongtroUpdate);

            Toast.makeText(ChiTietSuaTinCanMuonActivity.this, "Cập nhật tin thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ChiTietSuaTinCanMuonActivity.this,QuanLyActivity.class));
        }

    }

    private void addControls() {
        edtDiaChiCanTim = (EditText) findViewById(R.id.edtDiaChiSuaTinCT);
        edtDienTichPhongCanTim = (EditText) findViewById(R.id.edtDienTichSuaTinCT);
        edtGiaPhongMin = (EditText) findViewById(R.id.edtGiaPhongMinSuaTinCT);
        edtGiaPhongMax = (EditText) findViewById(R.id.edtGiaPhongMaxSuaTinCT);
        edtMoTaCanTim = (EditText) findViewById(R.id.edtMoTaSuaTinCT);
        edtSoDtCanTim = (EditText) findViewById(R.id.edtSoDtSuaTinCT);
        edtHotenCanTim = (EditText) findViewById(R.id.edtHotenSuaTinCT);
        btnSuaTinCanTim = (Button) findViewById(R.id.btnSuaTinCanTim);
        btnSuaDCCT = (Button) findViewById(R.id.btnSuaDCCT);
        sbBanKinhSuaTinCT = (SeekBar) findViewById(R.id.sbBanKinhSuaTinCT);
        tvBanKinhSuaTinCT = (TextView) findViewById(R.id.tvBanKinhSuaTinCT);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
    }
}
