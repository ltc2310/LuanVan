package vn.home.com.model;

import java.io.Serializable;


/**
 * Created by THANHCONG-PC on 6/18/2017.
 */

public class PhongTroCanMuon implements Serializable {
    public String tenNguoiDung;
    public String diaChi;
    public Integer dienTich;
    public Double giaPhongMin;
    public Double giaPhongMax;
    public String sdt;
    public String moTa;
    public String ngayDang;
    public boolean kichHoat;
    public String emailPhongTroCM;
    public String idPhongTroCM;

    public PhongTroCanMuon() {
    }

    public PhongTroCanMuon(String tenNguoiDung, String diaChi, Integer dienTich, Double giaPhongMin, Double giaPhongMax,
                           String sdt, String moTa, String ngayDang, boolean kichHoat, String emailPhongTroCM, String idPhongTroCM) {
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
    }
}
