package com.leielyq.send;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myxingyu.BuildConfig;
import com.example.myxingyu.R;
import com.leielyq.adapter.TabFragmentAdapter;
import com.leielyq.base.BaseActivity;
import com.leielyq.bean.Content;
import com.leielyq.bean.Item;
import com.leielyq.bean.MyUser;
import com.leielyq.bean.Subject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.yokeyword.fragmentation.SupportActivity;

public class MSendActivity extends BaseActivity {
    @Bind(R.id.mtoolbar)
    Toolbar mtoolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] mtitles;
    private List<Fragment> mPagers;
    private MItemFragment mitem;
    private MTitleFragment mtitle;
    private MResultFragment mresult;
    private SweetAlertDialog pDialog;
    private int mcheck1 = 0;
    private int mcheck2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msend);
        ButterKnife.bind(this);
        mtoolbar.setTitle("出题");
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initview();
    }

    private void initview() {
        tabLayout = (TabLayout) findViewById(R.id.send_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.send_viewPager);
        mtitles = new String[]{"标题", "小题", "结果"};
        mPagers = new ArrayList<>();
        mtitle = new MTitleFragment();
        mitem = new MItemFragment();
        mresult = new MResultFragment();
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
                    pDialog = new SweetAlertDialog(MSendActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("上传中");
                    pDialog.setCancelable(true);
                    pDialog.show();
                    final Subject subject = new Subject();
                    subject.setTitle(mtitle.TitleToString());
                    subject.setContext(mtitle.ContextToString());
                    subject.setType(mtitle.TypeToString());
                    subject.setSender(BmobUser.getCurrentUser(MyUser.class));
                    subject.setNum(mitem.ItemToString().size());
                    subject.setHp(mtitle.HpToString());
                    subject.setSendername(BmobUser.getCurrentUser().getUsername());
                    //1为测试，2为娱乐
                    if (mtitle.HpToString() == null) subject.setMold(1);
                    else subject.setMold(2);
                    subject.setRecommend(false);
                    subject.setRank(1);
                    final List<Item> itemList = mitem.ItemToString();
                    mcheck1 = mitem.ItemToString().size();
                    mcheck2 = 0;
                    final Content content = new Content();
                    content.setResults(mresult.ResultToString());
                    for (final Item i : itemList) {
                        if (i.getLoactimg() == null) {
                            mcheck2++;
                            pDialog.setTitleText("上传中:" + mcheck2 + "/" + mcheck1);
                            if (mcheck1 == mcheck2) {
                                content.setItems(itemList);
                                subject.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            content.setSubject(subject);
                                            content.save(new SaveListener<String>() {
                                                @Override
                                                public void done(String s, BmobException e) {
                                                    if (e == null) {
                                                        pDialog.dismiss();
                                                        Toast.makeText(MSendActivity.this, "成功", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        pDialog.dismiss();
                                                        Toast.makeText(MSendActivity.this, "失败", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                            pDialog.dismiss();
                                            Toast.makeText(MSendActivity.this, "成功", Toast.LENGTH_SHORT).show();
                                        } else {
                                            pDialog.dismiss();
                                            Toast.makeText(MSendActivity.this, "失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            continue;
                        }
                        final BmobFile file = new BmobFile(i.getLoactimg());
                        if (BuildConfig.DEBUG) Log.d("MSendActivity", mcheck2 + "上传图片" + mcheck1);
                        file.uploadblock(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    mcheck2++;
                                    pDialog.setTitleText("上传中:" + mcheck2 + "/" + mcheck1);
                                    i.setImg(file);

                                    if (mcheck1 == mcheck2) {
                                        content.setItems(itemList);
                                        subject.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e == null) {
                                                    content.setSubject(subject);
                                                    content.save(new SaveListener<String>() {
                                                        @Override
                                                        public void done(String s, BmobException e) {
                                                            if (e == null) {
                                                                subject.setContent(content);
                                                                pDialog.dismiss();
                                                                Toast.makeText(MSendActivity.this, "成功", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                pDialog.dismiss();
                                                                Toast.makeText(MSendActivity.this, "失败", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                    pDialog.dismiss();
                                                    Toast.makeText(MSendActivity.this, "成功", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    pDialog.dismiss();
                                                    Toast.makeText(MSendActivity.this, "失败", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }

                }
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
