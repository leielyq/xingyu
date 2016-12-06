package com.leielyq.show;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.leielyq.base.BaseFragment;
import com.leielyq.bean.Subject;
import com.leielyq.xingyu.BuildConfig;
import com.leielyq.xingyu.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListRecentlyFragment extends ListRecyclerFragment {


    @Override
    public void onQuery(final Boolean STATUI_LOADDATA) {
        final BmobQuery<Subject> query = new BmobQuery<>();
        query.include("sender");
        query.order("rank");
        query.setLimit(10);
        if (STATUI_LOADDATA) {
            if (BuildConfig.DEBUG) Log.d("ListRecommendFragment", "加载更多");
            query.setSkip(10 * index);
            index++;
        }
        query.findObjects(new FindListener<Subject>() {
            @Override
            public void done(List<Subject> list, BmobException e) {
                if (e == null) {
                    if (list.isEmpty()) {
                        Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                    } else {
                        if (STATUI_LOADDATA == false && subjects.size() > 0) {
                            subjects.clear();
                        }
                        subjects.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    if (BuildConfig.DEBUG) Log.d("ListRecommendFragment2", e.toString());
                }
                showContent(STATUI_LOADDATA);
            }
        });
    }
}
