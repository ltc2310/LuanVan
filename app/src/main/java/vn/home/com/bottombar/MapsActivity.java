package vn.home.com.bottombar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.home.com.model.PhongTro;
import vn.home.com.model.PhongTroCanMuon;
import vn.home.com.model.Route;
import vn.home.com.utils.DirectionFinder;
import vn.home.com.utils.DirectionFinderListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, DirectionFinderListener {

    private GoogleMap mMap;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private LatLng loc;
    private Double banKinh, giaPhong, latLang, latLong;
    private String tinhDuocChon,quanDuocChon;
    private Double giaPhongTu,giaPhongDen;
    private int dienTich;
    private String luaChon;
    private List<PhongTro> listPhongTro;
    private DatabaseReference mDatabase;
    Geocoder geocoder;
    private LatLng latLng;
    private String diaChi;
    private String tenNguoiTim,sdt,moTa,ngayDang,email,key;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        geocoder = new Geocoder(this, Locale.getDefault());


        luaChon = getIntent().getStringExtra("luachon");
        mDatabase = FirebaseDatabase.getInstance().getReference();

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

        if (luaChon.equals("xungquanh")) {
            Bundle bundle = getIntent().getExtras();
            banKinh = bundle.getDouble("bankinh");
            giaPhongTu =Double.parseDouble(bundle.getString("giaphongtu"));
            giaPhongDen =Double.parseDouble(bundle.getString("giaphongden"));
            dienTich = bundle.getInt("dientich");

            progressDialog = ProgressDialog.show(this, "Vui lòng chờ.", "Đang tải bản đồ...", true);

        }else if (luaChon.equals("chitiet")) {
            Bundle bundle = getIntent().getExtras();
            latLang = bundle.getDouble("lang");
            latLong = bundle.getDouble("long");
            giaPhong = bundle.getDouble("giaphong");
            diaChi = bundle.getString("diachi");

        }else if (luaChon.equals("khuvuc")) {
            Bundle bundle = getIntent().getExtras();
            tinhDuocChon = bundle.getString("tinhduocchon");
            quanDuocChon = bundle.getString("quanduocchon");
            //giaPhong = bundle.getDouble("giaphong");
            giaPhongTu =Double.parseDouble(bundle.getString("giaphongtutt"));
            giaPhongDen =Double.parseDouble(bundle.getString("giaphongdentt"));
            dienTich = bundle.getInt("dientich");

            progressDialog = ProgressDialog.show(this, "Vui lòng chờ.", "Đang tải bản đồ...", true);
        }else if (luaChon.equals("tuchon")) {
            Bundle bundle = getIntent().getExtras();
            banKinh = bundle.getDouble("bankinh");
            giaPhongTu =Double.parseDouble(bundle.getString("giaphongtubk"));
            giaPhongDen =Double.parseDouble(bundle.getString("giaphongdenbk"));
            dienTich = bundle.getInt("dientich");
        }else if (luaChon.equals("chontoado")) {
            Bundle bundle = getIntent().getExtras();
            tenNguoiTim=bundle.getString("tenNguoiTim");
            banKinh = bundle.getDouble("banKinh");
            dienTich = bundle.getInt("dienTich");
            giaPhongTu =bundle.getDouble("giaPhongMin");
            giaPhongDen =bundle.getDouble("giaPhongMax");
            sdt=bundle.getString("sdt");
            moTa=bundle.getString("moTa");
            ngayDang=bundle.getString("ngayDang");
            email=bundle.getString("email");
            key=bundle.getString("key");

        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    private void sendRequest(String location_end) {
        try {
            new DirectionFinder(this,loc.latitude + "," + loc.longitude, location_end).Execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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

//        if (!check_Gps(MapsActivity.this)) {
//            open_Gps_Activity(MapsActivity.this);
//        }

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(listener);


        //lấy tọa độ một điểm bất kỳ để tìm kiếm xung quanh


        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

                if(luaChon.equals("xungquanh")) {
                    location=mMap.getMyLocation();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17));
                    if (banKinh == 0 && dienTich == 0) {
                        for (PhongTro item : listPhongTro) {
                            if(item.kichHoat==true) {
                                if (item.giaPhong >= giaPhongTu && item.giaPhong <= giaPhongDen) {
                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(item.latitude, item.longtitue))
                                            .title(item.diaChi.diaChiChiTiet)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                            .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                }
                            }
                        }

                    }else if (banKinh == 0) {
                        for (PhongTro item : listPhongTro) {
                            if(item.kichHoat==true) {
                                if (item.dienTich > dienTich && item.giaPhong >= giaPhongTu && item.giaPhong <= giaPhongDen) {
                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(item.latitude, item.longtitue))
                                            .title(item.diaChi.diaChiChiTiet)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                            .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                }
                            }
                        }
                    } else if (dienTich == 0) {
                        for (PhongTro item : listPhongTro) {
                            if(item.kichHoat==true) {
                                if (item.giaPhong >= giaPhongTu && item.giaPhong <= giaPhongDen && CalculationByDistance(loc, new LatLng(item.latitude, item.longtitue)) <= banKinh) {
                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(item.latitude, item.longtitue))
                                            .title(item.diaChi.diaChiChiTiet)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                            .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                }
                            }
                        }
                    }else if(banKinh != 0 && dienTich != 0) {
                        for (PhongTro item : listPhongTro) {
                            if(item.kichHoat==true) {
                                if (item.giaPhong >= giaPhongTu && item.giaPhong <= giaPhongDen && item.dienTich > dienTich && CalculationByDistance(loc, new LatLng(item.latitude, item.longtitue)) <= banKinh) {
                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(item.latitude, item.longtitue))
                                            .title(item.diaChi.diaChiChiTiet)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                            .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                }
                            }
                        }
                    }
                    progressDialog.dismiss();
                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            mMap.clear();
                            mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon)));
                            if (banKinh == 0 && dienTich == 0) {
                                for (PhongTro item : listPhongTro) {
                                    if(item.kichHoat==true) {
                                        if (item.giaPhong >= giaPhongTu && item.giaPhong <= giaPhongDen) {
                                            mMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(item.latitude, item.longtitue))
                                                    .title(item.diaChi.diaChiChiTiet)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                                    .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                        }
                                    }
                                }

                            }else if (banKinh == 0) {
                                for (PhongTro item : listPhongTro) {
                                    if(item.kichHoat==true) {
                                        if (item.dienTich > dienTich && item.giaPhong >= giaPhongTu && item.giaPhong <= giaPhongDen) {
                                            mMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(item.latitude, item.longtitue))
                                                    .title(item.diaChi.diaChiChiTiet)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                                    .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                        }
                                    }
                                }
                            }  else if (dienTich == 0) {
                                for (PhongTro item : listPhongTro) {
                                    if(item.kichHoat==true) {
                                        if (item.giaPhong >= giaPhongTu && item.giaPhong <= giaPhongDen && CalculationByDistance(latLng, new LatLng(item.latitude, item.longtitue)) <= banKinh) {
                                            mMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(item.latitude, item.longtitue))
                                                    .title(item.diaChi.diaChiChiTiet)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                                    .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                        }
                                    }
                                }
                            }else if(banKinh != 0 && dienTich != 0) {
                                for (PhongTro item : listPhongTro) {
                                    if(item.kichHoat==true) {
                                        if (item.giaPhong >= giaPhongTu && item.giaPhong <= giaPhongDen && item.dienTich > dienTich && CalculationByDistance(latLng, new LatLng(item.latitude, item.longtitue)) <= banKinh) {
                                            mMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(item.latitude, item.longtitue))
                                                    .title(item.diaChi.diaChiChiTiet)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                                    .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                        }
                                    }
                                }
                            }
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        }
                    });
                }
                else if(luaChon.equals("khuvuc")) {
                    latLng = layToaDo (tinhDuocChon +" " + quanDuocChon,MapsActivity.this);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    if (dienTich == 0) {
                        for (PhongTro item : listPhongTro) {
                            if(item.kichHoat==true) {
                                if (item.giaPhong >= giaPhongTu && item.giaPhong <= giaPhongDen && item.diaChi.quan.equals(quanDuocChon)) {
                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(item.latitude, item.longtitue))
                                            .title(item.diaChi.diaChiChiTiet)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                            .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                }
                            }
                        }

                    }else{
                        for (PhongTro item : listPhongTro) {
                            if(item.kichHoat==true) {
                                if (item.giaPhong >= giaPhongTu && item.giaPhong <= giaPhongDen && item.diaChi.quan.equals(quanDuocChon) && item.dienTich>dienTich) {
                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(item.latitude, item.longtitue))
                                            .title(item.diaChi.diaChiChiTiet)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                            .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                }
                            }
                        }
                    }
                    progressDialog.dismiss();
                }
                else if(luaChon.equals("chontoado")) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.399367, 108.010967), 6));
                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {

                            diaChi=getDiaChiByLaLng(latLng);
                            putPhongTroCanMuon(latLng,diaChi);


                        }
                    });
                }
                else {
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
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17));
                }
                if (luaChon.equals("khuvuc")) {
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 6));
                }
                if (luaChon.equals("tuchon")) {
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.399367, 108.010967), 8));
                }

            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (luaChon.equals("chitiet")) {
            MenuItem menuItem = menu.add(1,1,1,"Chỉ đường");
            menuItem.setIcon(R.drawable.ic_directions_white_36dp);
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            sendRequest(latLang + "," + latLong);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onInfoWindowClick( Marker marker) {
        for (PhongTro item : listPhongTro) {
            if (item.latitude == marker.getPosition().latitude && item.longtitue == marker.getPosition().longitude) {
                Intent intent = new Intent(MapsActivity.this, XemChiTietActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PHONGTRO", item);
                intent.putExtra("MY_BUNDLE", bundle);
                startActivity(intent);
            }
        }
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


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Vui lòng chờ.", "Đang tìm đường đi..!", true);
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }
        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }
        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.getStartLocation(), 16));

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_end))
                    .title(route.getStartAddress())
                    .position(route.getStartLocation())));

            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_start))
                    .title(route.getEndAddress())
                    .position(route.getEndLocation())));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.getPoints().size(); i++)
                polylineOptions.add(route.getPoints().get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
    public void putPhongTroCanMuon(final LatLng latLng, final String diaChi) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent(MapsActivity.this, DangTinTimPhongActivity.class);
                        intent.putExtra("diachi","diachidcchon");
                        Bundle bundle = new Bundle();
                        bundle.putString("diaChiTimDuoc", diaChi);
                        bundle.putDouble("latitude", latLng.latitude);
                        bundle.putDouble("longitude", latLng.longitude);
                        intent.putExtras(bundle);
                        startActivity(intent);



                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc chắn muốn tìm xung quanh địa điểm này?")
                .setPositiveButton("Có", dialogClickListener)
                .setNegativeButton("Không", dialogClickListener).show();
    }
    public String getDiaChiByLaLng(LatLng latLng){
        List<Address> addresses = new ArrayList<>();
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude,1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        android.location.Address address = addresses.get(0);

        if (address != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++){
                sb.append(address.getAddressLine(i) + " ");
            }
            return sb.toString();
        }
        return null;
    }

    }


