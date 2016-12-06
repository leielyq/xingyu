package com.leielyq.type;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.classic.common.MultipleStatusView;
import com.example.myxingyu.R;
import com.leielyq.adapter.TypeAdapter;
import com.leielyq.bean.MyType;
import com.leielyq.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.yokeyword.fragmentation.SupportFragment;

import static com.example.myxingyu.R.id.recyclerview;

/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends SupportFragment {


    @Bind(R.id.rv_type)
    RecyclerView rvType;
    private TypeAdapter mAdapter;
    private List<MyType> mlist;
    private MultipleStatusView msv;

    public TypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        ButterKnife.bind(this, view);
        msv = (MultipleStatusView) view.findViewById(R.id.type_multiplestatusview);
        msv.showLoading();
        initView();
        return view;
    }

    private void initView() {
        mlist = new ArrayList<>();
        mAdapter = new TypeAdapter(mlist);
        BmobQuery<MyType> query = new BmobQuery<>();
        query.findObjects(new FindListener<MyType>() {
            @Override
            public void done(List<MyType> list, BmobException e) {
                if (e == null) {
                    mlist.clear();
                    mlist.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    msv.showContent();
                } else {
                    msv.showError();
                    Toast.makeText(_mActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        rvType.setLayoutManager(new GridLayoutManager(_mActivity, 3));
        rvType.setHasFixedSize(true);
        rvType.setAdapter(mAdapter);
        rvType.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(_mActivity, TypeListActivity.class);
                intent.putExtra("flag", mlist.get(i).getType());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
