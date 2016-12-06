package com.leielyq.send;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myxingyu.R;
import com.leielyq.bean.ItemChild;

import java.util.List;

/**
 * Created by Administrator on 2016/11/22 0022.
 */

public class SendItemSelectAdapter extends BaseQuickAdapter<ItemChild> {
    public SendItemSelectAdapter(List<ItemChild> data) {
        super(R.layout.list_send_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ItemChild itemChild) {
        baseViewHolder.setText(R.id.tv_title, itemChild.getSelect().toString());
    }


}
