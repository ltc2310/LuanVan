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

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;

import vn.home.com.bottombar.R;
import vn.home.com.model.PhongTroCanMuon;

/**
 * Created by THANHCONG-PC on 6/18/2017.
 */

public class UnlikePhongTroCanMuonAdapter extends ArrayAdapter<PhongTroCanMuon> {
    Activity context;
    int resource;
    List<PhongTroCanMuon> objects;
    String phongTroQuanTam = "PhongTroQuanTam";
    ImageButton btnUnlikeTimPhong;
    TextView txtDiaChi, txtGia, txtDienTich, txtNgayDang;
    ImageView imgHinhCanTim;

    public UnlikePhongTroCanMuonAdapter(Activity context, int resource, List<PhongTroCanMuon> objects) {
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
        txtDiaChi = (TextView) row.findViewById(R.id.txtDiaChiCAM1);
        txtGia = (TextView) row.findViewById(R.id.txtGiaPhongCAM1);
        txtDienTich = (TextView) row.findViewById(R.id.txtDienTichCAM1);
        txtNgayDang = (TextView) row.findViewById(R.id.txtNgayDangCAM1);
        btnUnlikeTimPhong = (ImageButton) row.findViewById(R.id.btnUnlikeTimPhong);
        imgHinhCanTim = (ImageView) row.findViewById(R.id.imgHinhCanTim1);

        final PhongTroCanMuon phongTro = this.objects.get(position);
        txtGia.setText(phongTro.giaPhongMin + " VNĐ" + " - " + phongTro.giaPhongMax + " VNĐ");
        txtDienTich.setText(phongTro.dienTich + " mét vuông");
        txtDiaChi.setText(phongTro.diaChi);
        txtNgayDang.setText(phongTro.ngayDang);
        Picasso.with(context).load("https://firebasestorage.googleapis.com/v0/b/mylogin-c65fa.appspot.com/o/Photos%2Fcantim.jpg?alt=media&token=3c85b848-0b08-4454-bdb5-b56b9e5bbb49").into(imgHinhCanTim);


        btnUnlikeTimPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyKhongThich(phongTro);
            }
        });

        return row;
    }

    private void xuLyKhongThich(PhongTroCanMuon phongTro) {
        SharedPreferences preferences = getContext().getSharedPreferences(phongTroQuanTam, Context.MODE_PRIVATE);
        Set<String> list;
        if (preferences.getAll().size() != 0) {
            list = preferences.getStringSet("PHONGTROQUANTAM", null);
            list.remove(phongTro.idPhongTroCM);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet("PHONGTROQUANTAM", list);
            editor.apply();
            Toast.makeText(getContext(), "Đã xóa phòng trọ khỏi danh sách quan tâm", Toast.LENGTH_SHORT).show();
        }
    }

}
