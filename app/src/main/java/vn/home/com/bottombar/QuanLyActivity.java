package vn.home.com.bottombar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import vn.home.com.adapter.DuyetTinCanMuonAdapter;
import vn.home.com.adapter.DuyetTinChoMuonAdapter;
import vn.home.com.adapter.QuanLyTinCanMuonAdapter;
import vn.home.com.adapter.QuanLyTinChoMuonAdapter;
import vn.home.com.model.PhongTro;
import vn.home.com.model.PhongTroCanMuon;

public class QuanLyActivity extends AppCompatActivity {

    private ArrayList<PhongTro> dsPhongTro;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private TabHost tabHostQuanLy;
    private ListView lvQuanLyChoMuon, lvQuanLyCanMuon;
    private ArrayList<PhongTroCanMuon> dsPhongTroCanMuon;
    private QuanLyTinChoMuonAdapter quanLyTinChoMuonAdapter;
    private QuanLyTinCanMuonAdapter quanLyTinCanMuonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);

        lvQuanLyChoMuon = (ListView) findViewById(R.id.lvQuanLyChoMuon);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        dsPhongTro = new ArrayList<>();
        dsPhongTroCanMuon = new ArrayList<>();

        lvQuanLyCanMuon = (ListView) findViewById(R.id.lvQuanLyCanMuon);

        tabHostQuanLy = (TabHost) findViewById(R.id.tabHostQuanLy);
        tabHostQuanLy.setup();

        //add tab1
        TabHost.TabSpec tab1 = tabHostQuanLy.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Cho mướn");
        tabHostQuanLy.addTab(tab1);

        //add tab2
        TabHost.TabSpec tab2 = tabHostQuanLy.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Cần mướn");
        tabHostQuanLy.addTab(tab2);

        mDatabase.child("phongtro").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PhongTro phongTro = dataSnapshot.getValue(PhongTro.class);
                if (phongTro.email.equals(auth.getCurrentUser().getEmail())){
                    dsPhongTro.add(phongTro);
                    quanLyTinChoMuonAdapter = new QuanLyTinChoMuonAdapter(QuanLyActivity.this, R.layout.item_quanly, dsPhongTro);
                    lvQuanLyChoMuon.setAdapter(quanLyTinChoMuonAdapter);
                    lvQuanLyChoMuon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            PhongTro a = dsPhongTro.get(position);
                            Intent intent = new Intent(QuanLyActivity.this, SuaTinChoMuonActivity.class);
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
                if (phongTroCanMuon.emailPhongTroCM.equals(auth.getCurrentUser().getEmail())){
                    dsPhongTroCanMuon.add(phongTroCanMuon);
                    quanLyTinCanMuonAdapter = new QuanLyTinCanMuonAdapter(QuanLyActivity.this, R.layout.item_quanly, dsPhongTroCanMuon);
                    lvQuanLyCanMuon.setAdapter(quanLyTinCanMuonAdapter);
                    lvQuanLyCanMuon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            PhongTroCanMuon b = dsPhongTroCanMuon.get(position);
                            Intent intent = new Intent(QuanLyActivity.this, SuaTinCanMuonActivity.class);
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
