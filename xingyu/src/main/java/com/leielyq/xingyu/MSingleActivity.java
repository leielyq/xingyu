package com.leielyq.xingyu;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.classic.common.MultipleStatusView;
import com.leielyq.adapter.MSingleAdapter;
import com.leielyq.base.BaseActivity;
import com.leielyq.bean.Content;
import com.leielyq.bean.Item;
import com.leielyq.bean.ItemChild;
import com.leielyq.bean.Result;
import com.leielyq.bean.Subject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MSingleActivity extends BaseActivity {

    @Bind(R.id.tltle)
    TextView tltle;
    @Bind(R.id.hp)
    TextView hp;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.iv_content)
    ImageView ivContent;
    @Bind(R.id.main_multiplestatusview)
    MultipleStatusView mainMultiplestatusview;
    @Bind(R.id.mtoolbar)
    Toolbar mtoolbar;
    @Bind(R.id.tv_num)
    TextView tvNum;
    private int mhp;
    private MSingleAdapter adapter;
    private List<ItemChild> itemChildList;
    private List<Item> items;
    private List<Result> results;
    private Content content;
    private int f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msingle);
        ButterKnife.bind(this);
        mainMultiplestatusview.showLoading();
        mtoolbar.setTitle("做题");
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        mhp = bundle.getInt("hp");
        hp.setText(mhp + "");
        BmobQuery<Content> query = new BmobQuery();
        Subject subject = new Subject();
        subject.setObjectId(bundle.getString("id"));
        query.addWhereEqualTo("subject", new BmobPointer(subject));
        query.findObjects(new FindListener<Content>() {
            @Override
            public void done(List<Content> list, BmobException e) {
                if (e == null) {
                    content = list.get(0);
                    items = content.getItems();
                    results = content.getResults();
                    itemChildList.addAll(items.get(f).getItemchildrens());
                    adapter.notifyDataSetChanged();
                    tltle.setText(items.get(f).getTitle());
                    if (items.get(f).getImg() != null) {
                        Glide.with(MSingleActivity.this).load(items.get(f).getImg().getUrl()).into(ivContent);
                    }
                    mainMultiplestatusview.showContent();
                } else {
                    Toast.makeText(MSingleActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    if (BuildConfig.DEBUG) Log.d("MSingleActivity", e.toString());
                }
            }
        });
        itemChildList = new ArrayList<>();
        adapter = new MSingleAdapter(itemChildList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
        recyclerview.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                f++;
                List<Integer> arr = adapter.getArr();
                mhp += arr.get(i);
                if (f >= items.size()) {
                    Toast.makeText(MSingleActivity.this, "mhp:" + mhp, Toast.LENGTH_SHORT).show();
                } else {
                    itemChildList.clear();
                    itemChildList.addAll(items.get(f).getItemchildrens());
                    adapter.notifyDataSetChanged();
                    tvNum.setText(f + "");
                    tltle.setText(items.get(f).getTitle());
                    if (items.get(f).getImg() != null) {
                        Glide.with(MSingleActivity.this).load(items.get(f).getImg().getUrl()).into(ivContent);
                        ivContent.setVisibility(View.VISIBLE);
                    } else {
                        ivContent.setVisibility(View.GONE);
                    }
                    if (mhp <= 0) {
                        Toast.makeText(MSingleActivity.this, "游戏结束", Toast.LENGTH_SHORT).show();
                    } else
                        hp.setText(mhp + "");
                }

            }
        });
    }
}
