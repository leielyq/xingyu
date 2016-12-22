package com.leielyq.mine;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myxingyu.R;
import com.leielyq.GlideLoader;
import com.leielyq.base.BaseFragment;
import com.leielyq.bean.MyUser;
import com.leielyq.mine.mysubject.MySubjectActivity;
import com.leielyq.sign.LoginFixActivity;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {

    @Bind(R.id.iv_do_num)
    ImageView iv01;
    @Bind(R.id.iv_img)
    ImageView ivImg;
    @Bind(R.id.tv_name)
    TextView tvName;

    public static MineFragment newInstance(String param1) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MineFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        checkUser();
    }

    private void checkUser() {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        if (user == null) {
            Intent intent = new Intent(mActivity, LoginFixActivity.class);
            startActivity(intent);
            mActivity.finish();
        }
    }

    public void initView() {
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        if (user != null) {
            tvName.setText(user.getUsername());
            if (user.getImg() != null)
                Glide.with(mActivity).load(user.getImg().getUrl()).into(ivImg);
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.bt_quit, R.id.bt_mine, R.id.bt_last, R.id.bt_like})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_quit:
                BmobUser.logOut();
                checkUser();
                break;
            case R.id.bt_mine:
                Intent intent = new Intent(mActivity, MySubjectActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_last:
                break;
            case R.id.bt_like:
                break;
        }
    }

    @OnClick(R.id.iv_img)
    public void onClick() {
        Intent intent = new Intent(_mActivity, MineContextActivity.class);
        startActivity(intent);
    }
}
