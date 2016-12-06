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
import com.leielyq.adapter.MSendItemAdapter;
import com.leielyq.adapter.SendItemAdapter;
import com.leielyq.bean.Item;
import com.leielyq.bean.MItem;
import com.leielyq.xingyu.MSendItemActivity;
import com.leielyq.xingyu.R;
import com.leielyq.xingyu.SendItemActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MItemFragment extends Fragment {
    @Bind(R.id.send_recy)
    RecyclerView sendRecy;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private MSendItemAdapter mAdapter;
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
        mAdapter = new MSendItemAdapter(mitems);
        sendRecy.setAdapter(mAdapter);
        sendRecy.addOnItemTouchListener(new OnItemTouchListener(sendRecy) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
                Intent intent = new Intent(getContext(), MSendItemActivity.class);
                intent.putExtra("item", mitems.get(position));
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
