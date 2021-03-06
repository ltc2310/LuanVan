package vn.home.com.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import vn.home.com.bottombar.DangNhapActivity;
import vn.home.com.bottombar.DangTinChoThueActivity;
import vn.home.com.bottombar.DangTinTimPhongActivity;
import vn.home.com.bottombar.DuyetTinActivity;
import vn.home.com.bottombar.MainActivity;
import vn.home.com.bottombar.QuanLyActivity;
import vn.home.com.bottombar.R;

/**
 * Created by THANHCONG on 2/28/2017.
 */

public class MenuFragment extends Fragment {
    private FirebaseAuth auth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            v = inflater.inflate(R.layout.layout_menu, container, false);

            Button btnDangNhap = (Button) v.findViewById(R.id.btnDangNhapMenu);
            Button btnThoat = (Button) v.findViewById(R.id.btnThoat);
            Button btnThongTin = (Button) v.findViewById(R.id.btnThongTin);
            Button btnTroGiup = (Button) v.findViewById(R.id.btnTroGiup);
            btnDangNhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                    startActivity(intent);
                }
            });
            btnThoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage("Bạn có chắc chắn muốn thoát?")
                            .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            }).setNegativeButton("Hủy bỏ", null).show();
                }
            });
            btnThongTin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Thông tin phiên bản")
                            .setMessage("App Phong Tro V1.2017")
                            .show();
                }
            });
            btnTroGiup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Hổ trợ")
                            .setMessage("Mọi thắc mắc vui lòng gửi email đến địa chỉ thanhcong.dev@gmail.com")
                            .show();
                }
            });


        } else {
            v = inflater.inflate(R.layout.layout_menu_login, container, false);
            Button btnDangXuat = (Button) v.findViewById(R.id.btnDangXuat);
            Button btnNguoiDung = (Button) v.findViewById(R.id.btnNguoiDung);
//            Button btnDangTin = (Button) v.findViewById(R.id.btnDangTin);
            Button btnThoat = (Button) v.findViewById(R.id.btnThoat);
            Button btnThongTin = (Button) v.findViewById(R.id.btnThongTin);
            Button btnTroGiup = (Button) v.findViewById(R.id.btnTroGiup);
            Button btnDuyetTinCM = (Button) v.findViewById(R.id.btnDuyeTinCM);
            btnDuyetTinCM.setEnabled(false);
            btnDuyetTinCM.setVisibility(View.INVISIBLE);
            if (auth.getCurrentUser().getEmail().equals("thanhcong.dev@gmail.com")) {
                btnDuyetTinCM.setVisibility(View.VISIBLE);
                btnDuyetTinCM.setEnabled(true);
            }

            Button btnQuanLy = (Button) v.findViewById(R.id.btnQuanLyTin);

            btnNguoiDung.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Thông tin người dùng ")
                            .setMessage("Email:" + auth.getCurrentUser().getEmail())
                            .show();
                }
            });

            btnDangXuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auth.signOut();
                    Toast.makeText(getActivity(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
//            btnDangTin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//                    alertDialog.setTitle("Chọn tin cần đăng");
//                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Đăng tin cho mướn", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Intent intent = new Intent(getActivity(), DangTinChoThueActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Đăng tin cần mướn", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Intent intent = new Intent(getActivity(), DangTinTimPhongActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//                    alertDialog.show();
//
//                }
//            });
            btnThongTin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Thông tin phiên bản")
                            .setMessage("App Phong Tro V1.2017")
                            .show();
                }
            });
            btnTroGiup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Hổ trợ")
                            .setMessage("Mọi thắc mắc vui lòng gửi email đến địa chỉ thanhcong.dev@gmail.com")
                            .show();
                }
            });
            btnThoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage("Bạn có chắc chắn muốn thoát?")
                            .setPositiveButton("Chấp nhận", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                    System.exit(1);
                                }
                            }).setNegativeButton("Hủy bỏ", null).show();
                }
            });

            btnDuyetTinCM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), DuyetTinActivity.class));
                }
            });

            btnQuanLy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), QuanLyActivity.class));
                }
            });
        }
        return v;
    }
}
