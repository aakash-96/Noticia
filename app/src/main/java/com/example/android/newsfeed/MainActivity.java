package com.example.android.newsfeed;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;

import com.example.android.newsfeed.Fragments.Fragment_Business;
import com.example.android.newsfeed.Fragments.Fragment_Others;
import com.example.android.newsfeed.Fragments.Fragment_Sports;
import com.example.android.newsfeed.Fragments.Fragment_Technology;
import com.example.android.newsfeed.Fragments.Fragment_Test;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        generateTabs();

    }

    private void generateTabs()
    {
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Entertainment"));
        tabLayout.addTab(tabLayout.newTab().setText("Technology"));
        tabLayout.addTab(tabLayout.newTab().setText("Sports"));
        tabLayout.addTab(tabLayout.newTab().setText("Business"));

        viewPager = (ViewPager) findViewById(R.id.main_activity_viewpager);
        viewPager.setOffscreenPageLimit(2);
        adapter = new TabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public class TabPagerAdapter extends FragmentPagerAdapter
    {
        int tabCount;
        public TabPagerAdapter(FragmentManager fragmentManager, int numberOfTabs)
        {
            super(fragmentManager);
            this.tabCount = numberOfTabs;
        }

        @Override
        public Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    Fragment_Test tab = new Fragment_Test();
                    return tab;
                case 1:
                    Fragment_Others tab1 = new Fragment_Others(MainActivity.this,1);
                    return tab1;
                case 2:
                    Fragment_Technology tab2 = new Fragment_Technology((MainActivity.this));
                    //Fragment_Others tab2 = new Fragment_Others(MainActivity.this,2);
                    return tab2;
                case 3:
                    Fragment_Sports tab3 = new Fragment_Sports(MainActivity.this);
                    return tab3;
                case 4:
                    Fragment_Business tab4 = new Fragment_Business(MainActivity.this);
                    return tab4;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }
}
