package com.leielyq.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.myxingyu.R;

import cn.bmob.v3.Bmob;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2016/11/22 0022.
 */

public abstract class BaseFragment extends SupportFragment {
    protected Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        Bmob.initialize(mActivity, "fb6f19fc34491e98673d2636d29cb164");
    }

    public void initToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setHasOptionsMenu(true);
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) mActivity;
        mAppCompatActivity.setSupportActionBar(toolbar);
        //mAppCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
