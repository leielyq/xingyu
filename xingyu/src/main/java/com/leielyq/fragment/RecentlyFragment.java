package com.leielyq.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.leielyq.OnItemTouchListener;
import com.leielyq.adapter.NewAdapter;
import com.leielyq.bean.MySubject;
import com.leielyq.xingyu.R;
import com.leielyq.xingyu.SingleActivity;

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
 * A simple {@link Fragment} subclass.
 */
public class RecentlyFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.swipe_target)
    RecyclerView recyclerView;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private NewAdapter mAdapter;
    private List<MySubject> mdatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recently, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mdatas = new ArrayList<>();
        initview();
        queryData(0, STATE_REFRSH);
    }


    public void initview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NewAdapter(mdatas);
        recyclerView.setAdapter(mAdapter);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        recyclerView.addOnItemTouchListener(new OnItemTouchListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {

                MySubject subject = mdatas.get(position);

                subject.increment("rank");
                subject.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });

                Intent intent = new Intent(getContext(), SingleActivity.class);
                intent.putExtra("item", (Serializable) mdatas.get(position).getMitem());
                intent.putExtra("jieguo", (Serializable) mdatas.get(position).getMresult());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh, int position) {
                new SweetAlertDialog(getContext())
                        .setTitleText("测试相关")
                        .setContentText(mdatas.get(position).getMcontext())
                        .show();
            }

        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
        query.order("-createdAt");
        if (actionType == STATE_MORE && lasttime != null) {
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
                        mAdapter.notifyDataSetChanged();
                    } else if (actionType == STATE_MORE)
                        Toast.makeText(getContext(), "没有更多的数据了", Toast.LENGTH_SHORT).show();
                    else if (actionType == STATE_REFRSH) {
                        if (mdatas.size() > 0)
                            mdatas.clear();
                        Toast.makeText(getContext(), "没有数据", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(getContext(), "加载数据失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
