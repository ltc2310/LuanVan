package vn.home.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import vn.home.com.adapter.PhongTroAdapter;
import vn.home.com.adapter.PhongTroCanMuonAdapter;
import vn.home.com.bottombar.ChiTietCanTimActivity;
import vn.home.com.bottombar.DangNhapActivity;
import vn.home.com.bottombar.DangTinChoThueActivity;
import vn.home.com.bottombar.DangTinTimPhongActivity;
import vn.home.com.bottombar.R;
import vn.home.com.bottombar.XemChiTietActivity;
import vn.home.com.model.PhongTro;
import vn.home.com.model.PhongTroCanMuon;

/**
 * Created by THANHCONG on 2/26/2017.
 */

public class TimelineFragment extends Fragment {

    private TabHost tabHost;
    private ListView lvTimeLine, lvTimeLine1;
    private PhongTroAdapter phongTroAdapter;
    private ArrayList<PhongTro> dsPhongTro;
    private Button btnDangTinNgay, btnDangTinTimPhongNgay;
    private PhongTroCanMuonAdapter phongTroCanMuonAdapter;
    private ArrayList<PhongTroCanMuon> dsPhongTroCanTim;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);
        lvTimeLine = (ListView) v.findViewById(R.id.lvTimeLine);
        lvTimeLine1 = (ListView) v.findViewById(R.id.lvTimeLine1);
        btnDangTinTimPhongNgay = (Button) v.findViewById(R.id.btnDangTinTimPhongNgay);

        tabHost = (TabHost) v.findViewById(R.id.tabHost1);
        tabHost.setup();

        //add tab1
        TabHost.TabSpec tab1=tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Cho mướn");
        tabHost.addTab(tab1);

        //add tab2
        TabHost.TabSpec tab2=tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Cần mướn");
        tabHost.addTab(tab2);


        btnDangTinNgay = (Button) v.findViewById(R.id.btnDangTinNgay);
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        btnDangTinNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = auth.getCurrentUser();
                if(user !=null){
                    Intent intent = new Intent(getActivity(), DangTinChoThueActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                    startActivity(intent);
                }
            }
        });

        dsPhongTro = new ArrayList<>();
        mDatabase.child("phongtro").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PhongTro phongTro = dataSnapshot.getValue(PhongTro.class);
                if (phongTro.kichHoat == true){
                    dsPhongTro.add(phongTro);
                    phongTroAdapter = new PhongTroAdapter(getActivity(), R.layout.item, dsPhongTro);
                    lvTimeLine.setAdapter(phongTroAdapter);
                    lvTimeLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            PhongTro a = dsPhongTro.get(position);
                            Intent intent = new Intent(getActivity(), XemChiTietActivity.class);
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

        btnDangTinTimPhongNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = auth.getCurrentUser();
                if(user !=null){
                    Intent intent = new Intent(getActivity(), DangTinTimPhongActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                    startActivity(intent);
                }
            }
        });

        dsPhongTroCanTim = new ArrayList<>();
        mDatabase.child("cantim").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PhongTroCanMuon phongTroCanMuon = dataSnapshot.getValue(PhongTroCanMuon.class);
                if (phongTroCanMuon.kichHoat == true){
                    dsPhongTroCanTim.add(phongTroCanMuon);
                    phongTroCanMuonAdapter = new PhongTroCanMuonAdapter(getActivity(), R.layout.item_canmuon, dsPhongTroCanTim);
                    lvTimeLine1.setAdapter(phongTroCanMuonAdapter);
                    lvTimeLine1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            PhongTroCanMuon b = dsPhongTroCanTim.get(position);
                            Intent intent = new Intent(getActivity(), ChiTietCanTimActivity.class);
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




        return v;
    }

}