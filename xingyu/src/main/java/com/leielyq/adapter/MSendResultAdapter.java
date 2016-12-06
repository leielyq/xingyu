package com.leielyq.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leielyq.bean.MResult;
import com.leielyq.bean.Result;
import com.leielyq.xingyu.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class MSendResultAdapter extends RecyclerView.Adapter<MSendResultAdapter.ViewHolder> {
    private List<Result> mdata;

    public MSendResultAdapter(List<Result> mdata) {
        this.mdata = mdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.send_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textname.setText(mdata.get(position).getResult());
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
