package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.regex.Pattern;

public class EditSimpleActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {

    private EditText et_phone, et_pwd;
    private TextView tv_alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_simple);
        et_phone = findViewById(R.id.et_phone);
        et_pwd = findViewById(R.id.et_password);
        et_pwd.setOnFocusChangeListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_alert).setOnClickListener(this);

        tv_alert = findViewById(R.id.tv_alert);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            String phone = et_phone.getText().toString();
            // 手机号码不足11位
            if ( TextUtils.isEmpty(phone) || phone.length() < 11) {
                // 手机号码编辑框请求焦点，把光标移到手机号码编辑处
                et_phone.requestFocus();
                // 提示
                Toast.makeText(this,"请输入11位手机号",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login){
            String pwd = et_pwd.getText().toString();
            if (!isValidPassword(pwd)) {
                Toast.makeText(this, "密码至少包含8位大小写字母和数字",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "密码符合格式",Toast.LENGTH_SHORT).show();
            }
        }else if (v.getId() == R.id.btn_alert){
            // 创建提醒对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // 设置对话框标题
            builder.setTitle("尊敬的用户");
            // 设置对话框的内容文本
            builder.setMessage("您真的要卸载我吗？");
            //设置对话框的肯定按钮文本以及监听器
            builder.setPositiveButton("残忍卸载", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tv_alert.setText("虽然依依不舍，但是只能离开了");
                }
            });

            // 设置对话框的否定按钮文本和点击监听器
            builder.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tv_alert.setText("让我再陪你365个日日夜夜");
                }
            });

            // 根据建造器构建题型对话框
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    // 设置判断密码格式的正则表达式
    private boolean isValidPassword(String password) {
        // 正则表达式：至少8个字符，包含大小写字母和数字
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        return !TextUtils.isEmpty(password) && pattern.matcher(password).matches();
    }

}