package vn.home.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.home.com.adapter.PhongTroAdapter;
import vn.home.com.bottombar.DangNhapActivity;
import vn.home.com.bottombar.DangTinChoThueActivity;
import vn.home.com.bottombar.QuenMatKhauActivity;
import vn.home.com.bottombar.R;
import vn.home.com.bottombar.XemChiTietActivity;
import vn.home.com.model.DiaChi;
import vn.home.com.model.PhongTro;

/**
 * Created by THANHCONG on 2/26/2017.
 */

public class TimelineFragment extends Fragment {

    ListView lvTimeLine;
    PhongTroAdapter phongTroAdapter;
    ArrayList<PhongTro> dsPhongTro;
    Button btnDangTinNgay;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);
        lvTimeLine = (ListView) v.findViewById(R.id.lvTimeLine);
        btnDangTinNgay = (Button) v.findViewById(R.id.btnDangTinNgay);

        btnDangTinNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DangTinChoThueActivity.class);
                startActivity(intent);
            }
        });



        dsPhongTro = new ArrayList<>();
        DiaChi diaChi = new DiaChi("Hồ Chí Minh","Gò Vấp","637/33 Quang Trung Phường 11");
        ArrayList<String> linkHinh = new ArrayList<>();
        linkHinh.add("https://www.bestprice.vn/images/hotels/uploads/khach-san-buu-dien-ha-long-5350fb814f175.jpg");
        PhongTro phongTro = new PhongTro(new Date(2017,4,8),diaChi,2000000D,20,linkHinh);
        PhongTro phongTro1 = new PhongTro(new Date(2017,4,8),diaChi,2000000D,20,linkHinh);
        PhongTro phongTro2 = new PhongTro(new Date(2017,4,8),diaChi,2000000D,20,linkHinh);
        dsPhongTro.add(phongTro);
        dsPhongTro.add(phongTro1);
        dsPhongTro.add(phongTro2);
        phongTroAdapter = new PhongTroAdapter(getActivity(), R.layout.item, dsPhongTro);
        lvTimeLine.setAdapter(phongTroAdapter);
        lvTimeLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhongTro a = dsPhongTro.get(position);
                Intent intent = new Intent(getActivity(), XemChiTietActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PHONGTRO", a);
                intent.putExtra("MY_BUNDLE", bundle);
                startActivity(intent);
            }
        });
        return v;
    }

}
