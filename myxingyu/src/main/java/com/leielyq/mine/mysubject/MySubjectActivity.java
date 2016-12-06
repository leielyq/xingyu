package com.leielyq.mine.mysubject;

import android.os.Bundle;

import com.example.myxingyu.R;
import com.leielyq.base.BaseActivity;

import me.yokeyword.fragmentation.SupportActivity;

public class MySubjectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subject);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.activity_my_subject, MySubjectFragment.newInstance());
        }
    }
}
