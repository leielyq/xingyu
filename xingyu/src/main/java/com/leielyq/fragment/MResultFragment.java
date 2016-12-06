package com.leielyq.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leielyq.OnItemTouchListener;
import com.leielyq.adapter.MSendResultAdapter;
import com.leielyq.adapter.SendResultAdapter;
import com.leielyq.bean.MResult;
import com.leielyq.bean.Result;
import com.leielyq.xingyu.MSendResultActivity;
import com.leielyq.xingyu.R;
import com.leielyq.xingyu.SendResultActivity;

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
    private MSendResultAdapter mAdapter;
    private List<Result> mresults;

    public MResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        sendTitleRecyclerview = (RecyclerView) getView().findViewById(R.id.send_title_recyclerview);
        sendTitleRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MSendResultAdapter(mresults);
        sendTitleRecyclerview.setAdapter(mAdapter);
        sendTitleRecyclerview.addOnItemTouchListener(new OnItemTouchListener(sendTitleRecyclerview) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
                Intent intent = new Intent(getContext(), SendResultActivity.class);
                intent.putExtra("result", mresults.get(position));
                intent.putExtra("id", position);
                startActivityForResult(intent, 1000);
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh, int position) {

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
