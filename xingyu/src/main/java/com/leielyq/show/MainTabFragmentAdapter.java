package com.leielyq.show;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.leielyq.base.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class MainTabFragmentAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> mPagers;
    private String[] mTitles;

    public MainTabFragmentAdapter(FragmentManager fm, List<BaseFragment> mPagers, String[] mTitles) {
        super(fm);
        this.mPagers = mPagers;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mPagers.get(position);
    }

    @Override
    public int getCount() {
        return mPagers.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
