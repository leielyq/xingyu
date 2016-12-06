package com.leielyq.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leielyq.bean.Item;
import com.leielyq.bean.ItemChild;
import com.leielyq.bean.MItem;
import com.leielyq.xingyu.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class MSendItemAdapter extends RecyclerView.Adapter<MSendItemAdapter.ViewHolder> {
    private List<Item> mdata;

    public MSendItemAdapter(List<Item> mdata) {
        this.mdata = mdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_item_list, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textname.setText(mdata.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textname;

        public ViewHolder(View itemView) {
            super(itemView);
            textname = (TextView) itemView.findViewById(R.id.send_item_list_title);
        }
    }
}
