package com.leielyq.type;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.myxingyu.R;
import com.leielyq.base.BaseInitActivity;
import com.leielyq.list.ListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TypeListActivity extends BaseInitActivity {

    @Bind(R.id.mtoolbar)
    Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_list);
        ButterKnife.bind(this);
        String query = getIntent().getStringExtra("flag");
        mtoolbar.setTitle(query);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.activity_type_list, ListFragment.newInstance(8, query));
        }
    }


}
