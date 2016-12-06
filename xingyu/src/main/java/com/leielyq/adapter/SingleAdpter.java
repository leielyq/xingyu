package com.leielyq.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leielyq.bean.MItem;
import com.leielyq.xingyu.BuildConfig;
import com.leielyq.xingyu.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/26.
 */
public class SingleAdpter extends RecyclerView.Adapter<SingleAdpter.ViewHolder> {
    private List<MItem> mitems;
    private SparseArray mcheck = new SparseArray();
    private SparseArray mstates = new SparseArray();
    private Context mcontext;


    public SingleAdpter(Context mcontext, List<MItem> mitems) {
        this.mitems = mitems;
        this.mcontext = mcontext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textnum.setText((position + 1) + "/" + mitems.size());
        holder.textname.setText(mitems.get(position).getTitle());
        holder.radioButtonA.setText(mitems.get(position).getQ1());
        holder.radioButtonB.setText(mitems.get(position).getQ2());
        holder.radioButtonA.setTag(mitems.get(position).getN1());
        holder.radioButtonB.setTag(mitems.get(position).getN2());


        if (mitems.get(position).getQ3().equals("")) {
            holder.radioButtonC.setVisibility(View.INVISIBLE);
            holder.radioButtonD.setVisibility(View.INVISIBLE);
        } else {
            holder.radioButtonC.setText(mitems.get(position).getQ3());
            holder.radioButtonC.setTag(mitems.get(position).getN3());
            if (mitems.get(position).getQ4().equals("")) {
                holder.radioButtonD.setVisibility(View.INVISIBLE);
            } else {
                holder.radioButtonD.setText(mitems.get(position).getQ4());
                holder.radioButtonD.setTag(mitems.get(position).getN4());
                holder.radioButtonD.setVisibility(View.VISIBLE);
            }
        }

        holder.ivimg.setVisibility(View.INVISIBLE);

        if (mitems.get(position).getImg() != null) {
            Glide.with(mcontext).load(mitems.get(position).getImg().getUrl()).into(holder.ivimg);
            holder.ivimg.setVisibility(View.VISIBLE);
        }

        if (BuildConfig.DEBUG) Log.d("SingleAdpter", mstates.toString());

        holder.radioGroup.setOnCheckedChangeListener(null);

        if (mstates.get(position) == null || mstates.get(position) == "") {
            holder.radioGroup.clearCheck();
        } else {
            RadioButton rad = (RadioButton) holder.radioGroup.findViewById((Integer) mstates.get(position));
            rad.setChecked(true);
        }


        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radbtn = (RadioButton) group.findViewById(checkedId);
                mstates.put(position, checkedId);
                mcheck.put(position, radbtn.getTag());
            }
        });

    }

    public int getScore() {
        int score = 0;
        for (int i = 0; i < mcheck.size(); i++) {
            if (mcheck.get(i) == null || mcheck.get(i) == "") {
                return -1;
            } else {
                score += Integer.parseInt(mcheck.get(i).toString());
            }
        }
        return score;
    }

    @Override
    public int getItemCount() {
        return mitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textname;
        private RadioButton radioButtonA;
        private RadioButton radioButtonB;
        private RadioButton radioButtonC;
        private RadioButton radioButtonD;
        private RadioGroup radioGroup;
        private TextView textnum;
        private ImageView ivimg;

        public ViewHolder(View itemView) {
            super(itemView);
            textname = (TextView) itemView.findViewById(R.id.single_item_title);
            textnum = (TextView) itemView.findViewById(R.id.single_item_num);
            radioButtonA = (RadioButton) itemView.findViewById(R.id.radioButtonA);
            radioButtonB = (RadioButton) itemView.findViewById(R.id.radioButtonB);
            radioButtonC = (RadioButton) itemView.findViewById(R.id.radioButtonC);
            radioButtonD = (RadioButton) itemView.findViewById(R.id.radioButtonD);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.radiogroup);
            ivimg = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }
}
