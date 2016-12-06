package com.leielyq.search;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.myxingyu.R;
import com.leielyq.adapter.TabFragmentAdapter;
import com.leielyq.base.BaseFragment;
import com.leielyq.list.ListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment {


    @Bind(R.id.mtoolbar)
    Toolbar mtoolbar;
    @Bind(R.id.tl_main)
    TabLayout tlMain;
    @Bind(R.id.vp_fragment)
    ViewPager vpFragment;
    private String[] mtitles = new String[]{"测试", "娱乐", "用户"};

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString("flag", query);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_subject, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        final String query = bundle.getString("flag");
        List<Fragment> fragments = new ArrayList<Fragment>() {{
            add(ListFragment.newInstance(5, query));
            add(ListFragment.newInstance(6, query));
            add(ListFragment.newInstance(7, query));
        }};
        initToolbar(mtoolbar, query);
        _mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vpFragment.setAdapter(new TabFragmentAdapter(getChildFragmentManager(), fragments, mtitles));
        vpFragment.setOffscreenPageLimit(2);
        tlMain.setupWithViewPager(vpFragment);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            _mActivity.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
