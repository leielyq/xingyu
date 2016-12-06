package com.leielyq.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.leielyq.base.BaseActivity;
import com.leielyq.bean.MyUser;
import com.leielyq.xingyu.MainActivity;
import com.leielyq.xingyu.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class MLoginActivity extends BaseActivity {


    @Bind(R.id.mtoolbar)
    Toolbar mtoolbar;
    @Bind(R.id.etname)
    EditText etname;
    @Bind(R.id.etpassword)
    EditText etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            // 允许用户使用应用
            login();
        } else {
            //缓存用户对象为空时， 可打开用户注册界面…
        }
        setContentView(R.layout.activity_mlogin);
        ButterKnife.bind(this);
        mtoolbar.setTitle("星语");
    }

    @OnClick({R.id.login, R.id.register, R.id.forget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                BmobUser.loginByAccount(etname.getText().toString().trim(), etpassword.getText().toString().trim(), new LogInListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        if (myUser != null) {
                            login();
                        }
                    }
                });
                break;
            case R.id.register:
                break;
            case R.id.forget:
                break;
        }
    }

    private void login() {
        Intent intent = new Intent(MLoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
