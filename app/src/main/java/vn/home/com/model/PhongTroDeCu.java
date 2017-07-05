package vn.home.com.model;

import java.io.Serializable;

/**
 * Created by ntdan on 6/24/2017.
 */

public class PhongTroDeCu implements Serializable {
    public String email;
    public int soLuong;
    public String id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PhongTroDeCu() {
    }

    public PhongTroDeCu(String email, int soLuong, String id) {
        this.email = email;
        this.soLuong = soLuong;
        this.id = id;
    }
}
