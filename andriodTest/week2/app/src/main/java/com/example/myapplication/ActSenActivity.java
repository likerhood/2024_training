package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Utils.DateUtil;

public class ActSenActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_send, tv_response;
    private String mRequest = "你什么时候回家";
    private ActivityResultLauncher<Intent> register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sen);
        tv_send = findViewById(R.id.tv_send);
        tv_response = findViewById(R.id.tv_sdreceive);
        tv_send.setText(mRequest);
        findViewById(R.id.btn_send).setOnClickListener(this);

        // 重写一下接受数据的
       register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result != null){
                Intent intent = result.getData();
                if(intent != null && result.getResultCode() == ActSenActivity.RESULT_OK){
                    Bundle bundle = intent.getExtras();
                    String request_time = bundle.getString("response_time");
                    String request_content = bundle.getString("response_content");
                    String desc = String.format("收到返回消息：\n应答时间为%s\n应答内容为%s",request_time,request_content);
                    //显示返回详情的文字
                    tv_response.setText(desc);
                }
            }
       });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ActReceiveActivity.class);
        Bundle bundle = new Bundle();
        // 创建一个新包裹
        bundle.putString("request_time", DateUtil.getNowTime());
        bundle.putString("request_content", mRequest);
        intent.putExtras(bundle);

        // 转到下一个页面之后，也要右一个接口来更新下一个activity传过来的数据
        // startActivityForResult(); 该方法已经被弃用
        // 用全局变量register的launch方法跳转
        register.launch(intent);

        //startActivity(intent);

    }
}