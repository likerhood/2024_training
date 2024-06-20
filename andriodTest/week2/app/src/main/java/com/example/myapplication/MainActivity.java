package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 跳转启动模式测试页面
        Button btnToSetupTest = findViewById(R.id.main_activity_setup);
        btnToSetupTest.setOnClickListener(this);
        // 登录活动启动模式设置
        findViewById(R.id.main_setup_login).setOnClickListener(this);
        // 隐式intent的测试
        findViewById(R.id.main_intent_test).setOnClickListener(this);
        // 数据传递测试
        findViewById(R.id.main_activity_datatest).setOnClickListener(this);
        // 元数据传递测试
        findViewById(R.id.main_metadata_test).setOnClickListener(this);
        // shape测试
        findViewById(R.id.main_shape_test).setOnClickListener(this);
        // 单选框测试
        findViewById(R.id.main_radio_test).setOnClickListener(this);
        // 登录测试
        findViewById(R.id.main_edit_test).setOnClickListener(this);
        // 日期测试
        findViewById(R.id.main_date_test).setOnClickListener(this);
        // 时间测试
        findViewById(R.id.main_time_test).setOnClickListener(this);
        // 登录案例
        findViewById(R.id.main_login_demo).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if(v.getId() == R.id.main_activity_setup){
            intent.setClass(MainActivity.this, ActivitySetupTest.class);
            startActivity(intent);
        }else if(v.getId() == R.id.main_setup_login){
            intent.setClass(MainActivity.this, LoginTestInputActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.main_intent_test){
            intent.setClass(MainActivity.this, ActionUriActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.main_activity_datatest){
            intent.setClass(MainActivity.this, ActSenActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_metadata_test) {
            intent.setClass(MainActivity.this, MetaDataActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_shape_test) {
            intent.setClass(MainActivity.this, DrawableShapeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_radio_test) {
            intent.setClass(MainActivity.this, RadioHorizontalActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_edit_test) {
            intent.setClass(MainActivity.this, EditSimpleActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_date_test) {
            intent.setClass(MainActivity.this, DatePickerActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_time_test) {
            intent.setClass(MainActivity.this, TimePickerActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.main_login_demo) {
            intent.setClass(MainActivity.this, LoginMainActivity.class);
            startActivity(intent);
        }
    }
}