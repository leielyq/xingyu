package com.leielyq.login;

/**
 * Created by Administrator on 2016/7/27.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.leielyq.base.BaseActivity;
import com.leielyq.bean.MyUser;
import com.leielyq.bean.user;
import com.leielyq.xingyu.MainActivity;
import com.leielyq.xingyu.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.etname)
    EditText mname;
    @Bind(R.id.etpassword)
    EditText mpassword;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    private SweetAlertDialog pDialog;
    private SharedPreferences userinfo;
    /*private String mid;*/
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        ButterKnife.bind(this);
        toolbar.setTitle("登陆");
        userinfo = this.getSharedPreferences("user", 0);
        if (userinfo.getString("autoLogin", "0") != "0") {
            /*mname.setText(user.getString("username", ""));
            mpassword.setText(user.getString("password", ""));*/
            MyUser bmobUser = MyUser.getCurrentUser(MyUser.class);
            if (bmobUser != null) {
                // 允许用户使用应用
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }


    /*private void downloadFile(BmobFile file) {
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        file.download(new DownloadFileListener() {

            @Override
            public void onStart() {
                //
            }

            @Override
            public void done(String savePath, BmobException e) {
                if (e == null) {
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = user.edit();
                    if (checkBox.isChecked()) {
                        editor.putString("autoLogin", "1");
                    }
                    editor.commit();
                    pDialog.dismiss();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, ("头像读取失败：" + e.getErrorCode() + "," + e.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                //Log.i("bmob", "下载进度：" + value + "," + newworkSpeed);
            }

        });
    }*/

    public void logining() {


        String name = mname.getText().toString();
        String password = mpassword.getText().toString();
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("登陆中");
        pDialog.setCancelable(false);
        pDialog.show();


        BmobUser.loginByAccount(name, password, new LogInListener<MyUser>() {

            @Override
            public void done(MyUser user, BmobException e) {
                if (user != null) {
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = userinfo.edit();
                    if (checkBox.isChecked()) {
                        editor.putString("autoLogin", "1");
                    }
                    editor.commit();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });

        /*BmobQuery<user> query = new BmobQuery<>();
        query.addWhereEqualTo("name", name);
        query.addWhereEqualTo("password", password);
        query.findObjects(new FindListener<user>() {
            @Override
            public void done(List<user> list, BmobException e) {
                if (list.size() > 0) {
                    BmobFile bmobFile = list.get(0).getImg();
                    mid = list.get(0).getObjectId();
                    if (bmobFile != null) {
                        downloadFile(bmobFile);
                    } else {
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = user.edit();
                        editor.putString("username", mname.getText().toString());
                        editor.putString("id", mid);
                        if (checkBox.isChecked()) {
                            editor.putString("password", mpassword.getText().toString());
                        }
                        editor.commit();
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        pDialog.dismiss();
                    }

                } else {
                    mpassword.setText("");
                    SharedPreferences.Editor editor = user.edit();
                    editor.putString("password", "");
                    Toast.makeText(LoginActivity.this, "用户名或者密码不对", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            }
        });*/

    }


    @OnClick({R.id.button, R.id.textView2, R.id.textView3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                String name = mname.getText().toString();
                String password = mpassword.getText().toString();
                if (password.equals("") || name.equals("")) {
                    Toast.makeText(this, "用户名或者密码未输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                logining();
                break;
            case R.id.textView2:
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
                break;
            case R.id.textView3:
                Toast.makeText(this, "暂时未接入辅助安全工具，大人请牢记自己的密码哦", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
