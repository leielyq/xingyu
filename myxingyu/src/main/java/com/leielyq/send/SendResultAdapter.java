package com.leielyq.send;

import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myxingyu.BuildConfig;
import com.example.myxingyu.R;
import com.leielyq.bean.Item;
import com.leielyq.bean.Result;

import java.util.List;

/**
 * Created by Administrator on 2016/11/22 0022.
 */

public class SendResultAdapter extends BaseQuickAdapter<Result> {


    public SendResultAdapter(List<Result> data) {
        super(R.layout.list_send_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Result result) {
        baseViewHolder.setText(R.id.tv_title, result.getResult() + "");
        if (BuildConfig.DEBUG) Log.d("lllllll", "fff");
    }
}
