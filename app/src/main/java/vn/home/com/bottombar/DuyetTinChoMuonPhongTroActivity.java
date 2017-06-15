package vn.home.com.bottombar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import vn.home.com.adapter.DuyetTinChoMuonAdapter;
import vn.home.com.model.PhongTro;

public class DuyetTinChoMuonPhongTroActivity extends AppCompatActivity {

    private ListView lvDuyetTinDang;
    private DuyetTinChoMuonAdapter duyetTinChoMuonAdapter;
    private ArrayList<PhongTro> dsPhongTro;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyet_tin_cho_muon_phong_tro);
        lvDuyetTinDang = (ListView) findViewById(R.id.lvDuyetTinDang);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dsPhongTro = new ArrayList<>();

        mDatabase.child("phongtro").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PhongTro phongTro = dataSnapshot.getValue(PhongTro.class);
                if (phongTro.kichHoat == false){
                    dsPhongTro.add(phongTro);
                    duyetTinChoMuonAdapter = new DuyetTinChoMuonAdapter(DuyetTinChoMuonPhongTroActivity.this, R.layout.itemduyettinchomuon, dsPhongTro);
                    lvDuyetTinDang.setAdapter(duyetTinChoMuonAdapter);
                    lvDuyetTinDang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            PhongTro a = dsPhongTro.get(position);
                            Intent intent = new Intent(DuyetTinChoMuonPhongTroActivity.this, DuyetTinChoMuonActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("PHONGTRO", a);
                            intent.putExtra("MY_BUNDLE", bundle);
                            startActivity(intent);
                        }
                    });

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
