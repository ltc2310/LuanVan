package vn.home.com.bottombar;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import vn.home.com.adapter.MyAdapter;
import vn.home.com.model.PhongTroCanMuon;

public class SuaTinCanMuonActivity extends AppCompatActivity {

    TextView txtNguoiDung, txtMoTa, txtGia, txtDiaChi, txtLienLac, txtDienTich, txtNgayDang ;
    Button btnSuaTinCanMuon;
    PhongTroCanMuon phongTro;
    private static ViewPager mPager;
    private static int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_tin_can_muon);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Trở lại quản lý");
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
        mPager = (ViewPager) findViewById(R.id.pagerSuaCM);
        ArrayList<String> hinhCanTim = new ArrayList<>();
        hinhCanTim.add("https://firebasestorage.googleapis.com/v0/b/mylogin-c65fa.appspot.com/o/Photos%2Fcantim.jpg?alt=media&token=3c85b848-0b08-4454-bdb5-b56b9e5bbb49");
        mPager.setAdapter(new MyAdapter(SuaTinCanMuonActivity.this, hinhCanTim));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicatorSuaCM);
        indicator.setViewPager(mPager);

        if (phongTro.kichHoat == false){
            btnSuaTinCanMuon.setVisibility(View.VISIBLE);
            btnSuaTinCanMuon.setEnabled(true);
        }

        btnSuaTinCanMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuaTinCanMuonActivity.this, ChiTietSuaTinCanMuonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PHONGTROCANMUON", phongTro);
                intent.putExtra("PHONGTRO_CANMUON_BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }


    private void addControls() {
        txtMoTa = (TextView) findViewById(R.id.tvMoTaTimPhongSuaCM);
        txtNguoiDung = (TextView) findViewById(R.id.tvTenNguoiTimSuaCM);
        txtGia = (TextView) findViewById(R.id.tvGiaPhongTimPhongSuaCM);
        txtDiaChi = (TextView) findViewById(R.id.tvDiaChiTimPhongSuaCM);
        txtLienLac = (TextView) findViewById(R.id.tvLienHeTimPhongSuaCM);
        txtDienTich = (TextView) findViewById(R.id.tvDienTichTimPhongSuaCM);
        txtNgayDang = (TextView) findViewById(R.id.tvNgayDangTimPhongSuaCM);
        btnSuaTinCanMuon = (Button) findViewById(R.id.btnSuaCM);
        btnSuaTinCanMuon.setVisibility(View.INVISIBLE);
        btnSuaTinCanMuon.setEnabled(false);
    }



}
