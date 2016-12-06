package com.leielyq.show;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leielyq.base.BaseFragment;
import com.leielyq.xingyu.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    @Bind(R.id.tl_main)
    TabLayout tlMain;
    @Bind(R.id.vp_fragment)
    ViewPager vpFragment;
    private List<BaseFragment> fragments = new ArrayList<BaseFragment>() {{
        add(new ListRankFragment());
        add(new ListRecentlyFragment());
        add(new ListRecommendFragment());
        add(new TypeFragment());
    }};
    private String[] mtitles = new String[]{"分类 ", "推荐", "最新", "排行"};
    private MainTabFragmentAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {
        adapter = new MainTabFragmentAdapter(getChildFragmentManager(), fragments, mtitles);
        vpFragment.setAdapter(adapter);
        /*
        * onCreateView每次调用导致fragment每次都会设置新的view，
        * 并且之前的view没有被回收……这就导致了，新的view覆盖了之前设置的view
        * 设置viewpager.setOffscreenPageLimit(tabs);tabs为tab数
        * 2016.11.03 --leielyq
        * */
        vpFragment.setOffscreenPageLimit(4);
        vpFragment.setCurrentItem(1);
        tlMain.setupWithViewPager(vpFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
