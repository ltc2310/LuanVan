package vn.home.com.bottombar;

import android.*;
import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import vn.home.com.fragment.FavoriteFragment;
import vn.home.com.fragment.MenuFragment;
import vn.home.com.fragment.SearchFragement;
import vn.home.com.fragment.TimelineFragment;
import vn.home.com.model.NguoiDung;
import vn.home.com.model.PhongTro;
import vn.home.com.model.PhongTroCanMuon;
import vn.home.com.model.PhongTroDeCu;


public class MainActivity extends AppCompatActivity {


    static List<NguoiDung> listNguoiDung;
    int notificationId;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    List<PhongTro> listPhongTro;
    List<PhongTroCanMuon> listPhongTroCanMuon;

    static ArrayList<PhongTro> listPhongTroDeCu ;


    static long soLuong= 0;
    private PhongTro phongTro;
    private NguoiDung ngDung;
    private FirebaseUser nguoiDung;
    private Double giaPhongTu,giaPhongDen;
    static long sizeCanTim=0;
    private PhongTroCanMuon phongTroCanMuon;
    private PhongTroDeCu pTroDeCuTheoUser;
    static String idPhongTroDeCu;
    static boolean check;
    int n=0;
    Handler handler = new Handler();
    Runnable refresh;
    static PhongTro data;

    private static final String[] STORAGE_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final String[] LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static final String[] INTERNET_PERMISSIONS = {
            Manifest.permission.INTERNET
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        refresh = new Runnable() {
            public void run() {
                addEvent();
                handler.postDelayed(refresh, 5000);
            }
        };
        handler.post(refresh);
        xacThucQuyenTruyCap();

        auth = FirebaseAuth.getInstance();
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_schedule) {
                    TimelineFragment timelineFragment = new TimelineFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, timelineFragment).commit();
                }
                if (tabId == R.id.tab_favorites) {
                    if(auth.getCurrentUser() !=null){
                        FavoriteFragment favoriteFragment = new FavoriteFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, favoriteFragment).commit();
                    }else {
                        Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                        startActivity(intent);
                    }

                }
                if (tabId == R.id.tab_search) {
                    SearchFragement searchFragement = new SearchFragement();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, searchFragement).commit();
                }
                if (tabId == R.id.tab_menu) {
                    MenuFragment menuFragment = new MenuFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer, menuFragment).commit();
                }
            }
        });

         addEvent();
    }

    private void addEvent() {
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listPhongTro = new ArrayList<>();
        listPhongTroCanMuon = new ArrayList<>();




        mDatabase.child("cantim").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                sizeCanTim=dataSnapshot.getChildrenCount();
                nguoiDung = auth.getCurrentUser();
                if(nguoiDung!=null) {
                    if (sizeCanTim > 0 && sizeCanTim>listPhongTroCanMuon.size()) {
                        phongTroCanMuon = dataSnapshot.getValue(PhongTroCanMuon.class);
                        if (nguoiDung.getEmail().equals(phongTroCanMuon.emailPhongTroCM)) {
                            if (phongTroCanMuon.kichHoat == true)
                                listPhongTroCanMuon.add(phongTroCanMuon);
                        }

                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        mDatabase.child("phongtro").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listPhongTroDeCu = new ArrayList<PhongTro>();
                phongTro = dataSnapshot.getValue(PhongTro.class);
                if (phongTro.kichHoat == true) {
                    listPhongTro.add(phongTro);
                    nguoiDung = auth.getCurrentUser();
                    if(nguoiDung!=null) {

                        for (PhongTro phongTro : listPhongTro) {
                            if (!(nguoiDung.getEmail().equals(phongTro.email))) {

                                for (PhongTroCanMuon phongTroCanMuon : listPhongTroCanMuon) {

                                    if (phongTro.giaPhong >= phongTroCanMuon.giaPhongMin && phongTro.giaPhong <= phongTroCanMuon.giaPhongMax
                                            &&phongTro.dienTich>=phongTroCanMuon.dienTich&&CalculationByDistance(new LatLng(phongTro.latitude,phongTro.longtitue),new LatLng(phongTroCanMuon.latitude
                                            ,phongTroCanMuon.longitude))<= phongTroCanMuon.banKinh) {
                                        for (PhongTro pTroListDeCu : listPhongTroDeCu) {

                                            check = soSanh(pTroListDeCu, phongTro);
                                            n++;


                                        }

                                        if (check == false && n == listPhongTroDeCu.size()) {
                                            listPhongTroDeCu.add(phongTro);
                                        }
                                    }





                                }
                                if (listPhongTroDeCu.size() > soLuong ) {
                                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                    notificationManager.cancel(notificationId);
                                    notificationManager.cancelAll();
                                    createNotifications(listPhongTroDeCu);
                                    soLuong = listPhongTroDeCu.size();

                                }



                            }
                        }

                    }


                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void createNotifications(List<PhongTro> listPhongTro) {
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.location_icon)
                .setContentTitle("Có thông báo")
                .setContentText("Đã có phòng trọ mới phù hợp!!!");

        Intent intent = new Intent(this,NotificationActivity.class);

        intent.putExtra("LIST_PTRODECU", (Serializable) listPhongTro);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);


        //Default Sound
        builder.setContentIntent(resultPendingIntent);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);

        notificationId =113;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(113,builder.build());

    }
    private boolean soSanh(PhongTro phongTro,PhongTro phongTro1){
        if(phongTro.giaPhong==phongTro1.giaPhong&&phongTro.dienTich==phongTro1.dienTich&&
                phongTro.email==phongTro1.email&&phongTro.kichHoat==phongTro1.kichHoat
                &&phongTro.diaChi==phongTro1.diaChi&&phongTro.id==phongTro1.id
                && phongTro.latitude==phongTro1.latitude&&phongTro.longtitue==phongTro1.longtitue
                && phongTro.moTa==phongTro1.moTa&&phongTro.ngayDang==phongTro1.ngayDang) {


            return true;
        }else
            return false;
    }
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return Radius * c;
    }


    private void xacThucQuyenTruyCap() {
        int permissionInternet = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET);

        int permissionExternalMemory = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        int permissionLocation = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionInternet != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, INTERNET_PERMISSIONS, 1);
        }

        if (permissionLocation != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, LOCATION_PERMISSIONS, 1);
        }

        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, STORAGE_PERMISSIONS, 1);
        }
    }



}
