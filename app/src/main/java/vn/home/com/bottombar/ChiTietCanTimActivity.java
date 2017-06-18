package vn.home.com.bottombar;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import vn.home.com.adapter.MyAdapter;
import vn.home.com.model.PhongTro;
import vn.home.com.model.PhongTroCanMuon;

public class ChiTietCanTimActivity extends AppCompatActivity {

    TextView txtNguoiDung, txtMoTa, txtGia, txtDiaChi, txtLienLac, txtDienTich, txtNgayDang ;
    Button btnCall, btnMessage;
    PhongTroCanMuon phongTro;
    private static ViewPager mPager;
    private static int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_can_tim);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết tìm phòng");
        addControls();
        addEvents();
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
        mPager = (ViewPager) findViewById(R.id.pager1);
        ArrayList<String> hinhCanTim = new ArrayList<>();
        hinhCanTim.add("https://firebasestorage.googleapis.com/v0/b/mylogin-c65fa.appspot.com/o/Photos%2Fcantim.jpg?alt=media&token=3c85b848-0b08-4454-bdb5-b56b9e5bbb49");
        mPager.setAdapter(new MyAdapter(ChiTietCanTimActivity.this, hinhCanTim));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator1);
        indicator.setViewPager(mPager);
    }

    private void addEvents() {
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGuiTinNhan();
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGoiDien();
            }
        });


    }

    private void xuLyGoiDien() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phongTro.sdt));
        startActivity(intent);
    }

    private void xuLyGuiTinNhan() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.putExtra("sms_body", "Nhập nội dung bạn muốn giới thiệu phòng trọ tại đây !!");
        intent.setType("text/plain");
        intent.setData(Uri.parse("sms:" + phongTro.sdt));
        startActivity(intent);
    }

    private void addControls() {
        txtMoTa = (TextView) findViewById(R.id.tvMoTaTimPhong);
        txtNguoiDung = (TextView) findViewById(R.id.tvTenNguoiTim);
        txtGia = (TextView) findViewById(R.id.tvGiaPhongTimPhong);
        txtDiaChi = (TextView) findViewById(R.id.tvDiaChiTimPhong);
        txtLienLac = (TextView) findViewById(R.id.tvLienHeTimPhong);
        txtDienTich = (TextView) findViewById(R.id.tvDienTichTimPhong);
        txtNgayDang = (TextView) findViewById(R.id.tvNgayDangTimPhong);
        btnCall = (Button) findViewById(R.id.btnGoiTimPhong);
        btnMessage = (Button) findViewById(R.id.btnNhanTinTimPhong);
    }
}
