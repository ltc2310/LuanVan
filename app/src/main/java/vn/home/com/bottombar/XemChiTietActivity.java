package vn.home.com.bottombar;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import vn.home.com.adapter.MyAdapter;
import vn.home.com.model.PhongTro;

public class XemChiTietActivity extends AppCompatActivity {

    TextView txtNguoiDung, txtMoTa, txtGia, txtDiaChi, txtLienLac, txtDienTich, txtNgayDang ;
    Button btnCall, btnMessage, btnLocation;
    PhongTro phongTro;
    private static ViewPager mPager;
    private static int currentPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_chi_tiet);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết phòng trọ");
        addControls();
        addEvents();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("MY_BUNDLE");
        phongTro = (PhongTro) bundle.getSerializable("PHONGTRO");
        txtDiaChi.setText(phongTro.diaChi.diaChiChiTiet + " ,"+ phongTro.diaChi.quan + " ," + phongTro.diaChi.thanhPho);
        txtGia.setText(phongTro.giaPhong.toString() + " triệu");
        txtMoTa.setText(phongTro.moTa);
        txtNguoiDung.setText(phongTro.tenNguoiDung);
        txtLienLac.setText(phongTro.sdt);
        txtDienTich.setText(phongTro.dienTich + " mét vuông");
        txtNgayDang.setText(phongTro.ngayDang);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(XemChiTietActivity.this, phongTro.linkHinh));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == phongTro.linkHinh.size()) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
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
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyTimDuongDi();

            }
        });
    }

    private void xuLyTimDuongDi() {
        Intent intent_map = new Intent(XemChiTietActivity.this , MapsActivity.class);
        intent_map.putExtra("luachon","chitiet");

        Bundle bundle = new Bundle();
        bundle.putDouble("lang", phongTro.latitude);
        bundle.putDouble("long", phongTro.longtitue);
        bundle.putDouble("giaphong", phongTro.giaPhong);
        bundle.putString("diachi", phongTro.diaChi.diaChiChiTiet);

        intent_map.putExtras(bundle);
        startActivity(intent_map);
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
        intent.putExtra("sms_body", "Nhập nội dung bạn muốn hỏi về phòng trọ tại đây!!!");
        intent.setType("text/plain");
        intent.setData(Uri.parse("sms:" + phongTro.sdt));
        startActivity(intent);
    }

    private void addControls() {
        txtNguoiDung = (TextView) findViewById(R.id.tvTenChuPhongTro);
        txtDiaChi = (TextView) findViewById(R.id.tvDiaChiCT);
        txtGia = (TextView) findViewById(R.id.tvGiaPhongCT);
        txtMoTa = (TextView) findViewById(R.id.tvMoTa);
        txtLienLac = (TextView) findViewById(R.id.tvLienHe);
        txtDienTich = (TextView) findViewById(R.id.tvDienTichCT);
        txtNgayDang = (TextView) findViewById(R.id.tvNgayDangCT);
        btnCall = (Button) findViewById(R.id.btnGoiCT);
        btnLocation = (Button) findViewById(R.id.btnBanDo);
        btnMessage = (Button) findViewById(R.id.btnNhanTinCT);


    }

}
