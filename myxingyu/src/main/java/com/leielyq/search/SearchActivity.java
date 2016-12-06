package com.leielyq.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.myxingyu.R;
import com.leielyq.base.BaseInitActivity;

import me.yokeyword.fragmentation.SupportActivity;

public class SearchActivity extends BaseInitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String flag = getIntent().getStringExtra("flag");
        if (savedInstanceState == null)
            loadRootFragment(R.id.activity_search, SearchFragment.newInstance(flag));
    }
}
