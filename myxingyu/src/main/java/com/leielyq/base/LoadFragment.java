package com.leielyq.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import cn.bmob.v3.Bmob;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2016/11/2 0002.
 */

public abstract class LoadFragment extends SupportFragment {
    //是否可见
    protected boolean isVisble;
    // 标志位，标志Fragment已经初始化完成。
    public boolean isPrepared = false;
    //判断是否为第二次加载,如果是则不需要重新加载数据
    public boolean isFragment = true;

    /**
     * 实现Fragment数据的缓加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisble = true;
            if (isFragment) {
                onVisible();
                isFragment = false;
            }
        } else {
            isVisble = false;
            onInVisible();
        }
    }

    protected void onInVisible() {

    }

    protected void onVisible() {
        //加载数据
        loadData();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bmob.initialize(getContext(), "fb6f19fc34491e98673d2636d29cb164");
        super.onCreate(savedInstanceState);
    }

    protected abstract void loadData();
}
