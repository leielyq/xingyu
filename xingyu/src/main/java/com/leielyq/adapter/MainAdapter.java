package com.leielyq.adapter;


import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leielyq.bean.Content;
import com.leielyq.bean.Subject;
import com.leielyq.xingyu.BuildConfig;
import com.leielyq.xingyu.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/20 0020.
 */

public class MainAdapter extends BaseQuickAdapter<Subject> {
    private Context context;

    public MainAdapter(Context context, List<Subject> subjects) {
        super(R.layout.recomment_list, subjects);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, Subject subject) {
        baseViewHolder.setText(R.id.text_name, subject.getTitle())
                .setText(R.id.text_num, "总题量：" + subject.getNum())
                .setText(R.id.text_sender, "传题人：" + subject.getSender().getUsername())
                .setText(R.id.text_type, "类型：" + subject.getType());
        Glide.with(context).load(subject.getSender().getImg().getUrl()).into((ImageView) baseViewHolder.getView(R.id.iv_img));
    }
}
