package com.leielyq.send;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.myxingyu.BuildConfig;
import com.example.myxingyu.R;
import com.leielyq.bean.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MResultFragment extends Fragment {


    @Bind(R.id.send_title_recyclerview)
    RecyclerView sendTitleRecyclerview;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private SendResultAdapter mAdapter;
    private List<Result> mresults;

    public MResultFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mresult, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MSendResultActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        mresults = new ArrayList<>();
        sendTitleRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SendResultAdapter(mresults);
        sendTitleRecyclerview.setAdapter(mAdapter);
        sendTitleRecyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getContext(), MSendResultActivity.class);
                intent.putExtra("result", mresults.get(i));
                intent.putExtra("id", i);
                startActivityForResult(intent, 1000);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001) {
            if (data.getIntExtra("id", -1) == -1)
                mresults.add((Result) data.getParcelableExtra("jieguo"));
            else {
                mresults.remove(data.getIntExtra("id", -1));
                mresults.add(data.getIntExtra("id", -1), (Result) data.getParcelableExtra("jieguo"));
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    public List<Result> ResultToString() {
        if (mresults.size() > 0)
            return mresults;
        else
            return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
