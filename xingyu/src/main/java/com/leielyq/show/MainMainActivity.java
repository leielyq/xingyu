package com.leielyq.show;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.leielyq.base.BaseActivity;
import com.leielyq.chat.ChatFragment;
import com.leielyq.find.FindFragment;
import com.leielyq.mine.MineFragment;
import com.leielyq.xingyu.ListSingleActivity;
import com.leielyq.xingyu.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainMainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    @Bind(R.id.mtoolbar)
    Toolbar mtoolbar;
    @Bind(R.id.bnb_main)
    BottomNavigationBar bnbMain;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
        ButterKnife.bind(this);
        /*mtoolbar.setTitle("星语");
        setSupportActionBar(mtoolbar);
*/
        bnbMain.addItem(new BottomNavigationItem(R.drawable.ic_home, "主页").setActiveColor(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.ic_plus_one, "加一").setActiveColor(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.ic_done, "发现").setActiveColor(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.drawable.ic_my, "我的").setActiveColor(R.color.colorAccent))
                .setFirstSelectedPosition(0)
                .initialise();

        fragments = getFragments();
        setDefaultFragment();
        bnbMain.setTabSelectedListener(this);

    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.ll, new MainFragment());
        transaction.commit();
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ChatFragment());
        fragments.add(new FindFragment());
        fragments.add(new ChatFragment());
        fragments.add(new MineFragment());
        return fragments;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //设置搜索框hint内容
        mSearchView.setQueryHint("测试");
        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete)
                mSearchView.findViewById(R.id.search_src_text);
        //设置搜索框中输入文字的颜色
        textView.setTextColor(Color.WHITE);
        //搜索内容监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainMainActivity.this, ListSingleActivity.class);
                intent.putExtra("search", query);
                intent.putExtra("type", -1);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    ft.show(fragment);
                } else {
                    ft.add(R.id.ll, fragment).show(fragment);
                }
                ft.commit();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.hide(fragment);
                ft.commit();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }
}
