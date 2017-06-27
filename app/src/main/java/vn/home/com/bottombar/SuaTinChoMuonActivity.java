package vn.home.com.bottombar;

import android.content.Intent;
import android.os.Handler;
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

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import vn.home.com.adapter.MyAdapter;
import vn.home.com.model.PhongTro;

public class SuaTinChoMuonActivity extends AppCompatActivity {

    private TextView txtNguoiDung, txtMoTa, txtGia, txtDiaChi, txtLienLac, txtDienTich, txtNgayDang ;
    private Button btnSuaTinCM;
    private PhongTro phongTro;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_tin_cho_muon);
        addControls();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("MY_BUNDLE");
        phongTro = (PhongTro) bundle.getSerializable("PHONGTRO");
        txtDiaChi.setText(phongTro.diaChi.diaChiChiTiet + " ,"+ phongTro.diaChi.quan + " ," + phongTro.diaChi.thanhPho);
        txtGia.setText(phongTro.giaPhong.toString() + " VNĐ");
        txtMoTa.setText(phongTro.moTa);
        txtNguoiDung.setText(phongTro.tenNguoiDung);
        txtLienLac.setText(phongTro.sdt);
        txtDienTich.setText(phongTro.dienTich + " mét vuông");
        txtNgayDang.setText(phongTro.ngayDang);
        mPager = (ViewPager) findViewById(R.id.pagerSTCM);
        mPager.setAdapter(new MyAdapter(SuaTinChoMuonActivity.this, phongTro.linkHinh));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicatorSTCM);
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

        if (phongTro.kichHoat == false){
            btnSuaTinCM.setVisibility(View.VISIBLE);
            btnSuaTinCM.setEnabled(true);
        }

        btnSuaTinCM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuaTinChoMuonActivity.this, ChiTietSuaTinChoMuonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PHONGTRO", phongTro);
                intent.putExtra("PHONGTRO_BUNDLE", bundle);
                startActivity(intent);
            }
        });


    }

    private void addControls() {
        txtNguoiDung = (TextView) findViewById(R.id.tvTenChuPhongTroSTCM);
        txtDiaChi = (TextView) findViewById(R.id.tvDiaChiSTCM);
        txtGia = (TextView) findViewById(R.id.tvGiaPhongSTCM);
        txtMoTa = (TextView) findViewById(R.id.tvMoTaSTCM);
        txtLienLac = (TextView) findViewById(R.id.tvLienHeSTCM);
        txtDienTich = (TextView) findViewById(R.id.tvDienTichSTCM);
        txtNgayDang = (TextView) findViewById(R.id.tvNgayDangSTCM);
        btnSuaTinCM = (Button) findViewById(R.id.btnSuaTinCM);
        auth = FirebaseAuth.getInstance();
        btnSuaTinCM.setVisibility(View.INVISIBLE);
        btnSuaTinCM.setEnabled(false);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}
