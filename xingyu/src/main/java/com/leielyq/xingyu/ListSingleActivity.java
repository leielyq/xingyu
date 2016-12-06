package com.leielyq.xingyu;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.leielyq.OnItemTouchListener;
import com.leielyq.adapter.NewAdapter;
import com.leielyq.bean.MySubject;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2016/9/17 0017.
 */
public class ListSingleActivity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.swipe_target)
    RecyclerView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @Bind(R.id.nof)
    TextView nof;
    private SystemBarTintManager tintManager;
    private List<MySubject> mdatas;
    private NewAdapter adapter;
    private int type;
    private String search;
    private Context mcontext = ListSingleActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_single);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.colorPrimary));
            tintManager.setStatusBarTintEnabled(true);
        }
        mdatas = new ArrayList<>();
        Intent intent = getIntent();
        search = intent.getStringExtra("search");
        type = intent.getIntExtra("type", -1);
        toolbar.setTitle(search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        queryData(0, STATE_REFRSH);
        adapter = new NewAdapter(mdatas);
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        swipeTarget.setAdapter(adapter);
        swipeToLoadLayout.setOnRefreshListener(ListSingleActivity.this);
        swipeToLoadLayout.setOnLoadMoreListener(ListSingleActivity.this);
        swipeTarget.addOnItemTouchListener(new OnItemTouchListener(swipeTarget) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
                MySubject subject = mdatas.get(position);

                subject.increment("rank");
                subject.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });

                Intent intent = new Intent(ListSingleActivity.this, SingleActivity.class);
                intent.putExtra("item", (Serializable) mdatas.get(position).getMitem());
                intent.putExtra("jieguo", (Serializable) mdatas.get(position).getMresult());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh, int position) {
                new SweetAlertDialog(ListSingleActivity.this)
                        .setTitleText("测试相关")
                        .setContentText(mdatas.get(position).getMcontext())
                        .show();
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
                queryData(0, STATE_REFRSH);
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
                queryData(curpage, STATE_MORE);
            }
        }, 1000);
    }

    final int STATE_REFRSH = 0;
    final int STATE_MORE = 1;
    int limit = 10;
    int curpage = 0;
    String lasttime;

    public void queryData(final int page, final int actionType) {

        BmobQuery<MySubject> query = new BmobQuery<>();
        if (type == -1) {
            BmobQuery<MySubject> query1 = new BmobQuery<>();
            query1.addWhereEqualTo("mtitle", search);
            BmobQuery<MySubject> query2 = new BmobQuery<>();
            query2.addWhereEqualTo("sender", search);
            List<BmobQuery<MySubject>> list = new ArrayList<>();
            list.add(query1);
            list.add(query2);
            query.or(list);
            nof.setVisibility(View.VISIBLE);
        } else {
            query.addWhereEqualTo("type", search);
        }
        query.order("-createdAt");
        if (actionType == STATE_MORE) {
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = sdf.parse(lasttime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
            query.setSkip(page * limit + 1);
        }
        query.setLimit(limit);
        query.findObjects(new FindListener<MySubject>() {
            @Override
            public void done(List<MySubject> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        if (actionType == STATE_REFRSH) {
                            mdatas.clear();
                            curpage = 0;
                            lasttime = list.get(list.size() - 1).getCreatedAt();
                        }
                        for (MySubject i : list) {
                            mdatas.add(i);
                        }
                        curpage++;
                        adapter.notifyDataSetChanged();
                    } else if (actionType == STATE_MORE)
                        Toast.makeText(mcontext, "没有更多的数据了", Toast.LENGTH_SHORT).show();
                    else if (actionType == STATE_REFRSH) {
                        if (mdatas.size() > 0)
                            mdatas.clear();
                        Toast.makeText(mcontext, "没有数据", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(mcontext, "加载数据失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
