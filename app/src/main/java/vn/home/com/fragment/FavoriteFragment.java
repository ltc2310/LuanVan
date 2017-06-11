package vn.home.com.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import vn.home.com.adapter.PhongTroAdapter;
import vn.home.com.bottombar.R;
import vn.home.com.bottombar.XemChiTietActivity;
import vn.home.com.model.PhongTro;

/**
 * Created by THANHCONG on 2/26/2017.
 */

public class FavoriteFragment extends Fragment {

    private ListView lvTimeFavorite;
    private PhongTroAdapter phongTroAdapter;
    private ArrayList<PhongTro> dsPhongTro;
    private DatabaseReference mDatabase;
    private String phongTroYeuThich = "TrangThaiPhongTro";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        lvTimeFavorite = (ListView) v.findViewById(R.id.lvFavorite);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        dsPhongTro = new ArrayList<>();
        mDatabase.child("phongtro").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PhongTro phongTro = dataSnapshot.getValue(PhongTro.class);
                SharedPreferences preferences = getContext().getSharedPreferences(phongTroYeuThich, Context.MODE_PRIVATE);
                String id = preferences.getString("PHONGTRO",null);
                if (phongTro.id.equals(id)){
                    dsPhongTro.add(phongTro);
                    phongTroAdapter = new PhongTroAdapter(getActivity(), R.layout.item, dsPhongTro);
                    lvTimeFavorite.setAdapter(phongTroAdapter);
                    lvTimeFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        return v;
    }

}
