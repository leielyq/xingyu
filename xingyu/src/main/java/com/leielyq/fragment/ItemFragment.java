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
import com.leielyq.adapter.SendItemAdapter;
import com.leielyq.bean.MItem;
import com.leielyq.xingyu.R;
import com.leielyq.xingyu.SendItemActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private SendItemAdapter mAdapter;
    private List<MItem> mitems;

    public ItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fab = (FloatingActionButton) getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SendItemActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

        mitems = new ArrayList<>();
        recyclerView = (RecyclerView) getView().findViewById(R.id.send_recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SendItemAdapter(mitems);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new OnItemTouchListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
                Intent intent = new Intent(getContext(), SendItemActivity.class);
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
                mitems.add((MItem) data.getParcelableExtra("info"));
            else {
                mitems.remove(data.getIntExtra("id", -1));
                mitems.add(data.getIntExtra("id", -1), (MItem) data.getParcelableExtra("info"));
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    public List<MItem> ItemToString() {
        if (mitems.size() > 0)
            return mitems;
        else
            return null;
    }

}
