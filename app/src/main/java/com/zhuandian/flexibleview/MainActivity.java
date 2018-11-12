package com.zhuandian.flexibleview;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Fragment> pages=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pages.add( SubFragment.getInstance(1));
        pages.add( SubFragment.getInstance(2));
        pages.add( SubFragment.getInstance(3));
        pages.add( SubFragment.getInstance(4));
        viewPager.setAdapter(new VpAdapter(getSupportFragmentManager(),pages));
    }


}
