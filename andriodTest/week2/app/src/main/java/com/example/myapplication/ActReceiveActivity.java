package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Utils.DateUtil;

public class ActReceiveActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_receive;
    private static final String res = "我马上回家";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_receive);
        tv_receive = findViewById(R.id.tv_receive);
        //从上一个页面传来的意图中获取快递包裹
        Bundle bundle = getIntent().getExtras();
        String request_time = bundle.getString("request_time");
        String request_content = bundle.getString("request_content");
        String desc = String.format("收到请求消息：\n请求时间为%s\n 请求内容为：%s",request_time,request_content);
        tv_receive.setText(desc);

        // 按钮发送数据
        findViewById(R.id.btn_rvsend).setOnClickListener(this);
        TextView response = findViewById(R.id.tv_rvsend);
        response.setText("待返回的消息：" + res);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("response_time", DateUtil.getNowTime());
        bundle.putString("response_content", res);
        // 注意要将bundle放到新建的intent中
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}