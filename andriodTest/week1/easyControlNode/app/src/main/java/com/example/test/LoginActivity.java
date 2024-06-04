package com.example.test;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
{
    @SuppressLint("WrongViewCast")
    EditText user,pwo;
    Button button5,buttonR;
    TextView tvC;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        button5.setOnClickListener(new MyButtonListener());
    }
    @SuppressLint("WrongViewCast")
    private void initView ()
    {
        user = findViewById(R.id.user);
        pwo = findViewById(R.id.pwo);
        tvC = findViewById(R.id.tvRC);
        button5 = findViewById(R.id.button5);
        //点击注册跳转
        buttonR = findViewById(R.id.buttonR);
        buttonR.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    //显示登录信息
    public void Login(View view)
    {

    }

    class MyButtonListener implements View.OnClickListener
    {
        public void onClick(View view)
        {
            String name=user.getText().toString();
            String pow=pwo.getText().toString();
            tvC.setText("用户名:"+name+"密码:"+pow+"登录");

        }
    }
}