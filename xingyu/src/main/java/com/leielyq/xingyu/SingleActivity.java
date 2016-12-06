package com.leielyq.xingyu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.leielyq.adapter.SingleAdpter;
import com.leielyq.bean.MItem;
import com.leielyq.bean.MResult;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/8/26.
 */
public class SingleActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private RecyclerViewPager mpagerview;
    private SingleAdpter singleRecyclerViewAdpter;
    private List<MItem> mdatas;
    private List<MResult> mresults;
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.colorPrimary));
            tintManager.setStatusBarTintEnabled(true);
        }
        mpagerview = (RecyclerViewPager) findViewById(R.id.viewpager);
        toolbar.setTitle("做题");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mdatas = (List<MItem>) intent.getSerializableExtra("item");
        mresults = (List<MResult>) intent.getSerializableExtra("jieguo");

        singleRecyclerViewAdpter = new SingleAdpter(SingleActivity.this, mdatas);
        mpagerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mpagerview.setAdapter(singleRecyclerViewAdpter);
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
            int score = singleRecyclerViewAdpter.getScore();
            for (MResult i : mresults) {
                if (score >= Integer.parseInt(i.getMin()) && score <= Integer.parseInt(i.getMax())) {
                    new SweetAlertDialog(SingleActivity.this)
                            .setTitleText("测试结果")
                            .setContentText(i.getResult())
                            .show();
                }
            }

        } else if (menuItemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}