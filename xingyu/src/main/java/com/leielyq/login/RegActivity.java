package com.leielyq.login;

/**
 * Created by Administrator on 2016/7/27.
 */

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.leielyq.bean.MyUser;
import com.leielyq.bean.user;
import com.leielyq.xingyu.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.editText3)
    EditText mname;
    @Bind(R.id.editText4)
    EditText mpassword;
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_main);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.colorPrimary));
            tintManager.setStatusBarTintEnabled(true);
        }
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);
    }


    @OnClick(R.id.button2)
    public void onClick() {
        String name = mname.getText().toString();
        String password = mpassword.getText().toString();
        if (name.equals("") || password.equals("")) {
            return;
        }
        if (name.length() < 6 || password.length() < 6) {
            Toast.makeText(RegActivity.this, "用户名或者密码长度不够", Toast.LENGTH_SHORT).show();
            return;
        }
        final SweetAlertDialog pDialog = new SweetAlertDialog(RegActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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
                    Toast.makeText(RegActivity.this, ("注册成功"), Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                } else {
                    Toast.makeText(RegActivity.this, "失败，可能网络错误，再试试吧", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }
        });
    }
}