package com.leielyq.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myxingyu.BuildConfig;
import com.example.myxingyu.R;
import com.leielyq.GlideLoader;
import com.leielyq.base.BaseInitActivity;
import com.leielyq.bean.MyUser;
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

public class MineContextActivity extends BaseInitActivity {

    @Bind(R.id.iv01)
    ImageView iv01;
    @Bind(R.id.mtoolbar)
    Toolbar mtoolbar;
    private MyUser user;
    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_context);
        ButterKnife.bind(this);
        mtoolbar.setTitle("修改信息");
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = MyUser.getCurrentUser(MyUser.class);
        if (user.getImg() != null) {
            Glide.with(this).load(user.getImg().getUrl()).into(iv01);
        } else
            Toast.makeText(this, "上传一个头像吧", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            pDialog = new SweetAlertDialog(MineContextActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(R.color.colorPrimary);
            pDialog.setTitleText("上传头像中");
            pDialog.setCancelable(false);
            pDialog.show();
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            for (final String path : pathList) {
                final BmobFile bmobFile = new BmobFile(new File(path));
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
                                        iv01.setImageURI(Uri.parse(path));
                                        Toast.makeText(MineContextActivity.this, "恭喜你解锁了新技能", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MineContextActivity.this, "修改失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    pDialog.dismiss();
                                }
                            });
                        } else {
                            Toast.makeText(MineContextActivity.this, "上传头像失败", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.iv01)
    public void onClick() {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .titleBgColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .titleSubmitTextColor(ContextCompat.getColor(this, R.color.white))
                .titleTextColor(ContextCompat.getColor(this, R.color.white))
                // 开启单选   （默认为多选）
                .singleSelect()
                .crop()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();
        ImageSelector.open(MineContextActivity.this, imageConfig);   // 开启图片选择器
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
