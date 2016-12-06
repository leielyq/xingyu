package com.leielyq.show;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.myxingyu.R;
import com.leielyq.adapter.TabFragmentAdapter;
import com.leielyq.base.BaseFragment;
import com.leielyq.list.ListFragment;
import com.leielyq.search.SearchActivity;
import com.leielyq.send.MSendActivity;
import com.leielyq.type.TypeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {


    @Bind(R.id.tl_main)
    TabLayout tlMain;
    @Bind(R.id.vp_fragment)
    ViewPager vpFragment;
    @Bind(R.id.mtoolbar)
    Toolbar mtoolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;


    public MainFragment() {
    }

    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar(mtoolbar, "首页");
        mtoolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(item);
                SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete)
                        mSearchView.findViewById(R.id.search_src_text);
                //设置搜索框中输入文字的颜色
                textView.setTextColor(Color.WHITE);
                //搜索内容监听
                mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Intent intent = new Intent(_mActivity, SearchActivity.class);
                        intent.putExtra("flag", query);
                        startActivity(intent);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
                return false;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);
    }

    public void initView() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TypeFragment());
        fragments.add(ListFragment.newInstance(3));
        fragments.add(ListFragment.newInstance(0));
        fragments.add(ListFragment.newInstance(4));
        String[] strings = new String[]{"分类", "推荐", "最新", "排行"};
       /*
        * onCreateView每次调用导致fragment每次都会设置新的view，
        * 并且之前的view没有被回收……这就导致了，新的view覆盖了之前设置的view
        * 设置viewpager.setOffscreenPageLimit(tabs);tabs为tab数
        * 2016.11.03 --leielyq
        * */

        vpFragment.setAdapter(new TabFragmentAdapter(getChildFragmentManager(), fragments, strings));
        vpFragment.setOffscreenPageLimit(4);
        vpFragment.setCurrentItem(1);
        tlMain.setupWithViewPager(vpFragment);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, MSendActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
