package vn.home.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vn.home.com.bottombar.MainActivity;
import vn.home.com.bottombar.R;
import vn.home.com.fragment.TimelineFragment;
import vn.home.com.model.PhongTro;

/**
 * Created by THANHCONG on 2/22/2017.
 */

public class PhongTroAdapter extends ArrayAdapter<PhongTro> {
    Activity context;
    int resource;
    List<PhongTro> objects;
    String phongTroYeuThich = "TrangThaiPhongTro";



    public PhongTroAdapter(Activity context, int resource, List<PhongTro> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        ImageView imgHinh = (ImageView) row.findViewById(R.id.imgHinh);
        TextView txtDiaChi = (TextView) row.findViewById(R.id.txtDiaChi);
        TextView txtGia = (TextView) row.findViewById(R.id.txtGiaPhong);
        TextView txtDienTich = (TextView) row.findViewById(R.id.txtDienTich);
        TextView txtNgayDang = (TextView) row.findViewById(R.id.txtNgayDang);
        Button btnLike = (Button) row.findViewById(R.id.btnLike);


        final PhongTro phongTro = this.objects.get(position);
        txtGia.setText(phongTro.giaPhong + " triệu");
        txtDienTich.setText(phongTro.dienTich + " mét vuông");
        txtDiaChi.setText(phongTro.diaChi.diaChiChiTiet + ", " + phongTro.diaChi.quan + ", " +phongTro.diaChi.thanhPho);
        if (phongTro.linkHinh != null) {
            Picasso.with(context).load(phongTro.linkHinh.get(0)).into(imgHinh);
        }else {
            Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/mylogin-c65fa.appspot.com/o/Photos%2F1.jpg?alt=media&token=aa387a71-52e3-46a5-a5a8-8712d3220ad3").into(imgHinh);
        }
        txtNgayDang.setText(phongTro.ngayDang);

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              xuLyThich(phongTro);
            }
        });

        return row;
    }
    Set<String> list = new HashSet<>();
    private void xuLyThich(PhongTro phongTro) {
        SharedPreferences preferences = getContext().getSharedPreferences(phongTroYeuThich,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        list.add(phongTro.id);
        editor.putStringSet("PHONGTRO",list);
        editor.commit();
    }


}