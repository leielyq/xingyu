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
import com.leielyq.bean.Result;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MSendResultActivity extends BaseActivity {

    @Bind(R.id.text_min)
    EditText textMin;
    @Bind(R.id.text_result)
    EditText textResult;
    @Bind(R.id.text_max)
    EditText textMax;
    @Bind(R.id.mtoolbar)
    Toolbar mtoolbar;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msend_result);
        ButterKnife.bind(this);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Result result = intent.getParcelableExtra("result");
        id = intent.getIntExtra("id", -1);
        if (result != null) {
            textMin.setText(result.getMin() + "");
            textMax.setText(result.getMax() + "");
            textResult.setText(result.getResult() + "");
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
            String str_result = textResult.getText().toString();
            String str_min = textMin.getText().toString();
            String str_max = textMax.getText().toString();
            if (str_max.equals("") || str_min.equals("") || str_result.equals(""))
                Toast.makeText(MSendResultActivity.this, "还没有输入完整的结果信息啦", Toast.LENGTH_SHORT).show();
            else {
                Result mresult = new Result();
                mresult.setMax(Integer.parseInt(str_max));
                mresult.setMin(Integer.parseInt(str_min));
                mresult.setResult(str_result);
                Intent intent = new Intent();
                intent.putExtra("jieguo", mresult);
                intent.putExtra("id", id);
                setResult(1001, intent);
                finish();
            }
        } else if (menuItemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
