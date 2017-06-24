package vn.home.com.bottombar;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import vn.home.com.model.PhongTro;
import vn.home.com.model.PhongTroCanMuon;

public class GuiMailTuChoiTinCanMuonActivity extends AppCompatActivity {

    TextView tvEmailNguoiGuiDT;
    EditText edtNoiDungCanGuiDT;
    Button btnGuiMailChoNguoiDungDT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_mail_tu_choi_tin_can_muon);
        addControls();
        addEvents();
    }

    private void addEvents() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("PHONGTRO_CANMUON_BUNDLE");
        final PhongTroCanMuon phongtro = (PhongTroCanMuon) bundle.getSerializable("PHONGTROCANMUON");
        tvEmailNguoiGuiDT.setText(phongtro.emailPhongTroCM);
        final String tieuDe = "Phòng trọ C&D thông báo: ";
        final String tieuDeKhiGui = "Vui lòng chọn trình gửi mail";
        btnGuiMailChoNguoiDungDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String noiDung = edtNoiDungCanGuiDT.getText().toString();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" +phongtro.emailPhongTroCM));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, tieuDe);
                emailIntent.putExtra(Intent.EXTRA_TEXT,"Xin chào anh/chị \n" +
                        " Tin đăng" + phongtro.diaChi + " của anh/chị \n"+
                        noiDung +
                        "\n Vui lòng kiểm tra lại hoặc liên hệ admin để được hỗ trợ \n Xin cảm ơn!");
                startActivity(Intent.createChooser(emailIntent, tieuDeKhiGui));
            }
        });

    }

    private void addControls() {
        tvEmailNguoiGuiDT = (TextView) findViewById(R.id.tvEmailNguoiGuiDT);
        edtNoiDungCanGuiDT = (EditText) findViewById(R.id.edtNoiDungCanGuiDT);
        btnGuiMailChoNguoiDungDT = (Button) findViewById(R.id.btnGuiMailChoNguoiDungDT);

    }
}
