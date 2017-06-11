package vn.home.com.bottombar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupWindow;
import android.widget.SeekBar;

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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import vn.home.com.model.PhongTro;
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
    private String diaChi;
    private int dienTich;
    private String luaChon;
    private List<PhongTro> listPhongTro;
    private DatabaseReference mDatabase;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    SeekBar sbGiaPhongBK,sbDienTichBK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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
            giaPhong = bundle.getDouble("giaphong");
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
            giaPhong = bundle.getDouble("giaphong");
            dienTich = bundle.getInt("dientich");

            progressDialog = ProgressDialog.show(this, "Vui lòng chờ.", "Đang tải bản đồ...", true);
        }else if (luaChon.equals("tuchon")) {
            Bundle bundle = getIntent().getExtras();
            banKinh = bundle.getDouble("bankinh");
            giaPhong = bundle.getDouble("giaphong");
            dienTich = bundle.getInt("dientich");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    private void sendRequest(String location_end) {
        try {
            new DirectionFinder(this, location_end, loc.latitude + "," + loc.longitude).Execute();
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

                    } else if (banKinh == 0 && dienTich == 0) {
                        for (PhongTro item : listPhongTro) {
                            if (item.giaPhong>giaPhong) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(item.latitude, item.longtitue))
                                        .title(item.diaChi.diaChiChiTiet)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                        .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                            }
                        }

                    }else if (banKinh == 0) {
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
                }
                else if(luaChon.equals("khuvuc")) {
                    if (giaPhong == 0 && dienTich == 0) {
                        for (PhongTro item : listPhongTro) {
                            if(item.diaChi.quan.equals(quanDuocChon)) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(item.latitude, item.longtitue))
                                        .title(item.diaChi.diaChiChiTiet)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                        .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                            }
                        }
                    } else if (giaPhong == 0) {
                        for (PhongTro item : listPhongTro) {
                            if (item.dienTich > dienTich && item.diaChi.quan.equals(quanDuocChon)) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(item.latitude, item.longtitue))
                                        .title(item.diaChi.diaChiChiTiet)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                        .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                            }
                        }
                    } else if (dienTich == 0) {
                        for (PhongTro item : listPhongTro) {
                            if (item.giaPhong>giaPhong && item.diaChi.quan.equals(quanDuocChon)) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(item.latitude, item.longtitue))
                                        .title(item.diaChi.diaChiChiTiet)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                        .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                            }
                        }

                    }
                    progressDialog.dismiss();
                }
                else if(luaChon.equals("tuchon")) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.399367, 108.010967), 6));
                    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            mMap.clear();
                            mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_icon)));
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
                                    if (CalculationByDistance(latLng, new LatLng(item.latitude, item.longtitue)) < banKinh)  {
                                        mMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(item.latitude, item.longtitue))
                                                .title(item.diaChi.diaChiChiTiet)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                                .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                    }
                                }

                            } else if (banKinh == 0 && dienTich == 0) {
                                for (PhongTro item : listPhongTro) {
                                    if (item.giaPhong>giaPhong) {
                                        mMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(item.latitude, item.longtitue))
                                                .title(item.diaChi.diaChiChiTiet)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                                .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                    }
                                }

                            }else if (banKinh == 0) {
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
                                    if (item.dienTich > dienTich && CalculationByDistance(latLng, new LatLng(item.latitude, item.longtitue)) < banKinh) {
                                        mMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(item.latitude, item.longtitue))
                                                .title(item.diaChi.diaChiChiTiet)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                                .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                    }
                                }
                            } else if (dienTich == 0) {
                                for (PhongTro item : listPhongTro) {
                                    if (item.giaPhong > giaPhong && CalculationByDistance(latLng, new LatLng(item.latitude, item.longtitue)) < banKinh) {
                                        mMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(item.latitude, item.longtitue))
                                                .title(item.diaChi.diaChiChiTiet)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_green_home))
                                                .snippet("Giá phòng: " + item.giaPhong + " triệu"));
                                    }
                                }
                            }
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
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
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17));
                }
                if (luaChon.equals("khuvuc")) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 6));
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
                intent.putExtra("PhongTro", item);
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
}