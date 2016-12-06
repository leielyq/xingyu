package com.leielyq.xingyu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leielyq.GlideLoader;
import com.leielyq.OnItemTouchListener;
import com.leielyq.adapter.MSendItemAdapter;
import com.leielyq.adapter.MSendItemSelectAdapter;
import com.leielyq.adapter.SendItemAdapter;
import com.leielyq.bean.Item;
import com.leielyq.bean.ItemChild;
import com.leielyq.bean.MItem;
import com.leielyq.bean.MResult;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MSendItemActivity extends AppCompatActivity {

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
    private MSendItemSelectAdapter mAdapter;
    private List<ItemChild> mitems;
    private int id;
    private Item mitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msend_item);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("小题");
        setSupportActionBar(toolbar);
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
        mAdapter = new MSendItemSelectAdapter(mitems);
        sendRecy.setAdapter(mAdapter);
        sendRecy.addOnItemTouchListener(new OnItemTouchListener(sendRecy) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
                Intent intent = new Intent(MSendItemActivity.this, MSendItemSelectActivity.class);
                intent.putExtra("itemchild", mitems.get(position));
                intent.putExtra("id", position);
                startActivityForResult(intent, 1002);
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh, int position) {

            }
        });
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        Item item = intent.getParcelableExtra("item");
        if (item != null) {
            mitems = item.getItemchildrens();
            Log.d("MSendItemActivity", "mitems:" + mitems.get(0).getSelect());
            /*未知问题，必须要重新new一个适配器，再设置给显示器*/
            mAdapter = new MSendItemSelectAdapter(mitems);
            sendRecy.setAdapter(mAdapter);
            Toast.makeText(this, "mitem:" + mitem, Toast.LENGTH_SHORT).show();
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
                // mresults.add((MResult) data.getParcelableExtra("jieguo"));
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
                mitem.setTitle(mtitle);
                mitem.setItemchildrens(mitems);
                Toast.makeText(this, mitem.getItemchildrens().get(0).getSelect().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("item", mitem);
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
