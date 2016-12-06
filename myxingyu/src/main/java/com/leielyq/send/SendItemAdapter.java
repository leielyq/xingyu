package com.leielyq.send;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myxingyu.R;
import com.leielyq.bean.Item;

import java.util.List;

/**
 * Created by Administrator on 2016/11/22 0022.
 */

public class SendItemAdapter extends BaseQuickAdapter<Item> {


    public SendItemAdapter(List<Item> data) {
        super(R.layout.list_send_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Item item) {
        baseViewHolder.setText(R.id.tv_title, item.getTitle().toString());
    }


}
