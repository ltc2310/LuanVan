package vn.home.com.bottombar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.home.com.model.PhongTro;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private LatLng loc;
    private Double banKinh, giaPhong, latLang, latLong;
    private String diaChi;
    private int dienTich;
    private String luaChon;
    private List<PhongTro> listPhongTro;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        luaChon = getIntent().getStringExtra("luachon");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (luaChon.equals("xungquanh")) {
            Bundle bundle = getIntent().getExtras();
            banKinh = bundle.getDouble("bankinh");
            giaPhong = bundle.getDouble("giaphong");
            dienTich = bundle.getInt("dientich");

            listPhongTro = new ArrayList<>();
            mDatabase.child("phongtro").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    PhongTro data = dataSnapshot.getValue(PhongTro.class);
                    listPhongTro.add(data);
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

            progressDialog = ProgressDialog.show(this, "Vui lòng chờ.", "Đang tải bản đồ...", true);
        } else if (luaChon.equals("chitiet")) {
            Bundle bundle = getIntent().getExtras();
            latLang = bundle.getDouble("lang");
            latLong = bundle.getDouble("long");
            giaPhong = bundle.getDouble("giaphong");
            diaChi = bundle.getString("diachi");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(listener);

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if(luaChon.equals("xungquanh")) {
                    if (banKinh == 0 && giaPhong == 0 && dienTich == 0) {
                        for (PhongTro item : listPhongTro) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(item.latitude, item.longtitue))
                                    .title(item.diaChi.diaChiChiTiet)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                    .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                        }
                    } else if (banKinh == 0 && giaPhong == 0) {
                        for (PhongTro item : listPhongTro) {
                            if (item.dienTich > dienTich) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(item.latitude, item.longtitue))
                                        .title(item.diaChi.diaChiChiTiet)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                        .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                            }
                        }
                    } else if (giaPhong == 0 && dienTich == 0) {
                        for (PhongTro item : listPhongTro) {
                            if (CalculationByDistance(loc, new LatLng(item.latitude, item.longtitue)) < banKinh) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(item.latitude, item.longtitue))
                                        .title(item.diaChi.diaChiChiTiet)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                        .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                            }
                        }
                    } else if (banKinh == 0) {
                        for (PhongTro item : listPhongTro) {
                            if (item.dienTich > dienTich && item.giaPhong > giaPhong) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(item.latitude, item.longtitue))
                                        .title(item.diaChi.diaChiChiTiet)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                        .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                            }
                        }
                    } else if (giaPhong == 0) {
                        for (PhongTro item : listPhongTro) {
                            if (item.dienTich > dienTich && CalculationByDistance(loc, new LatLng(item.latitude, item.longtitue)) < banKinh) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(item.latitude, item.longtitue))
                                        .title(item.diaChi.diaChiChiTiet)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                        .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                            }
                        }
                    } else if (dienTich == 0) {
                        for (PhongTro item : listPhongTro) {
                            if (item.giaPhong > giaPhong && CalculationByDistance(loc, new LatLng(item.latitude, item.longtitue)) < banKinh) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(item.latitude, item.longtitue))
                                        .title(item.diaChi.diaChiChiTiet)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                        .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                            }
                        }
                    }
                    progressDialog.dismiss();
                } else {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latLang, latLong))
                            .title(diaChi)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                            .snippet("Giá phòng: " + giaPhong + " triệu"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLang, latLong), 18));
                }
            }
        });
        mMap.setOnInfoWindowClickListener(this);
    }
    GoogleMap.OnMyLocationChangeListener listener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            loc = new LatLng(location.getLatitude(), location.getLongitude());
            if (mMap != null) {
                mMap.addMarker(new MarkerOptions().position(loc).visible(false));

                if (luaChon.equals("xungquanh")) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17));
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        if (luaChon.equals("chitiet")) {
//            MenuItem menuItem = menu.add(1,1,1,"Chỉ đường");
//            menuItem.setIcon(R.drawable.ic_directions_white_36dp);
//            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == 1) {
//            sendRequest(latLang + "," + latLong);
//        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onInfoWindowClick(Marker marker) {
//        for (PhongTro item : listPhongTro) {
//            if (item.latitude == marker.getPosition().latitude && item.longtitue == marker.getPosition().longitude) {
//                Intent intent = new Intent(MapsActivity.this, DetailActivity.class);
//                intent.putExtra("PhongTro", item);
//                startActivity(intent);
//            }
//        }
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
}