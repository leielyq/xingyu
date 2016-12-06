package com.leielyq.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leielyq.bean.ItemChild;
import com.leielyq.xingyu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/1 0001.
 */

public class MSingleAdapter extends BaseQuickAdapter<ItemChild> {
    private List<Integer> arr;

    public MSingleAdapter(List<ItemChild> data) {
        super(R.layout.send_item_list, data);
        arr = new ArrayList<>();
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ItemChild item) {

        baseViewHolder.setText(R.id.send_item_list_title, item.getSelect())
                .setTag(R.id.send_item_list_title, item.getSocre());
        arr.add(item.getSocre());
    }

    public List<Integer> getArr() {
        return arr;
    }

}
