package vn.home.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

import vn.home.com.bottombar.MainActivity;
import vn.home.com.bottombar.MapsActivity;
import vn.home.com.bottombar.R;

/**
 * Created by THANHCONG on 2/28/2017.
 */

public class SearchFragement extends Fragment{

    TabHost tabHost;
    Button btnTimPhongTro,btnTimPhongTroTT;
    TextView tvBanKinh,tvGiaPhong,tvDienTich,tvTinhThanh,tvGiaPhongTT,tvDienTichTT;
    SeekBar sbBanKinh,sbGiaPhong,sbDienTich,sbGiaPhongTT,sbDienTichTT;

    Spinner spTinhThanh;
    String [] arrTinhThanh;
    ArrayAdapter<String> adapterTinhThanh;

    private Double banKinh = 0D, giaPhong = 0D;
    private int dienTich = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        tabHost = (TabHost) v.findViewById(R.id.tabHost);
        tabHost.setup();

        //add tab1
        TabHost.TabSpec tab1=tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Tìm kiếm xung quanh");
        tabHost.addTab(tab1);

        //add tab2
        TabHost.TabSpec tab2=tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Tìm kiếm theo khu vực");
        tabHost.addTab(tab2);


        //add controls
        btnTimPhongTro = (Button) v.findViewById(R.id.btnTimPhongTro);
        btnTimPhongTroTT = (Button) v.findViewById(R.id.btnTimPhongTroTT);

        tvBanKinh= (TextView) v.findViewById(R.id.tvBanKinh);
        tvGiaPhong = (TextView) v.findViewById(R.id.tvGiaPhong);
        tvGiaPhongTT = (TextView) v.findViewById(R.id.tvGiaPhongTT);
        tvDienTich = (TextView) v.findViewById(R.id.tvDienTich);
        tvDienTichTT = (TextView) v.findViewById(R.id.tvDienTichTT);
        tvTinhThanh = (TextView) v.findViewById(R.id.tvTinhThanh);

        sbBanKinh = (SeekBar) v.findViewById(R.id.sbBanKinh);
        sbDienTich = (SeekBar) v.findViewById(R.id.sbDienTich);
        sbDienTichTT = (SeekBar) v.findViewById(R.id.sbDienTichTT);
        sbGiaPhong = (SeekBar) v.findViewById(R.id.sbGiaPhong);
        sbGiaPhongTT = (SeekBar) v.findViewById(R.id.sbGiaPhongTT);

        spTinhThanh = (Spinner) v.findViewById(R.id.spTinhThanh);
        arrTinhThanh= getResources().getStringArray(R.array.arrTinhThanh);
        adapterTinhThanh = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrTinhThanh);
        adapterTinhThanh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTinhThanh.setAdapter(adapterTinhThanh);


        sbBanKinh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case 1:
                        tvBanKinh.setText("Bán kính: 500 m");
                        banKinh = 0.5D;
                        break;
                    case 2:
                        tvBanKinh.setText("Bán kính: 1 km");
                        banKinh = 1D;
                        break;
                    case 3:
                        tvBanKinh.setText("Bán kính: 2 km");
                        banKinh = 2D;
                        break;
                    case 4:
                        tvBanKinh.setText("Bán kính: 4 km");
                        banKinh = 4D;
                        break;
                    case 5:
                        tvBanKinh.setText("Bán kính: 8 km");
                        banKinh = 8D;
                        break;
                    case 6:
                        tvBanKinh.setText("Bán kính: 15 km");
                        banKinh = 15D;
                        break;
                    case 7:
                        tvBanKinh.setText("Bán kính: 20 km");
                        banKinh = 20D;
                        break;
                    case 8:
                        tvBanKinh.setText("Bán kính: 30 km");
                        banKinh = 30D;
                        break;
                    default:
                        tvBanKinh.setText("Bán kính: tất cả");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbGiaPhong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case 1:
                        tvGiaPhong.setText("Giá phòng: trên 500 nghìn");
                        giaPhong = 0.5D;
                        break;
                    case 2:
                        tvGiaPhong.setText("Giá phòng: trên 1 triệu");
                        giaPhong = 1D;
                        break;
                    case 3:
                        tvGiaPhong.setText("Giá phòng: trên 1.5 triệu");
                        giaPhong = 1.5D;
                        break;
                    case 4:
                        tvGiaPhong.setText("Giá phòng: trên 2 triệu");
                        giaPhong = 2D;
                        break;
                    case 5:
                        tvGiaPhong.setText("Giá phòng: trên 2.5 triệu");
                        giaPhong = 2.5D;
                        break;
                    case 6:
                        tvGiaPhong.setText("Giá phòng: trên 3 triệu");
                        giaPhong = 3D;
                        break;
                    case 7:
                        tvGiaPhong.setText("Giá phòng: trên 3.5 triệu");
                        giaPhong = 3.5D;
                        break;
                    case 8:
                        tvGiaPhong.setText("Giá phòng: trên 4 triệu");
                        giaPhong = 4D;
                        break;
                    case 9:
                        tvGiaPhong.setText("Giá phòng: trên 4.5 triệu");
                        giaPhong = 4.5D;
                        break;
                    case 10:
                        tvGiaPhong.setText("Giá phòng: trên 5 triệu");
                        giaPhong = 5D;
                        break;
                    default:
                        tvGiaPhong.setText("Giá phòng: tất cả");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbDienTich.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case 1:
                        tvDienTich.setText("Diện tích: trên 5 mét vuông");
                        dienTich = 5;
                        break;
                    case 2:
                        tvDienTich.setText("Diện tích: trên 10 mét vuông");
                        dienTich = 10;
                        break;
                    case 3:
                        tvDienTich.setText("Diện tích: trên 15 mét vuông");
                        dienTich = 15;
                        break;
                    case 4:
                        tvDienTich.setText("Diện tích: trên 20 mét vuông");
                        dienTich = 20;
                        break;
                    case 5:
                        tvDienTich.setText("Diện tích: trên 25 mét vuông");
                        dienTich = 25;
                        break;
                    case 6:
                        tvDienTich.setText("Diện tích: trên 30 mét vuông");
                        dienTich = 30;
                        break;
                    case 7:
                        tvDienTich.setText("Diện tích: trên 35 mét vuông");
                        dienTich = 35;
                        break;
                    case 8:
                        tvDienTich.setText("Diện tích: trên 40 mét vuông");
                        dienTich = 40;
                        break;
                    case 9:
                        tvDienTich.setText("Diện tích: trên 45 mét vuông");
                        dienTich = 45;
                        break;
                    case 10:
                        tvDienTich.setText("Diện tích: trên 50 mét vuông");
                        dienTich = 50;
                        break;
                    default:
                        tvDienTich.setText("Diện tích: tất cả");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnTimPhongTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("luachon", "xungquanh");

                Bundle bundle = new Bundle();
                bundle.putDouble("bankinh", banKinh);
                bundle.putDouble("giaphong", giaPhong);
                bundle.putInt("dientich", dienTich);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return v;
    }

}