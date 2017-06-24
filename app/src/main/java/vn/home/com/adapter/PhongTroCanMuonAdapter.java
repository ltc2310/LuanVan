package vn.home.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.home.com.bottombar.R;
import vn.home.com.model.PhongTroCanMuon;
import vn.home.com.model.PhongTroQuanTam;


/**
 * Created by THANHCONG-PC on 6/18/2017.
 */

public class PhongTroCanMuonAdapter extends ArrayAdapter<PhongTroCanMuon> {
    Activity context;
    int resource;
    List<PhongTroCanMuon> objects;
    ImageButton btnLikeTimPhong;
    TextView txtDiaChi, txtGia, txtDienTich, txtNgayDang;
    ImageView imgHinhCanTim;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    ArrayList<String> dsPhongTroQuanTam;



    public PhongTroCanMuonAdapter(Activity context, int resource, List<PhongTroCanMuon> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;

        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        dsPhongTroQuanTam = new ArrayList<>();

        if (auth.getCurrentUser() != null) {
            databaseReference.child("phongtroquantam").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    PhongTroQuanTam phongTroQuanTam = dataSnapshot.getValue(PhongTroQuanTam.class);
                    if (phongTroQuanTam.email.toString().equals(auth.getCurrentUser().getEmail()))
                        dsPhongTroQuanTam.add(phongTroQuanTam.maPhongTroQuanTam);
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

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        txtDiaChi = (TextView) row.findViewById(R.id.txtDiaChiCAM);
        txtGia = (TextView) row.findViewById(R.id.txtGiaPhongCAM);
        txtDienTich = (TextView) row.findViewById(R.id.txtDienTichCAM);
        txtNgayDang = (TextView) row.findViewById(R.id.txtNgayDangCAM);
        btnLikeTimPhong = (ImageButton) row.findViewById(R.id.btnLikeTimPhong);
        imgHinhCanTim = (ImageView) row.findViewById(R.id.imgHinhCanTim);


        final PhongTroCanMuon phongTro = this.objects.get(position);
        txtGia.setText(phongTro.giaPhongMin + " VNĐ" + " - "  + phongTro.giaPhongMax + " VNĐ");
        txtDienTich.setText(phongTro.dienTich + " mét vuông");
        txtDiaChi.setText(phongTro.diaChi);
        txtNgayDang.setText(phongTro.ngayDang);

        Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/mylogin-c65fa.appspot.com/o/Photos%2Fcantim.jpg?alt=media&token=3c85b848-0b08-4454-bdb5-b56b9e5bbb49").into(imgHinhCanTim);

        btnLikeTimPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThich(phongTro);
            }
        });

        return row;
    }

    int check = 0;
    private void xuLyThich(final PhongTroCanMuon phongTro) {
        if (auth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "Vui lòng đăng nhập để thêm phòng trọ yêu thích", Toast.LENGTH_SHORT).show();
        } else {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("phongtroquantam").exists()) {
                        for (String item : dsPhongTroQuanTam) {
                            if (item.toString().equals(phongTro.idPhongTroCM)) {
                                check = 1;
                                break;
                            } else {
                                check = 2;
                            }
                        }
                        if (check == 2) {
                            xuLyThemPhongTroVaoDanhSachQuanTam(phongTro);
                            dsPhongTroQuanTam.add(phongTro.idPhongTroCM);
                            check = 0;
                        } else if (check == 1) {
                            Toast.makeText(getContext(), "Phòng trọ đã được thêm phòng trọ vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        xuLyThemPhongTroVaoDanhSachQuanTam(phongTro);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void xuLyThemPhongTroVaoDanhSachQuanTam(PhongTroCanMuon phongTro) {
        String email = auth.getCurrentUser().getEmail();
        String key = databaseReference.push().getKey();
        databaseReference.child("phongtroquantam").child(key).setValue(new PhongTroQuanTam(key, email, phongTro.idPhongTroCM));
        Toast.makeText(getContext(), "Đã thêm tin vào danh sách quan tâm", Toast.LENGTH_SHORT).show();
    }
}
