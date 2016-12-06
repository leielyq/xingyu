package com.leielyq.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.classic.common.MultipleStatusView;
import com.example.myxingyu.R;
import com.leielyq.adapter.MainAdapter;
import com.leielyq.bean.MyUser;
import com.leielyq.bean.Subject;
import com.leielyq.base.BaseLoadFragment;
import com.leielyq.single.MSingleActivity;
import com.leielyq.single.SingleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends BaseLoadFragment implements OnLoadMoreListener, OnRefreshListener {


    @Bind(R.id.swipe_target)
    RecyclerView swipeTarget;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private MultipleStatusView multipleStatusView;
    private MainAdapter mAdapter;
    private List<Subject> list;


    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(int i) {
        Bundle args = new Bundle();
        args.putInt("type", i);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ListFragment newInstance(int i, String query) {
        Bundle args = new Bundle();
        args.putInt("type", i);
        args.putString("flag", query);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        multipleStatusView = (MultipleStatusView) view.findViewById(R.id.main_multiplestatusview);
        ButterKnife.bind(this, view);
        initView();
        //isPrepared = true;
        //initLazyView(savedInstanceState);
        return view;
    }

    private void initView() {
        multipleStatusView.showLoading();
    }

    @Override
    protected void setQueryData(final BmobQuery query) {
        Bundle bundle = getArguments();
        int i = bundle.getInt("type");
        String mquery = bundle.getString("flag");
        switch (i) {
            case 1:
                //娱乐fragment
                query.addWhereEqualTo("sender", MyUser.getCurrentUser(MyUser.class));
                query.addWhereEqualTo("mold", 2);
                query.order("-createdAt");
                break;
            case 2:
                //测试类型
                query.addWhereEqualTo("sender", MyUser.getCurrentUser(MyUser.class));
                query.addWhereEqualTo("mold", 1);
                query.order("-createdAt");
                break;
            case 3:
                //推荐类型
                query.addWhereEqualTo("recommend", true);
                query.order("-createdAt");
                break;
            case 4:
                //热度类型
                query.order("-rank,-createdAt");
                break;
            case 5:
                //测试搜索
                query.addWhereEqualTo("title", mquery);
                query.addWhereEqualTo("mold", 1);
                query.order("-createdAt");
                break;
            case 6:
                //娱乐搜索
                query.addWhereEqualTo("title", mquery);
                query.addWhereEqualTo("mold", 2);
                query.order("-createdAt");
                break;
            case 7:
                //用户名搜索
                query.addWhereEqualTo("sendername", mquery);
                query.order("-createdAt");
                break;
            case 8:
                query.addWhereEqualTo("type", mquery);
                query.order("-createdAt");
                break;
            default:
                //默认类型
                query.order("-createdAt");
                break;
        }
        query.include("sender");
        query.setLimit(6);
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        list = new ArrayList<>();
        mAdapter = new MainAdapter(_mActivity, list);
        swipeTarget.setLayoutManager(new LinearLayoutManager(_mActivity));
        swipeTarget.setAdapter(mAdapter);
        swipeTarget.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Subject subject = (Subject) baseQuickAdapter.getData().get(i);
                subject.increment("rank");
                subject.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });
                if (subject.getHp() == null) {
                    Intent intent = new Intent(_mActivity, SingleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", subject.getObjectId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(_mActivity, MSingleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", subject.getObjectId());
                    bundle.putInt("hp", subject.getHp());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemLongClick(adapter, view, position);
                Subject subject = (Subject) baseQuickAdapter.getData().get(position);
                new SweetAlertDialog(_mActivity)
                        .setTitleText(subject.getTitle() + "的简介")
                        .setContentText(subject.getContext())
                        .setConfirmText("了解")
                        .show();
            }
        });
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        queryData(list, mAdapter);

    }

    @Override
    protected void doQueryData() {
        swipeToLoadLayout.setRefreshing(false);
        multipleStatusView.showContent();
        if (list.size() == 0) {
            multipleStatusView.showEmpty();
        }
    }

    @Override
    protected void doMoreData() {
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onLoadMore() {
        moreData(mAdapter);
    }

    @Override
    public void onRefresh() {
        queryData(list, mAdapter);
    }

}
