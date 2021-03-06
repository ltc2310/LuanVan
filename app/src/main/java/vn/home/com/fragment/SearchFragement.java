package vn.home.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import vn.home.com.bottombar.MapsActivity;
import vn.home.com.bottombar.R;

/**
 * Created by THANHCONG on 2/28/2017.
 */

public class SearchFragement extends Fragment{

    TabHost tabHost;
    Button btnTimPhongTro,btnTimPhongTroTT,btnTimPhongTroBK;
    TextView tvBanKinh,tvGiaPhong,tvDienTich,tvTinhThanh,tvGiaPhongTT,tvDienTichTT,tvBanKinhBK,tvDienTichBK,tvGiaPhongBK;

    SeekBar sbBanKinh,sbDienTich,sbDienTichTT,sbBanKinhBK,sbGiaPhongBK,sbDienTichBK;
    EditText txtGiaTuTT,txtGiaDenTT,txtGiaTu,txtGiaDen,txtGiaTuBK,txtGiaDenBK;

    Spinner spTinhThanh,spQuanHuyen;
    String [] arrTinhThanh;
    ArrayAdapter<String> adapterTinhThanh;
    private String []arrQuanHuyen;
    private ArrayAdapter<String> adapterQuanHuyen;
    private String tinhDuocChon;
    private String quanDuocChon;

    private Double banKinh = 0D, giaPhong = 0D;
    private static String giaPhongTu,giaPhongDen;
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
        tab1.setIndicator("Xung quanh");
        tabHost.addTab(tab1);

        //add tab2
        TabHost.TabSpec tab2=tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Khu vực");
        tabHost.addTab(tab2);




        //add controls
        btnTimPhongTro = (Button) v.findViewById(R.id.btnTimPhongTro);
        btnTimPhongTroTT = (Button) v.findViewById(R.id.btnTimPhongTroTT);



        tvBanKinh= (TextView) v.findViewById(R.id.tvBanKinhCT);

        tvGiaPhong = (TextView) v.findViewById(R.id.tvGiaPhong);
        tvGiaPhongTT = (TextView) v.findViewById(R.id.tvGiaPhongTT);

        tvDienTich = (TextView) v.findViewById(R.id.tvDienTich);
        tvDienTichTT = (TextView) v.findViewById(R.id.tvDienTichTT);

        tvTinhThanh = (TextView) v.findViewById(R.id.tvTinhThanh);

        sbBanKinh = (SeekBar) v.findViewById(R.id.sbBanKinhCT);

        sbDienTich = (SeekBar) v.findViewById(R.id.sbDienTich);
        sbDienTichTT = (SeekBar) v.findViewById(R.id.sbDienTichTT);

        spTinhThanh = (Spinner) v.findViewById(R.id.spTinhThanh);
        spQuanHuyen = (Spinner) v.findViewById(R.id.spQuanHuyen);
        arrTinhThanh= getResources().getStringArray(R.array.arrTinhThanh);
        adapterTinhThanh = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrTinhThanh);
        adapterTinhThanh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTinhThanh.setAdapter(adapterTinhThanh);

        txtGiaTuTT= (EditText) v.findViewById(R.id.txtGiaTuTT);
        txtGiaDenTT = (EditText) v.findViewById(R.id.txtGiaDenTT);
        txtGiaTu= (EditText) v.findViewById(R.id.txtGiaTu);
        txtGiaDen = (EditText) v.findViewById(R.id.txtGiaDen);


        sbBanKinh.setMax(20);
        sbDienTich.setMax(10);
        sbDienTichTT.setMax(10);








        spTinhThanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object itemSelected = spTinhThanh.getItemAtPosition(i);
                tinhDuocChon = itemSelected.toString();
                LayHuyenTheoTinh(tinhDuocChon);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //add Events

        sbBanKinh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case 1:
                        tvBanKinh.setText("Bán kính: 1 km");
                        banKinh = 1D;
                        break;
                    case 2:
                        tvBanKinh.setText("Bán kính: 2 km");
                        banKinh = 2D;
                        break;
                    case 3:
                        tvBanKinh.setText("Bán kính: 3 km");
                        banKinh = 3D;
                        break;
                    case 4:
                        tvBanKinh.setText("Bán kính: 4 km");
                        banKinh = 4D;
                        break;
                    case 5:
                        tvBanKinh.setText("Bán kính: 5 km");
                        banKinh = 5D;
                        break;
                    case 6:
                        tvBanKinh.setText("Bán kính: 6 km");
                        banKinh = 6D;
                        break;
                    case 7:
                        tvBanKinh.setText("Bán kính: 7 km");
                        banKinh = 7D;
                        break;
                    case 8:
                        tvBanKinh.setText("Bán kính: 8 km");
                        banKinh = 8D;
                        break;
                    case 9:
                        tvBanKinh.setText("Bán kính: 9 km");
                        banKinh = 9D;
                        break;
                    case 10:
                        tvBanKinh.setText("Bán kính: 10 km");
                        banKinh = 10D;
                        break;
                    case 11:
                        tvBanKinh.setText("Bán kính: 11 km");
                        banKinh = 11D;
                        break;
                    case 12:
                        tvBanKinh.setText("Bán kính: 12 km");
                        banKinh = 12D;
                        break;
                    case 13:
                        tvBanKinh.setText("Bán kính: 13 km");
                        banKinh = 13D;
                        break;
                    case 14:
                        tvBanKinh.setText("Bán kính: 14 km");
                        banKinh = 14D;
                        break;
                    case 15:
                        tvBanKinh.setText("Bán kính: 15 km");
                        banKinh = 15D;
                        break;
                    case 16:
                        tvBanKinh.setText("Bán kính: 16 km");
                        banKinh = 16D;
                        break;
                    case 17:
                        tvBanKinh.setText("Bán kính: 17 km");
                        banKinh = 17D;
                        break;
                    case 18:
                        tvBanKinh.setText("Bán kính: 18 km");
                        banKinh = 18D;
                        break;
                    case 19:
                        tvBanKinh.setText("Bán kính: 19 km");
                        banKinh = 19D;
                        break;
                    case 20:
                        tvBanKinh.setText("Bán kính: 20 km");
                        banKinh = 20D;
                        break;
                    default:
                        tvBanKinh.setText("Bán kính: tất cả");
                        banKinh = 0D;
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
                        dienTich=0;
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



        sbDienTichTT.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case 1:
                        tvDienTichTT.setText("Diện tích: trên 5 mét vuông");
                        dienTich = 5;
                        break;
                    case 2:
                        tvDienTichTT.setText("Diện tích: trên 10 mét vuông");
                        dienTich = 10;
                        break;
                    case 3:
                        tvDienTichTT.setText("Diện tích: trên 15 mét vuông");
                        dienTich = 15;
                        break;
                    case 4:
                        tvDienTichTT.setText("Diện tích: trên 20 mét vuông");
                        dienTich = 20;
                        break;
                    case 5:
                        tvDienTichTT.setText("Diện tích: trên 25 mét vuông");
                        dienTich = 25;
                        break;
                    case 6:
                        tvDienTichTT.setText("Diện tích: trên 30 mét vuông");
                        dienTich = 30;
                        break;
                    case 7:
                        tvDienTichTT.setText("Diện tích: trên 35 mét vuông");
                        dienTich = 35;
                        break;
                    case 8:
                        tvDienTichTT.setText("Diện tích: trên 40 mét vuông");
                        dienTich = 40;
                        break;
                    case 9:
                        tvDienTichTT.setText("Diện tích: trên 45 mét vuông");
                        dienTich = 45;
                        break;
                    case 10:
                        tvDienTichTT.setText("Diện tích: trên 50 mét vuông");
                        dienTich = 50;
                        break;
                    default:
                        tvDienTichTT.setText("Diện tích: tất cả");
                        dienTich=0;
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
                giaPhongTu = txtGiaTu.getText().toString();
                giaPhongDen = txtGiaDen.getText().toString();

                if(giaPhongTu.isEmpty()||giaPhongTu.equals(""))
                {
                    Toast.makeText(getActivity(), "Vui lòng nhập vào giá phòng từ", Toast.LENGTH_SHORT).show();
                    return;

                }

                else if(giaPhongDen.isEmpty()||giaPhongDen.equals(""))
                {
                    Toast.makeText(getActivity(), "Vui lòng nhập vào giá phòng đến", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if(Double.parseDouble(giaPhongDen)<=Double.parseDouble(giaPhongTu))
                {
                    Toast.makeText(getActivity(), "Giá phòng đến không được nhỏ hơn giá phòng từ", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    intent.putExtra("luachon", "xungquanh");
                    Bundle bundle = new Bundle();
                    bundle.putDouble("bankinh", banKinh);
                    bundle.putString("giaphongtu", giaPhongTu);
                    bundle.putString("giaphongden", giaPhongDen);
                    bundle.putInt("dientich", dienTich);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        btnTimPhongTroTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giaPhongTu = txtGiaTuTT.getText().toString();
                giaPhongDen = txtGiaDenTT.getText().toString();

                if(giaPhongTu.isEmpty()||giaPhongTu.equals(""))
                {
                    Toast.makeText(getActivity(), "Vui lòng nhập vào giá phòng từ", Toast.LENGTH_SHORT).show();
                    return;

                }

                else if(giaPhongDen.isEmpty()||giaPhongDen.equals(""))
                {
                    Toast.makeText(getActivity(), "Vui lòng nhập vào giá phòng đến", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if(Double.parseDouble(giaPhongDen)<=Double.parseDouble(giaPhongTu))
                {
                    Toast.makeText(getActivity(), "Giá phòng đến không được nhỏ hơn giá phòng từ", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    quanDuocChon= spQuanHuyen.getSelectedItem().toString();
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    intent.putExtra("luachon", "khuvuc");
                    Bundle bundle = new Bundle();
                    bundle.putString("tinhduocchon",tinhDuocChon);
                    bundle.putString("quanduocchon",quanDuocChon);
                    bundle.putString("giaphongtutt", giaPhongTu);
                    bundle.putString("giaphongdentt", giaPhongDen);
                    bundle.putInt("dientich", dienTich);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });





        return v;
    }

    private void LayHuyenTheoTinh(Object itemSelected) {
        if (itemSelected.toString().equals("Thành phố Hồ Chí Minh")){
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanTPHCM);
            adapterQuanHuyen = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,arrQuanHuyen);
            spQuanHuyen.setAdapter(adapterQuanHuyen);
        }
        else if (itemSelected.toString().equals("Thừa Thiên Huế")){
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanHue);
            adapterQuanHuyen = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrQuanHuyen);
            spQuanHuyen.setAdapter(adapterQuanHuyen);
        }
        else if (itemSelected.toString().equals("Hà Nội")) {
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanHaNoi);
            adapterQuanHuyen = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrQuanHuyen);
            spQuanHuyen.setAdapter(adapterQuanHuyen);
        }
        else if (itemSelected.toString().equals("Cần Thơ")){
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanCanTho);
            adapterQuanHuyen = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrQuanHuyen);
            spQuanHuyen.setAdapter(adapterQuanHuyen);
        }else {
            Toast.makeText(getActivity(), "Tỉnh thành chưa được hỗ trợ", Toast.LENGTH_SHORT).show();
        }
    }


}