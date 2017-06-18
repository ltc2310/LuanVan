package vn.home.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vn.home.com.bottombar.R;
import vn.home.com.model.PhongTro;

/**
 * Created by THANHCONG-PC on 6/13/2017.
 */

public class DuyetTinChoMuonAdapter extends ArrayAdapter<PhongTro> {
    Activity context;
    int resource;
    List<PhongTro> objects;


    public DuyetTinChoMuonAdapter(Activity context, int resource, List<PhongTro> objects) {
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
        TextView txtDiaChi = (TextView) row.findViewById(R.id.txtDiaChiDTCM);
        TextView txtGia = (TextView) row.findViewById(R.id.txtGiaPhongDTCM);
        TextView txtDienTich = (TextView) row.findViewById(R.id.txtDienTichDTCM);
        TextView txtNgayDang = (TextView) row.findViewById(R.id.txtNgayDangDTCM);


        final PhongTro phongTro = this.objects.get(position);
        txtGia.setText(phongTro.giaPhong + " VNĐ");
        txtDienTich.setText(phongTro.dienTich + " mét vuông");
        txtDiaChi.setText(phongTro.diaChi.diaChiChiTiet + ", " + phongTro.diaChi.quan + ", " +phongTro.diaChi.thanhPho);
        txtNgayDang.setText(phongTro.ngayDang);
        return row;
    }

}
