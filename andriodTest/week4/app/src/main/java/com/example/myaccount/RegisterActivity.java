package com.example.myaccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myaccount.DBHelper.UserDBHelper;
import com.example.myaccount.entity.UserInfo;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private EditText editPhone;
    private EditText editPassword;
    private EditText editConfirmPassword;
    private EditText editVerificationCode;
    private TextView textLogin;
    private Button buttonRegister, btnGetVcode;

    private UserDBHelper mUserDBHelper;
    private String generatedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 初始化视图
        editPhone = findViewById(R.id.edit_phone_register);
        editPassword = findViewById(R.id.edit_password_register);
        editConfirmPassword = findViewById(R.id.edit_confirm_password);
        editVerificationCode = findViewById(R.id.edit_verification_code);
        textLogin = findViewById(R.id.to_login);
        buttonRegister = findViewById(R.id.button_register);
        btnGetVcode = findViewById(R.id.button_get_verification_code);

        // 初始化数据库帮助类
        mUserDBHelper = MyApplication.getInstance().mUserDBHelper;

        // 设置点击事件
        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                startActivity(intent);
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        btnGetVcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateVerificationCode();
            }
        });

    }


    private void registerUser() {
        String phone = editPhone.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editConfirmPassword.getText().toString().trim();
        String verificationCode = editVerificationCode.getText().toString().trim();

        if (phone.isEmpty()) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "请输入密码和确认密码", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        if (verificationCode.isEmpty()) {
            Toast.makeText(this, "请输入验证码" + generatedCode, Toast.LENGTH_SHORT).show();
            return;
        } else if (!verificationCode.equals(generatedCode)) {
            Toast.makeText(this, "验证码不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        // 插入用户信息到数据库
        UserInfo user = new UserInfo();
        user.username = "user";
        user.phoneNumber = phone;
        user.password = password;

        mUserDBHelper.openWriteLink();
        long result = mUserDBHelper.insert(user);

        if (result > 0) {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "注册失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateVerificationCode() {
        String phone = editPhone.getText().toString().trim();
        // 生成6位随机验证码
        generatedCode = String.format("%06d", new Random().nextInt(999999));
        // 弹出提醒对话框，提示用户记住六位验证码
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请记住验证码");
        builder.setMessage("手机号"+phone+",本次验证码是"+generatedCode+"请输入验证码");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
