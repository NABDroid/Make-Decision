package bd.com.nabdroid.makedecision.activity;

import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.resources.TextAppearanceConfig;

import bd.com.nabdroid.makedecision.fragment.AskOpinionFragment;
import bd.com.nabdroid.makedecision.fragment.HomeFragment;
import bd.com.nabdroid.makedecision.fragment.ProfileFragment;
import bd.com.nabdroid.makedecision.R;
import bd.com.nabdroid.makedecision.fragment.ResultFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment(new HomeFragment());
        BottomNavigationView navView = findViewById(R.id.bottomNav);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home_nav:
                    replaceFragment(new HomeFragment());
                    return true;


                case R.id.history_nav:
                    replaceFragment(new ResultFragment());
                    return true;


                case R.id.post_nav:
                    replaceFragment(new AskOpinionFragment());
                    return true;


                case R.id.profile_nav:
                    replaceFragment(new ProfileFragment(MainActivity.this));
                    return true;
            }
            return false;
        }
    };


    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainFrameLayout, fragment);
        ft.commit();
    }


    private void addFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.mainFrameLayout, fragment);
        ft.commit();
    }


}
