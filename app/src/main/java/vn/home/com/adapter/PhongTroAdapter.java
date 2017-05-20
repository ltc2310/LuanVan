package vn.home.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

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


    public PhongTroAdapter(Activity context, int resource, List<PhongTro> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);
        ImageView imgHinh = (ImageView) row.findViewById(R.id.imgHinh);
        TextView txtDiaChi = (TextView) row.findViewById(R.id.txtDiaChi);
        TextView txtGia = (TextView) row.findViewById(R.id.txtGiaPhong);
        TextView txtDienTich = (TextView) row.findViewById(R.id.txtDienTich);
        TextView txtNgayDang = (TextView) row.findViewById(R.id.txtNgayDang);

        PhongTro phongTro = this.objects.get(position);
        txtGia.setText(phongTro.giaPhong + "");
        txtDienTich.setText(phongTro.dienTich +"");
        txtDiaChi.setText(phongTro.diaChi.diaChiChiTiet+"");
        Picasso.with(context).load(phongTro.linkHinh.get(0)).into(imgHinh);
        txtNgayDang.setText(phongTro.ngayDang.getDay()+"/"+phongTro.ngayDang.getMonth()+"/"+phongTro.ngayDang.getYear());
        return row;
    }
}
