package com.leielyq.send;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.myxingyu.R;
import com.leielyq.base.BaseFragment;
import com.leielyq.bean.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MItemFragment extends BaseFragment {
    @Bind(R.id.send_recy)
    RecyclerView sendRecy;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private SendItemAdapter mAdapter;
    private List<Item> mitems;

    public MItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mitem, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MSendItemActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

        mitems = new ArrayList<>();
        sendRecy.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SendItemAdapter(mitems);
        sendRecy.setAdapter(mAdapter);
        sendRecy.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(mActivity, MSendItemActivity.class);
                intent.putExtra("item", mitems.get(i));
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
                mitems.add((Item) data.getParcelableExtra("item"));
            else {
                mitems.remove(data.getIntExtra("id", -1));
                mitems.add(data.getIntExtra("id", -1), (Item) data.getParcelableExtra("item"));
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    public List<Item> ItemToString() {
        if (mitems.size() > 0)
            return mitems;
        else
            return null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
