package com.leielyq.xingyu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leielyq.GlideLoader;
import com.leielyq.bean.MItem;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendItemActivity extends AppCompatActivity {
    @Bind(R.id.send_item_add_img)
    ImageView sendItemAddImg;
    @Bind(R.id.item_img_add)
    Button itemImgAdd;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.text_title)
    EditText et_title;
    @Bind(R.id.text_q1)
    EditText et_q1;
    @Bind(R.id.text_n1)
    EditText et_n1;
    @Bind(R.id.text_q2)
    EditText et_q2;
    @Bind(R.id.text_n2)
    EditText et_n2;
    @Bind(R.id.text_q3)
    EditText et_q3;
    @Bind(R.id.text_n3)
    EditText et_n3;
    @Bind(R.id.text_q4)
    EditText et_q4;
    @Bind(R.id.text_n4)
    EditText et_n4;
    private SystemBarTintManager tintManager;
    private MItem mitem;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_item);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.colorPrimary));
            tintManager.setStatusBarTintEnabled(true);
        }
        toolbar.setTitle("小题目");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        MItem item = intent.getParcelableExtra("item");
        id = intent.getIntExtra("id", -1);
        if (item != null) {
            et_title.setText(item.getTitle());
            et_q1.setText(item.getQ1());
            et_q2.setText(item.getQ2());
            et_q3.setText(item.getQ3());
            et_q4.setText(item.getQ4());
            et_n1.setText(item.getN1());
            et_n2.setText(item.getN2());
            et_n3.setText(item.getN3());
            et_n4.setText(item.getN4());
            if (item.getLoactimg() != null) {
                Glide.with(this).load(item.getLoactimg().getPath()).into(sendItemAddImg);
                sendItemAddImg.setVisibility(View.VISIBLE);
                itemImgAdd.setText("修改图片");
            }
        }

        mitem = new MItem();
    }

    @OnClick(R.id.item_img_add)
    public void onClick() {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/Pictures")
                .build();
        ImageSelector.open(SendItemActivity.this, imageConfig);   // 开启图片选择器
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            for (final String path : pathList) {
                GlideLoader glideLoader = new GlideLoader();
                File file = new File(path);
                //mpath = path;
                glideLoader.setpath(this, path, sendItemAddImg);
                mitem.setLoactimg(file);
            }
            sendItemAddImg.setVisibility(View.VISIBLE);
            itemImgAdd.setText("修改图片");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.nav_search) {
            String str_title = et_title.getText().toString();
            String str_q1 = et_q1.getText().toString();
            String str_q2 = et_q2.getText().toString();
            String str_q3 = et_q3.getText().toString();
            String str_q4 = et_q4.getText().toString();
            String str_n1 = et_n1.getText().toString();
            String str_n2 = et_n2.getText().toString();
            String str_n3 = et_n3.getText().toString();
            String str_n4 = et_n4.getText().toString();
            if (str_n2.equals(""))
                Toast.makeText(SendItemActivity.this, "还没有输入完整的题目信息啦", Toast.LENGTH_SHORT).show();
            else {
                mitem.setN1(str_n1);
                mitem.setN2(str_n2);
                mitem.setN3(str_n3);
                mitem.setN4(str_n4);
                mitem.setQ1(str_q1);
                mitem.setQ2(str_q2);
                mitem.setQ3(str_q3);
                mitem.setQ4(str_q4);
                mitem.setTitle(str_title);
                Intent intent = new Intent();
                intent.putExtra("info", mitem);
                intent.putExtra("id", id);
                setResult(1001, intent);
                finish();
            }
        } else if (menuItemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}