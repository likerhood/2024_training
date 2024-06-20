package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class LoginForgetActivity extends AppCompatActivity implements View.OnClickListener {


    private String mPhone;      // 手机号
    private String mVerifycode; // 验证码
    private EditText etPassord1, etPassord2;    // 新密码
    private EditText et_verifycode;     // 输入的新的验证码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
        // 从上一个页面获取到需要修改密码的手机号
        mPhone = getIntent().getStringExtra("phone");
        TextView tv_username = findViewById(R.id.tv_username);
        tv_username.setText("用户"+mPhone);

        // 监听获取验证码按钮
        findViewById(R.id.btn_verifycode).setOnClickListener(this);

        // 获取文本编辑框的密码
        etPassord1 = findViewById(R.id.et_password_first);
        etPassord2 = findViewById(R.id.et_password_second);

        // 输入的验证码文本
        et_verifycode = findViewById(R.id.et_verifycode);

        // 确定修改密码按钮
        findViewById(R.id.btn_code_confirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_verifycode){
            // 生成验证码
            mVerifycode = String.format("%06d",new Random().nextInt(999999));
            // 弹出对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请记住验证码");
            builder.setMessage("手机号"+mPhone+"，本次验证码是"+mVerifycode+"，请输入验证码");
            builder.setPositiveButton("好的",null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (v.getId() == R.id.btn_code_confirm) {
            // 判断密码长度
            String newPwd1 = etPassord1.getText().toString();
            String newPwd2 = etPassord2.getText().toString();
            // Toast.makeText(this, "hhh",Toast.LENGTH_SHORT).show();
            if (newPwd1.length() < 6){
                Toast.makeText(this, "请输入正确的密码",Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPwd1.equals(newPwd2)){
                Toast.makeText(this, "两次输入密码不一致",Toast.LENGTH_SHORT).show();
                return;
            }

            if (!mVerifycode.equals(et_verifycode.getText().toString())){
                Toast.makeText(this, "验证码输入不正确",Toast.LENGTH_SHORT).show();
                return;
            }

            // 修改密码，把修改好的密码返回给上一个页面
            Intent intent = new Intent();
            intent.putExtra("new_password",newPwd1);
            setResult(Activity.RESULT_OK, intent);
            // 弹出提示信息
            // 弹出提醒对话框，提示用户记住六位验证码
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("密码修改成功");
            builder.setMessage("请返回到登录页面");
            // 返回登录界面
            builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}