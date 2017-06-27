package vn.home.com.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vn.home.com.bottombar.R;
import vn.home.com.model.PhongTroCanMuon;

/**
 * Created by THANHCONG-PC on 6/25/2017.
 */

public class QuanLyTinCanMuonAdapter extends ArrayAdapter<PhongTroCanMuon> {
    Activity context;
    int resource;
    List<PhongTroCanMuon> objects;

    public QuanLyTinCanMuonAdapter(Activity context, int resource, List<PhongTroCanMuon> objects) {
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
        TextView txtDiaChi = (TextView) row.findViewById(R.id.txtDiaChiQL);
        TextView txtGia = (TextView) row.findViewById(R.id.txtGiaPhongQL);
        TextView txtDienTich = (TextView) row.findViewById(R.id.txtDienTichQL);
        TextView txtNgayDang = (TextView) row.findViewById(R.id.txtNgayDangQL);
        TextView txtTrangThai = (TextView) row.findViewById(R.id.txtTrangThai);


        final PhongTroCanMuon phongTro = this.objects.get(position);
        txtGia.setText(phongTro.giaPhongMin + " VNĐ - " + phongTro.giaPhongMax + " VNĐ");
        txtDienTich.setText(phongTro.dienTich + " mét vuông");
        txtDiaChi.setText(phongTro.diaChi);
        txtNgayDang.setText(phongTro.ngayDang);
        if (phongTro.kichHoat == true){
            txtTrangThai.setText("Tin đã được đăng");
            txtTrangThai.setTextColor(Color.GREEN);
        }else {
            txtTrangThai.setText("Tin đang chờ duyệt");
            txtTrangThai.setTextColor(Color.RED);
        }
        return row;
    }
}
