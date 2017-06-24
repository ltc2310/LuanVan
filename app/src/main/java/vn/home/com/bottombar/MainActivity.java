package vn.home.com.bottombar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import vn.home.com.fragment.FavoriteFragment;
import vn.home.com.fragment.MenuFragment;
import vn.home.com.fragment.SearchFragement;
import vn.home.com.fragment.TimelineFragment;
import vn.home.com.model.NguoiDung;
import vn.home.com.model.PhongTro;
import vn.home.com.model.PhongTroYeuThich;

public class MainActivity extends AppCompatActivity {


    static List<NguoiDung> listNguoiDung;
    int notificationId;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    List<PhongTro> listPhongTro;

    static boolean read;
    static long size= 0;
    static long sizeAfter=0;
    static PhongTro data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


        FirebaseDatabase myFirebaseRef = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = myFirebaseRef.getReference("phongtro");


        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // data=dataSnapshot.getValue(PhongTro.class);



                //put your notification code here
                if(size<dataSnapshot.getChildrenCount()) {
                    if(data.kichHoat==true) {
                        createNotifications(data);
                        size = dataSnapshot.getChildrenCount();
                    }
                }

                //sizeAfter =dataSnapshot.getChildrenCount();
                //read=false;

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("FirebaseError", databaseError.getMessage());
            }




        };

        myRef.addValueEventListener(valueEventListener);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        listPhongTro = new ArrayList<>();
        mDatabase.child("phongtro").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                data = dataSnapshot.getValue(PhongTro.class);
//                listPhongTro.add(data);
//                if(sizeBefore<sizeAfter)
//                {
//                    if(data.kichHoat==true)
//                    {
//                        createNotifications(data);
//                    }
//
//                    sizeBefore = sizeAfter;
//
//                }
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

    private void createNotifications(PhongTro phongTro) {
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.location_icon)
                .setContentTitle("Có thông báo")
                .setContentText("Nhấn vào để cập nhật version");

        Intent intent = new Intent(this,XemChiTietActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("PHONGTRO", phongTro);
        intent.putExtra("MY_BUNDLE", bundle);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);


        //Default Sound
        builder.setContentIntent(resultPendingIntent);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);

        notificationId =113;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId,builder.build());

    }


}
