package vn.home.com.bottombar;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import vn.home.com.model.PhongTro;

public class XemChiTietActivity extends AppCompatActivity {

    TextView txtNguoiDung,txtMoTa,txtGia,txtDiaChi;
    ImageButton btnCall,btnMessage,btnLocation;
    PhongTro phongTro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_chi_tiet);
        addControls();
        addEvents();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("MY_BUNDLE");
        phongTro = (PhongTro) bundle.getSerializable("PHONGTRO");
        txtDiaChi.setText(phongTro.diaChi.diaChiChiTiet);
        txtGia.setText(phongTro.giaPhong.toString());
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
        intent.putExtra("sms_body","Nhập nội dung bạn muốn hỏi về phòng trọ tại đây!!!");
        intent.setType("text/plain");
        intent.setData(Uri.parse("sms:" + phongTro.sdt));
        startActivity(intent);
    }

    private void addControls() {
        txtNguoiDung = (TextView) findViewById(R.id.txtTenNguoiDung);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChi);
        txtGia = (TextView) findViewById(R.id.txtGia);
        txtMoTa = (TextView) findViewById(R.id.txtMoTa);
        btnCall = (ImageButton) findViewById(R.id.btnCall);
        btnLocation = (ImageButton) findViewById(R.id.btnLocation);
        btnMessage = (ImageButton) findViewById(R.id.btnMessage);



    }

}
