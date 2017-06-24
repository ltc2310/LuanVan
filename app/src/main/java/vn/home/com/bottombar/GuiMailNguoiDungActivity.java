package vn.home.com.bottombar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.home.com.model.PhongTro;

public class GuiMailNguoiDungActivity extends AppCompatActivity {

    TextView tvEmailNguoiGui;
    EditText edtNoiDungCanGui;
    Button btnGuiMailChoNguoiDung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_mail_nguoi_dung);
        addControls();
        addEvents();

    }

    private void addEvents() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("PHONGTRO_BUNDLE");
        final PhongTro phongtro = (PhongTro) bundle.getSerializable("PHONGTRO");
        tvEmailNguoiGui.setText(phongtro.email);
        final String tieuDe = "Phòng trọ C&D thông báo: ";
        final String tieuDeKhiGui = "Vui lòng chọn trình gửi mail";
        btnGuiMailChoNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String noiDung = edtNoiDungCanGui.getText().toString();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" +phongtro.email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, tieuDe);
                emailIntent.putExtra(Intent.EXTRA_TEXT,"Xin chào anh/chị \n" +
                        " Tin đăng" + phongtro.diaChi.diaChiChiTiet +" "+ phongtro.diaChi.quan +" "+
                        phongtro.diaChi.thanhPho + " của anh/chị \n"+
                        noiDung +
                        "\n Vui lòng kiểm tra lại hoặc liên hệ admin để được hỗ trợ \n Xin cảm ơn!");
                startActivity(Intent.createChooser(emailIntent, tieuDeKhiGui));
            }
        });

    }

    private void addControls() {
        tvEmailNguoiGui = (TextView) findViewById(R.id.tvEmailNguoiGui);
        edtNoiDungCanGui = (EditText) findViewById(R.id.edtNoiDungCanGui);
        btnGuiMailChoNguoiDung = (Button) findViewById(R.id.btnGuiMailChoNguoiDung);

    }

}
