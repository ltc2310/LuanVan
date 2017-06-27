package vn.home.com.bottombar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import vn.home.com.model.DiaChi;
import vn.home.com.model.PhongTro;

public class ChiTietSuaTinChoMuonActivity extends AppCompatActivity {

    private Spinner spTinhThanh, spQuanHuyen;
    private EditText edtChiTietDiaChi, edtGiaPhong, edtDienTichPhong, edtMoTa, edtSoDt, edtHoten;
    private Button btnDangTinThuePhong;
    private ImageButton btnChonHinh;
    private ArrayAdapter<String> adapterTinhThanh;
    private String[] arrTinhThanh;
    private String[] arrQuanHuyen;
    private ArrayAdapter<String> adapterQuanHuyen;
    private String tinhDuocChon, quanDuocChon;
    private ArrayList<String> filePaths;
    private ArrayList<String> linkHinh;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private PhongTro phongTro;
    private boolean kiemTra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sua_tin_cho_muon);
        addControls();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("PHONGTRO_BUNDLE");
        phongTro = (PhongTro) bundle.getSerializable("PHONGTRO");
        edtGiaPhong.setText(phongTro.giaPhong.toString());
        edtChiTietDiaChi.setText(phongTro.diaChi.diaChiChiTiet);
        edtDienTichPhong.setText(phongTro.dienTich.toString());
        edtMoTa.setText(phongTro.moTa);
        edtSoDt.setText(phongTro.sdt);
        edtHoten.setText(phongTro.tenNguoiDung);
        int posTp = adapterTinhThanh.getPosition(phongTro.diaChi.thanhPho);
        spTinhThanh.setSelection(posTp);

        if (phongTro.diaChi.thanhPho.equals("Thành phố Hồ Chí Minh")) {
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanTPHCM);
            adapterQuanHuyen = new ArrayAdapter<String>(ChiTietSuaTinChoMuonActivity.this, android.R.layout.simple_list_item_1, arrQuanHuyen);
            int posQuan = adapterQuanHuyen.getPosition(phongTro.diaChi.quan);
            spQuanHuyen.setSelection(posQuan);
//            kiemTra = true;
        } else if (phongTro.diaChi.thanhPho.equals("Thừa Thiên Huế")) {
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanHue);
            adapterQuanHuyen = new ArrayAdapter<String>(ChiTietSuaTinChoMuonActivity.this, android.R.layout.simple_list_item_1, arrQuanHuyen);
            int posQuan = adapterQuanHuyen.getPosition(phongTro.diaChi.quan);
            spQuanHuyen.setSelection(posQuan);
//            kiemTra = true;
        } else if (phongTro.diaChi.thanhPho.toString().equals("Hà Nội")) {
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanHaNoi);
            adapterQuanHuyen = new ArrayAdapter<String>(ChiTietSuaTinChoMuonActivity.this, android.R.layout.simple_list_item_1, arrQuanHuyen);
            int posQuan = adapterQuanHuyen.getPosition(phongTro.diaChi.quan);
            spQuanHuyen.setSelection(posQuan);
//            kiemTra = true;
        } else if (phongTro.diaChi.thanhPho.toString().equals("Cần Thơ")) {
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanCanTho);
            adapterQuanHuyen = new ArrayAdapter<String>(ChiTietSuaTinChoMuonActivity.this, android.R.layout.simple_list_item_1, arrQuanHuyen);
            int posQuan = adapterQuanHuyen.getPosition(phongTro.diaChi.quan);
            spQuanHuyen.setSelection(posQuan);
//            kiemTra = true;
        } else {
            Toast.makeText(ChiTietSuaTinChoMuonActivity.this, "", Toast.LENGTH_SHORT).show();
        }

//        kiemTra = false;

        addEvents();
    }

    private void addEvents() {
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
                        .pickPhoto(ChiTietSuaTinChoMuonActivity.this);
            }
        });

        btnDangTinThuePhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLuDangTin(linkHinh);
            }
        });
    }

    private ArrayList<String> getLinkHinh() {
        final ArrayList<String> arrayHinh = new ArrayList<>();
        for (String path : filePaths) {
            Uri file = Uri.fromFile(new File(path));
            StorageReference filePath = storageReference.child("Photos").child(file.getLastPathSegment());
            UploadTask uploadTask = filePath.putFile(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ChiTietSuaTinChoMuonActivity.this, "Upload hình thất bại", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    arrayHinh.add(downloadUrl.toString());
                }
            });
        }

        return arrayHinh;
    }

    private void xuLuDangTin(ArrayList<String> arrHinh) {

        String giaPhongTro = edtGiaPhong.getText().toString();
        final String dienTich = edtDienTichPhong.getText().toString();
        final String diaChiChiTiet = edtChiTietDiaChi.getText().toString();
        String moTa = edtMoTa.getText().toString();
        String soDt = edtSoDt.getText().toString();
        String hoTen = edtHoten.getText().toString();
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getEmail();


        if (giaPhongTro.isEmpty() || giaPhongTro.equals("")) {
            Toast.makeText(this, "Vui lòng nhập vào giá phòng", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dienTich.isEmpty() || dienTich.equals("")) {
            Toast.makeText(this, "Vui lòng nhập vào diện tích", Toast.LENGTH_SHORT).show();
            return;
        }
        if (diaChiChiTiet.isEmpty() || diaChiChiTiet.equals("")) {
            Toast.makeText(this, "Vui lòng nhập vào địa chỉ ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (moTa.isEmpty() || moTa.equals("")) {
            Toast.makeText(this, "Vui lòng nhập vào mô tả", Toast.LENGTH_SHORT).show();
            return;
        }

        if (soDt.isEmpty() || soDt.equals("")) {
            Toast.makeText(this, "Vui lòng nhập vào số điện thoại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (hoTen.isEmpty() || hoTen.equals("")) {
            Toast.makeText(this, "Vui lòng nhập vào họ tên", Toast.LENGTH_SHORT).show();
            return;
        }

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String currentDate = formatter.format(date);

        LatLng latLng = layToaDo(edtChiTietDiaChi.getText().toString() + "," + quanDuocChon + "," + tinhDuocChon, ChiTietSuaTinChoMuonActivity.this);
        String key = phongTro.id;

        if (arrHinh.isEmpty()){
            arrHinh = phongTro.linkHinh;
        }

        DatabaseReference phongtroRef = databaseReference.child("phongtro");
        Map<String, Object> phongtroUpdate = new HashMap<String, Object>();
        phongtroUpdate.put( key, new PhongTro( hoTen, latLng.latitude, latLng.longitude, new DiaChi(quanDuocChon, tinhDuocChon, diaChiChiTiet),
                Integer.parseInt(dienTich), Double.parseDouble(giaPhongTro), soDt, moTa, currentDate, arrHinh, false, email, key ));
        phongtroRef.getRef().updateChildren(phongtroUpdate);

        Toast.makeText(ChiTietSuaTinChoMuonActivity.this, "Cập nhật tin thành công", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ChiTietSuaTinChoMuonActivity.this,QuanLyActivity.class));
    }

    private LatLng layToaDo(String myAddress, Context context) {
        LatLng latLng = null;
        Geocoder gc = new Geocoder(context);
        List<Address> addressList = null;
        if (gc.isPresent()) {
            try {
                addressList = gc.getFromLocationName(myAddress, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            latLng = new LatLng(address.getLatitude(), address.getLongitude());
        }
        return latLng;
    }

    private void LayHuyenTheoTinh(Object itemSelected) {
        if (itemSelected.toString().equals("Thành phố Hồ Chí Minh")) {
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanTPHCM);
            adapterQuanHuyen = new ArrayAdapter<String>(ChiTietSuaTinChoMuonActivity.this, android.R.layout.simple_list_item_1, arrQuanHuyen);
            spQuanHuyen.setAdapter(adapterQuanHuyen);
        } else if (itemSelected.toString().equals("Thừa Thiên Huế")) {
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanHue);
            adapterQuanHuyen = new ArrayAdapter<String>(ChiTietSuaTinChoMuonActivity.this, android.R.layout.simple_list_item_1, arrQuanHuyen);
            spQuanHuyen.setAdapter(adapterQuanHuyen);
        } else if (itemSelected.toString().equals("Hà Nội")) {
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanHaNoi);
            adapterQuanHuyen = new ArrayAdapter<String>(ChiTietSuaTinChoMuonActivity.this, android.R.layout.simple_list_item_1, arrQuanHuyen);
            spQuanHuyen.setAdapter(adapterQuanHuyen);
        } else if (itemSelected.toString().equals("Cần Thơ")) {
            arrQuanHuyen = getResources().getStringArray(R.array.arrayQuanCanTho);
            adapterQuanHuyen = new ArrayAdapter<String>(ChiTietSuaTinChoMuonActivity.this, android.R.layout.simple_list_item_1, arrQuanHuyen);
            spQuanHuyen.setAdapter(adapterQuanHuyen);
        } else {
            Toast.makeText(ChiTietSuaTinChoMuonActivity.this, "Tỉnh thành chưa được hỗ trợ", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        spTinhThanh = (Spinner) findViewById(R.id.spTinhThanhSTCM);
        spQuanHuyen = (Spinner) findViewById(R.id.spQuanHuyenSTCM);
        arrTinhThanh = getResources().getStringArray(R.array.arrTinhThanh);
        adapterTinhThanh = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrTinhThanh);
        spTinhThanh.setAdapter(adapterTinhThanh);
        btnChonHinh = (ImageButton) findViewById(R.id.btnChonHinhSTCM);
        btnDangTinThuePhong = (Button) findViewById(R.id.btnSuaTinChoMuon);
        edtChiTietDiaChi = (EditText) findViewById(R.id.edtChiTietDiaChiSTCM);
        edtDienTichPhong = (EditText) findViewById(R.id.edtDienTichPhongSTCM);
        edtGiaPhong = (EditText) findViewById(R.id.edtGiaPhongSTCM);
        edtMoTa = (EditText) findViewById(R.id.edtMoTaSTCM);
        edtSoDt = (EditText) findViewById(R.id.edtSoDtSTCM);
        edtHoten = (EditText) findViewById(R.id.edtHotenSTCM);
        filePaths = new ArrayList<>();
        linkHinh = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    filePaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                    Toast.makeText(this, "Bạn đã chọn " +filePaths.size() + " hình", Toast.LENGTH_SHORT).show();
                    linkHinh = getLinkHinh();
                }
        }
    }
}
