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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    ImageButton btnLike;
    TextView txtDiaChi, txtGia, txtDienTich, txtNgayDang;
    ImageView imgHinh;


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
        imgHinh = (ImageView) row.findViewById(R.id.imgHinh);
        txtDiaChi = (TextView) row.findViewById(R.id.txtDiaChi);
        txtGia = (TextView) row.findViewById(R.id.txtGiaPhong);
        txtDienTich = (TextView) row.findViewById(R.id.txtDienTich);
        txtNgayDang = (TextView) row.findViewById(R.id.txtNgayDang);
        btnLike = (ImageButton) row.findViewById(R.id.btnLike);

        final PhongTro phongTro = this.objects.get(position);
        txtGia.setText(phongTro.giaPhong + " VNĐ");
        txtDienTich.setText(phongTro.dienTich + " mét vuông");
        txtDiaChi.setText(phongTro.diaChi.diaChiChiTiet + ", " + phongTro.diaChi.quan + ", " + phongTro.diaChi.thanhPho);
        if (phongTro.linkHinh != null) {
            Picasso.with(context).load(phongTro.linkHinh.get(0)).into(imgHinh);
        } else {
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

    private void xuLyThich(PhongTro phongTro) {
        SharedPreferences preferences = getContext().getSharedPreferences(phongTroYeuThich, Context.MODE_PRIVATE);
        Set<String> list;
        if (preferences.getAll().size() != 0) {
            list = preferences.getStringSet("PHONGTRO", null);
        } else {
            list = new HashSet<>();
        }
        SharedPreferences.Editor editor = preferences.edit();
        list.add(phongTro.id);
        editor.putStringSet("PHONGTRO", list);
        editor.apply();
        Toast.makeText(getContext(), "Đã thêm phòng trọ vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
    }


}