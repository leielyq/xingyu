package com.leielyq.xingyu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.leielyq.bean.MResult;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SendResultActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.text_min)
    EditText et_min;
    @Bind(R.id.text_result)
    EditText et_result;
    @Bind(R.id.text_max)
    EditText et_max;
    private SystemBarTintManager tintManager;
    private int id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_result);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.colorPrimary));
            tintManager.setStatusBarTintEnabled(true);
        }
        toolbar.setTitle("结果");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        MResult result = intent.getParcelableExtra("result");
        id = intent.getIntExtra("id", -1);
        if (result != null) {
            et_min.setText(result.getMin());
            et_max.setText(result.getMax());
            et_result.setText(result.getResult());
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
            String str_result = et_result.getText().toString();
            String str_min = et_min.getText().toString();
            String str_max = et_max.getText().toString();
            if (str_max.equals("") && str_min.equals("") && str_result.equals(""))
                Toast.makeText(SendResultActivity.this, "还没有输入完整的结果信息啦", Toast.LENGTH_SHORT).show();
            else {
                MResult mresult = new MResult();
                mresult.setMax(str_max);
                mresult.setMin(str_min);
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
