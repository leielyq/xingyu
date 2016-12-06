package com.leielyq.send;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myxingyu.R;
import com.leielyq.base.BaseActivity;
import com.leielyq.bean.ItemChild;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MSendItemSelectActivity extends BaseActivity {

    @Bind(R.id.select)
    EditText select;
    @Bind(R.id.socre)
    EditText socre;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msend_item_select);
        Toolbar mtoolbar = (Toolbar) findViewById(R.id.mtoolbar);
        mtoolbar.setTitle("选项和分值");
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        ItemChild child = intent.getParcelableExtra("itemchild");
        id = intent.getIntExtra("id", -1);
        if (child != null) {
            select.setText(child.getSelect());
            socre.setText(child.getSocre().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        if (menuItemId == R.id.nav_search) {
            String mselect = select.getText().toString();
            String msocre = socre.getText().toString();
            if (mselect.equals("") || msocre.equals(""))
                Toast.makeText(MSendItemSelectActivity.this, "还没有输入完整的结果信息啦", Toast.LENGTH_SHORT).show();
            else {
                ItemChild mitem = new ItemChild();
                mitem.setSelect(mselect);
                mitem.setSocre(Integer.parseInt(msocre));
                Intent intent = new Intent();
                intent.putExtra("itemchild", mitem);
                intent.putExtra("id", id);
                setResult(1003, intent);
                finish();
            }
        } else if (menuItemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
