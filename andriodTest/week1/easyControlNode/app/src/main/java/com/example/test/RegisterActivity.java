package com.example.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class RegisterActivity extends AppCompatActivity {
    EditText user, pwo, rpw, uname;
    Button buttonR, buttonB;
    RadioGroup sexq;
    CheckBox checkBox3;
    String sex="男";

    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        {
            buttonB = findViewById(R.id.buttonB);
            buttonR = findViewById(R.id.buttonR);
            uname = findViewById(R.id.uname);
            pwo = findViewById(R.id.pwo);
            rpw = findViewById(R.id.rpw);
            user = findViewById(R.id.user);
            sexq = findViewById(R.id.sexq);
            checkBox3 = findViewById(R.id.checkBox3);
            sexq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i)
                {
                    if(radioGroup.getCheckedRadioButtonId() == R.id.ButtonM){
                        sex = "男";
                    }else if(radioGroup.getCheckedRadioButtonId() == R.id.ButtonW){
                        sex = "女";
                    }
//                    switch (radioGroup.getCheckedRadioButtonId())
//                    {
//                        case R.id.ButtonM:
//                            sex = "男";
//                            break;
//                        case R.id.ButtonW:
//                            sex = "女";
//                            break;
//                    }
                }
            });
            buttonR.setOnClickListener(new View.OnClickListener()
            {
                @Override

                public void onClick(View view)
                {
                    if (checkBox3.isChecked())
                    {
                        if (rpw.getText().toString().trim().equals("") || uname.getText().toString().trim().equals("") || pwo.getText().toString().trim().equals("") || user.getText().toString().trim().equals(""))
                        {
                            Toast.makeText(RegisterActivity.this, "请填写所有内容", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (!rpw.getText().toString().trim().equals(pwo.getText().toString().trim()))
                        {
                            Toast.makeText(RegisterActivity.this, "两次密码输入不一致，请重新输入", Toast.LENGTH_LONG).show();
                            return;
                        }
                        String res = "欢迎你，你的注册信息如下：\n" + "用户名：" + user.getText().toString().trim() + "\n密码：" + pwo.getText().toString().trim() + "\n姓名：" + uname.getText().toString().trim() + "\n性别" + sex + "\n";
                        Toast.makeText(RegisterActivity.this, res, Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "请阅读会员协议并勾选", Toast.LENGTH_LONG).show();
                    }

                }
            });
            //点击返回跳转登录界面
            buttonB = findViewById(R.id.buttonB);
            buttonB.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    finish();
                }
            });
        }

    }
}