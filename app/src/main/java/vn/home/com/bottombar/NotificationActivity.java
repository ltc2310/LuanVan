package vn.home.com.bottombar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vn.home.com.adapter.PhongTroDeCuAdapter;
import vn.home.com.model.PhongTro;

public class NotificationActivity extends AppCompatActivity {

    ListView lvPhongTro;
    List<PhongTro> dsPhongTro;
    PhongTroDeCuAdapter phongTroDeCuAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        dsPhongTro = new ArrayList<>();
        lvPhongTro= (ListView) findViewById(R.id.lvPhongTro);
        Intent itent = getIntent();
        dsPhongTro = (List<PhongTro>) itent
                .getSerializableExtra("LIST_PTRODECU");


        phongTroDeCuAdapter =new PhongTroDeCuAdapter(NotificationActivity.this,R.layout.activity_notification_item,dsPhongTro);
        lvPhongTro.setAdapter(phongTroDeCuAdapter);
    }
}
