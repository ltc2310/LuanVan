package vn.home.com.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.home.com.bottombar.R;
import vn.home.com.bottombar.XemChiTietActivity;
import vn.home.com.model.PhongTro;

/**
 * Created by ntdan on 6/21/2017.
 */

public class PhongTroDeCuAdapter extends ArrayAdapter<PhongTro> {

    Activity context;
    int resource;
    List<PhongTro> objects;
    TextView txtTenNguoiDung, txtEmail, txtSDT, txtMoTa;
    LinearLayout lnPhongTro;

    public PhongTroDeCuAdapter(Activity context, int resource, List<PhongTro> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        //notification view
        txtTenNguoiDung = (TextView) row.findViewById(R.id.txtTenNguoiDung);
        txtEmail = (TextView) row.findViewById(R.id.txtEmail);
        txtSDT = (TextView) row.findViewById(R.id.txtSDT);
        txtMoTa = (TextView) row.findViewById(R.id.txtMoTa);
        lnPhongTro= (LinearLayout) row.findViewById(R.id.lnPhongTro);

        final PhongTro pTro =this.objects.get(position);
        txtTenNguoiDung.setText("Tên chủ trọ: "+pTro.tenNguoiDung);
        txtEmail.setText("Email: "+pTro.email);
        txtSDT.setText("SĐT: "+pTro.sdt);
        txtMoTa.setText("Mô tả: "+pTro.moTa);

        lnPhongTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), XemChiTietActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PHONGTRO",pTro);
                intent.putExtra("MY_BUNDLE", bundle);
                context.startActivity(intent);
            }
        });
        return row;

    }
}
