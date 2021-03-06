package com.leielyq.send;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.myxingyu.R;
import com.leielyq.GlideLoader;
import com.leielyq.base.BaseActivity;
import com.leielyq.bean.Item;
import com.leielyq.bean.ItemChild;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MSendItemActivity extends BaseActivity {

    @Bind(R.id.send_recy)
    RecyclerView sendRecy;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.text_title)
    EditText textTitle;
    @Bind(R.id.send_item_add_img)
    ImageView sendItemAddImg;
    @Bind(R.id.item_img_add)
    Button itemImgAdd;
    @Bind(R.id.mtoolbar)
    Toolbar mtoolbar;
    private SendItemSelectAdapter mAdapter;
    private List<ItemChild> mitems;
    private int id;
    private Item mitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msend_item);
        ButterKnife.bind(this);

        mtoolbar.setTitle("小题");
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MSendItemActivity.this, MSendItemSelectActivity.class);
                startActivityForResult(intent, 1002);
            }
        });
        mitem = new Item();
        mitems = new ArrayList<>();
        sendRecy.setLayoutManager(new LinearLayoutManager(MSendItemActivity.this));
        mAdapter = new SendItemSelectAdapter(mitems);
        sendRecy.setAdapter(mAdapter);
        sendRecy.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(MSendItemActivity.this, MSendItemSelectActivity.class);
                intent.putExtra("itemchild", mitems.get(i));
                intent.putExtra("id", i);
                startActivityForResult(intent, 1002);
            }
        });

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        Item item = intent.getParcelableExtra("item");
        if (item != null) {
            mitems.clear();
            mitems.addAll(item.getItemchildrens());
            textTitle.setText(item.getTitle());
            if (item.getLoactimg() != null) {
                Glide.with(this).load(item.getLoactimg().getPath()).into(sendItemAddImg);
                sendItemAddImg.setVisibility(View.VISIBLE);
                itemImgAdd.setText("修改图片");
            }
        }
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
        ImageSelector.open(MSendItemActivity.this, imageConfig);   // 开启图片选择器
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
        } else if (requestCode == 1002 && resultCode == 1003) {
            if (data.getIntExtra("id", -1) == -1)
                mitems.add((ItemChild) data.getParcelableExtra("itemchild"));
            else {
                mitems.remove(data.getIntExtra("id", -1));
                mitems.add(data.getIntExtra("id", -1), (ItemChild) data.getParcelableExtra("itemchild"));
            }
            mAdapter.notifyDataSetChanged();
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
            String mtitle = textTitle.getText().toString();
            if (mtitle.equals(""))
                Toast.makeText(MSendItemActivity.this, "还没有输入完整的结果信息啦", Toast.LENGTH_SHORT).show();
            else {
                if (mitems.isEmpty()) {
                    Toast.makeText(this, "请添加选项", Toast.LENGTH_SHORT).show();
                } else {
                    mitem.setTitle(mtitle);
                    mitem.setItemchildrens(mitems);
                    Intent intent = new Intent();
                    intent.putExtra("item", mitem);
                    intent.putExtra("id", id);
                    setResult(1001, intent);
                    finish();
                }

            }
        } else if (menuItemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
