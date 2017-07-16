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
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import vn.home.com.bottombar.R;
import vn.home.com.model.PhongTroCanMuon;
import vn.home.com.model.PhongTroQuanTam;
import vn.home.com.model.PhongTroYeuThich;

/**
 * Created by THANHCONG-PC on 6/18/2017.
 */

public class UnlikePhongTroCanMuonAdapter extends ArrayAdapter<PhongTroCanMuon> {
    Activity context;
    int resource;
    List<PhongTroCanMuon> objects;
    ImageButton btnUnlikeTimPhong;
    TextView txtDiaChi, txtGia, txtDienTich, txtNgayDang;
    ImageView imgHinhCanTim;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    ArrayList<PhongTroQuanTam> dsPhongTroQuanTam;


    public UnlikePhongTroCanMuonAdapter(Activity context, int resource, List<PhongTroCanMuon> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        dsPhongTroQuanTam = new ArrayList<>();

        databaseReference.child("phongtroquantam").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PhongTroQuanTam phongTroQuanTam = dataSnapshot.getValue(PhongTroQuanTam.class);
                if (phongTroQuanTam.email.toString().equals(auth.getCurrentUser().getEmail()))
                    dsPhongTroQuanTam.add(phongTroQuanTam);
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

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        txtDiaChi = (TextView) row.findViewById(R.id.txtDiaChiCAM1);
        txtGia = (TextView) row.findViewById(R.id.txtGiaPhongCAM1);
        txtDienTich = (TextView) row.findViewById(R.id.txtDienTichCAM1);
        txtNgayDang = (TextView) row.findViewById(R.id.txtNgayDangCAM1);
        btnUnlikeTimPhong = (ImageButton) row.findViewById(R.id.btnUnlikeTimPhong);
        imgHinhCanTim = (ImageView) row.findViewById(R.id.imgHinhCanTim1);



        final PhongTroCanMuon phongTro = this.objects.get(position);

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String giaPhongMin =  currencyFormatter.format(phongTro.giaPhongMin);
        String giaPhongMax = currencyFormatter.format(phongTro.giaPhongMax);

        txtGia.setText(giaPhongMin + " - " + giaPhongMax);

        txtDienTich.setText(phongTro.dienTich + " mét vuông");
        txtDiaChi.setText(phongTro.diaChi);
        txtNgayDang.setText(phongTro.ngayDang);
        Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/mylogin-c65fa.appspot.com/o/Photos%2Fcantim.jpg?alt=media&token=3c85b848-0b08-4454-bdb5-b56b9e5bbb49").into(imgHinhCanTim);


        btnUnlikeTimPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyKhongThich(objects.get(position));
                objects.remove(position);
                notifyDataSetChanged();
            }
        });

        return row;
    }

    private void xuLyKhongThich(final PhongTroCanMuon phongTro) {
        for (PhongTroQuanTam item : dsPhongTroQuanTam){
            if (item.maPhongTroQuanTam.equals(phongTro.idPhongTroCM)){
                String id = item.id;
                databaseReference.child("phongtroquantam").child(id).removeValue();
                Toast.makeText(getContext(), "Đã xóa tin khỏi danh sách quan tâm", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
