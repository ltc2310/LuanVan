package vn.home.com.utils;

import java.util.List;

import vn.home.com.model.Route;

/**
 * Created by THANHCONG-PC on 6/11/2017.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
