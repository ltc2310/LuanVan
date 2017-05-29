package vn.home.com.bottombar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

public class DangTinChoThueActivity extends AppCompatActivity {



    private Spinner spTinhThanh, spQuanHuyen;
    private EditText edtChiTietDiaChi,edtGiaPhong,edtDienTichPhong,edtMoTa;
    private Button btnDangTinThuePhong;
    private ImageButton btnChonHinh;
    private ArrayAdapter<String> adapterTinhThanh;
    private String []arrTinhThanh;
    private String []arrQuanHuyen;
    private ArrayAdapter<String> adapterQuanHuyen;
    private String tinhDuocChon,quanDuocChon;
    private ArrayList<String> filePaths;
    private ArrayList<String> linkHinh;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_tin_cho_thue);
        addControls();
        addEvent();
    }

    private void addEvent() {
        spTinhThanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object itemSelected = spTinhThanh.getItemAtPosition(i);
                tinhDuocChon = itemSelected.toString();
                LayHuyenTheoTinh(itemSelected);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spQuanHuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                quanDuocChon = spQuanHuyen.getItemAtPosition(i).toString();
                Toast.makeText(DangTinChoThueActivity.this, "sadasda"+quanDuocChon, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filePaths.clear();
                FilePickerBuilder.getInstance().setMaxCount(5)
                        .setSelectedFiles(filePaths)
                        .setActivityTheme(R.style.AppTheme)
                        .pickPhoto(DangTinChoThueActivity.this);
            }
        });

        btnDangTinThuePhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLuDangTin();
            }
        });
    }

    private void xuLuDangTin() {
        progressDialog.setMessage("Uploading ...");
        progressDialog.show();

        for(String path : filePaths){
            Uri file = Uri.fromFile(new File(path));
            StorageReference filePath = storageReference.child("Photos").child(file.getLastPathSegment());
            UploadTask uploadTask = filePath.putFile(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DangTinChoThueActivity.this, "Upload hình thất bại", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    linkHinh.add(downloadUrl.toString());
                    Toast.makeText(DangTinChoThueActivity.this, "Upload success", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }

        String giaPhongTro = edtGiaPhong.getText().toString();
        String dienTich = edtDienTichPhong.getText().toString();
        String diaChiChiTiet = edtChiTietDiaChi.getText().toString();
        String moTa = edtMoTa.getText().toString();
        if (giaPhongTro.isEmpty()||giaPhongTro.equals("")){
            Toast.makeText(this, "Vui lòng nhập vào giá phòng", Toast.LENGTH_SHORT).show();
        }
        if (dienTich.isEmpty()||dienTich.equals("")){
            Toast.makeText(this, "Vui lòng nhập vào diện tích", Toast.LENGTH_SHORT).show();
        }
        if (diaChiChiTiet.isEmpty()||diaChiChiTiet.equals("")){
            Toast.makeText(this, "Vui lòng nhập vào địa chỉ ", Toast.LENGTH_SHORT).show();
        }
        if (moTa.isEmpty()||moTa.equals("")){
            Toast.makeText(this, "Vui lòng nhập vào mô tả", Toast.LENGTH_SHORT).show();
        }

        LatLng latLng = RetrieveLatlangFromAddress(edtChiTietDiaChi.getText().toString() + "," +quanDuocChon+","+tinhDuocChon, DangTinChoThueActivity.this);


    }

    private LatLng RetrieveLatlangFromAddress(String myAddress, Context context){
        LatLng latLng=null;

        Geocoder gc=new Geocoder(context);
        List<Address> addressList=null;
        if(gc.isPresent()){
            try {
                addressList=gc.getFromLocationName(myAddress,1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address=addressList.get(0);
            latLng=new LatLng(address.getLatitude(),address.getLongitude());
        }
        return latLng;
    }

    private void LayHuyenTheoTinh(Object itemSelected) {
        if (itemSelected.toString().equals("TP HCM")){
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanTPHCM);
            adapterQuanHuyen = new ArrayAdapter<String>(DangTinChoThueActivity.this,android.R.layout.simple_list_item_1,arrQuanHuyen);
            spQuanHuyen.setAdapter(adapterQuanHuyen);
        }
        else if (itemSelected.toString().equals("Thừa Thiên Huế")){
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanHue);
            adapterQuanHuyen = new ArrayAdapter<String>(DangTinChoThueActivity.this,android.R.layout.simple_list_item_1,arrQuanHuyen);
            spQuanHuyen.setAdapter(adapterQuanHuyen);
        }
        else if (itemSelected.toString().equals("Hà Nội")) {
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanHaNoi);
            adapterQuanHuyen = new ArrayAdapter<String>(DangTinChoThueActivity.this, android.R.layout.simple_list_item_1, arrQuanHuyen);
            spQuanHuyen.setAdapter(adapterQuanHuyen);
        }
        else if (itemSelected.toString().equals("Cần Thơ")){
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanCanTho);
            adapterQuanHuyen = new ArrayAdapter<String>(DangTinChoThueActivity.this,android.R.layout.simple_list_item_1,arrQuanHuyen);
            spQuanHuyen.setAdapter(adapterQuanHuyen);
        }else {
            Toast.makeText(DangTinChoThueActivity.this, "Tỉnh thành chưa được hỗ trợ", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        spTinhThanh = (Spinner) findViewById(R.id.spTinhThanh);
        spQuanHuyen = (Spinner) findViewById(R.id.spQuanHuyen);
        arrTinhThanh = getResources().getStringArray(R.array.arrTinhThanh);
        adapterTinhThanh = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrTinhThanh);
        spTinhThanh.setAdapter(adapterTinhThanh);
        btnChonHinh = (ImageButton) findViewById(R.id.btnChonHinh);
        btnDangTinThuePhong = (Button) findViewById(R.id.btnDangTinThuePhong);
        edtChiTietDiaChi = (EditText) findViewById(R.id.edtChiTietDiaChi);
        edtDienTichPhong = (EditText) findViewById(R.id.edtDienTichPhong);
        edtGiaPhong = (EditText) findViewById(R.id.edtGiaPhong);
        edtMoTa = (EditText) findViewById(R.id.edtMoTa);
        filePaths = new ArrayList<>();
        linkHinh = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    filePaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                }
        }
    }
}
