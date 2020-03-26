package bd.com.nabdroid.makedecision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ColorStateList colorStateList;

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


                case R.id.groups_nav:
                    Toast.makeText(MainActivity.this, "Groups", Toast.LENGTH_SHORT).show();
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
