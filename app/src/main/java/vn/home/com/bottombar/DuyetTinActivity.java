package vn.home.com.bottombar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import vn.home.com.adapter.DuyetTinCanMuonAdapter;
import vn.home.com.adapter.DuyetTinChoMuonAdapter;
import vn.home.com.model.PhongTro;
import vn.home.com.model.PhongTroCanMuon;

public class DuyetTinActivity extends AppCompatActivity {


    private ArrayList<PhongTro> dsPhongTro;
    private DatabaseReference mDatabase;
    private TabHost tabHostDuyetTin;
    private ListView lvDuyetTinDang, lvDuyetTinDang2;
    private ArrayList<PhongTroCanMuon> dsPhongTroCanMuon;
    private DuyetTinCanMuonAdapter duyetTinCanMuonAdapter;
    private DuyetTinChoMuonAdapter duyetTinChoMuonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyet_tin);
        lvDuyetTinDang = (ListView) findViewById(R.id.lvDuyetTinDang);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        dsPhongTro = new ArrayList<>();
        dsPhongTroCanMuon = new ArrayList<>();

        lvDuyetTinDang2 = (ListView) findViewById(R.id.lvDuyetTinDang2);

        tabHostDuyetTin = (TabHost) findViewById(R.id.tabHostDuyetTin);
        tabHostDuyetTin.setup();

        //add tab1
        TabHost.TabSpec tab1 = tabHostDuyetTin.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Cho mướn");
        tabHostDuyetTin.addTab(tab1);

        //add tab2
        TabHost.TabSpec tab2 = tabHostDuyetTin.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Cần mướn");
        tabHostDuyetTin.addTab(tab2);


        mDatabase.child("phongtro").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PhongTro phongTro = dataSnapshot.getValue(PhongTro.class);
                if (phongTro.kichHoat == false){
                    dsPhongTro.add(phongTro);
                    duyetTinChoMuonAdapter = new DuyetTinChoMuonAdapter(DuyetTinActivity.this, R.layout.itemduyettinchomuon, dsPhongTro);
                    lvDuyetTinDang.setAdapter(duyetTinChoMuonAdapter);
                    lvDuyetTinDang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            PhongTro a = dsPhongTro.get(position);
                            Intent intent = new Intent(DuyetTinActivity.this, DuyetTinChoMuonActivity.class);
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

        mDatabase.child("cantim").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PhongTroCanMuon phongTroCanMuon = dataSnapshot.getValue(PhongTroCanMuon.class);
                if (phongTroCanMuon.kichHoat == false){
                    dsPhongTroCanMuon.add(phongTroCanMuon);
                    duyetTinCanMuonAdapter = new DuyetTinCanMuonAdapter(DuyetTinActivity.this, R.layout.itemduyettinchomuon, dsPhongTroCanMuon);
                    lvDuyetTinDang2.setAdapter(duyetTinCanMuonAdapter);
                    lvDuyetTinDang2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            PhongTroCanMuon b = dsPhongTroCanMuon.get(position);
                            Intent intent = new Intent(DuyetTinActivity.this, DuyetTinCanMuonActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("PHONGTROCANMUON", b);
                            intent.putExtra("MY_BUNDLE1", bundle);
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
