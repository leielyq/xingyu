package com.leielyq.show;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.classic.common.MultipleStatusView;
import com.leielyq.adapter.MainAdapter;
import com.leielyq.base.BaseFragment;
import com.leielyq.bean.Subject;
import com.leielyq.xingyu.BuildConfig;
import com.leielyq.xingyu.MSingleActivity;
import com.leielyq.xingyu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class ListRecyclerFragment extends BaseFragment implements OnLoadMoreListener, OnRefreshListener {


    @Bind(R.id.swipe_target)
    RecyclerView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    protected MainAdapter adapter;
    protected List<Subject> subjects;
    protected MultipleStatusView multipleStatusView;

    public ListRecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        multipleStatusView = (MultipleStatusView) view.findViewById(R.id.main_multiplestatusview);
        multipleStatusView.showLoading();
        initView();
        return view;
    }

    protected void initView() {
        isPrepared = true;
        loadData();

        subjects = new ArrayList<>();
        adapter = new MainAdapter(getContext(), subjects);
        swipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeTarget.setAdapter(adapter);
        swipeTarget.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Subject subject = subjects.get(i);
                if (subject.getHp() == null) {

                } else {
                    Intent intent = new Intent(getContext(), MSingleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", subject.getObjectId());
                    bundle.putInt("hp", subject.getHp());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void loadData() {
        if (!isPrepared || !isVisble) {
            return;
        }
        onQuery(false);
        //multipleStatusView.showContent();
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
                //swipeToLoadLayout.setRefreshing(false);
                onQuery(false);
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //swipeToLoadLayout.setLoadingMore(false);
                onQuery(true);
            }
        }, 1000);
    }

    protected void showContent(Boolean STATUI_LOADDATA) {
        if (STATUI_LOADDATA)
            swipeToLoadLayout.setLoadingMore(false);
        else
            swipeToLoadLayout.setRefreshing(false);
        multipleStatusView.showContent();
    }

    protected int index = 1;

    public abstract void onQuery(final Boolean STATUI_LOADDATA);
}