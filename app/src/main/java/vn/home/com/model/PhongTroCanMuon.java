package vn.home.com.model;

import java.io.Serializable;


/**
 * Created by THANHCONG-PC on 6/18/2017.
 */

public class PhongTroCanMuon implements Serializable {
    public String tenNguoiDung;
    public String diaChi;
    public Double dienTich;
    public Double giaPhongMin;
    public Double giaPhongMax;
    public String sdt;
    public String moTa;
    public String ngayDang;
    public boolean kichHoat;
    public String emailPhongTroCM;
    public String idPhongTroCM;
    public Double latitude;
    public Double longitude;
    public Double banKinh;
    public boolean ngungDangTinCM;

    public PhongTroCanMuon() {
    }

    public PhongTroCanMuon(String tenNguoiDung, String diaChi, Double dienTich, Double giaPhongMin, Double giaPhongMax,
                           String sdt, String moTa, String ngayDang, boolean kichHoat,
                           String emailPhongTroCM, String idPhongTroCM,Double latitude,
                           Double longitude, Double banKinh, boolean ngungDangTinCM) {
        this.tenNguoiDung = tenNguoiDung;
        this.diaChi = diaChi;
        this.dienTich = dienTich;
        this.giaPhongMin = giaPhongMin;
        this.giaPhongMax = giaPhongMax;
        this.sdt = sdt;
        this.moTa = moTa;
        this.ngayDang = ngayDang;
        this.kichHoat = kichHoat;
        this.emailPhongTroCM = emailPhongTroCM;
        this.idPhongTroCM = idPhongTroCM;
        this.latitude = latitude;
        this.longitude = longitude;
        this.banKinh = banKinh;
        this.ngungDangTinCM = ngungDangTinCM;
    }
}
