package vn.home.com.model;

import java.io.Serializable;

/**
 * Created by THANHCONG on 2/26/2017.
 */

public class DiaChi implements Serializable {
    public String thanhPho;
    public String quan;
    public String diaChiChiTiet;

    public DiaChi(String quan, String thanhPho, String diaChiChiTiet) {
        this.quan = quan;
        this.thanhPho = thanhPho;
        this.diaChiChiTiet = diaChiChiTiet;
    }

    public DiaChi() {
    }
}
