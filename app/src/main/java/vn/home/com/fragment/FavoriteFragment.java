package vn.home.com.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import vn.home.com.adapter.PhongTroAdapter;
import vn.home.com.adapter.UnlikePhongTroAdapter;
import vn.home.com.adapter.UnlikePhongTroCanMuonAdapter;
import vn.home.com.bottombar.ChiTietCanTimActivity;
import vn.home.com.bottombar.R;
import vn.home.com.bottombar.XemChiTietActivity;
import vn.home.com.model.PhongTro;
import vn.home.com.model.PhongTroCanMuon;
import vn.home.com.model.PhongTroQuanTam;
import vn.home.com.model.PhongTroYeuThich;

/**
 * Created by THANHCONG on 2/26/2017.
 */

public class FavoriteFragment extends Fragment {

    private TabHost tabHost;
    private ListView lvFavorite;
    private UnlikePhongTroAdapter phongTroAdapter;
    private ArrayList<PhongTro> dsPhongTro;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private ArrayList<String> dsMaYeuThich;

    private ListView lvFavorite1;
    private UnlikePhongTroCanMuonAdapter phongTroCanMuonAdapter;
    private ArrayList<PhongTroCanMuon> dsPhongTroCanMuon;
    private ArrayList<String> dsQuanTam;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        lvFavorite = (ListView) v.findViewById(R.id.lvFavorite);
        lvFavorite1 = (ListView) v.findViewById(R.id.lvFavorite1);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        dsPhongTro = new ArrayList<>();
        dsPhongTroCanMuon = new ArrayList<>();
        dsMaYeuThich = new ArrayList<>();
        dsQuanTam = new ArrayList<>();

        tabHost = (TabHost) v.findViewById(R.id.tabHost2);
        tabHost.setup();

        //add tab1
        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Cho mướn");
        tabHost.addTab(tab1);

        //add tab2
        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Cần mướn");
        tabHost.addTab(tab2);

        mDatabase.child("phongtroyeuthich").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PhongTroYeuThich phongTroYeuThich = dataSnapshot.getValue(PhongTroYeuThich.class);
                if (phongTroYeuThich.email.toString().equals(auth.getCurrentUser().getEmail()))
                    dsMaYeuThich.add(phongTroYeuThich.maPhongTroYeuThich);
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

        mDatabase.child("phongtro").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PhongTro phongTro = dataSnapshot.getValue(PhongTro.class);
                for (String maPhong : dsMaYeuThich)
                if (maPhong.equals(phongTro.id)){
                    dsPhongTro.add(phongTro);
                    if (getActivity() !=null){
                        phongTroAdapter = new UnlikePhongTroAdapter(getActivity(), R.layout.item_unkike, dsPhongTro);
                        lvFavorite.setAdapter(phongTroAdapter);
                        lvFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                PhongTro phongTroYeuThich = dsPhongTro.get(i);
                                Intent intent = new Intent(getActivity(), XemChiTietActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("PHONGTRO", phongTroYeuThich);
                                intent.putExtra("MY_BUNDLE", bundle);
                                startActivity(intent);
                            }
                        });
                    }
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

        mDatabase.child("phongtroquantam").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PhongTroQuanTam phongTroQuanTam = dataSnapshot.getValue(PhongTroQuanTam.class);
                if (phongTroQuanTam.email.toString().equals(auth.getCurrentUser().getEmail()))
                    dsQuanTam.add(phongTroQuanTam.maPhongTroQuanTam);
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
                for (String maPhong : dsQuanTam)
                    if (maPhong.equals(phongTroCanMuon.idPhongTroCM)){
                        dsPhongTroCanMuon.add(phongTroCanMuon);
                        if (getActivity() !=null){
                            phongTroCanMuonAdapter = new UnlikePhongTroCanMuonAdapter(getActivity(), R.layout.item_canmuon_unlike, dsPhongTroCanMuon);
                            lvFavorite1.setAdapter(phongTroCanMuonAdapter);
                            lvFavorite1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    PhongTroCanMuon phongTroCanMuon = dsPhongTroCanMuon.get(i);
                                    Intent intent = new Intent(getActivity(), ChiTietCanTimActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("PHONGTROCANMUON", phongTroCanMuon);
                                    intent.putExtra("MY_BUNDLE1", bundle);
                                    startActivity(intent);
                                }
                            });
                        }
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
