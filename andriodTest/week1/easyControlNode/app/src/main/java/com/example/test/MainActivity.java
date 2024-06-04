package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     // 继承
        setContentView(R.layout.activity_main);

//        按钮一，跳转到按钮示例页面
        Button buttonTest = findViewById(R.id.bnt_click_to_buttonTest);
//        匿名类
        buttonTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, buttonTestActivity.class);
                startActivity(intent);
            }
        });

//        按钮二，跳转到计算器页面
        Button toCalculator = findViewById(R.id.bnt_to_calculator);
        toCalculator.setOnClickListener(this);

        // 按钮三，生命周期测试
        Button toLifeCircle = findViewById(R.id.bnt_to_lifyCircle);
        toLifeCircle.setOnClickListener(this);

        // 按钮四，登录
        Button toLogin = findViewById(R.id.bnt_to_login);
        toLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {    //重写方法
        Intent intent = new Intent();
        if(v.getId() == R.id.bnt_to_calculator){
            intent.setClass(MainActivity.this, CalculatorActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.bnt_to_lifyCircle){
            intent.setClass(MainActivity.this, LifeCircle.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.bnt_to_login){
            intent.setClass(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }



}