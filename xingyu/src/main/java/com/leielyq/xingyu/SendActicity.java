package com.leielyq.xingyu;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.leielyq.adapter.TabFragmentAdapter;
import com.leielyq.bean.MItem;
import com.leielyq.bean.MySubject;
import com.leielyq.bean.MyUser;
import com.leielyq.fragment.ItemFragment;
import com.leielyq.fragment.ResultFragment;
import com.leielyq.fragment.TitleFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SendActicity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] mtitles;
    private List<Fragment> mPagers;
    private ItemFragment mitem;
    private TitleFragment mtitle;
    private ResultFragment mresult;
    private SweetAlertDialog pDialog;
    private SystemBarTintManager tintManager;
    private int mcheck1 = 0;
    private int mcheck2 = 0;
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_main);
        initview();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("出题");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.colorPrimary));
            tintManager.setStatusBarTintEnabled(true);
        }

        /*SharedPreferences sharedPreferences = this.getSharedPreferences("user", 0);
        *//*username = sharedPreferences.getString("username", "");*/
        username = (String) MyUser.getObjectByKey("username");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void initview() {
        tabLayout = (TabLayout) findViewById(R.id.send_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.send_viewPager);
        mtitles = new String[]{"标题", "小题", "结果"};
        mPagers = new ArrayList<>();
        mtitle = new TitleFragment();
        mitem = new ItemFragment();
        mresult = new ResultFragment();
        mPagers.add(mtitle);
        mPagers.add(mitem);
        mPagers.add(mresult);
        TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), mPagers, mtitles);
        viewPager.setAdapter(tabFragmentAdapter);
        //设置缓存页面的数量
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_search:
                if (mtitle.TitleToString() == null) {
                    Toast.makeText(this, "取一个好听，响亮的名字以及好好介绍一下你的题目", Toast.LENGTH_SHORT).show();
                    break;
                } else if (mitem.ItemToString() == null) {
                    Toast.makeText(this, "还没有一个题目哦", Toast.LENGTH_SHORT).show();
                    break;
                } else if (mresult.ResultToString() == null) {
                    Toast.makeText(this, "别人做了题目后肯定需要一个结果啊", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    pDialog = new SweetAlertDialog(SendActicity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("上传中");
                    pDialog.setCancelable(true);
                    pDialog.show();
                    final MySubject mySubject = new MySubject();
                    mySubject.setMtitle(mtitle.TitleToString());
                    mySubject.setType(mtitle.TypeToString());
                    mySubject.setMcontext(mtitle.ContextToString());
                    mySubject.setMresult(mresult.ResultToString());
                    mySubject.setNum(mitem.ItemToString().size());
                    mySubject.setSender(username);
                    mcheck1 = mitem.ItemToString().size();
                    for (final MItem i : mitem.ItemToString()) {
                        if (i.getLoactimg() == null) {
                            mcheck2++;
                            if (mcheck1 == mcheck2) {
                                mySubject.setMitem(mitem.ItemToString());
                                mySubject.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            pDialog.dismiss();
                                            Toast.makeText(SendActicity.this, "成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            pDialog.dismiss();
                                            Toast.makeText(SendActicity.this, "失败", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                            continue;
                        }
                        final BmobFile file = new BmobFile(i.getLoactimg());
                        file.uploadblock(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {

                                if (e == null) {
                                    i.setImg(file);
                                    if (mcheck1 == mcheck2) {
                                        mySubject.setMitem(mitem.ItemToString());
                                        mySubject.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e == null) {
                                                    pDialog.dismiss();
                                                    Toast.makeText(SendActicity.this, "66", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    pDialog.dismiss();
                                                    Toast.makeText(SendActicity.this, "55", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }

                }
        }
        return super.onOptionsItemSelected(item);
    }

}
