package vn.home.com.bottombar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import vn.home.com.model.DiaChi;
import vn.home.com.model.PhongTro;
import vn.home.com.model.PhongTroCanMuon;

public class ChiTietSuaTinCanMuonActivity extends AppCompatActivity {
    private EditText edtDiaChiCanTim, edtGiaPhongMin, edtGiaPhongMax, edtDienTichPhongCanTim,
            edtMoTaCanTim, edtSoDtCanTim, edtHotenCanTim;
    private Button btnSuaTinCanTim;
    private  PhongTroCanMuon phongTroCanMuon;

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

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

        DatabaseReference phongtroRef = databaseReference.child("cantim");
        Map<String, Object> phongtroUpdate = new HashMap<String, Object>();
        phongtroUpdate.put( key, new PhongTroCanMuon(tenNguoiTim, diaChiCanTim, Integer.parseInt(dienTich),
                Double.parseDouble(giaPhongMin), Double.parseDouble(giaPhongMax), sdt, moTa, currentDate, false, email, key ));
        phongtroRef.getRef().updateChildren(phongtroUpdate);

        Toast.makeText(ChiTietSuaTinCanMuonActivity.this, "Cập nhật tin thành công", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ChiTietSuaTinCanMuonActivity.this,QuanLyActivity.class));

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
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

    }
}
