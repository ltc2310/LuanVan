package vn.home.com.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vn.home.com.bottombar.R;
import vn.home.com.model.PhongTro;
import vn.home.com.model.PhongTroCanMuon;

/**
 * Created by THANHCONG-PC on 6/24/2017.
 */

public class DuyetTinCanMuonAdapter extends ArrayAdapter<PhongTroCanMuon> {
    Activity context;
    int resource;
    List<PhongTroCanMuon> objects;

    public DuyetTinCanMuonAdapter(Activity context, int resource, List<PhongTroCanMuon> objects) {
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


        final PhongTroCanMuon phongTro = this.objects.get(position);
        txtGia.setText(phongTro.giaPhongMin + " VNĐ - " + phongTro.giaPhongMax + " VNĐ");
        txtDienTich.setText(phongTro.dienTich + " mét vuông");
        txtDiaChi.setText(phongTro.diaChi);
        txtNgayDang.setText(phongTro.ngayDang);
        return row;
    }
}
