package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActionUriActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_uri);
        findViewById(R.id.btn_dial).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String phoneNo = "12345";
        Intent intent = new Intent();
        if(v.getId() == R.id.btn_dial){
            // 设置意图动作
            intent.setAction(Intent.ACTION_DIAL);
            // 声明一个拨号的Uri
            Uri uri = Uri.parse("tel:" + phoneNo);
            intent.setData(uri);
            startActivity(intent);
        }else if(v.getId() == R.id.btn_sms){
            // 设置意图动作
            intent.setAction(Intent.ACTION_SENDTO);
            // 声明一个拨号的Uri
            Uri uri2 = Uri.parse("smsto:" + phoneNo);
            intent.setData(uri2);
            startActivity(intent);
        }

    }
}