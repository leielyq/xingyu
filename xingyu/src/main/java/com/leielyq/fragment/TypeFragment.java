package com.leielyq.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leielyq.OnItemTouchListener;
import com.leielyq.adapter.TypeAdapter;
import com.leielyq.bean.MyType;
import com.leielyq.xingyu.ListSingleActivity;
import com.leielyq.xingyu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends Fragment {


    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    private List<String> mdatas;
    private TypeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mdatas = new ArrayList<>();
        BmobQuery<MyType> query = new BmobQuery<>();
        query.findObjects(new FindListener<MyType>() {
            @Override
            public void done(List<MyType> list, BmobException e) {
                if (e == null) {
                    for (MyType i : list)
                        mdatas.add(i.getType());
                    adapter.notifyDataSetChanged();
                } else
                    Log.d("TitleFragment", e.toString());

            }
        });
        adapter = new TypeAdapter(mdatas);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerview.setHasFixedSize(true);
        recyclerview.setAdapter(adapter);

        recyclerview.addOnItemTouchListener(new OnItemTouchListener(recyclerview) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh, int position) {
                Intent intent = new Intent(getContext(), ListSingleActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("search", mdatas.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh, int position) {
                Toast.makeText(getContext(), "王，这是你的" + mdatas.get(position) + "江山", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
