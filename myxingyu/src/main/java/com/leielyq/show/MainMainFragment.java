package com.leielyq.show;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.myxingyu.R;
import com.jaeger.library.StatusBarUtil;
import com.leielyq.base.BaseFragment;
import com.leielyq.chat.ChatFragment;
import com.leielyq.find.FindFragment;
import com.leielyq.mine.MineFragment;


import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

import static com.example.myxingyu.R.color.white;

public class MainMainFragment extends BaseFragment implements BottomNavigationBar.OnTabSelectedListener {
    @Bind(R.id.bnb_main)
    BottomNavigationBar bnbMain;
    private SupportFragment[] fragments = new SupportFragment[4];


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_main, container, false);
        ButterKnife.bind(this, view);
        if (savedInstanceState == null) {
            fragments[0] = MainFragment.newInstance("首页");
            fragments[1] = FindFragment.newInstance("发现");
            fragments[2] = ChatFragment.newInstance("聊天");
            fragments[3] = MineFragment.newInstance("我的");
            loadMultipleRootFragment(R.id.ll, 0, fragments);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getChildFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            fragments[0] = findChildFragment(MainFragment.class);
            fragments[1] = findChildFragment(FindFragment.class);
            fragments[2] = findChildFragment(ChatFragment.class);
            fragments[3] = findChildFragment(MineFragment.class);
        }


        bnbMain.addItem(new BottomNavigationItem(R.drawable.ic_home, "主页"))
                .addItem(new BottomNavigationItem(R.drawable.ic_plus_one, "动态"))
                .addItem(new BottomNavigationItem(R.drawable.ic_done, "发现"))
                .addItem(new BottomNavigationItem(R.drawable.ic_my, "我的"))
                .setFirstSelectedPosition(0)
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setInActiveColor(R.color.white)
                .setBarBackgroundColor(R.color.colorPrimary)
                .initialise();
        bnbMain.setTabSelectedListener(this);
        return view;
    }

    private boolean isFullScreen = false;

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.length) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments[position];
                if (fragment.isAdded()) {
                    ft.show(fragment);
                } else {
                    ft.add(R.id.ll, fragment);
                }
                ft.commitAllowingStateLoss();
            }
            switch (position) {
                case 3:
                    isFullScreen = true;
                    StatusBarUtil.setTranslucentForImageViewInFragment(mActivity, 0, null);
                    break;
                default:
                    if (isFullScreen) {
                        resetFragmentView(fragments[position]);
                    }
                    StatusBarUtil.setColor(mActivity, ContextCompat.getColor(mActivity, R.color.colorPrimary), 0);
                    break;
            }

        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.length) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments[position];
                ft.hide(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void resetFragmentView(Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View contentView = fragment.getView().findViewById(android.R.id.content);
            if (contentView != null) {
                ViewGroup rootView;
                rootView = (ViewGroup) ((ViewGroup) contentView).getChildAt(0);
                if (rootView.getPaddingTop() != 0) {
                    rootView.setPadding(0, 0, 0, 0);
                }
            }
            if (fragment.getView() != null)
                fragment.getView().setPadding(0, getStatusBarHeight(mActivity), 0, 0);
        }
    }

    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
