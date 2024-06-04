package com.example.test;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.test.util.DateUtil;

public class buttonTestActivity extends AppCompatActivity {

    private TextView tv_result;
    public TextView tv_test;
    public Button btnTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_test);
        tv_result = findViewById(R.id.tv_result);
//      展示点击时间按钮

        Button btn_click_showTime = findViewById(R.id.bnt_show);
//        接口，setOnClickListener是view的一个内部类
        btn_click_showTime.setOnClickListener(new MyOnClickListener(tv_result));

//        打印信息的按钮
        Button btnClickPrintName = findViewById(R.id.bnt_print_name);
        btnClickPrintName.setOnClickListener(new publicButtonClick());
        Button btnClickPrintSchool = findViewById(R.id.bnt_print_school);
        btnClickPrintSchool.setOnClickListener(new publicButtonClick());

//        禁用和启用按钮
        Button btnEnable = findViewById(R.id.btn_enable);
        btnEnable.setOnClickListener(new publicButtonClick());
        Button btnDisable = findViewById(R.id.btn_disable);
        btnDisable.setOnClickListener(new publicButtonClick());

        tv_test = findViewById(R.id.tv_test);
        btnTest = findViewById(R.id.btn_test);
        btnTest.setOnClickListener(new publicButtonClick());


    }

//    内部类
    static class MyOnClickListener implements View.OnClickListener{
        private final TextView tv_result;
        public MyOnClickListener(TextView tv_result){
            this.tv_result = tv_result;
        }
        @Override
        public void onClick(View v) {
            String desc = String.format("%s 您点击了按钮 ：%s", DateUtil.getNowTime(), ((Button) v).getText());
            tv_result.setText(desc);
        }
    }

//    设置按钮点击的公共类实现
    protected class publicButtonClick implements  View.OnClickListener{

        @Override
        public void onClick(View v){
            if(v.getId() == R.id.bnt_print_name){
                Toast t = Toast.makeText(buttonTestActivity.this,"李珂你好",Toast.LENGTH_SHORT);
                t.show();
            }else if(v.getId() == R.id.bnt_print_school) {
                Toast t = Toast.makeText(buttonTestActivity.this, "武汉科技大学", Toast.LENGTH_SHORT);
                t.show();
            }else if(v.getId() == R.id.btn_enable){
//                启用按钮
                btnTest.setEnabled(true);
//                btnTest.setTextColor(Color.BLUE);
                btnTest.setTextColor(Color.BLACK);

            }else if(v.getId() == R.id.btn_disable){
//                  禁用
                btnTest.setEnabled(false);
                btnTest.setTextColor(Color.GRAY);
            }else if(v.getId() == R.id.btn_test){
//                测试
                String desc = String.format("%s 您点击了按钮 ：%s", DateUtil.getNowTime(), ((Button) v).getText());
                tv_test.setText(desc);
            }
        }
    }



}