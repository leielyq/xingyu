package com.leielyq.base;

import android.os.Bundle;

import com.example.myxingyu.R;
import com.jaeger.library.StatusBarUtil;

import cn.bmob.v3.Bmob;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2016/11/27 0027.
 */

public class BaseInitActivity extends SupportActivity {

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
}
