package com.leielyq.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;
import com.leielyq.xingyu.R;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class BaseActivity extends AppCompatActivity {

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
        /*
        * 0表示完全透明
        * */
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
    }
}
