package com.leielyq.single;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.myxingyu.BuildConfig;
import com.example.myxingyu.R;
import com.leielyq.adapter.SingleAdapter;
import com.leielyq.base.BaseActivity;
import com.leielyq.bean.Content;
import com.leielyq.bean.Item;
import com.leielyq.bean.Result;
import com.leielyq.bean.Subject;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/8/26.
 */
public class SingleActivity extends BaseActivity {


    @Bind(R.id.mtoolbar)
    Toolbar mtoolbar;
    @Bind(R.id.viewpager)
    RecyclerViewPager viewpager;
    private SingleAdapter mAdapter;
    private List<Item> items;
    private List<Result> results;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        ButterKnife.bind(this);
        initToolbar(mtoolbar, "做题");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        items = new ArrayList<>();
        results = new ArrayList<>();
        mAdapter = new SingleAdapter(SingleActivity.this, items, viewpager);
        viewpager.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        viewpager.setAdapter(mAdapter);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        BmobQuery<Content> query = new BmobQuery();
        Subject subject = new Subject();
        subject.setObjectId(bundle.getString("id"));
        query.addWhereEqualTo("subject", new BmobPointer(subject));
        query.findObjects(new FindListener<Content>() {
            @Override
            public void done(List<Content> list, BmobException e) {
                if (e == null) {
                    Content content = list.get(0);
                    items.addAll(content.getItems());
                    results.addAll(content.getResults());
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SingleActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    if (BuildConfig.DEBUG) Log.d("SingleActivity", e.toString());
                }

            }
        });
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
            int score = mAdapter.getScore();
            for (Result i : results) {
                if (score >= i.getMin() && score <= i.getMax()) {
                    new SweetAlertDialog(SingleActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("测试完成")
                            .setContentText(i.getResult())
                            .setConfirmText("分享")
                            .showCancelButton(true)
                            .setCancelText("确定")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.cancel();
                                    finish();
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog
                                            .setTitleText("成功!")
                                            .setContentText("感谢你对这个测试题的支持!")
                                            .showCancelButton(false)
                                            .setConfirmText("确定")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    finish();
                                                }
                                            })
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                }
                            })
                            .show();
                }
            }

        } else if (menuItemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}