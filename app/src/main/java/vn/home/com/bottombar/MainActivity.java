package vn.home.com.bottombar;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import vn.home.com.fragment.FavoriteFragment;
import vn.home.com.fragment.MenuFragment;
import vn.home.com.fragment.SearchFragement;
import vn.home.com.fragment.TimelineFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_schedule) {
                    TimelineFragment timelineFragment = new TimelineFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,timelineFragment).commit();
                }
                if (tabId == R.id.tab_favorites) {
                    FavoriteFragment favoriteFragment = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,favoriteFragment).commit();
                }
                if (tabId == R.id.tab_search) {
                    SearchFragement searchFragement = new SearchFragement();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,searchFragement).commit();
                }
                if (tabId == R.id.tab_menu) {
                    MenuFragment menuFragment = new MenuFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.contentContainer,menuFragment).commit();
                }
            }
        });


    }
}
