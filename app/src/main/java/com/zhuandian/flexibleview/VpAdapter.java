package com.zhuandian.flexibleview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * desc :
 * author：xiedong
 * data：2018/11/12
 */
public class VpAdapter extends FragmentPagerAdapter {
    private List<Fragment> mDatas;

    public VpAdapter(FragmentManager fm, List<Fragment> mDatas) {
        super(fm);
        this.mDatas = mDatas;
    }

    @Override
    public Fragment getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
}
