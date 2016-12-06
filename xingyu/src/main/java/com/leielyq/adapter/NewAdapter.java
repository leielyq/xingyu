package com.leielyq.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.leielyq.bean.MySubject;
import com.leielyq.xingyu.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/9/11 0011.
 */
public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHolder> {
    List<MySubject> mdatas = new ArrayList<>();

    public NewAdapter(List<MySubject> mdatas) {
        this.mdatas = mdatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recomment_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MySubject subject = mdatas.get(position);
        holder.textname.setText(subject.getMtitle());
        holder.textnum.setText("共" + subject.getNum().toString() + "题");
        holder.textsender.setText("上传者：" + subject.getSender());
        holder.texttype.setText("类型：" + subject.getType());
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textname;
        private TextView textnum;
        private TextView textsender;
        private TextView texttype;

        public ViewHolder(View itemView) {
            super(itemView);
            textname = (TextView) itemView.findViewById(R.id.text_name);
            textnum = (TextView) itemView.findViewById(R.id.text_num);
            textsender = (TextView) itemView.findViewById(R.id.text_sender);
            texttype = (TextView) itemView.findViewById(R.id.text_type);

        }
    }
}
