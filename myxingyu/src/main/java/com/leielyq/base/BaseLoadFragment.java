package com.leielyq.base;

import android.util.Log;
import android.widget.Toast;

import com.example.myxingyu.BuildConfig;
import com.leielyq.adapter.MainAdapter;
import com.leielyq.base.LoadFragment;
import com.leielyq.bean.Subject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2016/11/25 0025.
 */

public abstract class BaseLoadFragment extends SupportFragment {

    protected abstract void setQueryData(BmobQuery query);

    protected void queryData(final List<Subject> mlist, final MainAdapter mAdapter) {
        BmobQuery<Subject> query = new BmobQuery<>();
        setQueryData(query);
        query.findObjects(new FindListener<Subject>() {
            @Override
            public void done(List<Subject> list, BmobException e) {
                if (e == null) {
                    mlist.clear();
                    mlist.addAll(list);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(_mActivity, e.toString(), Toast.LENGTH_SHORT).show();
                    if (BuildConfig.DEBUG) Log.d("BaseLoadFragment", e.toString());
                }
                doQueryData();
            }
        });
    }

    protected abstract void doQueryData();

    protected abstract void doMoreData();

    protected void moreData(final MainAdapter mAdapter) {
        BmobQuery<Subject> query = new BmobQuery<>();
        final int mSize = mAdapter.getItemCount();
        query.setSkip(mSize);
        setQueryData(query);
        query.findObjects(new FindListener<Subject>() {
            @Override
            public void done(List<Subject> list, BmobException e) {
                if (e == null) {
                    if (!list.isEmpty())
                        mAdapter.addData(mSize - 1, list);
                    else Toast.makeText(_mActivity, "无更多数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(_mActivity, e.toString(), Toast.LENGTH_SHORT).show();
                    if (BuildConfig.DEBUG) Log.d("BaseLoadFragment", e.toString());
                }
                doMoreData();
            }
        });
    }


}
