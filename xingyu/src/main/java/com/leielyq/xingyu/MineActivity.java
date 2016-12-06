package com.leielyq.xingyu;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leielyq.GlideLoader;
import com.leielyq.bean.MyUser;
import com.leielyq.login.LoginActivity;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/8/29.
 */
public class MineActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private SweetAlertDialog pDialog;
    private SystemBarTintManager tintManager;
    /*private String mid;*/
    @Bind(R.id.iv01)
    ImageView iv01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_main);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.colorPrimary));
            tintManager.setStatusBarTintEnabled(true);
        }
        toolbar.setTitle("我的");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*SharedPreferences preferences = this.getSharedPreferences("user", 0);
        mid = preferences.getString("id", "");
        String mpath = preferences.getString("url", "");
        *//*if (mpath != "")
            Glide.with(this).load(mpath).into(iv01);*/
        MyUser user = MyUser.getCurrentUser(MyUser.class);
        if (user.getImg() != null) {
            Glide.with(this).load(user.getImg().getUrl()).into(iv01);
        } else
            Toast.makeText(this, "上传一个头像吧", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            for (final String path : pathList) {
                final BmobFile bmobFile = new BmobFile(new File(path));
                pDialog = new SweetAlertDialog(MineActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("上传头像中");
                pDialog.setCancelable(false);
                pDialog.show();
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            MyUser user = new MyUser();
                            user.setImg(bmobFile);
                            MyUser myUser = MyUser.getCurrentUser(MyUser.class);
                            user.update(myUser.getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        //iv01.setImageURI(Uri.parse(path));
                                        Toast.makeText(MineActivity.this, "恭喜你解锁了新技能", Toast.LENGTH_SHORT).show();
                                        pDialog.dismiss();
                                    } else {
                                        Toast.makeText(MineActivity.this, "哎哟，这应该是bug", Toast.LENGTH_LONG).show();
                                        if (BuildConfig.DEBUG) Log.d("MineActivity", e.toString());
                                        pDialog.dismiss();
                                    }

                                }
                            });
                        } else {
                            Toast.makeText(MineActivity.this, "上传头像失败", Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                        }
                    }

                    @Override
                    public void onProgress(Integer value) {
                        pDialog.setTitleText("已上传：" + value.toString() + "%");
                    }
                });
            }
        }
    }

    @OnClick({R.id.iv01, R.id.textView4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv01:
                ImageConfig imageConfig
                        = new ImageConfig.Builder(new GlideLoader())
                        .titleBgColor(getResources().getColor(R.color.colorPrimary))
                        .titleSubmitTextColor(getResources().getColor(R.color.white))
                        .titleTextColor(getResources().getColor(R.color.white))
                        // 开启单选   （默认为多选）
                        .singleSelect()
                        .crop()
                        // 开启拍照功能 （默认关闭）
                        .showCamera()
                        // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                        .filePath("/ImageSelector/Pictures")
                        .build();
                ImageSelector.open(MineActivity.this, imageConfig);   // 开启图片选择器
                break;
            case R.id.textView4:
                MyUser.logOut();
                Intent intent = new Intent(MineActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}