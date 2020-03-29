package bd.com.nabdroid.makedecision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewP;
    private SliderAdapter adapter;
    private BubbleNavigationLinearView bubbleNavigationLinearView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        /*bubbleNavigationLinearView.setTypeface(Typeface.createFromAsset(getAssets(),"rubik.ttf"));*/
        viewP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {
                bubbleNavigationLinearView.setCurrentActiveItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                viewP.setCurrentItem(position,true);
            }
        });

    }

    private void init() {
        List<Fragment> list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new ResultFragment());
        list.add(new AskOpinionFragment());
        /*list.add(new Groups());
        list.add(new Profile());*/
        viewP = findViewById(R.id.pager);
        adapter =new SliderAdapter(getSupportFragmentManager(),list);
        viewP.setAdapter(adapter);
        bubbleNavigationLinearView = findViewById(R.id.bubble_nav_bar);

    }


}
