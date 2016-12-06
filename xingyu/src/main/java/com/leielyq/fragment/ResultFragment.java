package com.leielyq.fragment;


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
import android.widget.Toast;

import com.leielyq.OnItemTouchListener;
import com.leielyq.adapter.SendItemAdapter;
import com.leielyq.adapter.SendResultAdapter;
import com.leielyq.bean.MItem;
import com.leielyq.bean.MResult;
import com.leielyq.xingyu.R;
import com.leielyq.xingyu.SendItemActivity;
import com.leielyq.xingyu.SendResultActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private SendResultAdapter mAdapter;
    private List<MResult> mresults;

    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SendResultActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
        mresults = new ArrayList<>();
        recyclerView = (RecyclerView) getView().findViewById(R.id.send_title_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SendResultAdapter(mresults);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new OnItemTouchListener(recyclerView) {
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
                mresults.add((MResult) data.getParcelableExtra("jieguo"));
            else {
                mresults.remove(data.getIntExtra("id", -1));
                mresults.add(data.getIntExtra("id", -1), (MResult) data.getParcelableExtra("jieguo"));
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    public List<MResult> ResultToString() {
        if (mresults.size() > 0)
            return mresults;
        else
            return null;
    }
}
