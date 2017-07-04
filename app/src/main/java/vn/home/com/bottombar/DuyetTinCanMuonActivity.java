package vn.home.com.bottombar;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;
import vn.home.com.adapter.MyAdapter;
import vn.home.com.model.PhongTroCanMuon;

public class DuyetTinCanMuonActivity extends AppCompatActivity {

    TextView txtNguoiDung, txtMoTa, txtGia, txtDiaChi, txtLienLac, txtDienTich, txtNgayDang ;
    Button btnDuyetTinCanMuon, btnHuyTinCanMuon;
    PhongTroCanMuon phongTro;
    private static ViewPager mPager;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private static int currentPage = 0;
    private String ten, diaChi, key, sdt, moTa, email, ngayDang;
    private Double  giaPhongMin, giaPhongMax, longtitue, latitue, banKinh, dienTich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyet_tin_can_muon);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Trở lại duyệt tin");
        addControls();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("MY_BUNDLE1");
        phongTro = (PhongTroCanMuon) bundle.getSerializable("PHONGTROCANMUON");
        txtDiaChi.setText(phongTro.diaChi);
        txtGia.setText(phongTro.giaPhongMin.toString() + " VNĐ" + " - " + phongTro.giaPhongMax.toString() + " VNĐ");
        txtMoTa.setText(phongTro.moTa);
        txtNguoiDung.setText(phongTro.tenNguoiDung);
        txtLienLac.setText(phongTro.sdt);
        txtDienTich.setText(phongTro.dienTich + " mét vuông");
        txtNgayDang.setText(phongTro.ngayDang);
        mPager = (ViewPager) findViewById(R.id.pagerDT);
        ArrayList<String> hinhCanTim = new ArrayList<>();
        hinhCanTim.add("https://firebasestorage.googleapis.com/v0/b/mylogin-c65fa.appspot.com/o/Photos%2Fcantim.jpg?alt=media&token=3c85b848-0b08-4454-bdb5-b56b9e5bbb49");
        mPager.setAdapter(new MyAdapter(DuyetTinCanMuonActivity.this, hinhCanTim));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicatorDT);
        indicator.setViewPager(mPager);
        //ten, diaChi, key, giaPhongMin, giaPhongMax, banKinh, sdt, moTa, longtitue, lattitue, email, ngayDang, dienTich
        ten = phongTro.tenNguoiDung;
        email = phongTro.emailPhongTroCM;
        ngayDang = phongTro.ngayDang;
        key = phongTro.idPhongTroCM;
        diaChi = phongTro.diaChi;
        giaPhongMin = phongTro.giaPhongMin;
        giaPhongMax = phongTro.giaPhongMax;
        email = phongTro.emailPhongTroCM;
        sdt = phongTro.sdt;
        longtitue = phongTro.longitude;
        latitue = phongTro.latitude;
        moTa = phongTro.moTa;
        banKinh = phongTro.banKinh;
        dienTich = phongTro.dienTich;


        addEvents();
    }

    private void addEvents() {
        btnDuyetTinCanMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference phongtroRef = databaseReference.child("cantim");
                Map<String, Object> phongtroUpdate = new HashMap<String, Object>();
                phongtroUpdate.put( key, new PhongTroCanMuon(ten, diaChi, dienTich,
                        giaPhongMin, giaPhongMax, sdt, moTa, ngayDang, true, email,
                        key, latitue, longtitue, banKinh, false  ));
                phongtroRef.getRef().updateChildren(phongtroUpdate);

                Toast.makeText(DuyetTinCanMuonActivity.this, "Tin dã được duyệt thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DuyetTinCanMuonActivity.this,DuyetTinActivity.class));
            }
        });

        btnHuyTinCanMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DuyetTinCanMuonActivity.this, GuiMailTuChoiTinCanMuonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PHONGTROCANMUON", phongTro);
                intent.putExtra("PHONGTRO_CANMUON_BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        txtMoTa = (TextView) findViewById(R.id.tvMoTaTimPhongDT);
        txtNguoiDung = (TextView) findViewById(R.id.tvTenNguoiTimDT);
        txtGia = (TextView) findViewById(R.id.tvGiaPhongTimPhongDT);
        txtDiaChi = (TextView) findViewById(R.id.tvDiaChiTimPhongDT);
        txtLienLac = (TextView) findViewById(R.id.tvLienHeTimPhongDT);
        txtDienTich = (TextView) findViewById(R.id.tvDienTichTimPhongDT);
        txtNgayDang = (TextView) findViewById(R.id.tvNgayDangTimPhongDT);
        btnDuyetTinCanMuon = (Button) findViewById(R.id.btnDuyetTinCanMuon);
        btnHuyTinCanMuon = (Button) findViewById(R.id.btnHuyTinCanMuon);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}
