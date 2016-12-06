package com.leielyq.adapter;

import android.app.Activity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myxingyu.BuildConfig;
import com.example.myxingyu.R;
import com.leielyq.bean.Item;
import com.leielyq.bean.ItemChild;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.List;

/**
 * Created by Administrator on 2016/11/23 0023.
 */

public class SingleAdapter extends BaseQuickAdapter<Item> {
    private Activity mActivity;
    private RecyclerViewPager viewpager;
    private SparseArray mcheck = new SparseArray();
    private SparseArray mstates = new SparseArray();

    public SingleAdapter(Activity mActivity, List<Item> data, RecyclerViewPager viewpager) {
        super(R.layout.list_single_item, data);
        this.mActivity = mActivity;
        this.viewpager = viewpager;
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, Item item) {
        final int position = baseViewHolder.getLayoutPosition();
        baseViewHolder.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_num, position + 1 + "");
        ImageView iv = baseViewHolder.getView(R.id.iv_content);
        iv.setVisibility(View.INVISIBLE);
        if (item.getImg() != null) {
            Glide.with(mActivity).load(item.getImg().getUrl()).into(iv);
            iv.setVisibility(View.VISIBLE);
        }
        RadioGroup rg = baseViewHolder.getView(R.id.rg_main);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (rg.getChildCount() == 0) {
            int j = 0;
            for (ItemChild i : item.getItemchildrens()) {
                RadioButton tempButton = new RadioButton(mActivity);
                tempButton.setId(j);
                j++;
                tempButton.setText(i.getSelect());
                //tempButton.setBackgroundResource(R.drawable.xxx);   // 设置RadioButton的背景图片
                //tempButton.setButtonDrawable(R.drawable.banjiao1);           // 设置按钮的样式
                //tempButton.setPadding(80, 0, 0, 0);                 // 设置文字距离按钮四周的距离
                tempButton.setTag(i.getSocre());
                rg.addView(tempButton, lp);
            }
        }

        rg.setOnCheckedChangeListener(null);

        if (mstates.get(position) == null || mstates.get(position) == "") {
            rg.clearCheck();
        } else {
            RadioButton rad = (RadioButton) rg.findViewById((Integer) mstates.get(position));
            rad.setChecked(true);
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radbtn = (RadioButton) group.findViewById(checkedId);
                mstates.put(position, checkedId);
                mcheck.put(position, radbtn.getTag());
                viewpager.smoothScrollToPosition(position + 1);
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
}
