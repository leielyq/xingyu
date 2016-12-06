package com.leielyq.xingyu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.leielyq.adapter.MainAdapter;
import com.leielyq.bean.Subject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ListActivity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener {
    private List<Subject> subjects;
    private MainAdapter adapter;
    private SwipeToLoadLayout swipeToLoadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("列表");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);


        subjects = new ArrayList<>();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
        adapter = new MainAdapter(ListActivity.this, subjects);
        recyclerView.setAdapter(adapter);
        BmobQuery<Subject> query = new BmobQuery<>();
        query.order("-createdAt");
        query.setLimit(10);
        query.include("sender");
        query.findObjects(new FindListener<Subject>() {
            @Override
            public void done(List<Subject> list, BmobException e) {
                if (e == null) {
                    subjects.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ListActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                    if (BuildConfig.DEBUG) Log.d("ListActivity", e.toString());
                }
            }
        });

        autoRefresh();

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                Intent intent = new Intent(ListActivity.this, MSingleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("hp", subjects.get(position).getHp());
                bundle.putString("id", subjects.get(position).getObjectId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
                Toast.makeText(ListActivity.this, "刷新完成", Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
                Toast.makeText(ListActivity.this, "加载更多", Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }

    private void autoRefresh() {
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }

}
