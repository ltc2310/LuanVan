package vn.home.com.model;

import java.io.Serializable;

/**
 * Created by THANHCONG-PC on 6/10/2017.
 */

public class NguoiDung implements Serializable {
    public String email;
    public String sdt;
    public String hoTen;

    public NguoiDung(String email, String sdt, String hoTen) {
        this.email = email;
        this.sdt = sdt;
        this.hoTen = hoTen;
    }

    public NguoiDung() {
    }


}
