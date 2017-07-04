package vn.home.com.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import vn.home.com.bottombar.R;
import vn.home.com.model.PhongTro;

/**
 * Created by THANHCONG-PC on 6/25/2017.
 */

public class QuanLyTinChoMuonAdapter extends ArrayAdapter<PhongTro> {
    Activity context;
    int resource;
    List<PhongTro> objects;


    public QuanLyTinChoMuonAdapter(Activity context, int resource, List<PhongTro> objects) {
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


        final PhongTro phongTro = this.objects.get(position);

        txtDienTich.setText(phongTro.dienTich + " mét vuông");
        txtDiaChi.setText(phongTro.diaChi.diaChiChiTiet + ", " + phongTro.diaChi.quan + ", " + phongTro.diaChi.thanhPho);
        txtNgayDang.setText(phongTro.ngayDang);

        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String giaPhong = currencyFormatter.format(phongTro.giaPhong);

        txtGia.setText(giaPhong);

        if (phongTro.kichHoat == false) {
            txtTrangThai.setText("Tin đang chờ duyệt");
            txtTrangThai.setTextColor(Color.RED);
        }

        if (phongTro.kichHoat == true && phongTro.ngungDangTin == false) {
            txtTrangThai.setText("Tin đang được đăng");
            txtTrangThai.setTextColor(Color.GREEN);
        }

        if (phongTro.kichHoat == true && phongTro.ngungDangTin == true) {
            txtTrangThai.setText("Tin đã dừng đăng");
            txtTrangThai.setTextColor(Color.GRAY);
        }
        return row;
    }
}
