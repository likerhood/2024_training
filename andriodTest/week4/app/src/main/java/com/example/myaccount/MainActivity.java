package com.example.myaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myaccount.DBHelper.UserDBHelper;
import com.example.myaccount.entity.UserInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textRegister;
    private UserDBHelper mUserDBHelper;
    private Button loginSettle;

    private EditText editPhoneNumber, editPassword;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserDBHelper = MyApplication.getInstance().mUserDBHelper;
        if(mUserDBHelper.queryAll().size() > 0){
            // 创建跳转到主页的意图
            Intent intent = new Intent(MainActivity.this, BillIndexActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mUserDBHelper.closeLink();
            startActivity(intent); // 启动主页
            return;
        }

        // 找到注册文本视图
        textRegister = findViewById(R.id.text_register);
        editPhoneNumber = findViewById(R.id.edit_phone);
        editPassword = findViewById(R.id.edit_password);
        loginSettle = findViewById(R.id.button_login);
        // 设置文本点击事件监听器
        textRegister.setOnClickListener(this);

        // 登录按钮
        loginSettle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.text_register){
            // 创建跳转到注册界面的意图
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent); // 启动注册界面
        } else if(v.getId() ==R.id.button_login){
            String phoneNumber = editPhoneNumber.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            // 检查输入是否合法
            if (phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请输入手机号和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            // 查询数据库中是否有该手机号
            List<UserInfo> userList = mUserDBHelper.queryByName(phoneNumber);
            if (userList.isEmpty()) {
                Toast.makeText(this, "手机号未注册", Toast.LENGTH_SHORT).show();
                return;
            }

            // 检查密码是否正确
            UserInfo user = userList.get(0);
            if (user.password.equals(password)) {
                // 密码正确，跳转到主页
                Intent intent = new Intent(MainActivity.this, BillIndexActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
//                finish(); // 关闭当前活动
            } else {
                // 密码错误，提示信息
                Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    }
}