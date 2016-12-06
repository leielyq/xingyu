package com.leielyq.sign;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.myxingyu.R;
import com.leielyq.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class LoginFixActivity extends BaseActivity {

    @Bind(R.id.mtoolbar)
    Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fix);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.activity_login_fix, LoginFragment.newInstance());
        }
        initToolbar(mtoolbar, "星语");
    }

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
        // 设置无动画
        // return new DefaultNoAnimator();
        // 设置自定义动画
        // return new FragmentAnimator(enter,exit,popEnter,popExit);

        // 默认竖向(和安卓5.0以上的动画相同)
        //return super.onCreateFragmentAnimator();
    }
}
