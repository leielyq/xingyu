package com.leielyq.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myxingyu.R;
import com.leielyq.bean.MyType;

import java.util.List;

/**
 * Created by Administrator on 2016/11/28 0028.
 */

public class TypeAdapter extends BaseQuickAdapter<MyType> {
    public TypeAdapter(List<MyType> data) {
        super(R.layout.send_item_list, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MyType myType) {
        baseViewHolder.setText(R.id.send_item_list_title, myType.getType());
    }
}
