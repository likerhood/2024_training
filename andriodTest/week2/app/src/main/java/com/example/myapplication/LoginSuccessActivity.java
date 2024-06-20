package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        // 接收上一个页面传送过来的信息
        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        String desc = String.format("欢迎%s用户来到主页",username);
        // 显示文本
        TextView tv_show = findViewById(R.id.tv_login_success);
        tv_show.setText(desc);
    }
}