package com.leielyq.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.myxingyu.R;
import com.jaeger.library.StatusBarUtil;

import cn.bmob.v3.Bmob;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class BaseActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "fb6f19fc34491e98673d2636d29cb164");
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected void setStatusBar() {
        //0表示完全透明
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
    }

    public Toolbar initToolbar(Toolbar toolbar, int title) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return toolbar;
    }

    public Toolbar initToolbar(Toolbar toolbar, CharSequence title) {
        setSupportActionBar(toolbar);
        return toolbar;
    }
}
