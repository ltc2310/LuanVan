package vn.home.com.bottombar;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import vn.home.com.adapter.MyAdapter;
import vn.home.com.model.DiaChi;
import vn.home.com.model.PhongTro;

public class DuyetTinChoMuonActivity extends AppCompatActivity {
    TextView txtNguoiDung, txtMoTa, txtGia, txtDiaChi, txtLienLac, txtDienTich, txtNgayDang ;
    Button btnDuyetTin;
    PhongTro phongTro;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyet_tin_cho_muon);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Trở lại duyệt tin");
        addControls();
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
        mPager = (ViewPager) findViewById(R.id.pagerDT);
        mPager.setAdapter(new MyAdapter(DuyetTinChoMuonActivity.this, phongTro.linkHinh));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicatorDT);
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


       btnDuyetTin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             databaseReference.child("phongtro").child(phongTro.id).addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {
                     dataSnapshot.getRef().child("kichHoat").setValue(true);
                     Toast.makeText(DuyetTinChoMuonActivity.this, "Tin được duyệt thành công", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(DuyetTinChoMuonActivity.this, DuyetTinChoMuonPhongTroActivity.class));
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });
           }
       });

    }


    private void addControls() {
        txtNguoiDung = (TextView) findViewById(R.id.tvTenChuPhongTroDT);
        txtDiaChi = (TextView) findViewById(R.id.tvDiaChiDT);
        txtGia = (TextView) findViewById(R.id.tvGiaPhongDT);
        txtMoTa = (TextView) findViewById(R.id.tvMoTaDT);
        txtLienLac = (TextView) findViewById(R.id.tvLienHeDT);
        txtDienTich = (TextView) findViewById(R.id.tvDienTichDT);
        txtNgayDang = (TextView) findViewById(R.id.tvNgayDangDT);
        btnDuyetTin = (Button) findViewById(R.id.btnDuyetTin);
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}
