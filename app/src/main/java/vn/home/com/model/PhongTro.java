package vn.home.com.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by THANHCONG on 2/22/2017.
 */

public class PhongTro implements Serializable {
    public String idNguoiDung;
    public Double latitude;
    public Double longtitue;
    public DiaChi diaChi;
    public Integer dienTich;
    public Double giaPhong;
    public String sdt;
    public String moTa;
    public String ngayDang;
    public ArrayList<String> linkHinh;
    public boolean kichHoat;

    public PhongTro(String idNguoiDung, Double latitude, Double longtitue, DiaChi diaChi,
                    Integer dienTich, Double giaPhong, String sdt, String moTa, String ngayDang, ArrayList<String> linkHinh,boolean kichHoat) {

        this.idNguoiDung = idNguoiDung;
        this.latitude = latitude;
        this.longtitue = longtitue;
        this.diaChi = diaChi;
        this.dienTich = dienTich;
        this.giaPhong = giaPhong;
        this.sdt = sdt;
        this.moTa = moTa;
        this.ngayDang = ngayDang;
        this.linkHinh = linkHinh;
        this.kichHoat = kichHoat;
    }

    public PhongTro(PhongTro phongTro) {
    }

    public PhongTro() {
    }

    public PhongTro(String ngayDang,DiaChi diaChi, Double giaPhong,Integer dienTich,ArrayList<String> linkHinh) {
        this.ngayDang = ngayDang;
        this.diaChi = diaChi;
        this.giaPhong = giaPhong;
        this.dienTich = dienTich;
        this.linkHinh = linkHinh;

    }
}
