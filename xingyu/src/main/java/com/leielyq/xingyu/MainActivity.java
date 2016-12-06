package com.leielyq.xingyu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leielyq.adapter.TabFragmentAdapter;
import com.leielyq.bean.MyUser;
import com.leielyq.fragment.RankFragment;
import com.leielyq.fragment.RecentlyFragment;
import com.leielyq.fragment.RecommendFragment;
import com.leielyq.fragment.TypeFragment;
import com.leielyq.show.MainMainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.mtoolbar)
    Toolbar mtoolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] mtitles;
    private List<Fragment> mPagers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        View headview = navView.getHeaderView(0);
        TextView tv01 = (TextView) headview.findViewById(R.id.textView01);
        ImageView iv01 = (ImageView) headview.findViewById(R.id.imageView);
        mtoolbar.setTitle("首页");
        setSupportActionBar(mtoolbar);
        Bmob.initialize(this, "fb6f19fc34491e98673d2636d29cb164");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mtoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        initView();


       /* SharedPreferences sharedPreferences = this.getSharedPreferences("user", 0);
        tv01.setText(sharedPreferences.getString("username", ""));
        iv01.setImageURI(Uri.parse(sharedPreferences.getString("url", "")));*/
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        tv01.setText((String) MyUser.getObjectByKey("username"));
        if (user.getImg() != null)
            Glide.with(this).load(user.getImg().getUrl()).into(iv01);
    }

    public void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mtitles = new String[]{"分类 ", "推荐", "最新", "排行"};
        mPagers = new ArrayList<>();
        Fragment mType = new TypeFragment();
        Fragment mRecommend = new RecommendFragment();
        Fragment mRecently = new RecentlyFragment();
        Fragment mRank = new RankFragment();
        mPagers.add(mType);
        mPagers.add(mRecommend);
        mPagers.add(mRecently);
        mPagers.add(mRank);

        TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), mPagers, mtitles);
        viewPager.setAdapter(tabFragmentAdapter);
        viewPager.setCurrentItem(1);
        //设置缓存页面的数量
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_index) {
            // Handle the camera action
        } else if (id == R.id.nav_find) {
            Intent intent = new Intent(MainActivity.this, MineActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(MainActivity.this, SendActicity.class);
            startActivity(intent);
        } else if (id == R.id.nav_msend) {
            Intent intent = new Intent(MainActivity.this, MSendActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_main) {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_main2) {
            Intent intent = new Intent(MainActivity.this, MainMainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //设置搜索框hint内容
        mSearchView.setQueryHint("请输入。。。");
        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete)
                mSearchView.findViewById(R.id.search_src_text);
        //设置搜索框中输入文字的颜色
        textView.setTextColor(Color.WHITE);
        //搜索内容监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, ListSingleActivity.class);
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
}
