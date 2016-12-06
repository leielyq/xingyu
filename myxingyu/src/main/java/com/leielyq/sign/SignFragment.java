package com.leielyq.sign;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myxingyu.R;
import com.leielyq.bean.MyUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignFragment extends SupportFragment {

    @Bind(R.id.etname)
    EditText etname;
    @Bind(R.id.etpassword)
    EditText etpassword;

    public static SignFragment newInstance() {
        return new SignFragment();
    }

    public SignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_sign)
    public void onClick() {
        String name = etname.getText().toString().trim();
        String password = etpassword.getText().toString().trim();
        if (name.equals("") || password.equals("")) {
            return;
        }
        if (name.length() < 6 || password.length() < 6) {
            Toast.makeText(_mActivity, "用户名或者密码长度不够", Toast.LENGTH_SHORT).show();
            return;
        }
        final SweetAlertDialog pDialog = new SweetAlertDialog(_mActivity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("注册中");
        pDialog.setCancelable(false);
        pDialog.show();
        MyUser user = new MyUser();
        user.setUsername(name);
        user.setPassword(password);
        user.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser s, BmobException e) {
                if (e == null) {
                    Toast.makeText(_mActivity, ("注册成功"), Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                } else {
                    Toast.makeText(_mActivity, "失败，换个用户名试试吧", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }
        });
    }
}
